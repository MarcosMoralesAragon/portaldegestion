package com.servicios;

import com.modelos.*;
import com.utilidades.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.*;
@SuppressWarnings({"rawtypes", "NonAsciiCharacters"})

public class ServiciosGeneral {

    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static ArrayList<Empleado> empleadosNuevos = new ArrayList<>();
    public static ArrayList<Empleado> empleadosModificados = new ArrayList<>();
    public static ArrayList<Empleado> empleadosBorradosNuevos = new ArrayList<>();

    public static ArrayList<Contrato> contratos = new ArrayList<>();
    public static ArrayList<Contrato> contratosNuevos = new ArrayList<>();

    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    private final ServiciosBaseDeDatos gestionBaseDeDatos = new ServiciosBaseDeDatos();
    private final ServiciosFicheros gestionFicheros = new ServiciosFicheros();
    private final ServiciosInformes informes = new ServiciosInformes();

    private final Prints prints = new Prints();

    public void crearEmpleado(Scanner in) {
        prints.escribir("1. Crear");
        String deDondeVieneElDato = "teclado";
        try {
            Empleado variableEmpleado = new Empleado();
            datosEmpleados(in, variableEmpleado, deDondeVieneElDato, null, null, null);
            if (variableEmpleado.getDNI() != null){
                variableEmpleado.setDireccion(datosDireccion(in, deDondeVieneElDato, null, null, null));
                empleados.add(variableEmpleado);
                empleadosNuevos.add(variableEmpleado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        prints.finalFuncion();
    } // 1

    public void crearContrato(Scanner in) {
        prints.escribir("2. Crear contrato");
        boolean salida;
        do {
            prints.introduzcaDatos();
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null) {
                prints.escribir("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    generarContrato(contratos, empleadoBuscado, in);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "crear un contrato");
                }
            } else {
                prints.escribir("Código erroneo, cerrando acción");
                salida = true;
            }
        } while (!salida);
        prints.finalFuncion();
    } // 2

    public void listarEmpleados(String numero) {
        prints.escribir(numero + "Listado");
        prints.separador();
        prints.limpiar(1);
        if (empleados.isEmpty()) {
            prints.escribir("No hay todavia ningun empleado registrado");
        } else {
            for (int i = 0; i < empleados.size(); i++) {
                prints.escribir("Empleado Nº " + (i + 1) + " --> " + empleados.get(i).cadenaFormateadaParaMostrarPorPantalla());
            }
        }
        prints.finalFuncion();
    } // 3

    public void listarContratos() {
        prints.escribir("4. Contratos");
        prints.separador();
        prints.limpiar(1);
        if (contratos.isEmpty()) {
            prints.escribir("No existen todavia contratos registrados");
        } else {
            for (int i = 0; i < contratos.size(); i++) {
                prints.escribir("Contrato Nº " + (i + 1) + " --> " + contratos.get(i).toString());
            }
        }
        prints.finalFuncion();
    } // 4

    public void listarPapelera() {
        prints.escribir("5. Listar papelera");
        prints.separador();
        prints.limpiar(1);
        int i = 0;
        if (empleadosBorrados.isEmpty()) {
            prints.escribir("La papelera esta vacía");
        } else {
            for (Entry borrados : empleadosBorrados.entrySet()) {
                prints.escribir("Empleado en papelera Nª" + (i + 1) + " --> ");
                prints.escribir(borrados.getValue().toString());
                i++;
            }
        }
        prints.finalFuncion();
    } // 5

    public void modificar(Scanner in) {
        boolean salida;
        prints.escribir("6. Modificar");
        do {
            prints.introduzcaDatos();
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null) {
                prints.escribir("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    elecciónDeGrupoQueQuiereCambiar(in, empleadoBuscado);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "cambiar");
                }
            } else {
                prints.escribir("Codigo erroneo, volviendo a menú principal");
                salida = true;
            }
        } while (!salida);
        prints.finalFuncion();
    } // 6

    public void borrado(Scanner in) {
        boolean salida;
        prints.escribir("7. Borrado");
        do {
            prints.introduzcaDatos();
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null) {
                prints.escribir("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    accionBorradoEmpleado(empleados, empleadoBuscado);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "borrado");
                }
            } else {
                prints.escribir("Código erroneo, cerrando acción");
                salida = true;
            }
        } while (!salida);
        prints.finalFuncion();
    } // 7

    public void cargarPapelera() {
        prints.escribir("8. Recuperar papelera");
        prints.separador();
        prints.limpiar(2);
        gestionFicheros.leerFichero("copiaDeSeguridad.txt", "papelera");
        prints.finalFuncion();
    } // 8

