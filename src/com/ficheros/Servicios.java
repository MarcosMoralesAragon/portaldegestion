package com.ficheros;

import com.modelos.Direccion;
import com.modelos.Empleado;
import com.modelos.Estado;
import com.utilidades.Alfanumerico;
import com.utilidades.Fecha;
import com.utilidades.Prints;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.util.Map.*;
@SuppressWarnings({"rawtypes", "NonAsciiCharacters"})

public class Servicios {

    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void crear(Scanner in) {
        System.out.println("1. Crear");
        Empleado variableEmpleado = new Empleado();
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

    public static void listarEmpleados(String numero) {
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
                System.out.println("Ha seleccionado a "+ empleadoBuscado.getNombre() +" ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSeguirConEsteEmpleadoOOtro(in,"");
                if (salida){
                    accionBorradoEmpleado(empleados, codigo, empleadoBuscado);
                } else {
                    salida = eleccionSeguirConEsteEmpleadoOOtro(in, "borrado");
                }
            } else {
                System.out.println("Código erroneo, cerrando acción");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    }

    public static void modificar(Scanner in){
        boolean salida;
        System.out.println("4. Modificar");
        do {
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null){
                System.out.println("Ha seleccionado a "+ empleadoBuscado.getNombre() +" ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSeguirConEsteEmpleadoOOtro(in,"");
                if (salida){
                    elecciónDeGrupoQueQuiereCambiar(in, empleadoBuscado);
                }else {
                    salida = eleccionSeguirConEsteEmpleadoOOtro(in, "cambiar");
                }
            } else {
                System.out.println("Codigo erroneo, volviendo a menú principal");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    }

    public static void guardarEmpleados(String nombreFichero) {
        System.out.println("5. Guardar empleados");
        Prints.separador();
        Prints.limpiar(1);

        boolean creado = false;
        boolean borrado = GestionFicheros.borrarFichero(nombreFichero);
        if (borrado){
            creado = GestionFicheros.creadorFicheros(nombreFichero);
        }
        if (creado){
            for (Empleado empleado : empleados) {
                try {
                    GestionFicheros.escribirFichero(nombreFichero, empleado.toString());
                    // Lo guarda con una fecha de baja estandar (12 : 00 : 00 AM // 30-11-0002) para evitar
                    // problemas con los nulos
                } catch (IOException e) { // TODO
                    System.out.println("Fallo guardando el fichero : " + nombreFichero );
                }
            }
            System.out.println("Empleados guardados con exito");
        }
        Prints.finalFuncion();
    }

    public static void guardarTodo() {
        System.out.println("6. Guardar todo");
        Prints.separador();
        Prints.limpiar(1);

        guardarEmpleados("empleados.txt");
        guardarPapelera("copiaDeSeguridad.txt");
    }

    public static void listarPapelera() {
        System.out.println("7. Listar papelera");
        Prints.separador();
        Prints.limpiar(1);
        int i = 0;
        if (empleadosBorrados.isEmpty()){
            System.out.println("La papelera esta vacía");
        } else {
            for (Entry borrados:empleadosBorrados.entrySet()) {
                System.out.print("Empleado en papelera Nª" + (i + 1) + " --> ");
                System.out.println(borrados.getValue().toString());
                i++;
            }
        }
        System.out.println(empleadosBorrados);
        Prints.finalFuncion();
    }

    public static void guardarPapelera(String nombreFichero) {
        System.out.println("8. Guardar papelera");
        Prints.separador();
        Prints.limpiar(1);

        boolean creado = true;
        if (!nombreFichero.equals("empleados.txt")) {
            creado = false;
            boolean borrado = GestionFicheros.borrarFichero(nombreFichero);
            if (borrado) {
                creado = GestionFicheros.creadorFicheros(nombreFichero);
            }
        }
        if (creado){
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()){
                try {
                    GestionFicheros.escribirFichero(nombreFichero, entry.getValue().toString());
                } catch (IOException e) { // TODO
                    System.out.println("Fallo guardando el fichero : " + nombreFichero);
                }
            }
            System.out.println("Papelera guardada con exito");
        }
        Prints.finalFuncion();
    }

    public static void recuperarPapelera(){
        System.out.println("9. Recuperar papelera");
        Prints.separador();
        Prints.limpiar(1);

        Prints.limpiar(1);
        GestionFicheros.leerFichero("copiaDeSeguridad.txt", "papelera");
        Prints.finalFuncion();
    }

    public static void vaciarPapelera(){
        System.out.println("11. Vaciar papelera");
        Prints.separador();
        Prints.limpiar(1);

        empleadosBorrados.clear();
        Prints.limpiar(1);
        System.out.println("Papelera vaciada con exito");
        Prints.finalFuncion();
    }

    public static void restaurarPapelera(){
        System.out.println("10. Restaurar papelera");
        Prints.separador();
        Prints.limpiar(1);

        if (!empleadosBorrados.isEmpty()){
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()) {
                Empleado variableEmpleado = new Empleado();
                variableEmpleado.setCodigo(entry.getValue().getCodigo());
                variableEmpleado.setNombre(entry.getValue().getNombre());
                variableEmpleado.setPrimerApellido(entry.getValue().getPrimerApellido());
                variableEmpleado.setSegundoApellido(entry.getValue().getSegundoApellido());
                variableEmpleado.setDNI(entry.getValue().getDNI());
                variableEmpleado.setFechaNacimiento(entry.getValue().getFechaNacimiento());
                variableEmpleado.setNacionalidad(entry.getValue().getCodigo());
                variableEmpleado.setEstado(entry.getValue().getEstado());
                variableEmpleado.setFechaAlta(entry.getValue().getFechaAlta());
                variableEmpleado.setFechaBaja(entry.getValue().getFechaBaja());
                Direccion variableDireccion = new Direccion();
                variableDireccion.setCalle(entry.getValue().getDireccion().getCalle());
                variableDireccion.setNumero(entry.getValue().getDireccion().getNumero());
                variableDireccion.setBloque(entry.getValue().getDireccion().getBloque());
                variableDireccion.setPiso(entry.getValue().getDireccion().getPiso());
                variableDireccion.setPuerta(entry.getValue().getDireccion().getPuerta());
                variableDireccion.setCodigoPostal(entry.getValue().getDireccion().getCodigoPostal());
                variableDireccion.setLocalidad(entry.getValue().getDireccion().getLocalidad());
                variableDireccion.setProvincia(entry.getValue().getDireccion().getProvincia());
                variableEmpleado.setDireccion(variableDireccion);
                empleados.add(variableEmpleado);
            }
            System.out.println("Los empleados borrados han sido restaurados con exito. Recuerda que mientras no guardes los cambios no surtiran efecto");
        } else {
            System.out.println("Papelera vacía. Borre empleados o recupere la papelera");
        }
        Prints.finalFuncion();
    }

    public static void informe(){
        System.out.println("12. Informe");
        Prints.separador();
        Prints.limpiar(1);

        System.out.println("→ ¿Cuál es el empleado actual de mayor edad?");
        empleadoMasMayor(empleados);
        Prints.limpiar(1);

        System.out.println("→ ¿Cuál es el empleado actual de menor edad?");
        empleadoMasJoven(empleados);
        Prints.limpiar(1);

        System.out.println("→ ¿Cuántos empleados tiene la empresa actualmente?");
        Prints.limpiar(1);
        System.out.println("La empresa cuenta actualmente con : " + empleados.size() + " empleados en plantilla");

        Prints.finalFuncion();
    }

    // ------------------------> FUNCIONES <-----------------------------

    public static void vaciarScanner(Scanner in){
        in.skip("\n");
    }

    private static boolean eleccionSeguirConEsteEmpleadoOOtro(Scanner in, String palabra){
        boolean salida = false;
        if ("".equals(palabra)){
            Prints.siNo();
        } else {
            System.out.println("¿Quiere " + palabra + " a otro empleado o desea salir?");
            Prints.otroSalir();
        }
        switch (in.nextInt()){
            case 1:
                salida = true;
                break;
            case 2:
                salida = false;
                break;
        }
        return salida;
    }

    private static Empleado buscaEmpleadoPorCodigo(ArrayList<Empleado> empleados, String codigo){
        Empleado empleadoResultado = null;
        int contadorParaRecorrerElArray = 0;
        if (codigoEstaAsignadoAAlguien(empleados, codigo)){

            while (contadorParaRecorrerElArray < empleados.size() && empleadoResultado == null){
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
        if ("empleados".equals(palabra)){
            empleados.add(variableEmpleado);
        } else if ("papelera".equals(palabra)){
            variableEmpleado.setFechaBaja(Fecha.leerStringDevolviendoFechaFormateada(datoSeparado[17]));
            empleadosBorrados.put(variableEmpleado.getCodigo(), variableEmpleado);
        }
    }

    public static boolean codigoEstaAsignadoAAlguien(ArrayList<Empleado> empleados, String codigo){
        boolean resultado = false;

        int contadorParaRecorrerElArray = 0;
        while ((contadorParaRecorrerElArray < empleados.size()) && !resultado){
            if (empleados.get(contadorParaRecorrerElArray).getCodigo().equals(codigo)) {
                resultado = true;
            }
            contadorParaRecorrerElArray ++;
        }
        return resultado;
    }

    // ######## --------> FUNCIONES LEER <-------------------- #########

    private static String leerStringTeclado(Scanner in, String palabra){
        Prints.separadorConTexto(palabra);
        return in.nextLine();
    }

    private static int leerEstado(Scanner in){
        Prints.separadorConTexto("Estado");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return estadoEleccion(palabraIntroducida);
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

    // -------------------->  FUNCIONES CREAR  <------------------------

    private static void datosEmpleados( Empleado variableEmpleado, String[] datoSeparado) throws ParseException {

        // Almacena y guarda los datos del empleado.
        if (datoSeparado[0] == null){
            variableEmpleado.setCodigo(Alfanumerico.generar());
        } else {
            variableEmpleado.setCodigo(datoSeparado[0]);
        }
        variableEmpleado.setNombre(datoSeparado[1]);
        variableEmpleado.setPrimerApellido(datoSeparado[2]);
        variableEmpleado.setSegundoApellido(datoSeparado[3]);
        variableEmpleado.setDNI(datoSeparado[4]);
        variableEmpleado.setFechaNacimiento(Fecha.fecha(datoSeparado[5]));
        variableEmpleado.setNacionalidad(datoSeparado[6]);
        variableEmpleado.setEstado(Estado.values()[estadoEleccion(datoSeparado[7])]);
        variableEmpleado.setFechaAlta(Fecha.leerStringDevolviendoFechaFormateada(datoSeparado[16]));
        variableEmpleado.setFechaBaja(Fecha.leerStringDevolviendoFechaFormateada(null));
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
        variableEmpleado.setNombre(leerStringTeclado(in,"Nombre"));
        variableEmpleado.setPrimerApellido(leerStringTeclado(in,"Primer apellido"));
        variableEmpleado.setSegundoApellido(leerStringTeclado(in,"Segundo apellido"));
        variableEmpleado.setDNI(leerStringTeclado(in,"DNI"));
        variableEmpleado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in,"Fecha de nacimiento")));
        variableEmpleado.setNacionalidad(leerStringTeclado(in,"Nacionalidad"));
        variableEmpleado.setEstado(Estado.values()[leerEstado(in)]);
        variableEmpleado.setFechaAlta(Fecha.creaciónFechaActual());
        variableEmpleado.setFechaBaja(Fecha.leerStringDevolviendoFechaFormateada(null));
    }

    private static Direccion datosDireccionPorTeclado(Scanner in){

        Direccion variableDireccion = new Direccion();
        variableDireccion.setCalle(leerStringTeclado(in,"Calle"));           // Calle
        variableDireccion.setNumero(leerNumero(in));                                // Numero
        variableDireccion.setBloque(leerStringTeclado(in,"Bloque"));         // Bloque
        variableDireccion.setPiso(leerStringTeclado(in,"Piso"));             // Piso
        variableDireccion.setPuerta(leerStringTeclado(in,"Puerta"));         // Puerta
        variableDireccion.setCodigoPostal(leerCodigoPostal(in));                    // Codigo postal
        variableDireccion.setLocalidad(leerStringTeclado(in,"Localidad"));   // Localidad
        variableDireccion.setProvincia(leerStringTeclado(in,"Provinicia"));  // Provincia
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

    private static void cambioNombre(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNombre(leerStringTeclado(in,""));
    }

    private static void cambioApellidos(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setPrimerApellido(leerStringTeclado(in,"Primer apellido"));
        empleadoBuscado.setSegundoApellido(leerStringTeclado(in,"Segundo apellido"));
    }

    private static void cambioDNI(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setDNI(leerStringTeclado(in,"DNI"));
    }

    private static void cambioFechaNacimiento(Scanner in, Empleado empleadoBuscado) throws ParseException {
        empleadoBuscado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in,"Fecha de nacimiento")));
    }

