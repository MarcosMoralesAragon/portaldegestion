import modelos.Direccion;
import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.Prints;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servicios {

    private static final int ESPACIOS = 5;
    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();


    public static void crear(Scanner in) {

        Empleado variableEmpleado = new Empleado(null);

        // Entrada de datos de los empleados
        datosEmpleados(in, variableEmpleado);

        // Entrada de datos de los campos de direccion
        // variableEmpleado.setDireccion(datosDireccion(in));

        Prints.separadorConTexto("Codigo");
        variableEmpleado.setCodigo(Alfanumerico.generar());
        System.out.println(variableEmpleado.getCodigo());

        empleados.add(variableEmpleado);
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

            salida = sigueOSale(eleccion, in, "borrar");

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

            salida = sigueOSale(eleccion, in, "modificar");

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

    private static String leerNombre(Scanner in){
            Prints.separadorConTexto("Nombre");
           String nombre = in.nextLine();
           return nombre;
    }
    private static String leerApellido(Scanner in, String palabra){
        Prints.separadorConTexto(palabra);
        String apellido = in.nextLine();
        return apellido;
    }
    private static String leerDNI(Scanner in){
        Prints.separadorConTexto("DNI");
        return in.nextLine();
    }
    private static String leerFecha(Scanner in) throws ParseException {
        String fecha = in.nextLine();
        return fecha;
    }
    private static String leerNacionalidad(Scanner in){
        Prints.separadorConTexto("Nacionalidad");
        return in.nextLine();
    }
    private static int leerEstado(Scanner in){
        Prints.estados();
        Prints.separadorConTexto("Estado");
        int eleccion = in.nextInt();
        vaciarScanner(in);
        return eleccion;
    }
    private static String leerStringDirección(Scanner in, String palabra){
        Prints.separadorConTexto(palabra);
        String string = in.nextLine();
        return string;
    }
    private static int leerIntDirección(Scanner in, String palabra){
        Prints.separadorConTexto(palabra);
        return in.nextInt();
    }
        // ------------------------> FUNCIONES <-----------------------------


    static void vaciarScanner(Scanner in){
        if (in.hasNextLine()){
            in.skip("\n");
        }
    }

    private static boolean sigueOSale(int eleccion, Scanner in, String palabra){
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
        salida = false;

        if (eleccion == 2){
            salida = true;
        }
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



   private static void datosEmpleados(Scanner in, Empleado variableEmpleado) {

        // Almacena y guarda los datos del empleado.

       System.out.println("1. Crear");
       variableEmpleado.setNombre(leerNombre(in));

       variableEmpleado.setPrimerApellido(leerApellido(in,"Primer apellido"));

       variableEmpleado.setSegundoApellido(leerApellido(in,"Segundo apellido"));

       variableEmpleado.setDNI(leerDNI(in));

       boolean salida;
       do {
           try{
               Prints.separadorConTexto("Fecha de Nacimiento");
               DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
               String fecha = in.nextLine();
               Date fechaNacimiento = format.parse(fecha);
               variableEmpleado.setFechaNacimiento(fechaNacimiento);
               salida = true;
           } catch (Exception e){
               System.out.println("Por favor repita la fecha asegurandose de que sigue bien el formato (dd-mm-yyyy)");
               salida = false;
           }
       } while (!salida);

       variableEmpleado.setNacionalidad(leerNacionalidad(in));

       Prints.estados();
       variableEmpleado.setEstado(Estado.values()[leerEstado(in) - 1]);
   }

    private static Direccion datosDireccion(Scanner in){

        // Almacena y guarda los datos sobre la direccion

        Direccion variableDireccion = new Direccion();


        variableDireccion.setCalle(leerStringDirección(in, "Calle"));

        variableDireccion.setNumero(leerIntDirección(in, "Numero"));

        vaciarScanner(in);
        variableDireccion.setBloque(leerStringDirección(in, "Bloque"));


        variableDireccion.setPiso(leerStringDirección(in, "Piso"));


        variableDireccion.setPuerta(leerStringDirección(in, "Puerta"));

        variableDireccion.setCodigoPostal(leerIntDirección(in, "Codigo Postal"));

        vaciarScanner(in);
        variableDireccion.setLocalidad(leerStringDirección(in, "Localidad"));

        variableDireccion.setProvincia(leerStringDirección(in, "Provincia"));

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static void accionBorradoEmpleado(ArrayList<Empleado> empleados, String codigo,HashMap empleadosBorrados){

        Empleado empleadoBuscado = buscaEmpleado(empleados, codigo);
        empleadosBorrados.put(codigo, empleadoBuscado);
        empleados.remove(empleadoBuscado);
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------


    private static void accionModificadoEmpleado(ArrayList<Empleado> empleados, String codigo, Scanner in) {
        Empleado empleadoBuscado = buscaEmpleado(empleados, codigo);
        cambioDeCampo(in , empleadoBuscado );
    }


    private static void cambioDeCampo(Scanner in, Empleado empleadoBuscado) {


        Empleado modificado;
        Prints.eleccionModificar();
        System.out.println("Elija que campo quiere cambiar");
        int decision = in.nextInt();

        switch (decision){
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
        empleadoBuscado.setNombre(leerNombre(in));
        return empleadoBuscado;
    }

    private static Empleado cambioApellidos(Scanner in, Empleado empleadoBuscado){

        empleadoBuscado.setPrimerApellido(leerApellido(in, "Primer apellido"));
        empleadoBuscado.setSegundoApellido(leerApellido(in,"Segundo apellido"));

        return empleadoBuscado;
    }

    private static Empleado cambioDNI(Scanner in, Empleado empleadoBuscado){
        empleadoBuscado.setDNI(leerDNI(in));
        return empleadoBuscado;
    }

    private static Empleado cambioFechaNacimiento(Scanner in, Empleado empleadoBuscado) {
        boolean salida = false;
        do {
            try {
                Prints.separadorConTexto("Fecha de Nacimiento");
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String fecha = leerFecha(in);
                Date fechaNacimiento = format.parse(fecha); // TODO Aqui da fallo
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
        empleadoBuscado.setNacionalidad(leerNacionalidad(in));
        return empleadoBuscado;
    }

    public static Empleado cambioEstado(Scanner in, Empleado empleadoBuscado){
        int eleccion = leerEstado(in);
        empleadoBuscado.setEstado(Estado.values()[eleccion - 1]);
        return empleadoBuscado;
    }

    private static void cambioDireccion(Scanner in, Empleado empleadoBuscado){
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setCalle(leerStringDirección(in, "Calle"));
        empleadoBuscado.getDireccion().setNumero(leerIntDirección(in, "Numero"));
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setBloque(leerStringDirección(in, "Bloque"));
        empleadoBuscado.getDireccion().setPiso(leerStringDirección(in, "Piso"));
        empleadoBuscado.getDireccion().setPuerta(leerStringDirección(in, "Puerta"));
        empleadoBuscado.getDireccion().setCodigoPostal(leerIntDirección(in, "Codigo Postal"));
        vaciarScanner(in);
        empleadoBuscado.getDireccion().setLocalidad(leerStringDirección(in, "Localidad"));
        empleadoBuscado.getDireccion().setProvincia(leerStringDirección(in, "Provincia"));

    }
}
