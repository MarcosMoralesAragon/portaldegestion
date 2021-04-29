package com.ficheros;

import com.modelos.Direccion;
import com.modelos.Empleado;
import com.modelos.Estado;
import com.utilidades.Alfanumerico;
import com.utilidades.Fecha;
import com.utilidades.Prints;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servicios {

    public static final ArrayList<Empleado> empleados = new ArrayList<>();
    public static final HashMap<String, Empleado> empleadosBorrados = new HashMap<String, Empleado>();

    public static void crear(Scanner in, String palabra) {


        System.out.println("1. Crear");

        Empleado variableEmpleado = new Empleado(null);

        datosEmpleadosPorTeclado( in, variableEmpleado);
        variableEmpleado.setDireccion(datosDireccionPorTeclado(in));

        generarCodigo(variableEmpleado);
        empleados.add(variableEmpleado);

        Prints.finalFuncion();
    }

    public static void listado(String numero) {

        System.out.println(numero + "Listado");
        Prints.separador();
        Prints.limpiar(1);
        for ( int i = 0; i < empleados.size(); i++){
            System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i).cadenaFormateadaParaMostrarPorPantalla());
        }
        if (!numero.equals("")){
            Prints.finalFuncion();
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
            String codigo;
            Empleado empleadoBuscado = buscaEmpleadoDevolviendoElEmpleado(empleados, codigo = in.nextLine());
            System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea cambiar a este empleado?");
            Prints.asegurar();
            Prints.siNo();
            int eleccion = in.nextInt();
            salida = sigueOSale(eleccion);
            if (salida){
                accionBorradoEmpleado(empleados, codigo, empleadoBuscado);
            } else {
                salida = otroOSalir(in, "borrado");
            }
        } while (!salida);
        Prints.finalFuncion();
    }

    public static void papelera() {
        System.out.println("4. Papelera");
        Prints.separador();
        Prints.limpiar(1);
        int i = 0;
        for (Map.Entry borrados:empleadosBorrados.entrySet()) {
            System.out.print("Empleado en papelera Nª" + (i + 1) + " --> ");
            System.out.println(borrados.getValue());
            i++;
        }
        Prints.finalFuncion();
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
            Empleado empleadoBuscado =buscaEmpleadoDevolviendoElEmpleado(empleados, codigo);
            System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea cambiar a este empleado?");
            Prints.siNo();
            int eleccion = in.nextInt();
            salida = sigueOSale(eleccion);

            if (salida){
                cambioDeCampo(in, empleadoBuscado);
            }else {
                salida = otroOSalir(in, "cambiar");
            }
        } while (!salida);
        Prints.finalFuncion();
    }

    public static void guardarPapelera() {

        String nombreFichero = "copiaDeSeguridad.txt";
        boolean creado = false;
        boolean borrado = GestionFicheros.borrarFichero(nombreFichero);
        if (borrado){
            creado = GestionFicheros.creadorFicheros(nombreFichero);
        }
        if (creado){
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()){
                try {
                    GestionFicheros.escribirFichero("copiaDeSeguridad.txt", entry.getValue().toString());
                } catch (IOException e) {
                    System.out.println("Fallo guardando la lista");
                }
            }

        }
        System.out.println("Papelera guardada con exito");
        Prints.finalFuncion();
    }

    public static void guardarEmpleados() {

        String nombreFichero = "empleados.txt";
        boolean creado = false;
        boolean borrado = GestionFicheros.borrarFichero(nombreFichero);
        if (borrado){
            creado = GestionFicheros.creadorFicheros(nombreFichero);
        }
        if (creado){
            for (Empleado empleado : empleados) {
                try {
                    GestionFicheros.escribirFichero("empleados.txt", empleado.toString());
                } catch (IOException e) {
                    System.out.println("Fallo guardando la lista");
                }
            }
        }
        System.out.println("Empleados guardados con exito");
        Prints.finalFuncion();
    }

    public static void guardarTodo() {
        guardarPapelera();
        guardarEmpleados();
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

    private static Empleado buscaEmpleadoDevolviendoElEmpleado(ArrayList<Empleado> empleados, String codigo){

        Empleado empleadoResultado = new Empleado(null);
        for (Empleado empleadoBuscado : empleados) {
            if (empleadoBuscado.getCodigo().equals(codigo)) {
                empleadoResultado = empleadoBuscado;
                break; // Terminar ciclo, pues ya lo encontramos
            }
        }
        return empleadoResultado;
    }

    private static void generarCodigo(Empleado variableEmpleado){
        Prints.separadorConTexto("Codigo");
        variableEmpleado.setCodigo(Alfanumerico.generar());
        System.out.println(variableEmpleado.getCodigo());
    }

    private static int estadoEleccion(String palabraIntroducida){

        String palabraIntroducidaMayusculas = palabraIntroducida.toUpperCase();
        int eleccion;
        if (palabraIntroducidaMayusculas.equals("ALTA")) {
            eleccion = 0;
        } else if (palabraIntroducidaMayusculas.equals("BAJA")) {
            eleccion = 1;
        } else {
            eleccion = 2;
        }
        return eleccion;
    }

    public static void cargarLista(Scanner in, String[] datoSeparado, String palabra) {
        Empleado variableEmpleado = new Empleado(null);
        datosEmpleados(variableEmpleado,in, datoSeparado);
        variableEmpleado.setDireccion(datosDireccion(datoSeparado, in));
        if (palabra.equals("empleados")){
            empleados.add(variableEmpleado);
        } else if (palabra.equals("papelera")){
            empleadosBorrados.put(variableEmpleado.getCodigo(), variableEmpleado);
        }
    }

    // ######## --------> FUNCIONES LEER <-------------------- #########

    private static String leerNombre(Scanner in){
        Prints.separadorConTexto("Nombre");
        return in.nextLine();
    }

    private static String leerPrimerApellido(Scanner in){
        Prints.separadorConTexto("Primer Apellido");
        return in.nextLine();
    }

    private static String leerSegundoApellido(Scanner in){
        Prints.separadorConTexto("Segundo Apellido");
        return in.nextLine();
    }

    private static String leerDNI(Scanner in) {
        Prints.separadorConTexto("DNI");
        return in.nextLine();
    }

    private static String leerFecha(Scanner in) {
        Prints.separadorConTexto("Fecha de Nacimiento");
        return in.nextLine();
    }

    private static String leerNacionalidad(Scanner in){
        Prints.separadorConTexto("Nacionalidad");
        return in.nextLine();
    }

    private static int leerEstado(Scanner in){
        Prints.separadorConTexto("Estado");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return estadoEleccion(palabraIntroducida);
    }

    private static String leerCalle(Scanner in){
        Prints.separadorConTexto("Calle");
        return in.nextLine();
    }

    private static int leerNumero(Scanner in){
        Prints.separadorConTexto("Numero");
        String resultado = in.nextLine();
        int resultadoInt = Integer.parseInt(resultado);
        return resultadoInt;
    }

    private static String leerBLoque(Scanner in){
        Prints.separadorConTexto("Bloque");
        return in.nextLine();
    }

    private static String leerPiso(Scanner in){
        Prints.separadorConTexto("Piso");
        return in.nextLine();
    }

    private static String leerPuerta(Scanner in){
        Prints.separadorConTexto("Puerta");
        return in.nextLine();
    }

    private static int leerCodigoPostal(Scanner in){
        Prints.separadorConTexto("Codigo Postal");
        String resultado = in.nextLine();
        int resultadoInt = Integer.parseInt(resultado);
        return resultadoInt;
    }

    private static String leerLocalidad(Scanner in){
        Prints.separadorConTexto("Localidad");
        return in.nextLine();
    }

    private static String leerProvincia(Scanner in){
        Prints.separadorConTexto("Provincia");
        return in.nextLine();
    }

    // -------------------->  FUNCIONES CREAR  <------------------------

    private static void datosEmpleados( Empleado variableEmpleado, Scanner in, String[] datoSeparado) {

        // Almacena y guarda los datos del empleado.
        variableEmpleado.setCodigo(datoSeparado[0]);
        variableEmpleado.setNombre(datoSeparado[1]);
        variableEmpleado.setPrimerApellido(datoSeparado[2]);
        variableEmpleado.setSegundoApellido(datoSeparado[3]);
        variableEmpleado.setDNI(datoSeparado[4]);
        variableEmpleado.setFechaNacimiento(Fecha.fecha(datoSeparado[5]));
        variableEmpleado.setNacionalidad(datoSeparado[6]);
        variableEmpleado.setEstado(Estado.values()[estadoEleccion(datoSeparado[7])]);
   }

    private static Direccion datosDireccion( String[] datoSeparado, Scanner in){

        // Almacena y guarda los datos sobre la direccion
        Direccion variableDireccion = new Direccion();
        variableDireccion.setCalle(datoSeparado[8]);
        variableDireccion.setNumero(Integer.parseInt(datoSeparado[9]));
        variableDireccion.setBloque(datoSeparado[10]);
        variableDireccion.setPiso(datoSeparado[11]);
        variableDireccion.setPuerta(datoSeparado[12]);
        variableDireccion.setCodigoPostal(Integer.parseInt(datoSeparado[13]));
        variableDireccion.setLocalidad(datoSeparado[14]);
        variableDireccion.setProvincia(datoSeparado[15]);
        return variableDireccion;
    }

    private static void datosEmpleadosPorTeclado( Scanner in , Empleado variableEmpleado) {

        vaciarScanner(in);
        variableEmpleado.setNombre(leerNombre(in));
        variableEmpleado.setPrimerApellido(leerPrimerApellido(in));
        variableEmpleado.setSegundoApellido(leerSegundoApellido(in));
        variableEmpleado.setDNI(leerDNI(in));
        variableEmpleado.setFechaNacimiento(Fecha.fecha(leerFecha(in)));
        variableEmpleado.setNacionalidad(leerNacionalidad(in));
        variableEmpleado.setEstado(Estado.values()[leerEstado(in)]);
    }

    private static Direccion datosDireccionPorTeclado(Scanner in) {

        Direccion variableDireccion = new Direccion();
        variableDireccion.setCalle(leerCalle(in));               // Calle
        variableDireccion.setNumero(leerNumero(in));             // Numero
        variableDireccion.setBloque(leerBLoque(in));             // Bloque
        variableDireccion.setPiso(leerPiso(in));                 // Piso
        variableDireccion.setPuerta(leerPuerta(in));             // Puerta
        variableDireccion.setCodigoPostal(leerCodigoPostal(in)); // Codigo postal
        variableDireccion.setLocalidad(leerLocalidad(in));       // Localidad
        variableDireccion.setProvincia(leerProvincia(in));       // Provincia

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados, String codigo, Empleado empleadoBuscado){

        // Busca el empleado por el codigo y una vez seleccionado lo guarda en un mapa y luego lo borra del arrraylist
        Date fecha = new Date();
        empleadoBuscado.setFechaBorrado(fecha);
        Servicios.empleadosBorrados.put(codigo,empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------


    // TODO Siguente cosa a trabajar, la modificacion de los datos mediante el fichero.
    //  Supongo que lo que tendremos que hacer sera que en esa posicion reescrbia el campo del fichero y tambien
    //  lo cambie en el objeto empleado

    private static void cambioDeCampo(Scanner in, Empleado empleadoBuscado) {

        Empleado modificado;
        Prints.eleccionModificar();
        System.out.println("Elija que campo quiere cambiar");
        int decision = in.nextInt();

        if (decision == 1) { // Campos informacion personal
            vaciarScanner(in);
            cambioCamposPersonales(in, empleadoBuscado);
        } else if (decision == 2) { // Campos de direccion
            vaciarScanner(in);
            cambioCamposDireccion(in, empleadoBuscado);
        } else if (decision == 3) { // Estado
            vaciarScanner(in);
            cambioEstado(empleadoBuscado, in);
        } else if (decision == 4) { // En caso de poner mal el numero
            empleadoBuscado = cambioCamposPersonales(in, empleadoBuscado);
            empleadoBuscado = cambioCamposDireccion(in, empleadoBuscado);
            cambioEstado(empleadoBuscado, in);
        } else {
            System.out.println("Error. Numero introducido por teclado erroneo");
        }
    }

    private static Empleado cambioCamposDireccion(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado = cambioCalle(in ,empleadoBuscado);
        empleadoBuscado = cambioNumero(in ,empleadoBuscado);
        empleadoBuscado = cambioBloque(in ,empleadoBuscado);
        empleadoBuscado = cambioPiso(in ,empleadoBuscado);
        empleadoBuscado = cambioPuerta(in ,empleadoBuscado);
        empleadoBuscado = cambioCodigoPostal(in ,empleadoBuscado);
        empleadoBuscado = cambioLocalidad(in ,empleadoBuscado);
        empleadoBuscado = cambioProvincia(in ,empleadoBuscado);

        return empleadoBuscado;
    }

    private static Empleado cambioCamposPersonales(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado = cambioNombre(in ,empleadoBuscado);
        empleadoBuscado = cambioApellidos(in ,empleadoBuscado);
        empleadoBuscado = cambioDNI(in ,empleadoBuscado);
        empleadoBuscado = cambioFechaNacimiento(in ,empleadoBuscado);
        empleadoBuscado = cambioNacionalidad(in , empleadoBuscado);
        return empleadoBuscado;
    }

    public static void cambioEstado(Empleado empleadoBuscado, Scanner in){
        empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);
    }

    // --------------------------------------------------------------------------

    private static Empleado cambioNombre(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNombre(leerNombre(in));
        return empleadoBuscado;
    }

    private static Empleado cambioApellidos(Scanner in, Empleado empleadoBuscado){

        empleadoBuscado.setPrimerApellido(leerPrimerApellido(in));
        empleadoBuscado.setSegundoApellido(leerSegundoApellido(in));

        return empleadoBuscado;
    }

    private static Empleado cambioDNI(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setDNI(leerDNI(in));
        return empleadoBuscado;
    }

    private static Empleado cambioFechaNacimiento(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado.setFechaNacimiento(Fecha.fecha(leerFecha(in)));
        return empleadoBuscado;
    }

    private static Empleado cambioNacionalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNacionalidad(leerNacionalidad(in));
        return empleadoBuscado;
    }

    private static Empleado cambioCalle(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCalle(leerCalle(in));
        return empleadoBuscado;
    }

    private static Empleado cambioNumero(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setNumero(leerNumero(in));
        return empleadoBuscado;
    }

    private static Empleado cambioBloque(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setBloque(leerBLoque(in));
        return empleadoBuscado;
    }

    private static Empleado cambioPiso(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPiso(leerPiso(in));
        return empleadoBuscado;
    }

    private static Empleado cambioPuerta(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPuerta(leerPuerta(in));
        return empleadoBuscado;
    }

    private static Empleado cambioCodigoPostal(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCodigoPostal(leerCodigoPostal(in));
        return empleadoBuscado;
    }

    private static Empleado cambioLocalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setLocalidad(leerLocalidad(in));
        return empleadoBuscado;
    }

    private static Empleado cambioProvincia(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setProvincia(leerProvincia(in));
        return empleadoBuscado;
    }
}
