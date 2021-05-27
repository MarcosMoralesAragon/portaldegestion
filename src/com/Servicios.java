package com;

import com.ficheros.GestionFicheros;
import com.modelos.*;
import com.utilidades.GeneradorCodigos;
import com.utilidades.Fecha;
import com.utilidades.Prints;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import static java.util.Map.*;
@SuppressWarnings({"rawtypes", "NonAsciiCharacters"})

public class Servicios {

    // TODO Ahora tendre que cambiar como busco los codigos de direccion y contratos, es importante
    // TODO Comentar todas las funciones como es debido JavaDOC
    // TODO Arreglar la cantidad de sout ( usar funcion Prints.escribir("") )
    // TODO Eliminar cantidad de bucles ( si se puede )
    // TODO Eliminar static

    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static ArrayList<Empleado> empleadosNuevos = new ArrayList<>();
    public static ArrayList<Contrato> contratos = new ArrayList<>();
    public static HashMap<String, Empleado> empleadosModificados = new HashMap<>();
    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    GestionFicheros GestionFicheros = new GestionFicheros();
    GeneradorCodigos GeneradorCodigos = new GeneradorCodigos();

    public void guardar() {
        for (int i = 0; i < empleados.size(); i++) {
            GestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_EMPLEADOS", empleados.get(i));
        }
    }

