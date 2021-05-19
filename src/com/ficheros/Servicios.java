package com.ficheros;

import com.modelos.*;
import com.utilidades.GeneradorCodigos;
import com.utilidades.Fecha;
import com.utilidades.Prints;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import static java.util.Map.*;
@SuppressWarnings({"rawtypes", "NonAsciiCharacters"})

public class Servicios {

    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static ArrayList<Contrato> contratos = new ArrayList<>();
    public static ArrayList empleadosModificados = new ArrayList<>();
    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void listarContratos(Scanner in){

        Prints.introduzcaDatos(in);
        String codigo;
        Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, codigo = in.nextLine());
        System.out.println(empleadoBuscado.getContratos());
        System.out.println(contratos);

        Prints.finalFuncion();
    }

    public static void guardarMemoriaABaseDeDatos(){
        for ( int i = 0; i < empleados.size(); i++){
            GestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_PRUEBA", empleados.get(i));
        }
    }

    public static void cargarEmpleadosDesdeBaseDeDatos(){
        GestionBaseDeDatos.cargarFilaBaseDeDatos("FPM_PRUEBA", empleados);
    }

    public static void crear(Scanner in) {
        System.out.println("Crear");
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

    public static void crearContrato (Scanner in){
        System.out.println("Crear contrato");
        boolean salida;
        do {
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados,in.nextLine());
            if (empleadoBuscado != null){
                System.out.println("Ha seleccionado a "+ empleadoBuscado.getNombre() +" ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in,"sino");
                if (salida){
                    generarContrato(contratos,empleadoBuscado,in);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "crear un contrato");
                }
            } else {
                System.out.println("Código erroneo, cerrando acción");
                salida = true;
            }
        }while (!salida);
        Prints.finalFuncion();
    }

    public static void listarContratos() {
        System.out.println("Contratos");
        Prints.separador();
        Prints.limpiar(1);
        if(contratos.isEmpty()){
            System.out.println("No existen todavia contratos registrados");
        } else {
            for ( int i = 0; i < contratos.size(); i++){
                System.out.println("Contrato Nº " + (i + 1) + " --> " + contratos.get(i).toString());
            }
        }
        Prints.finalFuncion();
    }

    public static void listarEmpleados(String numero) {
        System.out.println(numero + "Listado");
        Prints.separador();
        Prints.limpiar(1);
        if(empleados.isEmpty()){
            System.out.println("No hay todavia ningun empleado registrado");
        } else {
            for ( int i = 0; i < empleados.size(); i++){
                System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i).cadenaFormateadaParaMostrarPorPantalla());
            }
        }
        Prints.finalFuncion();
    }

    public static void borrado(Scanner in) {
        boolean salida;
        System.out.println("4. Borrado");
        do {
            Prints.introduzcaDatos(in);
            String codigo;
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, codigo = in.nextLine());
            if (empleadoBuscado != null){
                System.out.println("Ha seleccionado a "+ empleadoBuscado.getNombre() +" ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in,"sino");
                if (salida){
                    accionBorradoEmpleado(empleados, codigo, empleadoBuscado);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "borrado");
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
        System.out.println("5. Modificar");
        do {
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null){
                System.out.println("Ha seleccionado a "+ empleadoBuscado.getNombre() +" ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in,"sino");
                if (salida){
                    elecciónDeGrupoQueQuiereCambiar(in, empleadoBuscado);
                }else {
                    salida = eleccionSiNoYSalirOOtro(in, "cambiar");
                }
            } else {
                System.out.println("Codigo erroneo, volviendo a menú principal");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    }

    public static void guardarEmpleados(String nombreFichero) {
        System.out.println("6. Guardar empleados");
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
                } catch (IOException e) { // TODO
                    System.out.println("Fallo guardando el fichero : " + nombreFichero );
                }
            }
            System.out.println("Empleados guardados con exito");
        }
        Prints.finalFuncion();
    }

    public static void guardarTodo() {
        System.out.println("7. Guardar todo");
        Prints.separador();
        Prints.limpiar(1);

        guardarEmpleados("empleados.txt");
        guardarPapelera("copiaDeSeguridad.txt");
    }

    public static void listarPapelera() {
        System.out.println("8. Listar papelera");
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
        System.out.println("9. Guardar papelera");
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
        System.out.println("10. Recuperar papelera");
        Prints.separador();
        Prints.limpiar(1);

        Prints.limpiar(1);
        GestionFicheros.leerFichero("copiaDeSeguridad.txt", "papelera");
        Prints.finalFuncion();
    }

    public static void restaurarPapelera(){
        System.out.println("11. Restaurar papelera");
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
                variableEmpleado.setNacionalidad(entry.getValue().getNacionalidad());
                variableEmpleado.setEstado(entry.getValue().getEstado());
                variableEmpleado.setFechaAlta(entry.getValue().getFechaAlta());
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

    public static void vaciarPapelera(){
        System.out.println("12. Vaciar papelera");
        Prints.separador();
        Prints.limpiar(1);

        empleadosBorrados.clear();
        Prints.limpiar(1);
        System.out.println("Papelera vaciada con exito");
        Prints.finalFuncion();
    }

    public static void informe(){
        System.out.println("13. Informe");
        Prints.separador();
        Prints.limpiar(1);

        Informe.generarInforme(empleados);

        Prints.finalFuncion();
    }

    // ------------------------> FUNCIONES <-----------------------------

    private static boolean eleccionSiNoYSalirOOtro(Scanner in, String palabra){
        boolean salida;
        if ("sino".equals(palabra)){
            Prints.siNo();
        } else {
            System.out.println("¿Quiere " + palabra + " a otro empleado o desea salir?");
            Prints.otroSalir();
        }
        int eleccionParseada = transformaStringAIntDevuelveInt(in);
        switch (eleccionParseada){
            case 1:
                salida = true;
                break;
            case 2:
                salida = false;
                break;

            default:
                throw new IllegalStateException("Dato introducido erroneo, recuerde que solo puede poner 1 o 2");
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
            codigo = GeneradorCodigos.generarCodigoEmpleados();
        }while (codigoEstaAsignadoAAlguien(empleados, codigo));
        variableEmpleado.setCodigo(codigo);
        System.out.println(variableEmpleado.getCodigo()); }

    public static int estadoEleccion(String palabraIntroducida){

        String palabraIntroducidaMayusculas = palabraIntroducida.toUpperCase();
        int eleccion;
        switch (palabraIntroducidaMayusculas) {
            case "ALTA":
                eleccion = 0;
                break;
            case "BAJA":
                eleccion = 1;
                break;
            case "EN TRÁMITE":
                eleccion = 2;
                break;
            default:
                System.out.println("Palabra introducida erronea, se establecera ''Alta'' como predeterminado," +
                        "porfavor cambie el dato con la acción modificar");
                eleccion = 0;
                break;
        }
        return eleccion;
    }

    private static int puestoEleccion(String palabraIntroducida){
        String palabraIntroducidaMayusculas = palabraIntroducida.toUpperCase();
        int eleccion;
        switch (palabraIntroducidaMayusculas) {
            case "DIRECTIVO":
                eleccion = 0;
                break;
            case "OFICIAL":
                eleccion = 1;
                break;
            case "OPERARIO":
                eleccion = 2;
                break;
            case "TECNICO":
                eleccion = 3;
                break;
            default:
                System.out.println("Palabra introducida erronea, se establecera ''Operario'' como predeterminado," +
                        "porfavor cambie el dato con la acción modificar");
                eleccion = 2;
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

    public static int transformaStringAIntDevuelveInt(Scanner in){
        boolean salida = false;
        int eleccionParseado = 0;
        int contador = 0;

        do{
            try {
                String eleccion = in.nextLine();
                eleccionParseado = Integer.parseInt(eleccion);
                salida = true;
            }catch (NumberFormatException e){
                System.out.println("Formato erroneo, vuelva a repetir. Le quedan " + (3-contador));
                System.out.println("> ");
                contador ++;
            }
            if (contador == 3){
                System.out.println("No quedan intentos");
                eleccionParseado = -1;
            }
        } while(!salida);

        return eleccionParseado;
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

    public static int leerPuesto(Scanner in){
        Prints.separadorConTexto("Puesto");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return puestoEleccion(palabraIntroducida);
    }

    private static int leerNumero(Scanner in){
        Prints.separadorConTexto("Numero");
        int resultado = 0;

        resultado = transformaStringAIntDevuelveInt(in);
        if (resultado == -1){
            System.out.println("Se establecera 0 por defecto");
            resultado = 0;
        }
        return resultado;
    }

    private static int leerCodigoPostal(Scanner in){
        Prints.separadorConTexto("Codigo postal");
        int resultado = 0;
        try {
            resultado = transformaStringAIntDevuelveInt(in);
        } catch (Exception e){
            System.out.println("Error al introducir el código postal. Se establecera 00000 como predetermiando");
        }
        return resultado;
    }

    // -------------------->  FUNCIONES CREAR  <------------------------

    private static void datosEmpleados( Empleado variableEmpleado, String[] datoSeparado) throws ParseException {

        // Almacena y guarda los datos del empleado.

        if (datoSeparado[0].equals("null")){
            variableEmpleado.setCodigo(GeneradorCodigos.generarCodigoEmpleados());
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
        if ( datoSeparado[16].equals("null")){
            variableEmpleado.setFechaAlta(Fecha.leerStringDevolviendoFechaFormateada(null));
        } else {
            variableEmpleado.setFechaAlta(Fecha.leerStringDevolviendoFechaFormateada(datoSeparado[17]));
        }
   }

    private static Direccion datosDireccion( String[] datoSeparado){

        // Almacena y guarda los datos sobre la direccion
        Direccion variableDireccion = new Direccion();
        variableDireccion.setCodigo(Integer.parseInt(datoSeparado[8]));
        variableDireccion.setCalle(datoSeparado[9]);
        variableDireccion.setNumero(Integer.parseInt(datoSeparado[10]));
        variableDireccion.setBloque(datoSeparado[11]);
        variableDireccion.setPiso(datoSeparado[12]);
        variableDireccion.setPuerta(datoSeparado[13]);
        variableDireccion.setCodigoPostal(Integer.parseInt(datoSeparado[14]));
        variableDireccion.setLocalidad(datoSeparado[15]);
        variableDireccion.setProvincia(datoSeparado[16]);
        return variableDireccion;
    }

    private static void datosEmpleadosPorTeclado( Scanner in , Empleado variableEmpleado) throws ParseException {

        variableEmpleado.setNombre(leerStringTeclado(in,"Nombre"));
        variableEmpleado.setPrimerApellido(leerStringTeclado(in,"Primer apellido"));
        variableEmpleado.setSegundoApellido(leerStringTeclado(in,"Segundo apellido"));
        variableEmpleado.setDNI(leerStringTeclado(in,"DNI"));
        variableEmpleado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in,"Fecha de nacimiento")));
        variableEmpleado.setNacionalidad(leerStringTeclado(in,"Nacionalidad"));
        variableEmpleado.setEstado(Estado.values()[leerEstado(in)]); // TODO Revisar
        variableEmpleado.setFechaAlta(Fecha.creaciónFechaActual());
    }

    private static Direccion datosDireccionPorTeclado(Scanner in){

        Direccion variableDireccion = new Direccion();
        variableDireccion.setCodigo(GeneradorCodigos.generarCodigoDirección());     // Codigo
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

    // -------------------------------> FUNCIONES CONTRATOS <---------------------------

    private static void generarContrato(ArrayList<Contrato> contratoArrayList, Empleado empleadoBuscado, Scanner in){
        boolean seguir;
        Contrato contrato = new Contrato();

        seguir = establecerFechasParaContrato(empleadoBuscado,in, contrato);
        if (seguir){
            seguir = establecerSalarioParaContrato(in,contrato);
            if (seguir) {
                establecerPuesto(in, contrato);
                contrato.setId(empleadoBuscado.getContratos().size()); // TODO
                empleadoBuscado.getContratos().add(contrato);
                contratoArrayList.add(contrato);
            }
        }
    }

    private static boolean establecerFechasParaContrato( Empleado empleadoBuscado, Scanner in , Contrato contrato){
        Prints.generarContrato();
        int eleccion = transformaStringAIntDevuelveInt(in);
        boolean resultado = true;

        contrato.setFechaInicioContrato(empleadoBuscado.getFechaAlta());
        if (eleccion == 1) {
            establecerFechaSalidaDesdeTeclado(in,contrato);
        } else if (eleccion == 2){
            contrato.setFechaFinalContrato(null);
        } else {
            System.out.println("Elección errónea. Volviendo al menu principal");
            resultado = false;
        }
        return resultado;
    }

    private static void establecerFechaSalidaDesdeTeclado(Scanner in, Contrato contrato){
        boolean salida;
        String fecha;
        int contador = 0;

        do{
            System.out.println("Introduzca una fecha con el siguiente formato -> hh : mm : ss AM/PM // dd-MM-yyyy");
            System.out.println("Es importante que respete los espacios y ponga AM o PM");
            System.out.print("> ");
            try {
                fecha = in.nextLine();
                contrato.setFechaFinalContrato(Fecha.leerStringDevolviendoFechaFormateada(fecha));
                salida = true;
                if (contrato.getFechaFinalContrato().before(contrato.getFechaInicioContrato())){
                    System.out.println("La fecha introducida es antes de la fecha de alta del empleado");
                    salida = false;
                }
            } catch (ParseException e) {
                System.out.println("Ha cometido un error de formato con la fecha");
                salida = false;
            }
            if (!salida){
                System.out.println("Quedan " + (3-contador) + " intentos" + "\n");
                contador ++;
            }
            if (contador == 3){
                System.out.println("Ha fallado 3 veces, se establecera la fecha de estimacion como null");
                contrato.setFechaFinalContrato(null);
                salida = true;
            }
        }while (!salida );

    }

    private static boolean establecerSalarioParaContrato(Scanner in, Contrato contrato){
        boolean resultado;
        int contador = 0;

        do {
            Prints.separador();
            System.out.println("Introduzca el salario");
            System.out.print("> ");
            try{
                String salario = in.nextLine();
                double salarioParseado = Double.parseDouble(salario);
                contrato.setSalario(salarioParseado);
                resultado = true;
            } catch (NumberFormatException e){
                contador ++;
                System.out.println("Ha cometido un error, recuerde introducir solo números ( con dos decimales ) " +
                        "vuelva a intentarlo te quedan " + ( 3 - contador) + "\n");
                resultado = false;
            }
            if (contador == 3){
                double salarioPorDefecto = 1108.3;
                System.out.println("Ha fallado 3 veces, se establecera por defecto el salario minimo interprofesional");
                contrato.setSalario(salarioPorDefecto);
                resultado = true;
            }
        }while( !resultado );

        return resultado;
    }

    private static void establecerPuesto (Scanner in, Contrato contrato){
        contrato.setPuesto(Puesto.values()[leerPuesto(in)]);
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados , String codigo, Empleado empleadoBuscado){

        // Al empleado buscado, le asigna una fecha de borrado, lo guarda en un mapa y lo borra del array
        int ultimoContrato = empleadoBuscado.getContratos().size();
        empleadoBuscado.getContratos().get(ultimoContrato).setFechaFinalContrato(Fecha.creaciónFechaActual());
        empleadosBorrados.put(codigo,empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------

    private static void elecciónDeGrupoQueQuiereCambiar(Scanner in, Empleado empleadoBuscado) {

        Prints.eleccionModificar();
        System.out.println("Elija que campo quiere cambiar");
        int decision = transformaStringAIntDevuelveInt(in);
        boolean salida = true;
        do {
            try {
                if (decision == 1) { // Campos informacion personal

                    cambioCamposPersonales(in, empleadoBuscado);

                } else if (decision == 2) { // Campos de direccion

                    cambioCamposDireccion(in, empleadoBuscado);

                } else if (decision == 3) { // Estado

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
}