    private static void cambioNacionalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setNacionalidad(leerStringTeclado(in,"Nacionalidad"));
    }

    private static void cambioCalle(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCalle(leerStringTeclado(in,"Calle"));
    }

    private static void cambioNumero(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setNumero(leerNumero(in));
    }

    private static void cambioBloque(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setBloque(leerStringTeclado(in,"Bloque"));
    }

    private static void cambioPiso(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPiso(leerStringTeclado(in,"Piso"));
    }

    private static void cambioPuerta(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setPuerta(leerStringTeclado(in,"Puerta"));
    }

    private static void cambioCodigoPostal(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setCodigoPostal(leerCodigoPostal(in));
    }

    private static void cambioLocalidad(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setLocalidad(leerStringTeclado(in,"Localidad"));
    }

    private static void cambioProvincia(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.getDireccion().setProvincia(leerStringTeclado(in,"Provincia"));
    }

    // ---------------------------------------- FUNCIONES INFORME ------------------------------------------------

    // ¿Cuál es el empleado actual de mayor edad?

    private static void empleadoMasMayor(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        try {
            System.out.println("Empleado más mayor  ↓");
            System.out.println(empleadoConMasEdad(empleados).cadenaFormateadaParaMostrarPorPantalla());
        }catch (NullPointerException e){
            System.out.println("Al compararse las fechas se ha encontrado que ningun empleado es mas viejo que la fecha actual del equipo");
        }
    }

    private static Empleado empleadoConMasEdad(ArrayList<Empleado> empleados){
        Date fechaMasBaja = Fecha.creaciónFechaActual();
        Empleado empleadoMasMayor = new Empleado();
        for (Empleado empleadoSepardoDelArrayList : empleados) {
            if (empleadoSepardoDelArrayList.getFechaNacimiento().before(fechaMasBaja)) {
                empleadoMasMayor = empleadoSepardoDelArrayList;
                fechaMasBaja = empleadoSepardoDelArrayList.getFechaNacimiento();
            }
        }
        return empleadoMasMayor;
    }

    // ¿Cuál es el empleado actual de menor edad?

    private static void empleadoMasJoven(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        try {
            System.out.println("Empleado más joven  ↓");
            System.out.println(empleadoConMenosEdad(empleados).cadenaFormateadaParaMostrarPorPantalla());

        } catch (NullPointerException e) {
            System.out.println("Al compararse las fechas se ha encontrado que ningun empleado es mas joven que la fecha : 30-11-0002");
        }
    }

    private static Empleado empleadoConMenosEdad(ArrayList<Empleado> empleados){
        Date fechaMasBaja = null;
        String fecha = "30-11-0002";
        try {
            fechaMasBaja = Fecha.fecha(fecha);
        } catch (ParseException e){ // Nunca deberia de darse este caso porque la fecha esta introducida como una variable y no como un Scanner
            System.out.println("Error en la fecha base");
        }
        Empleado empleadoMasMayor = new Empleado();
        for (Empleado empleadoSepardoDelArrayList : empleados) {
            if (empleadoSepardoDelArrayList.getFechaNacimiento().after(fechaMasBaja)) {
                empleadoMasMayor = empleadoSepardoDelArrayList;
                fechaMasBaja = empleadoSepardoDelArrayList.getFechaNacimiento();
            }
        }
        return empleadoMasMayor;
    }

    // ¿Cuántos empleados se han dado de baja el año actual?
    // TODO Extraer el año mediante alguna función del Date.Una vez con esa fecha hacer un array con los empleados que
    //  estan baja y luego seleccionar de esos que el año sea el mismo

    private static void dadoDeBajaEsteAño(ArrayList<Empleado> empleados){

        SimpleDateFormat sacarElAñoDeLaFecha = new SimpleDateFormat("yyyy");
        Date añoActual = Fecha.creaciónFechaActual();
        ArrayList<Empleado> empleadosBorradosEsteAño = new ArrayList<> ();

        for (Empleado empleadoSepardoDelArrayList : empleados) {
            if (sacarElAñoDeLaFecha.format(añoActual).equals(sacarElAñoDeLaFecha.format(empleadoSepardoDelArrayList.getFechaBaja()))) {
                empleadosBorradosEsteAño.add(empleadoSepardoDelArrayList);
            }
        }
    }

    // ¿Qué directivo es responsable de más departamentos?
    // ¿Cuál es el directivo responsable del departamento X?
    // ¿Qué empleado tiene más titulaciones en especialidades?
    // ¿Cuál es la titulación de la especialidad más reciente y a quién pertenece?

}
