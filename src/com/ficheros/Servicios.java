package com.ficheros;

import com.modelos.Direccion;
import com.modelos.Empleado;
import com.modelos.Estado;
import com.utilidades.Alfanumerico;
import com.utilidades.Fecha;
import com.utilidades.Prints;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Servicios {

    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void crear(Scanner in) {


        System.out.println("1. Crear");

        Empleado variableEmpleado = new Empleado(null);
        try {
            datosEmpleadosPorTeclado(in, variableEmpleado);
            variableEmpleado.setDireccion(datosDireccionPorTeclado(in));
            generarCodigo(empleados, variableEmpleado);
            empleados.add(variableEmpleado);
        } catch (Exception e) {
            Prints.error();
        }
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
            Prints.introduzcaDatos(in);
            String codigo;
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, codigo = in.nextLine());
            if (empleadoBuscado != null){
                Prints.nombreDelEmpleadoElección(empleadoBuscado);
                int eleccion = in.nextInt();
                salida = sigueOSale(eleccion);
                if (salida){
                    accionBorradoEmpleado(empleados, codigo, empleadoBuscado);
                } else {
                    salida = otroOSalir(in, "borrado");
                }
            } else {
                System.out.println("Código erroneo, cerrando acción");
                salida = true;
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
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null){
                Prints.nombreDelEmpleadoElección(empleadoBuscado);
                int eleccion = in.nextInt();
                salida = sigueOSale(eleccion);

                if (salida){
                    elecciónDeGrupoQueQuiereCambiar(in, empleadoBuscado);
                }else {
                    salida = otroOSalir(in, "cambiar");
                }
            } else {
                System.out.println("Codigo erroneo, volviendo a menú principal");
                salida = true;
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
                    System.out.println("Fallo guardando la papelera");
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
                    // Cuando lo guarda le genera una fecha de borrado con la hora actual en el archivo txt, esta fecha
                    // no importa porque no la carga despues es para que no de fallo por campo nulo
                } catch (IOException e) {
                    System.out.println("Fallo guardando la lista de empleados");
                }
            }
        }
        System.out.println("Empleados guardados con exito");
        Prints.finalFuncion();
    }

    public static void guardarTodo() {
        guardarEmpleados();
        guardarPapelera();
    }



    // ------------------------> FUNCIONES <-----------------------------

    public static void vaciarScanner(Scanner in){
        in.skip("\n");
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

    private static Empleado buscaEmpleadoPorCodigo(ArrayList<Empleado> empleados, String codigo){
        Empleado empleadoResultado = null;
        int contadorParaRecorrerElArray = 0;
        if (codigoEstaAsignadoAAlguien(empleados, codigo)){

            while (contadorParaRecorrerElArray < empleados.size()){
                if (empleados.get(contadorParaRecorrerElArray).getCodigo().equals(codigo)){
                    empleadoResultado = empleados.get(contadorParaRecorrerElArray);
                }
                contadorParaRecorrerElArray ++;
            }
        }
        return empleadoResultado;
    }

    private static void generarCodigo(ArrayList<Empleado> empleados, Empleado variableEmpleado){
        Prints.separadorConTexto("Codigo");
        String codigo;
        do {
            codigo = Alfanumerico.generar();
        }while (codigoEstaAsignadoAAlguien(empleados, codigo));
        variableEmpleado.setCodigo(codigo);
        System.out.println(variableEmpleado.getCodigo()); }

    private static int estadoEleccion(String palabraIntroducida){

        String palabraIntroducidaMayusculas = palabraIntroducida.toUpperCase();
        int eleccion;
        switch (palabraIntroducidaMayusculas) {
            case "ALTA":
                eleccion = 0;
                break;
            case "BAJA":
                eleccion = 1;
                break;
            case "EN TRAMITE":
                eleccion = 2;
                break;
            default:
                System.out.println("Palabra introducida erronea, se establecera ''Alta'' como predeterminado,porfavor cambie el dato con la acción modificar");
                eleccion = 0;
                break;
        }
        return eleccion;
    }

    public static void cargarLista(String[] datoSeparado, String palabra) throws ParseException {
        Empleado variableEmpleado = new Empleado(null);
        datosEmpleados(variableEmpleado, datoSeparado);
        variableEmpleado.setDireccion(datosDireccion(datoSeparado));
        if (palabra.equals("empleados")){
            empleados.add(variableEmpleado);
        } else if (palabra.equals("papelera")){
            variableEmpleado.setFechaBaja(Fecha.leerFechaConFormato(datoSeparado[17]));
            empleadosBorrados.put(variableEmpleado.getCodigo(), variableEmpleado);
        }
    }

    public static boolean codigoEstaAsignadoAAlguien(ArrayList<Empleado> empleados, String codigo){
        boolean resultado = false;

        int contadorParaRecorrerElArray = 0;
        while (contadorParaRecorrerElArray < empleados.size()){
            if (empleados.get(contadorParaRecorrerElArray).getCodigo().equals(codigo)) {
                resultado = true;
                break;
            }
            contadorParaRecorrerElArray ++;
        }
        return resultado;
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

    public static String leerFecha(Scanner in) {
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
        int resultado = 0;
            try {
                resultado = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error, numero mal introducido. Se establecera 0 como predeterminado");
            } finally {
                in.nextLine();
            }
        return resultado;
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
        Prints.separadorConTexto("Codigo postal");
        int resultado = 0;
        try {
            resultado = in.nextInt();
        } catch (Exception e){
            System.out.println("Error al introducir el código postal. Se establecera 00000 como predetermiando");
        } finally {
            in.nextLine();
        }

        return resultado;
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

    private static void datosEmpleados( Empleado variableEmpleado, String[] datoSeparado) throws ParseException {

        // Almacena y guarda los datos del empleado.
        variableEmpleado.setCodigo(datoSeparado[0]);
        variableEmpleado.setNombre(datoSeparado[1]);
        variableEmpleado.setPrimerApellido(datoSeparado[2]);
        variableEmpleado.setSegundoApellido(datoSeparado[3]);
        variableEmpleado.setDNI(datoSeparado[4]);
        variableEmpleado.setFechaNacimiento(Fecha.fecha(datoSeparado[5]));
        variableEmpleado.setNacionalidad(datoSeparado[6]);
        variableEmpleado.setEstado(Estado.values()[estadoEleccion(datoSeparado[7])]);
        variableEmpleado.setFechaAlta(Fecha.leerFechaConFormato(datoSeparado[16]));
        variableEmpleado.setFechaBaja(Fecha.creaciónFechaActual());
   }

    private static Direccion datosDireccion( String[] datoSeparado){

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

    private static void datosEmpleadosPorTeclado( Scanner in , Empleado variableEmpleado) throws ParseException {

        vaciarScanner(in);
        variableEmpleado.setNombre(leerNombre(in));
        variableEmpleado.setPrimerApellido(leerPrimerApellido(in));
        variableEmpleado.setSegundoApellido(leerSegundoApellido(in));
        variableEmpleado.setDNI(leerDNI(in));
        variableEmpleado.setFechaNacimiento(Fecha.fecha(leerFecha(in)));
        variableEmpleado.setNacionalidad(leerNacionalidad(in));
        variableEmpleado.setEstado(Estado.values()[leerEstado(in)]);
        variableEmpleado.setFechaAlta(Fecha.creaciónFechaActual());
        variableEmpleado.setFechaBaja(Fecha.creaciónFechaActual());
    }

    private static Direccion datosDireccionPorTeclado(Scanner in){

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

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados , String codigo, Empleado empleadoBuscado){

        // Al empleado buscado, le asigna una fecha de borrado, lo guarda en un mapa y lo borra del array
        empleadoBuscado.setFechaBaja(Fecha.creaciónFechaActual());
        empleadosBorrados.put(codigo,empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------

    private static void elecciónDeGrupoQueQuiereCambiar(Scanner in, Empleado empleadoBuscado) {

        Prints.eleccionModificar();
        System.out.println("Elija que campo quiere cambiar");
        int decision = in.nextInt();
        boolean salida = true;
        do {
            try {
                if (decision == 1) { // Campos informacion personal

                    vaciarScanner(in);
                    cambioCamposPersonales(in, empleadoBuscado);

                } else if (decision == 2) { // Campos de direccion

                    vaciarScanner(in);
                    cambioCamposDireccion(in, empleadoBuscado);

                } else if (decision == 3) { // Estado

                    vaciarScanner(in);
                    cambioEstado(empleadoBuscado, in);

                } else if (decision == 4) { // Todos los campos

                    cambioCamposPersonales(in, empleadoBuscado);
                    cambioCamposDireccion(in, empleadoBuscado);
                    cambioEstado(empleadoBuscado, in);
                } else {
                    System.out.println("Error. Numero introducido por teclado erroneo");
                }
            } catch (Exception e){
                System.out.println("Error introduciendo datos, vuelva a intentarlo");
                salida = false;
            }
        }while (!salida);
    }

    private static void cambioCamposDireccion(Scanner in, Empleado empleadoBuscado){

        cambioCalle(in, empleadoBuscado);
        cambioNumero(in, empleadoBuscado);
        cambioBloque(in, empleadoBuscado);
        cambioPiso(in, empleadoBuscado);
        cambioPuerta(in, empleadoBuscado);
        cambioCodigoPostal(in, empleadoBuscado);
        cambioLocalidad(in, empleadoBuscado);
        cambioProvincia(in, empleadoBuscado);
    }

    private static void cambioCamposPersonales(Scanner in, Empleado empleadoBuscado) throws ParseException {
        cambioNombre(in ,empleadoBuscado);
        cambioApellidos(in ,empleadoBuscado);
        cambioDNI(in ,empleadoBuscado);
        cambioFechaNacimiento(in ,empleadoBuscado);
        cambioNacionalidad(in , empleadoBuscado);
    }

    public static void cambioEstado(Empleado empleadoBuscado, Scanner in){
        empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);
    }

    // --------------------------------------------------------------------------

    private static void cambioNombre(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNombre(leerNombre(in));
    }

    private static void cambioApellidos(Scanner in, Empleado empleadoBuscado){

        empleadoBuscado.setPrimerApellido(leerPrimerApellido(in));
        empleadoBuscado.setSegundoApellido(leerSegundoApellido(in));

    }

    private static void cambioDNI(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setDNI(leerDNI(in));
    }

    private static void cambioFechaNacimiento(Scanner in, Empleado empleadoBuscado) throws ParseException {
        empleadoBuscado.setFechaNacimiento(Fecha.fecha(leerFecha(in)));
    }

    private static void cambioNacionalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNacionalidad(leerNacionalidad(in));
    }

    private static void cambioCalle(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCalle(leerCalle(in));
    }

    private static void cambioNumero(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setNumero(leerNumero(in));
    }

    private static void cambioBloque(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setBloque(leerBLoque(in));
    }

    private static void cambioPiso(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPiso(leerPiso(in));
    }

    private static void cambioPuerta(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPuerta(leerPuerta(in));
    }

    private static void cambioCodigoPostal(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCodigoPostal(leerCodigoPostal(in));
    }

    private static void cambioLocalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setLocalidad(leerLocalidad(in));
    }

    private static void cambioProvincia(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setProvincia(leerProvincia(in));
    }
}
