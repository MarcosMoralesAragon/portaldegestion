import modelos.Direccion;
import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.Fecha;
import utilidades.GestionFicheros;
import utilidades.Prints;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servicios {

    private static final int ESPACIOS = 3;
    public static final ArrayList<Empleado> empleados = new ArrayList<>();
    public static final HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void crear(Scanner in, String palabra) {

        if (palabra.equals("cargar")){
            cargarLista(in);
        } else {
            System.out.println("1. Crear");

            Empleado variableEmpleado = new Empleado(null);

            datosEmpleadosPorTeclado( in, variableEmpleado);
            variableEmpleado.setDireccion(datosDireccionPorTeclado(in));

            try {
                GestionFicheros.escribirFichero("empleados.txt", ("\n" + variableEmpleado.cadenaConAlmoadilla()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            generarCodigo(variableEmpleado);

            Prints.saltoLinea();
            Prints.terminadaAccion();
            Prints.limpiar(ESPACIOS);
        }
    }

    public static void listado(String numero) {

        System.out.println(numero + "Listado");
        Prints.separador();
        Prints.saltoLinea();
        for ( int i = 0; i < empleados.size(); i++){
            System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i));
        }
        Prints.saltoLinea();
        if (!numero.equals("")){
            Prints.terminadaAccion();
            Prints.limpiar(ESPACIOS);
        }
    }

    public static void borrado(Scanner in) {
        boolean salida;

        System.out.println("3. Borrado");

        do {
            vaciarScanner(in);
            Prints.separador();
            System.out.println("Introduzca el codigo del empleado que desea borrar");
            System.out.print("> ");
            String codigo = in.nextLine();

            Empleado empleadoBuscado =buscaEmpleado(empleados, codigo);
            System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea cambiar a este empleado?");

            Prints.asegurar();

            Prints.siNo();
            int eleccion = in.nextInt();

            salida = sigueOSale(eleccion);

            if (salida){
                accionBorradoEmpleado(empleados, codigo);
            } else {
                salida = otroOSalir(in, "borrado");
            }

        } while (!salida);
        Prints.saltoLinea();
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }

    public static void papelera() {
        System.out.println("4. Papelera");
        Prints.separador();
        Prints.saltoLinea();
        int i = 0;
        for (Map.Entry borrados:empleadosBorrados.entrySet()) {
            System.out.print("Empleado en papelera Nª" + (i + 1));
            System.out.println(borrados.getValue().toString());
            i++;
        }
        Prints.saltoLinea();
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);

    }

    public static void modificar(Scanner in){

        boolean salida;
        System.out.println("5. Modificar");

        do {
            vaciarScanner(in);
            Prints.separador();
            System.out.println("Introduzca el numero del empleado del que desea cambiar algun dato");
            System.out.print(" > ");
            String codigo = in.nextLine();

            Empleado empleadoBuscado =buscaEmpleado(empleados, codigo);
            System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea cambiar a este empleado?");

            Prints.siNo();
            int eleccion = in.nextInt();

            salida = sigueOSale(eleccion);

            if (salida){
                accionModificadoEmpleado(empleados,codigo, in);
            }else {
                salida = otroOSalir(in, "cambiar");
            }

        } while (!salida);
        Prints.saltoLinea();
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }


    // ------------------------> FUNCIONES <-----------------------------


    static void vaciarScanner(Scanner in){
        if (in.hasNextLine()){
            in.skip("\n");
        }
    }

    private static boolean sigueOSale(int eleccion){
        boolean salida = false;

        switch (eleccion){
            case 1:
                salida = true;
                break;
            case 2:
                salida = false;
                break;
        }

        return salida;
    }

    private static boolean otroOSalir (Scanner in , String palabra){
        boolean salida;
        int eleccion;
        System.out.println("¿Quiere " + palabra + " a otro empleado o desea salir?");
        Prints.otroSalir();
        System.out.print(" > ");
        eleccion = in.nextInt();

        salida = eleccion == 2;
        return salida;
    }

    private static Empleado buscaEmpleado(ArrayList<Empleado> empleados, String codigo){

        Empleado empleadoResultado = new Empleado(null);

        for (int x = 0; x < empleados.size(); x++) {
            Empleado empleadoBuscado = empleados.get(x);
            if (empleadoBuscado.getCodigo().equals(codigo)) {
                 empleadoResultado= empleadoBuscado;
                break; // Terminar ciclo, pues ya lo encontramos
            }
        }
        return empleadoResultado;
    }

    private static void generarCodigo(Empleado variableEmpleado){
        Prints.separadorConTexto("Codigo");
        variableEmpleado.setCodigo(Alfanumerico.generar());
        System.out.println(variableEmpleado.getCodigo());
        empleados.add(variableEmpleado);
    }


    // ######## --------> FUNCIONES LEER <-------------------- #########

    // TODO Optimizacion ( uso lo mismo todo el rato podria sacarlo fuera como una funcion externa )

    private static String leerNombre(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Nombre");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerPrimerApellido(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Primer Apellido");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerSegundoApellido(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Segundo Apellido");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerDNI(int posicionLista, String palabra, Scanner in) {
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("DNI");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerFecha(int posicionLista, String palabra, Scanner in) {
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Fecha de Nacimiento");
            resultado = in.nextLine();
        }
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }

    private static String leerNacionalidad(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Nacionalidad");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static int leerEstado(int posicionLista, String palabra, Scanner in){
        String palabraIntroducida = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            palabraIntroducida = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Estado");
            palabraIntroducida = in.nextLine();
        }

        palabraIntroducida.toUpperCase();
        int eleccion;

        if (palabra.equals("ALTA")) {
            eleccion = 0;
        } else if (palabra.equals("BAJA")) {
            eleccion = 1;
        } else {
            eleccion = 2;
        }
        return eleccion;
    }

    private static String leerCalle(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Calle");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static int leerNumero(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        int resultadoInt = 0 ;
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Numero");
            resultado = in.nextLine();
        }
        resultadoInt = Integer.parseInt(resultado);

        return resultadoInt;
    }

    private static String leerBLoque(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Bloque");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerPiso(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Piso");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerPuerta(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Puerta");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static int leerCodigoPostal(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        int resultadoInt = 0;
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Codigo Postal");
            resultado = in.nextLine();
        }
        resultadoInt = Integer.parseInt(resultado);
        return resultadoInt;
    }

    private static String leerLocalidad(int posicionLista, String palabra, Scanner in){
        String resultado = "";
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Localidad");
            resultado = in.nextLine();
        }
        return resultado;
    }

    private static String leerProvincia(int posicionLista, String palabra, Scanner in){
        String resultado;
        if (palabra.equals("Fichero")){
            List fichero = GestionFicheros.leerFichero("empleados.txt");
            resultado = (String) fichero.get(posicionLista);
        } else {
            Prints.separadorConTexto("Provincia");
            resultado = in.nextLine();
        }
        return resultado;
    }

    // -------------------->  FUNCIONES CREAR  <------------------------

    public static void cargarLista(Scanner in) {

        System.out.println("Cargando lista de empleados");
        int camposRellenados = 0;
        int contador = 1;
        Prints.saltoLinea();

        do {
            Empleado variableEmpleado = new Empleado(null);

            camposRellenados = datosEmpleados( variableEmpleado, camposRellenados, in);
            variableEmpleado.setDireccion(datosDireccion(camposRellenados, in));

            System.out.println("Empleado Nº " + contador);
            contador ++;
            generarCodigo(variableEmpleado);
            camposRellenados += 8;
            empleados.add(variableEmpleado);
        } while (camposRellenados < GestionFicheros.leerFichero("empleados.txt").size());

        Prints.saltoLinea();
    }

   private static int datosEmpleados( Empleado variableEmpleado, int camposRellenados, Scanner in) {

        // Almacena y guarda los datos del empleado.

       variableEmpleado.setNombre(leerNombre(camposRellenados, "Fichero", in));
       camposRellenados++;

       variableEmpleado.setPrimerApellido(leerPrimerApellido(camposRellenados, "Fichero", in));
       camposRellenados++;
       variableEmpleado.setSegundoApellido(leerSegundoApellido(camposRellenados, "Fichero", in));
       camposRellenados++;

       variableEmpleado.setDNI(leerDNI(camposRellenados, "Fichero", in));
       camposRellenados++;

       Fecha.fecha(leerFecha(camposRellenados, "Fichero", in));
       camposRellenados++;

       variableEmpleado.setNacionalidad(leerNacionalidad(camposRellenados, "Fichero", in));
       camposRellenados++;

       variableEmpleado.setEstado(Estado.values()[leerEstado(camposRellenados, "Fichero", in)]);
       camposRellenados++;

       return camposRellenados;
   }

    private static Direccion datosDireccion( int camposRellenados, Scanner in){

        // Almacena y guarda los datos sobre la direccion
        Direccion variableDireccion = new Direccion();

        variableDireccion.setCalle(leerCalle(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setNumero(leerNumero(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setBloque(leerBLoque(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setPiso(leerPiso(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setPuerta(leerPuerta(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setCodigoPostal(leerCodigoPostal(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setLocalidad(leerLocalidad(camposRellenados, "Fichero", in));
        camposRellenados++;

        variableDireccion.setProvincia(leerProvincia(camposRellenados, "Fichero", in));

        return variableDireccion;
    }

    private static void datosEmpleadosPorTeclado( Scanner in , Empleado variableEmpleado) {

        vaciarScanner(in);
        variableEmpleado.setNombre(leerNombre(0, "", in));

        variableEmpleado.setPrimerApellido(leerPrimerApellido(0, "",in));

        variableEmpleado.setSegundoApellido(leerSegundoApellido(0, "",in));

        variableEmpleado.setDNI(leerDNI(0, "",in));

        Prints.separadorConTexto("Fecha de Nacimiento");
        Fecha.fecha(leerFecha(0, "",in));

        variableEmpleado.setNacionalidad(leerNacionalidad(0, "",in));

        variableEmpleado.setEstado(Estado.values()[leerEstado(0, "",in)]);
    }

    private static Direccion datosDireccionPorTeclado(Scanner in) {

        Direccion variableDireccion = new Direccion();
        variableDireccion.setCalle(leerCalle(0, "",in));               // Calle
        variableDireccion.setNumero(leerNumero(0, "",in));             // Numero
        variableDireccion.setBloque(leerBLoque(0, "",in));             // Bloque
        variableDireccion.setPiso(leerPiso(0, "",in));                 // Piso
        variableDireccion.setPuerta(leerPuerta(0, "",in));             // Puerta
        variableDireccion.setCodigoPostal(leerCodigoPostal(0, "",in)); // Codigo postal
        variableDireccion.setLocalidad(leerLocalidad(0, "",in));       // Localidad
        variableDireccion.setProvincia(leerProvincia(0, "",in));       // Provincia

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados, String codigo){

        // Busca el empleado por el codigo y una vez seleccionado lo guarda en un mapa y luego lo borra del arrray

        Empleado empleadoBuscado = buscaEmpleado(empleados, codigo);
        ((HashMap) Servicios.empleadosBorrados).put(codigo, empleadoBuscado.cadenaConAlmoadilla());

        try {
            GestionFicheros.escribirFichero("copiaDeSeguridad.txt", (empleadoBuscado.cadenaConAlmoadilla() + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------

    //TODO Seguir por aqui con modificar con respecto al archivo ( modificar el archivo original y luego cambiar
    // el array )

    private static void accionModificadoEmpleado(ArrayList<Empleado> empleados, String codigo, Scanner in) {

        // Busca el empleado por el codigo y una vez seleccionado lo guarda en un mapa y luego lleva a el usuario a el modificador
        // de campos

        Empleado empleadoBuscado = buscaEmpleado(empleados, codigo);
        cambioDeCampo(in , empleadoBuscado );
    }

    // TODO Siguente cosa a trabajar, la modificacion de los datos mediante el fichero.
    //  Supongo que lo que tendremos que hacer sera que en esa posicion reescrbia el campo del fichero y tambien
    //  lo cambie en el objeto empleado

    private static void cambioDeCampo(Scanner in, Empleado empleadoBuscado) {


        /* Empleado modificado;
        Prints.eleccionModificar();
        System.out.println("Elija que campo quiere cambiar");
        int decision = in.nextInt();

        switch (decision){ //TODO cambiar a datos de la persona, direccion y estado
            case 1: // Nombre
                vaciarScanner(in);
                cambioNombre(in, empleadoBuscado);
                break;
            case 2: // Apellido
                vaciarScanner(in);
                cambioApellidos(in, empleadoBuscado);
                break;
            case 3: // DNI
                vaciarScanner(in);
                 cambioDNI(in, empleadoBuscado);
                break;
            case 4: // Fecha de nacimiento
                vaciarScanner(in);
                cambioFechaNacimiento(in, empleadoBuscado);
                break;

            case 5: // Nacionalidad
                vaciarScanner(in);
                cambioNacionalidad(in, empleadoBuscado);
                break;

            case 6: // Estado
                vaciarScanner(in);
                 cambioEstado(in, empleadoBuscado);
                break;
            case 7: // Dirección
                vaciarScanner(in);
                 cambioDireccion(in, empleadoBuscado);
                break;

            case 8: // Todos
                vaciarScanner(in);
                modificado = cambioNombre(in, empleadoBuscado);
                modificado = cambioApellidos(in, modificado);
                modificado = cambioDNI(in, modificado);
                modificado = cambioFechaNacimiento(in, modificado);
                modificado = cambioNacionalidad(in, modificado);
                modificado = cambioEstado(in, modificado);
                cambioDireccion(in, modificado);
                break;
        } */
    }


    /* private static Empleado cambioNombre(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNombre(leerNombre());
        return empleadoBuscado;
    }

    private static Empleado cambioApellidos(Scanner in, Empleado empleadoBuscado){

        empleadoBuscado.setPrimerApellido(leerPrimerApellido());
        empleadoBuscado.setSegundoApellido(leerSegundoApellido());

        return empleadoBuscado;
    }

    private static Empleado cambioDNI(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setDNI(leerDNI());
        return empleadoBuscado;
    }

    private static Empleado cambioFechaNacimiento(Scanner in, Empleado empleadoBuscado) {
        boolean salida = false;
        do {
            try {
                // Prints.separadorConTexto("Fecha de Nacimiento");
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String fecha = leerFecha();
                Date fechaNacimiento = format.parse(fecha);
                empleadoBuscado.setFechaNacimiento(fechaNacimiento);

                salida = true;
            } catch (Exception e){
                System.out.println("Por favor repita la fecha asegurandose de que sigue bien el formato (dd-mm-yyyy)");
                salida = false;
            }
        } while (!salida);
        return empleadoBuscado;
    }

    private static Empleado cambioNacionalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNacionalidad(leerNacionalidad());
        return empleadoBuscado;
    }

    public static Empleado cambioEstado(Scanner in, Empleado empleadoBuscado){
        int eleccion = leerEstado();
        empleadoBuscado.setEstado(Estado.values()[eleccion]);
        return empleadoBuscado;
    }

    private static void cambioDireccion(Scanner in, Empleado empleadoBuscado){
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setCalle(leerCalle());
        empleadoBuscado.getDireccion().setNumero(leerNumero());
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setBloque(leerBLoque());
        empleadoBuscado.getDireccion().setPiso(leerPiso());
        empleadoBuscado.getDireccion().setPuerta(leerPuerta());
        empleadoBuscado.getDireccion().setCodigoPostal(leerCodigoPostal());
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setLocalidad(leerLocalidad());
        empleadoBuscado.getDireccion().setProvincia(leerProvincia());

    } */
}