    public void guardarPapelera(String nombreFichero) {
        prints.escribir("9. Guardar papelera");
        prints.separador();
        prints.limpiar(1);

        boolean creado = true;
        if (!nombreFichero.equals("empleados.txt")) {
            creado = false;
            boolean borrado = gestionFicheros.borrarFichero(nombreFichero);
            if (borrado) {
                creado = gestionFicheros.creadorFicheros(nombreFichero);
            }
        }
        if (creado) {
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()) {
                try {
                    gestionFicheros.escribirFichero(nombreFichero, entry.getValue().toString());
                } catch (IOException e) { // TODO
                    prints.escribir("Fallo guardando el fichero : " + nombreFichero);
                }
            }
            prints.escribir("Papelera guardada con exito");
        }
        prints.finalFuncion();
    } // 9

    public void restaurarPapelera() {
        // Mete los empeleados del mapa a donde van los borrados, y los introduce en el arraylist
        // de empleados
        prints.escribir("10. Restaurar papelera");
        prints.separador();
        prints.limpiar(1);
        String deDondeVieneElDato = "papelera";

        if (!empleadosBorrados.isEmpty()) {
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()) {
                Empleado variableEmpleado = new Empleado();
                try {
                    datosEmpleados(null, variableEmpleado, deDondeVieneElDato, null, entry, null);
                    variableEmpleado.setDireccion(datosDireccion(null, deDondeVieneElDato, null, entry, null));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ParseException e) {
                    prints.escribir("Problema con la conversion de los parametros");
                }
                empleados.add(variableEmpleado);
            }
            empleadosBorrados.clear();
            empleadosBorradosNuevos.clear();
            prints.escribir("Los empleados borrados han sido restaurados con exito. Recuerda que mientras no guardes los cambios no surtiran efecto");
        } else {
            prints.escribir("Papelera vacía. Borre empleados o recupere la papelera");
        }
        prints.finalFuncion();
    } // 10

    public void vaciarPapelera() {
        prints.escribir("11. Vaciar papelera");
        prints.separador();
        prints.limpiar(1);
        empleadosBorrados.clear();
        prints.limpiar(1);
        prints.escribir("Papelera vaciada con exito");
        prints.finalFuncion();
    } // 11

    public void informe() {
        prints.escribir("12. Informe");
        prints.separador();
        prints.limpiar(1);
        informes.generarInforme(empleados);
        prints.finalFuncion();
    } // 12

    public void guardarEmpleadosAMemoriaABaseDeDatos() {
        prints.escribir("13. Guardar empleados a la base de datos");
        prints.separador();
        prints.limpiar(1);
        if (empleadosNuevos.size() == 0) {
            prints.escribir("No existen empleados nuevos creados. Cree un empleado nuevo");
        } else {
            for (Empleado empleadosNuevo : empleadosNuevos) {
                gestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_PRUEBA", empleadosNuevo, null);
            }
        }
        prints.finalFuncion();
    } // 13

    public void updateEmpleadosABaseDeDatos() {
        prints.escribir("14. Actualizar empleados a la base de datos");
        prints.separador();
        prints.limpiar(1);
        if (empleadosModificados.size() == 0) {
            prints.escribir("No se ha modificado ningun empleado. Modifique un empleado");
        } else for (Empleado empleadosModificado : empleadosModificados) {
            Connection conexion = gestionBaseDeDatos.cargarBaseDeDatos("");
            try {
                gestionBaseDeDatos.updateTodoBaseDeDatos(empleadosModificado);
                break;
            } catch (SQLException throwables) {
                prints.escribir("Error actualizando las tablas");
            } finally {
                prints.limpiar(1);
                try {
                    conexion.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        prints.finalFuncion();
    } // 14

    public void guardarTodosLosCambios(){
        prints.escribir("15. Guardar empleados a la base de datos");
        prints.separador();
        prints.limpiar(1);
        if (empleadosNuevos.size() == 0) {
            prints.escribir("No existen empleados nuevos creados. Cree un empleado nuevo");
        } else {
            for (Empleado empleadosNuevo : empleadosNuevos) {
                gestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_EMPLEADOS", empleadosNuevo, null);
            }
        }
        if (contratosNuevos.size() == 0) {
            prints.escribir("No existen contratos nuevos creados. Cree un contrato nuevo");
        } else {
            for (Contrato contratos : contratosNuevos) {
                gestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_CONTRATOS", null, contratos);
            }
        }
        if (empleadosBorradosNuevos.size() > 0){
            // TODO ??
            for(Empleado empleadosBorrados : empleadosBorradosNuevos){
                gestionBaseDeDatos.borrarFilaBaseDeDatos(empleadosBorrados);
            }
        }
        guardarPapelera("copiaDeSeguridad.txt");
        prints.finalFuncion();
    } // 15

    public void cargarEmpleadosDesdeBaseDeDatos() {
        prints.separador();
        prints.limpiar(1);
        empleados = gestionBaseDeDatos.cargarFilaBaseDeDatos("FPM_EMPLEADOS", empleados);
    }

    // ------------------------> FUNCIONES GENERALES <-----------------------------

    /**
     * JavaDoc
     * @param in Pasar el scanner
     * @param palabra Cual es la accion que quiere hacer
     * @return Devuelve un boolean que determina si va a seguir o sale
     */
    private  boolean eleccionSiNoYSalirOOtro(Scanner in, String palabra) {
        boolean salida;
        if ("sino".equals(palabra)) {
            prints.siNo();
        } else {
            prints.escribir("¿Quiere " + palabra + " a otro empleado o desea salir?");
            prints.otroSalir();
        }
        int eleccionParseada = transformaStringAIntDevuelveInt(in);
        switch (eleccionParseada) {
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

    /**
     * Busca a un empleado en base de su codigo en la lista de empleados
     * @param empleados Arraylist de empleados
     * @param codigo Codigo del empleado que queremos buscar
     * @return Un empleado ( si no lo encuentra será null )
     */
    private  Empleado buscaEmpleadoPorCodigo(ArrayList<Empleado> empleados, String codigo) {
        Empleado empleadoResultado = null;
        int contadorParaRecorrerElArray = 0;
        if (codigoExiste(empleados, 0, codigo, "empleados")) {

            while (contadorParaRecorrerElArray < empleados.size() && empleadoResultado == null) {
                if (empleados.get(contadorParaRecorrerElArray).getCodigo().equals(codigo)) {
                    empleadoResultado = empleados.get(contadorParaRecorrerElArray);
                }
                contadorParaRecorrerElArray++;
            }
        }
        return empleadoResultado;
    }

    /**
     * Un bucle que genera códigos hasta que consigue crear una convinación que no exista
     * @param nombreLista Donde quieres buscar, pueder : "direccion", "contratos", "emplados"
     * @return Un string con un codigo ( el codigo int es parseado a string y luego se parsea de vuelta
     */
    private String generarCodigo(String nombreLista) {
        int codigoInt = 0;
        String codigoString = "";
        String resultado = "";
        ArrayList lista = empleados;
        do {
            GeneradorCodigos generadorCodigos = new GeneradorCodigos();
            if ("direccion".equals(nombreLista) || "contratos".equals(nombreLista)) {
                codigoInt = generadorCodigos.generarCodigoDirección();
                resultado = String.valueOf(codigoInt);
                if("contratos".equals(nombreLista)){
                    lista = contratos;
                }
            }
            if ("empleados".equals(nombreLista)) {
                codigoString = generadorCodigos.generarCodigoEmpleados();
                resultado = codigoString;
            }
        } while (codigoExiste(lista, codigoInt, codigoString, nombreLista));
        return resultado;
    }

    /**
     * Busca si ese codigo existe en la lista entregada, comparando todos los codigos establecidos. En caso de que
     * encuentre alguno igual devolvera true, en caso de no encontrar uno igual devolvera false
     * @param lista Lista en la que se va a revisar todos los codigos
     * @param codigoInt El código que se asigna a la direccion/contratos
     * @param codigoString El código que se asigna a los empleados
     * @param nombreLista Donde quieres buscar, pueder : "direccion", "contratos", "emplados"
     * @return false = no existe / true = existe
     */
    public boolean codigoExiste(ArrayList lista, int codigoInt, String codigoString, String nombreLista) {
        boolean resultado = false;

        switch (nombreLista.toLowerCase(Locale.ROOT)) { // TODO Control null
            case "empleados":
                for (Empleado empleado : (ArrayList<Empleado>) lista) {
                    if (empleado.getCodigo().equals(codigoString)) {
                        resultado = true;
                    }
                }
                break;
            case "contratos":
                for (Contrato contrato : (ArrayList<Contrato>) lista) {
                    if (contrato.getId() == codigoInt) {
                        resultado = true;
                    }
                }
                break;
            case "direccion":
                for (Empleado empleado : (ArrayList<Empleado>) lista) {
                    if (empleado.getDireccion().getCodigo() == codigoInt) {
                        resultado = true;
                    }
                }
                break;
            default:
                resultado = false;
                prints.escribir("No existe la lista elegida, no se podrá comparar el codigo"); // TODO
        }
        return resultado;
    }

    /**
     * Pone en upercase la palaba introducida para compararla, en caso de que la palabra no sea un estado establecido
     * se pone en Alta automatico
     * @param palabraIntroducida Recibe un String con una palabra para poner un estado
     * @return Devuelve un numero (int) en funcion de a que grupo se parecía
     */
    public  int estadoEleccion(String palabraIntroducida) {

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
                prints.escribir("Palabra introducida erronea, se establecera ''Alta'' como predeterminado," +
                        "porfavor cambie el dato con la acción modificar");
                eleccion = 0;
                break;
        }
        return eleccion;
    }

    /**
     * Pone en upercase la palabra introducida para compararla, en caso de que la plaba no sea un puesto de los establecidos
     * se establece de manera automatica el puesto Operario
     * @param palabraIntroducida Recibe un string con una palabra y lo compara
     * @return Devuelve un numero (int) en funcion de a que grupo se parecía
     */
    public int puestoEleccion(String palabraIntroducida) {
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
                prints.escribir("Palabra introducida erronea, se establecera ''Operario'' como predeterminado," +
                        "porfavor cambie el dato con la acción modificar");
                eleccion = 2;
                break;
        }
        return eleccion;
    }

    /**
     * Introduce en memoria los datos almacenados en los ficheros
     * @param datoSeparado Un String[] que viene de separar los datos de un fichero
     * @param palabra Puede ser: "papelera" o "fichero"
     */
    public void cargarLista(String[] datoSeparado, String palabra) throws ParseException {
        Empleado variableEmpleado = new Empleado();
        String deDondeVieneElDato = palabra;
        if ("papelera".equals(palabra)) {
            deDondeVieneElDato = "fichero";
        }
        try {
            datosEmpleados(null, variableEmpleado, deDondeVieneElDato, null, null, datoSeparado);
            variableEmpleado.setDireccion(datosDireccion(null, deDondeVieneElDato, null, null, datoSeparado));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if ("fichero".equals(palabra)) {
            empleados.add(variableEmpleado);
        } else if ("papelera".equals(palabra)) {
            empleadosBorrados.put(variableEmpleado.getCodigo(), variableEmpleado);
        }
    }

    /**
     * Lee el dato y lo parsea a int, tiene varios intentos para hacerlo
     * @param in Scanner para poder leer desde consola
     * @return Devuelve un int
     */
    public int transformaStringAIntDevuelveInt(Scanner in) {
        boolean salida = false;
        int eleccionParseado = 0;
        int contador = 0;

        do {
            try {
                String eleccion = in.nextLine();
                eleccionParseado = Integer.parseInt(eleccion);
                salida = true;
            } catch (NumberFormatException e) {
                prints.escribir("Formato erroneo, vuelva a repetir. Le quedan " + (3 - contador));
                System.out.print("> ");
                contador++;
            }
            if (contador == 3) {
                prints.escribir("No quedan intentos");
                eleccionParseado = -1;
                salida = true;
            }
        } while (!salida);
        return eleccionParseado;
    }

    // ######## --------------> FUNCIONES LEER <-------------------- #########

    private String leerStringTeclado(Scanner in, String palabra) {
        prints.separadorConTexto(palabra);
        return in.nextLine();
    }

    private String leerDNI (Scanner in)  {
        int contador = 0;
        boolean estaBien;
        String resultado;
        do{
            String dni = leerStringTeclado(in, "DNI");
            char[] datoSeparado = dni.toCharArray();
            try {
                for(int i = 0; i <= 7; i++){
                    // Prueba a transformar los 6 primeros caracteres a int usando los parse, los cuales pueden dar
                    // un error, error que uso a mi favor ya que es la manera de controlar si realmente el dato esta bien
                    Integer.parseInt(String.valueOf(datoSeparado[i]));
                }
                estaBien = false;
                try {
                    // Prueba a transformar la letra a int, lo cual tiene que dar error ya que una letra no puede ser numero
                    Integer.parseInt(String.valueOf(datoSeparado[8]));
                } catch (Exception e){
                    estaBien = true;
                }
            } catch (Exception e) {
                estaBien = false;
            }
            if(!estaBien){
                contador ++;
                prints.escribir("DNI incorrecto quedan " + (3-contador) + " intentos");
            }
            resultado = dni;
        } while (contador < 3 && !estaBien);

        if (contador == 3){
            prints.escribir("Te has quedado sin intentos, serás enviado a la páguina principal");
            // TODO crear una excepcion y lanzarla hacia arriba
        }
        return resultado;
    }

    private int leerEstado(Scanner in) {
        prints.separadorConTexto("Estado");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return estadoEleccion(palabraIntroducida);
    }

    public int leerPuesto(Scanner in) {
        prints.separadorConTexto("Puesto");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return puestoEleccion(palabraIntroducida);
    }

    private int leerNumeroYCodigoPostal(Scanner in, String campo) {
        prints.separadorConTexto(campo);
        int resultado;
        resultado = transformaStringAIntDevuelveInt(in);
        if (resultado == -1) {
            prints.escribir("Se establecera 0 por defecto");
            resultado = 0;
        }
        return resultado;
    }


    // -------------------->  FUNCIONES PARA PONER LOS DATOS EN MEMORIA  <------------------------

    /**
     * Hace los set que tienen que ver con el modelo Empleado
     * @param in Scanner para leer por consola
     * @param variableEmpleado La variable a la que hacemos los set para añadirlo a la lista
     * @param deDondeVieneElDato Puede ser : "teclado", "papelera", "bbdd" y "fichero"
     * @param set Los datos que vienen de la bbdd
     * @param entry Los datos que vienen de los empleados borrado
     * @param datoseparado Los datos que vienen de ficheros
     * @throws ParseException Errores parseando la fecha
     * @throws SQLException Errores con los datos de bbdd
     */
    public void datosEmpleados(Scanner in, Empleado variableEmpleado, String deDondeVieneElDato,
                               ResultSet set, Map.Entry<String, Empleado> entry, String[] datoseparado)
            throws ParseException, SQLException {

        switch (deDondeVieneElDato) {
            case "teclado":
                variableEmpleado.setCodigo(generarCodigo("empleados"));
                variableEmpleado.setNombre(leerStringTeclado(in, "Nombre"));
                variableEmpleado.setPrimerApellido(leerStringTeclado(in, "Primer Apellido"));
                variableEmpleado.setSegundoApellido(leerStringTeclado(in, "Segundo Apellido"));
                variableEmpleado.setDNI(leerDNI(in));
                if(variableEmpleado.getDNI() == null){
                    break;
                }
                variableEmpleado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in, "Fecha nacimiento")));
                variableEmpleado.setNacionalidad(leerStringTeclado(in, "Nacionalidad"));
                variableEmpleado.setEstado(Estado.values()[estadoEleccion(leerStringTeclado(in, "Estado"))]);
                variableEmpleado.setFechaAlta(Fecha.creaciónFechaActual());
                break;
            case "papelera":
                variableEmpleado.setCodigo(entry.getValue().getCodigo());
                variableEmpleado.setNombre(entry.getValue().getNombre());
                variableEmpleado.setPrimerApellido(entry.getValue().getPrimerApellido());
                variableEmpleado.setSegundoApellido(entry.getValue().getSegundoApellido());
                variableEmpleado.setDNI(entry.getValue().getDNI());
                variableEmpleado.setFechaNacimiento(entry.getValue().getFechaNacimiento());
                variableEmpleado.setNacionalidad(entry.getValue().getNacionalidad());
                variableEmpleado.setEstado(entry.getValue().getEstado());
                variableEmpleado.setFechaAlta(entry.getValue().getFechaAlta());
                variableEmpleado.setContratos(entry.getValue().getContratos());
                break;
            case "bbdd":
                variableEmpleado.setCodigo(set.getString(1));
                variableEmpleado.setNombre(set.getString(2));
                variableEmpleado.setPrimerApellido(set.getString(3));
                variableEmpleado.setSegundoApellido(set.getString(4));
                variableEmpleado.setDNI(set.getString(5));
                variableEmpleado.setFechaNacimiento(set.getDate(6));
                variableEmpleado.setNacionalidad(set.getString(7));
                variableEmpleado.setFechaAlta(set.getDate(8));
                variableEmpleado.setEstado(Estado.values()[estadoEleccion(set.getString(9))]);
                variableEmpleado.setDireccion(gestionBaseDeDatos.cargarDireccion("FPM_DIRECCION", set.getInt(10)));

                break;
            case "fichero":
                variableEmpleado.setCodigo(datoseparado[0]);
                variableEmpleado.setNombre(datoseparado[1]);
                variableEmpleado.setPrimerApellido(datoseparado[2]);
                variableEmpleado.setSegundoApellido(datoseparado[3]);
                variableEmpleado.setDNI(datoseparado[4]);
                variableEmpleado.setFechaNacimiento(Fecha.fecha(datoseparado[5]));
                variableEmpleado.setNacionalidad(datoseparado[6]);
                variableEmpleado.setEstado(Estado.values()[estadoEleccion(datoseparado[7])]);
                variableEmpleado.setFechaAlta(Fecha.leerStringDevolviendoFechaFormateada(datoseparado[17]));
                break;
        }
    }

    /**
     *
     * @param in Scanner para leer desde teclado
     * @param deDondeVieneElDato Puede ser : "teclado", "papelera", "bbdd" y "fichero"
     * @param set Los datos que vienen de la bbdd
     * @param entry Los datos que vienen de los empleados borrado
     * @param datoseparado Los datos que vienen de ficheros
     * @return Devuelve una elemento tipo direccion con los datos introducidos
     * @throws SQLException Errores con los datos de bbdd
     */
    public Direccion datosDireccion(Scanner in, String deDondeVieneElDato, ResultSet set,
                                    Entry<String, Empleado> entry, String[] datoseparado) throws SQLException {

        Direccion variableDireccion = new Direccion();

        switch (deDondeVieneElDato) {
            case "teclado":
                variableDireccion.setCodigo(Integer.parseInt(generarCodigo("direccion")));
                variableDireccion.setCalle(leerStringTeclado(in, "Calle"));
                variableDireccion.setNumero(leerNumeroYCodigoPostal(in, "Numero"));
                variableDireccion.setBloque(leerStringTeclado(in, "Bloque"));
                variableDireccion.setPiso(leerStringTeclado(in, "Piso"));
                variableDireccion.setPuerta(leerStringTeclado(in, "Puerta"));
                variableDireccion.setCodigoPostal(leerNumeroYCodigoPostal(in, "Codigo postal"));
                variableDireccion.setLocalidad(leerStringTeclado(in, "Localidad"));
                variableDireccion.setProvincia(leerStringTeclado(in, "Provincia"));
                break;
            case "papelera":
                variableDireccion.setCodigo(entry.getValue().getDireccion().getCodigo());
                variableDireccion.setCalle(entry.getValue().getDireccion().getCalle());
                variableDireccion.setNumero(entry.getValue().getDireccion().getNumero());
                variableDireccion.setBloque(entry.getValue().getDireccion().getBloque());
                variableDireccion.setPiso(entry.getValue().getDireccion().getPiso());
                variableDireccion.setPuerta(entry.getValue().getDireccion().getPuerta());
                variableDireccion.setCodigoPostal(entry.getValue().getDireccion().getCodigoPostal());
                variableDireccion.setLocalidad(entry.getValue().getDireccion().getLocalidad());
                variableDireccion.setProvincia(entry.getValue().getDireccion().getProvincia());
                break;
            case "bbdd":
                variableDireccion.setCodigo(set.getInt(1));
                variableDireccion.setCalle(set.getString(2));
                variableDireccion.setNumero(set.getInt(3));
                variableDireccion.setBloque(set.getString(4));
                variableDireccion.setPiso(set.getString(5));
                variableDireccion.setPuerta(set.getString(6));
                variableDireccion.setCodigoPostal(set.getInt(7));
                variableDireccion.setLocalidad(set.getString(8));
                variableDireccion.setProvincia(set.getString(9));
                break;
            case "fichero":
                variableDireccion.setCodigo(Integer.parseInt(datoseparado[8]));
                variableDireccion.setCalle(datoseparado[9]);
                variableDireccion.setNumero(Integer.parseInt(datoseparado[10]));
                variableDireccion.setBloque(datoseparado[11]);
                variableDireccion.setPiso(datoseparado[12]);
                variableDireccion.setPuerta(datoseparado[13]);
                variableDireccion.setCodigoPostal(Integer.parseInt(datoseparado[14]));
                variableDireccion.setLocalidad(datoseparado[15]);
                variableDireccion.setProvincia(datoseparado[16]);
                break;
        }
        return variableDireccion;
    }
    // -------------------------------> FUNCIONES CONTRATOS <---------------------------

    /**
     * Estable los datos para un contrato y lo añade a la lista de contratos de el empleado y a la lista general creada
     * en servicios. Al crear el contrato se le dara de alta directamente a ese empleado
     * @param contratoArrayList La lista general de Servicios para poder listar todos los empleados
     * @param empleadoBuscado El empleado a el que se le quiere hacer el contrato
     * @param in Scanner para leer por consola
     */
    private void generarContrato(ArrayList<Contrato> contratoArrayList, Empleado empleadoBuscado, Scanner in) {
        boolean seguir;
        Contrato contrato = new Contrato();

        establecerFechasParaContrato(empleadoBuscado, in, contrato);
        establecerSalarioParaContrato(in, contrato);
        contrato.setPuesto(Puesto.values()[leerPuesto(in)]);
        contrato.setId(Integer.parseInt(generarCodigo("contratos")));
        contrato.setCodigoEmpleadoAsignado(empleadoBuscado.getCodigo());

        if (empleadoBuscado.getContratos() == null){
            empleadoBuscado.setContratos(contrato);
        } else {
            empleadoBuscado.getContratos().add(contrato);
        }
        contratoArrayList.add(contrato);
        contratosNuevos.add(contrato);
        empleadoBuscado.setEstado(Estado.ALTA);
    }

    /**
     * Establece las fechas para los datos del contrato
     * @param empleadoBuscado El empleado al que se le guarda el contrato
     * @param in Scanner para leer desde consola
     * @param contrato El objeto contrato
     * @return boolean para comprobar si se ha realizado la acción o no
     */
    private void establecerFechasParaContrato(Empleado empleadoBuscado, Scanner in, Contrato contrato) {
        establecerFecha(in, contrato, empleadoBuscado, "Inicio");
        contrato.setFechaFinalContrato(null);
        establecerFecha(in, contrato, empleadoBuscado, "Estimada");
    }

    /**
     * Funcion para establecer las fechas desde el teclado
     * @param in Scanner para leer desde consola
     * @param contrato Objeto contrato al que se le estan guardando los valores
     * @param empleado El empleado al que se le estan guardando los datos
     * @param inicioOFinal String para indicar si es para fecha inicio , fecha final o fecha estiamda
     */
    private void establecerFecha(Scanner in, Contrato contrato, Empleado empleado, String inicioOFinal) {
        boolean salida = true;
        int contador = 0;
        do {
            prints.escribir("Introduzca una fecha con el siguiente formato -> hh : mm : ss AM/PM || dd-MM-yyyy");
            prints.escribir("Es importante que respete los espacios y ponga AM o PM");
            System.out.print(inicioOFinal + " --> ");
            try {
                if ("Estimada".equals(inicioOFinal)){
                    contrato.setFechaFinalizacionEstimada(Fecha.leerStringDevolviendoFechaFormateada(in.nextLine()));
                    salida = true;
                }else if ("Inicio".equals(inicioOFinal)) {
                    if (empleado.getContratos() == null) {
                        contrato.setFechaInicioContrato(empleado.getFechaAlta());
                        prints.escribir(empleado.getFechaAlta() + "\n");
                    } else {
                        contrato.setFechaInicioContrato(Fecha.leerStringDevolviendoFechaFormateada(in.nextLine()));
                    }
                    salida = true;
                }
            } catch (ParseException e) {
                prints.escribir("Ha cometido un error de formato con la fecha");
                salida = false;
            }
            if (!salida) {
                prints.escribir("Quedan " + (3 - contador) + " intentos" + "\n");
                contador++;
            }
            if (contador == 3) {
                prints.escribir("Ha fallado 3 veces, se establecerá la fecha como ");
                if ("Inicio".equals(inicioOFinal)) {
                    prints.escribir("--> " + Fecha.formateoDeFechaParaFechaCreadoYBorrado(Fecha.creaciónFechaActual()));
                    contrato.setFechaInicioContrato(Fecha.creaciónFechaActual());
                } else {
                    prints.escribir("--> " + Fecha.formateoDeFechaParaFechaCreadoYBorrado(Fecha.creaciónFechaActual()));
                    contrato.setFechaFinalizacionEstimada((Fecha.creaciónFechaActual()));
                }
                salida = true;
            }
        } while (!salida);
    }

    /**
     *
     * @param in Scanner para leer desde teclado los datos
     * @param contrato Objeto contrato al que se le introducen los datos
     */
    private void establecerSalarioParaContrato(Scanner in, Contrato contrato) {
        boolean resultado;
        int contador = 0;

        do {
            prints.separador();
            prints.escribir("Introduzca el salario");
            System.out.print("> ");
            try {
                String salario = in.nextLine();
                double salarioParseado = Double.parseDouble(salario);
                contrato.setSalario(salarioParseado);
                resultado = true;
            } catch (NumberFormatException e) {
                contador++;
                prints.escribir("Ha cometido un error, recuerde introducir solo números ( con dos decimales ) " +
                        "vuelva a intentarlo te quedan " + (3 - contador) + "\n");
                resultado = false;
            }
            if (contador == 3) {
                double salarioPorDefecto = 1108.3;
                prints.escribir("Ha fallado 3 veces, se establecera por defecto el salario minimo interprofesional");
                contrato.setSalario(salarioPorDefecto);
                resultado = true;
            }
        } while (!resultado);
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    /**
     * Borra a el empleado seleccionado y lo introduce en un mapa poniendo como Key su codigo y como Value el empleado entero
     * @param empleados Arraylist de los empleados
     * @param empleadoBuscado El empleado que queremos borrar
     */
    private void accionBorradoEmpleado(ArrayList<Empleado> empleados, Empleado empleadoBuscado) {

        // Al empleado buscado, le asigna una fecha de borrado, lo guarda en un mapa y lo borra del array
        int ultimoContrato = empleadoBuscado.getContratos().size();
        if (empleadoBuscado.getContratos().size() == 0) {
            prints.escribir("Este empleado no tiene contratos");
        } else {
            empleadoBuscado.getContratos().get(ultimoContrato - 1).setFechaFinalContrato(Fecha.creaciónFechaActual());
            prints.escribir("Se ha establecido la fecha actual como fecha de finalización de su ultimo contrato");
        }
        empleadoBuscado.setEstado(Estado.BAJA);
        empleadosBorrados.put(empleadoBuscado.getCodigo(), empleadoBuscado);
        empleadosBorradosNuevos.add(empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------

    /**
     * Se selecciona que campos quieres modificar y le redirije a la funcion necesaria
     * @param in Scanner para leer desde consola
     * @param empleadoBuscado El empleado al que se le van a aplicar los cambios
     */
    private void elecciónDeGrupoQueQuiereCambiar(Scanner in, Empleado empleadoBuscado) {

        prints.eleccionModificar();
        prints.escribir("Elija que campo quiere cambiar");
        int decision = transformaStringAIntDevuelveInt(in);
        boolean salida = true;
        do {
            try {
                if (decision == 1) { // Campos informacion personal

                    cambioCamposPersonales(in, empleadoBuscado);

                } else if (decision == 2) { // Campos de direccion

                    cambioCamposDireccion(in, empleadoBuscado);

                } else if (decision == 3) { // Estado

                    empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);

                } else if (decision == 4) { // Todos los campos
                    if (cambioCamposPersonales(in, empleadoBuscado)){
                        cambioCamposDireccion(in, empleadoBuscado);
                        empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);
                    }

                } else {
                    prints.escribir("Error. Numero introducido por teclado erroneo");
                }
            } catch (Exception e) {
                prints.escribir("Error introduciendo datos, vuelva a intentarlo");
                salida = false;
            }
        } while (!salida);
        empleadosModificados.add(empleadoBuscado);
    }

    private void cambioCamposDireccion(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado.getDireccion().setCalle(leerStringTeclado(in, "Calle"));
        empleadoBuscado.getDireccion().setNumero(leerNumeroYCodigoPostal(in, "Numero"));
        empleadoBuscado.getDireccion().setBloque(leerStringTeclado(in, "Bloque"));
        empleadoBuscado.getDireccion().setPiso(leerStringTeclado(in, "Piso"));
        empleadoBuscado.getDireccion().setPuerta(leerStringTeclado(in, "Puerta"));
        empleadoBuscado.getDireccion().setCodigoPostal(leerNumeroYCodigoPostal(in, "Codigo postal"));
        empleadoBuscado.getDireccion().setLocalidad(leerStringTeclado(in, "Localidad"));
        empleadoBuscado.getDireccion().setProvincia(leerStringTeclado(in, "Provincia"));
    }

    private boolean cambioCamposPersonales(Scanner in, Empleado empleadoBuscado) throws ParseException {
        boolean cambiadoBien = true;
        empleadoBuscado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in, "Fecha de nacimiento")));
        String dni = leerDNI(in);
        if (dni != null){
            empleadoBuscado.setDNI(dni);
            empleadoBuscado.setNombre(leerStringTeclado(in, "Nombre"));
            cambioApellidos(in, empleadoBuscado);
            empleadoBuscado.setNacionalidad(leerStringTeclado(in, "Nacionalidad"));
        } else {
            cambiadoBien = false;
        }
        return cambiadoBien;
    }

    private void cambioApellidos(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado.setPrimerApellido(leerStringTeclado(in, "Primer apellido"));
        empleadoBuscado.setSegundoApellido(leerStringTeclado(in, "Segundo apellido"));
    }
}