    public void crearEmpleado(Scanner in) {
        System.out.println("1. Crear");
        Prints.separador();
        Prints.limpiar(1);
        String deDondeVieneElDato = "teclado";
        try {
            Empleado variableEmpleado = new Empleado();
            datosEmpleados(in, variableEmpleado, deDondeVieneElDato, null, null, null);
            variableEmpleado.setDireccion(datosDireccion(in, deDondeVieneElDato, null, null, null));
            empleados.add(variableEmpleado);
            empleadosNuevos.add(variableEmpleado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Prints.finalFuncion();
    } // 1

    public void crearContrato(Scanner in) {
        System.out.println("2. Crear contrato");
        Prints.separador();
        Prints.limpiar(1);
        boolean salida;
        do {
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null) {
                System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    generarContrato(contratos, empleadoBuscado, in);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "crear un contrato");
                }
            } else {
                System.out.println("Código erroneo, cerrando acción");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    } // 2

    public void listarEmpleados(String numero) {
        System.out.println(numero + "Listado");
        Prints.separador();
        Prints.limpiar(1);
        if (empleados.isEmpty()) {
            System.out.println("No hay todavia ningun empleado registrado");
        } else {
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i).cadenaFormateadaParaMostrarPorPantalla());
            }
        }
        Prints.finalFuncion();
    } // 3

    public void listarContratos() {
        System.out.println("4. Contratos");
        Prints.separador();
        Prints.limpiar(1);
        if (contratos.isEmpty()) {
            System.out.println("No existen todavia contratos registrados");
        } else {
            for (int i = 0; i < contratos.size(); i++) {
                System.out.println("Contrato Nº " + (i + 1) + " --> " + contratos.get(i).toString());
            }
        }
        Prints.finalFuncion();
    } // 4

    public void listarPapelera() {
        System.out.println("5. Listar papelera");
        Prints.separador();
        Prints.limpiar(1);
        int i = 0;
        if (empleadosBorrados.isEmpty()) {
            System.out.println("La papelera esta vacía");
        } else {
            for (Entry borrados : empleadosBorrados.entrySet()) {
                System.out.print("Empleado en papelera Nª" + (i + 1) + " --> ");
                System.out.println(borrados.getValue().toString());
                i++;
            }
        }
        Prints.finalFuncion();
    } // 5

    public void modificar(Scanner in) {
        boolean salida;
        System.out.println("6. Modificar");
        do {
            Prints.introduzcaDatos(in);
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, in.nextLine());
            if (empleadoBuscado != null) {
                System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    elecciónDeGrupoQueQuiereCambiar(in, empleadoBuscado);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "cambiar");
                }
            } else {
                System.out.println("Codigo erroneo, volviendo a menú principal");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    } // 6

    public void borrado(Scanner in) {
        boolean salida;
        System.out.println("7. Borrado");
        do {
            Prints.introduzcaDatos(in);
            String codigo;
            Empleado empleadoBuscado = buscaEmpleadoPorCodigo(empleados, codigo = in.nextLine());
            if (empleadoBuscado != null) {
                System.out.println("Ha seleccionado a " + empleadoBuscado.getNombre() + " ¿Seguro que desea continuar con este empleado?");
                salida = eleccionSiNoYSalirOOtro(in, "sino");
                if (salida) {
                    accionBorradoEmpleado(empleados, empleadoBuscado);
                } else {
                    salida = eleccionSiNoYSalirOOtro(in, "borrado");
                }
            } else {
                System.out.println("Código erroneo, cerrando acción");
                salida = true;
            }
        } while (!salida);
        Prints.finalFuncion();
    } // 7

    public void cargarPapelera() {
        System.out.println("8. Recuperar papelera");
        Prints.separador();
        Prints.limpiar(1);
        Prints.limpiar(1);
        GestionFicheros.leerFichero("copiaDeSeguridad.txt", "papelera");
        Prints.finalFuncion();
    } // 8

    public void guardarPapelera(String nombreFichero) {
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
        if (creado) {
            for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()) {
                try {
                    GestionFicheros.escribirFichero(nombreFichero, entry.getValue().toString());
                } catch (IOException e) { // TODO
                    System.out.println("Fallo guardando el fichero : " + nombreFichero);
                }
            }
            System.out.println("Papelera guardada con exito");
        }
        Prints.finalFuncion();
    } // 9

    public void restaurarPapelera() {
        // Mete los empeleados del mapa a donde van los borrados, y los introduce en el arraylist
        // de empleados
        System.out.println("10. Restaurar papelera");
        Prints.separador();
        Prints.limpiar(1);
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
                    System.out.println("Problema con la conversion de los parametros");
                }
                empleados.add(variableEmpleado);
            }
            System.out.println("Los empleados borrados han sido restaurados con exito. Recuerda que mientras no guardes los cambios no surtiran efecto");
        } else {
            System.out.println("Papelera vacía. Borre empleados o recupere la papelera");
        }
        Prints.finalFuncion();
    } // 10

    public void vaciarPapelera() {
        System.out.println("11. Vaciar papelera");
        Prints.separador();
        Prints.limpiar(1);

        empleadosBorrados.clear();
        Prints.limpiar(1);
        System.out.println("Papelera vaciada con exito");
        Prints.finalFuncion();
    } // 11

    public void guardarTodo() {
        System.out.println("12. Guardar todo");
        Prints.separador();
        Prints.limpiar(1);

        guardarMemoriaABaseDeDatos();
        guardarPapelera("copiaDeSeguridad.txt");
    } // 12

    public void informe() {
        System.out.println("13. Informe");
        Prints.separador();
        Prints.limpiar(1);

        Informe.generarInforme(empleados);

        Prints.finalFuncion();
    } // 13

    public void guardarMemoriaABaseDeDatos() {
        System.out.println("14. Guardar empleados a la base de datos");
        Prints.separador();
        Prints.limpiar(1);
        if (empleadosNuevos.size() == 0) {
            System.out.println("No existen empleados nuevos creados. Cree un empleado nuevo");
        } else {
            for (int i = 0; i < empleadosNuevos.size(); i++) {
                GestionBaseDeDatos.guardarDatosEmpleadosBaseDeDato("FPM_PRUEBA", empleadosNuevos.get(i));
            }
        }
        Prints.finalFuncion();
    } // 14

    public void updateEmpleadosABaseDeDatos() {
        System.out.println("16. Actualizar empleados a la base de datos");
        Prints.separador();
        Prints.limpiar(1);
        if (empleadosModificados.size() == 0) {
            System.out.println("No se ha modificado ningun empleado. Modifique un empleado");
        } else {
            for (Map.Entry<String, Empleado> entry : empleadosModificados.entrySet()) {
                String camposAfectados = entry.getKey();
                Connection conexion = GestionBaseDeDatos.cargarBaseDeDatos("");
                try {
                    switch (camposAfectados) {
                        case "1":
                            GestionBaseDeDatos.updateCamposPersonales(entry);
                            break;
                        case "2":
                            GestionBaseDeDatos.updateCamposDireccion(entry);
                            break;
                        case "3":
                            GestionBaseDeDatos.updateCamposContrato(entry);
                            break;
                        case "4":
                            GestionBaseDeDatos.updateTodoBaseDeDatos(conexion, entry);
                            break;
                    }
                } catch (SQLException throwables) {
                    System.out.println("Error actualizando las tablas");
                } finally {
                    Prints.limpiar(1);
                    try {
                        conexion.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        Prints.finalFuncion();
    } // 15

    public void cargarEmpleadosDesdeBaseDeDatos() {
        Prints.separador();
        Prints.limpiar(1);
        empleados = GestionBaseDeDatos.cargarFilaBaseDeDatos("FPM_EMPLEADOS", empleados);

        Prints.finalFuncion();
    }

    // ------------------------> FUNCIONES <-----------------------------

    /**
     * JavaDoc
     * @param in Pasar el scanner
     * @param palabra Cual es la accion que quiere hacer
     * @return Devuelve un boolean que determina si va a seguir o sale
     */
    private  boolean eleccionSiNoYSalirOOtro(Scanner in, String palabra) {
        boolean salida;
        if ("sino".equals(palabra)) {
            Prints.siNo();
        } else {
            System.out.println("¿Quiere " + palabra + " a otro empleado o desea salir?");
            Prints.otroSalir();
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
     * --> Busca a un empleado en base de su codigo en la lista de empleados
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
     * --> Un bucle que genera códigos hasta que consigue crear una convinación que no exista
     * @param nombreLista Donde quieres buscar, pueder : "direccion", "contratos", "emplados"
     * @return Un string con un codigo ( el codigo int es parseado a string y luego se parsea de vuelta
     */
    private String generarCodigo(String nombreLista) {
        int codigoInt = 0;
        String codigoString = "";
        String resultado = "";
        ArrayList lista = empleados;
        do {
            if ("direccion".equals(nombreLista) || "contratos".equals(nombreLista)) {
                codigoInt = GeneradorCodigos.generarCodigoDirección();
                resultado = String.valueOf(codigoInt);
                if("contratos".equals(nombreLista)){
                    lista = contratos;
                }
            }
            if ("empleados".equals(nombreLista)) {
                codigoString = GeneradorCodigos.generarCodigoEmpleados();
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
                ArrayList<Empleado> empleadosLista = lista;
                Iterator<Empleado> listaIteradaEmpleados = empleadosLista.iterator();
                while (listaIteradaEmpleados.hasNext()) {
                    if (listaIteradaEmpleados.next().getCodigo().toString().equals(codigoString)) {
                        resultado = true;
                    }
                }
                break;
            case "contratos":
                ArrayList<Contrato> contratosLista = lista;
                Iterator<Contrato> listaIteradaContratos = contratosLista.iterator();
                while (listaIteradaContratos.hasNext()) {
                    if (listaIteradaContratos.next().getId() == codigoInt) {
                        resultado = true;
                    }
                }
                break;
            case "direccion":
                ArrayList<Empleado> empleadosListaDireccion = lista;
                Iterator<Empleado> listaIteradaEmpleadosDireccion = empleadosListaDireccion.iterator();
                while (listaIteradaEmpleadosDireccion.hasNext()) {
                    if (listaIteradaEmpleadosDireccion.next().getDireccion().getCodigo() == codigoInt) {
                        resultado = true;
                    }
                }
                break;
            default:
                resultado = false;
                Prints.escribir("No existe la lista elegida, no se podrá comparar el codigo"); // TODO
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
                System.out.println("Palabra introducida erronea, se establecera ''Alta'' como predeterminado," +
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
    private  int puestoEleccion(String palabraIntroducida) {
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

    /**
     * Introduce en memoria los datos almacenados en los ficheros
     * @param datoSeparado Un String[] que viene de separar los datos de un fichero
     * @param palabra Puede ser: "papelera" o "fichero"
     * @throws ParseException
     */
    public void cargarLista(String[] datoSeparado, String palabra) throws ParseException {
        Empleado variableEmpleado = new Empleado(null);
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
                System.out.println("Formato erroneo, vuelva a repetir. Le quedan " + (3 - contador));
                System.out.println("> ");
                contador++;
            }
            if (contador == 3) {
                System.out.println("No quedan intentos");
                eleccionParseado = -1;
            }
        } while (!salida);

        return eleccionParseado;
    }

    // ######## --------> FUNCIONES LEER <-------------------- #########

    private String leerStringTeclado(Scanner in, String palabra) {
        Prints.separadorConTexto(palabra);
        return in.nextLine();
    }

    private int leerEstado(Scanner in) {
        Prints.separadorConTexto("Estado");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return estadoEleccion(palabraIntroducida);
    }

    public int leerPuesto(Scanner in) {
        Prints.separadorConTexto("Puesto");
        String palabraIntroducida;
        palabraIntroducida = in.nextLine();

        return puestoEleccion(palabraIntroducida);
    }

    private int leerNumeroYCodigoPostal(Scanner in, String campo) {
        Prints.separadorConTexto(campo);
        int resultado = 0;
        resultado = transformaStringAIntDevuelveInt(in);
        if (resultado == -1) {
            System.out.println("Se establecera 0 por defecto");
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
                variableEmpleado.setDNI(leerStringTeclado(in, "DNI"));
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
                variableEmpleado.setDireccion(GestionBaseDeDatos.cargarDireccion("FPM_DIRECCION", set.getInt(10)));

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
                // variableEmpleado.setContratos(datoseparado[18]);
                // System.out.println(datoseparado[18]);
                // System.out.println(datoseparado);
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
    /*
    public static ArrayList<Contrato> transformaStringDatoSeparadoAArrayContratos(String[] datoseparado){
        Contrato variableContrato = new Contrato();
        int contado = 0;
        datoseparado[18].split()

        return variableContrato;
    }
    */
    // -------------------------------> FUNCIONES CONTRATOS <---------------------------

    /**
     * Estable los datos para un contrato y lo añade a la lista de contratos de el empleado y a la lista general creada
     * en servicios
     * @param contratoArrayList La lista general de Servicios para poder listar todos los empleados
     * @param empleadoBuscado El empleado a el que se le quiere hacer el contrato
     * @param in Scanner para leer por consola
     */
    private void generarContrato(ArrayList<Contrato> contratoArrayList, Empleado empleadoBuscado, Scanner in) {
        boolean seguir;
        Contrato contrato = new Contrato();

        seguir = establecerFechasParaContrato(empleadoBuscado, in, contrato);
        if (seguir) {
            seguir = establecerSalarioParaContrato(in, contrato);
            if (seguir) {
                establecerPuesto(in, contrato);
                contrato.setId(Integer.parseInt(generarCodigo("contratos")));
                contrato.setCodigoEmpleadoAsignado(empleadoBuscado.getCodigo());
                empleadoBuscado.getContratos().add(contrato);
                contratoArrayList.add(contrato);
            }
        }
    }

    /**
     *
     * @param empleadoBuscado
     * @param in
     * @param contrato
     * @return
     */
    private boolean establecerFechasParaContrato(Empleado empleadoBuscado, Scanner in, Contrato contrato) {
        Prints.generarContrato();
        int eleccion = transformaStringAIntDevuelveInt(in);
        boolean resultado = true;

        establecerFecha(in, contrato, empleadoBuscado, "Inicio");

        if (eleccion == 1) {
            establecerFecha(in, contrato, empleadoBuscado, "Final");
        } else if (eleccion == 2) {
            contrato.setFechaFinalContrato(null);
        } else {
            System.out.println("Elección errónea. Volviendo al menu principal");
            resultado = false;
        }
        return resultado;
    }

    /**
     *
     * @param in
     * @param contrato
     * @param empleado
     * @param inicioOFinal
     */
    private void establecerFecha(Scanner in, Contrato contrato, Empleado empleado, String inicioOFinal) {
        boolean salida = true;
        String fecha;
        int contador = 0;
        if (empleado.getContratos().size() > 0) {
            contrato.setFechaInicioContrato(empleado.getFechaAlta());
        } else {
            do {
                System.out.println("Introduzca una fecha con el siguiente formato -> hh : mm : ss AM/PM || dd-MM-yyyy");
                System.out.println("Es importante que respete los espacios y ponga AM o PM");
                System.out.print("> ");
                try {
                    fecha = in.nextLine();
                    if ("Inicio".equals(inicioOFinal)) {
                        contrato.setFechaFinalContrato(Fecha.leerStringDevolviendoFechaFormateada(fecha));
                        salida = true;
                    } else if ("Final".equals(inicioOFinal)) {
                        contrato.setFechaFinalContrato(Fecha.leerStringDevolviendoFechaFormateada(fecha));
                        salida = true;
                        if (contrato.getFechaFinalContrato().before(contrato.getFechaInicioContrato())) {
                            System.out.println("La fecha introducida es antes de la fecha de alta del empleado");
                            salida = false;
                        }
                    }
                } catch (ParseException e) {
                    System.out.println("Ha cometido un error de formato con la fecha");
                    salida = false;
                }
                if (!salida) {
                    System.out.println("Quedan " + (3 - contador) + " intentos" + "\n");
                    contador++;
                }
                if (contador == 3) {
                    System.out.println("Ha fallado 3 veces, se establecera la fecha de estimacion como null");
                    contrato.setFechaFinalContrato(null);
                    salida = true;
                }
            } while (!salida);
        }
    }

    /**
     *
     * @param in
     * @param contrato
     * @return
     */
    private boolean establecerSalarioParaContrato(Scanner in, Contrato contrato) {
        boolean resultado;
        int contador = 0;

        do {
            Prints.separador();
            System.out.println("Introduzca el salario");
            System.out.print("> ");
            try {
                String salario = in.nextLine();
                double salarioParseado = Double.parseDouble(salario);
                contrato.setSalario(salarioParseado);
                resultado = true;
            } catch (NumberFormatException e) {
                contador++;
                System.out.println("Ha cometido un error, recuerde introducir solo números ( con dos decimales ) " +
                        "vuelva a intentarlo te quedan " + (3 - contador) + "\n");
                resultado = false;
            }
            if (contador == 3) {
                double salarioPorDefecto = 1108.3;
                System.out.println("Ha fallado 3 veces, se establecera por defecto el salario minimo interprofesional");
                contrato.setSalario(salarioPorDefecto);
                resultado = true;
            }
        } while (!resultado);

        return resultado;
    }

    private void establecerPuesto(Scanner in, Contrato contrato) {
        contrato.setPuesto(Puesto.values()[leerPuesto(in)]);
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
            System.out.println("Este empleado no tiene contratos");
        } else {
            empleadoBuscado.getContratos().get(ultimoContrato - 1).setFechaFinalContrato(Fecha.creaciónFechaActual());
            System.out.println("Se ha establecido la fecha actual como fecha de finalización de su ultimo contrato");
        }
        empleadosBorrados.put(empleadoBuscado.getCodigo(), empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------

    /**
     * Se selecciona que campos quieres modificar y le redirije a la funcion necesaria
     * @param in Scanner para leer desde consola
     * @param empleadoBuscado El empleado al que se le van a aplicar los cambios
     */
    private void elecciónDeGrupoQueQuiereCambiar(Scanner in, Empleado empleadoBuscado) {

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

                    empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);

                } else if (decision == 4) { // Todos los campos

                    cambioCamposPersonales(in, empleadoBuscado);
                    cambioCamposDireccion(in, empleadoBuscado);
                    empleadoBuscado.setEstado(Estado.values()[leerEstado(in)]);
                } else {
                    System.out.println("Error. Numero introducido por teclado erroneo");
                }
            } catch (Exception e) {
                System.out.println("Error introduciendo datos, vuelva a intentarlo");
                salida = false;
            }
        } while (!salida);
        empleadosModificados.put(String.valueOf(decision), empleadoBuscado);
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

    private void cambioCamposPersonales(Scanner in, Empleado empleadoBuscado) throws ParseException {
        empleadoBuscado.setNombre(leerStringTeclado(in, "Nombre"));
        cambioApellidos(in, empleadoBuscado);
        empleadoBuscado.setDNI(leerStringTeclado(in, "DNI"));
        empleadoBuscado.setFechaNacimiento(Fecha.fecha(leerStringTeclado(in, "Fecha de nacimiento")));
        empleadoBuscado.setNacionalidad(leerStringTeclado(in, "Nacionalidad"));
    }

    private void cambioApellidos(Scanner in, Empleado empleadoBuscado) {
        empleadoBuscado.setPrimerApellido(leerStringTeclado(in, "Primer apellido"));
        empleadoBuscado.setSegundoApellido(leerStringTeclado(in, "Segundo apellido"));
    }
}