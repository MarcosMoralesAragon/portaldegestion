import modelos.Direccion;
import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.GestionFicheros;
import utilidades.Prints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servicios {

    private static final int ESPACIOS = 3;
    public static final ArrayList<Empleado> empleados = new ArrayList<>();
    public static final HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void crear(Scanner in) {

        System.out.println("1. Crear");
        int camposRellenados = 0;

        do {
            Empleado variableEmpleado = new Empleado(null);

            // Entrada de datos de los empleados
            // devuelve un int para que se sepa la cantidad de campos que se han rellenado en este apartado
            // que en este caso son 7 pero es mejor dejarlo asi para que si se pide una expansion de la entrada
            // de datos este adaptado

            camposRellenados = datosEmpleados(in, variableEmpleado, camposRellenados);

            // Entrada de datos de los campos de direccion
            variableEmpleado.setDireccion(datosDireccion(in, camposRellenados));

            Prints.separadorConTexto("Codigo");
            variableEmpleado.setCodigo(Alfanumerico.generar());
            System.out.println(variableEmpleado.getCodigo());

            camposRellenados += 8;
            System.out.println();
            empleados.add(variableEmpleado);

        } while (camposRellenados < GestionFicheros.leerFichero("empleados.txt").size());

        Prints.saltoLinea();
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }
    public static void listado() {

        System.out.println("2. Listado");
        Prints.separador();
        Prints.saltoLinea();
        for ( int i = 0; i < empleados.size(); i++){
            System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i));
        }
        Prints.saltoLinea();
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
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
                accionBorradoEmpleado(empleados, codigo, empleadosBorrados);
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
            System.out.println(borrados.getValue());
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

    // ######## --------> FUNCIONES LEER <-------------------- #########

    private static String leerNombre(int posicionLista){
        //Prints.separadorConTexto("Nombre");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerPrimerApellido(int posicionLista){
        // Prints.separadorConTexto("Primer Apellido");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerSegundoApellido(int posicionLista){
        // Prints.separadorConTexto("Segundo Apellido");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerDNI(int posicionLista){
        // Prints.separadorConTexto("DNI");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerFecha(int posicionLista) {
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerNacionalidad(int posicionLista){
        // Prints.separadorConTexto("Nacionalidad");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static int leerEstado(int posicionLista){
        // Prints.separadorConTexto("Estado");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        String palabra = (String) fichero.get(posicionLista);
        palabra = palabra.toUpperCase();
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
    private static String leerCalle(int posicionLista){
        // Prints.separadorConTexto("Calle");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerNumero(int posicionLista){
        // Prints.separadorConTexto("Numero");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerBLoque(int posicionLista){
        // Prints.separadorConTexto("Bloque");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerPiso(int posicionLista){
        // Prints.separadorConTexto("Piso");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerPuerta(int posicionLista){
        // Prints.separadorConTexto("Puerta");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerCodigoPostal(int posicionLista){
        // Prints.separadorConTexto("Codigo Postal");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerLocalidad(int posicionLista){
        // Prints.separadorConTexto("Localidad");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
    }
    private static String leerProvincia(int posicionLista){
        // Prints.separadorConTexto("Provincia");
        List fichero = GestionFicheros.leerFichero("empleados.txt");
        return (String) fichero.get(posicionLista);
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


    // -------------------->  FUNCIONES CREAR  <------------------------



   private static int datosEmpleados(Scanner in, Empleado variableEmpleado, int camposRellenados) {

        // Almacena y guarda los datos del empleado.


       variableEmpleado.setNombre(leerNombre(camposRellenados));
       camposRellenados++;

       variableEmpleado.setPrimerApellido(leerPrimerApellido(camposRellenados));
       camposRellenados++;
       variableEmpleado.setSegundoApellido(leerSegundoApellido(camposRellenados));
       camposRellenados++;

       variableEmpleado.setDNI(leerDNI(camposRellenados));
       camposRellenados++;

       boolean salida;
       do {
           try{
               // Prints.separadorConTexto("Fecha de Nacimiento");
               DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
               String fecha = leerFecha(camposRellenados);
               Date fechaNacimiento = format.parse(fecha);
               variableEmpleado.setFechaNacimiento(fechaNacimiento);
               salida = true;
           } catch (Exception e){
               System.out.println("Por favor repita la fecha asegurandose de que sigue bien el formato (dd-mm-yyyy)");
               salida = false;
           }
       } while (!salida);
       camposRellenados++;

       variableEmpleado.setNacionalidad(leerNacionalidad(camposRellenados));
       camposRellenados++;

       variableEmpleado.setEstado(Estado.values()[leerEstado(camposRellenados)]);
       camposRellenados++;

       return camposRellenados;
   }

    private static Direccion datosDireccion(Scanner in, int camposRellenados){

        // Almacena y guarda los datos sobre la direccion

        Direccion variableDireccion = new Direccion();

        variableDireccion.setCalle(leerCalle(camposRellenados));
        camposRellenados++;

        variableDireccion.setNumero(leerNumero(camposRellenados));
        camposRellenados++;

        variableDireccion.setBloque(leerBLoque(camposRellenados));
        camposRellenados++;

        variableDireccion.setPiso(leerPiso(camposRellenados));
        camposRellenados++;

        variableDireccion.setPuerta(leerPuerta(camposRellenados));
        camposRellenados++;

        variableDireccion.setCodigoPostal(leerCodigoPostal(camposRellenados));
        camposRellenados++;

        variableDireccion.setLocalidad(leerLocalidad(camposRellenados));
        camposRellenados++;

        variableDireccion.setProvincia(leerProvincia(camposRellenados));

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados, String codigo,HashMap empleadosBorrados){

        // Busca el empleado por el codigo y una vez seleccionado lo guarda en un mapa y luego lo borra del arrray

        Empleado empleadoBuscado = buscaEmpleado(empleados, codigo);
        empleadosBorrados.put(codigo, empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------


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


        Empleado modificado;
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
        }
    }


    private static Empleado cambioNombre(Scanner in, Empleado empleadoBuscado){
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

    }
}
