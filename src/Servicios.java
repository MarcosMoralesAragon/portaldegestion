import modelos.Direccion;
import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.Prints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Servicios {

    private static final int ESPACIOS = 15;
    public static ArrayList<Empleado> empleados = new ArrayList<>();


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
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }
    public static void listado() {

        System.out.println("2. Listado");
        Prints.separador();

        for ( int i = 0; i < empleados.size(); i++){
            System.out.println("model.Empleado Nº " + (i + 1) + " --> " + empleados.get(i));
        }
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }

    public static void borrado(Scanner in) {
        boolean salida = false;

        System.out.println("3. Borrado");

        do {
            Prints.separador();
            System.out.println("Introduzca el numero del empleado que desea borrar");
            System.out.print("> ");
            int posicion = in.nextInt() - 1;

            System.out.println("Ha seleccionado a " + empleados.get(posicion).getNombre() + " ¿Seguro que desea borrar a este empleado?");

            Prints.asegurar();

            Prints.siNo();
            int eleccion = in.nextInt();

            salida = sigueOSale(eleccion, in, "borrar");

            if (salida = true){
                Servicios.empleados.remove(posicion);
            }

        } while (!salida);
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }

    public static void modificar(Scanner in){

        boolean salida;
        System.out.println("4. Modificar");

        do {
            Prints.separador();
            System.out.println("Introduzca el numero del empleado del que desea cambiar algun dato");
            System.out.print(" > ");
            int posicion = in.nextInt() - 1;

            buscaEmpleado(empleado);

            System.out.println("Ha seleccionado a " + empleados.get(posicion).getNombre() + " ¿Seguro que desea cambiar a este empleado?");

            Prints.siNo();
            int eleccion = in.nextInt();

            salida = sigueOSale(eleccion, in, "modificar");

            if (salida = true){
                eleccionDeCambio(in,posicion, false); //TODO Seguir con esto
            }

        } while (!salida);
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }


    // ------------------------> FUNCIONES <-----------------------------


    static void vaciarScanner(Scanner in){
        if (in.hasNextLine()){
            in.skip("\n");
        }
    }


    // -------------------->  FUNCIONES CREAR  <------------------------



    private static void datosEmpleados(Scanner in, Empleado variableEmpleado) {

        // Almacena y guarda los datos del empleado.

       System.out.println("1. Crear");

        Prints.separadorConTexto("Nombre");
        vaciarScanner(in);
        variableEmpleado.setNombre(in.nextLine());

        Prints.separadorConTexto("Apellidos");
        variableEmpleado.setApellido(in.nextLine());

        Prints.separadorConTexto("DNI");
        variableEmpleado.setDNI(in.nextLine());
        boolean salida = false;
        do {
            try{
                vaciarScanner(in);
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

        Prints.separadorConTexto("Nacionalidad");
        variableEmpleado.setNacionalidad(in.nextLine());

        Prints.estados();
        Prints.separadorConTexto("Estado");
        int eleccion = in.nextInt();
        vaciarScanner(in);
        variableEmpleado.setEstado(Estado.values()[eleccion - 1]);
    }

    private static Direccion datosDireccion(Scanner in){

        // Almacena y guarda los datos sobre la direccion

        Direccion variableDireccion = new Direccion();

        Prints.separadorConTexto("Calle");
        variableDireccion.setCalle(in.nextLine());

        Prints.separadorConTexto("Numero");
        variableDireccion.setNumero(in.nextInt());

        Prints.separadorConTexto("Bloque");
        vaciarScanner(in);
        variableDireccion.setBloque(in.nextLine());

        Prints.separadorConTexto("Piso");
        variableDireccion.setPiso(in.nextLine());

        Prints.separadorConTexto("Puerta");
        variableDireccion.setPuerta(in.nextLine());

        Prints.separadorConTexto("Codigo Postal");
        variableDireccion.setCodigoPostal(in.nextInt());

        Prints.separadorConTexto("Localidad");
        vaciarScanner(in);
        variableDireccion.setLocalidad(in.nextLine());

        Prints.separadorConTexto("Provincia");
        variableDireccion.setProvincia(in.nextLine());

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------

    private static boolean sigueOSale(int eleccion, Scanner in, String palabra){
        boolean salida = false;

        switch (eleccion){
            case 1:
                salida = true;
                break;
            case 2:
                System.out.println("¿Quiere " + palabra + " a otro empleado o desea salir?");
                Prints.otroSalir();
                System.out.print(" > ");
                eleccion = in.nextInt();
                if (eleccion == 2){
                    salida = true;
                }
                break;
        }

        return salida;
    }

    private Empleado buscaEmpleado(List empleado){

        Empleado empleado = "null";

        return empleado;
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------


    private static boolean eleccionDeCambio ( Scanner in, int posicion){

        Prints.separador();
        Prints.eleccionModificar();
        System.out.println("Introduzca el numero del campo que desea editar");
        System.out.print("> ");
        vaciarScanner(in);
        salida = cambioDeCampo(in, in.nextInt(), posicion);

    }



    private static boolean cambioDeCampo(Scanner in, int decision, int posicion) {

        // El usuario desea el campo a cambiar y el switch le redirecciona a la funcion individual de cada un o para cambiar el parametro
        // , esta asi realizado y que el 8 es cambiar todos los campos y asi solo tenemos que llamar a las funciones y no copiar y pegar


        switch (decision){
            case 1: // Nombre
                cambioNombre(in, posicion);
                break;
            case 2: // Apellido
                cambioApellidos(in, posicion);
                break;
            case 3: // DNI
                cambioDNI(in, posicion);
                break;
            case 4: // Fecha de nacimiento
                cambioFechaNacimiento(in, posicion);
                break;

            case 5: // Nacionalidad
                cambioNacionalidad(in, posicion);
                break;

            case 6: // Estado
                 cambioEstado(in, posicion);
                break;
            case 7: // Dirección
                cambioDireccion(in, posicion);
                break;

            case 8: // Todos
                cambioNombre(in, posicion);
                cambioApellidos(in, posicion);
                cambioDNI(in, posicion);
                cambioFechaNacimiento(in, posicion);
                cambioNacionalidad(in, posicion);
                Servicios.cambioEstado(in, posicion);
                cambioDireccion(in, posicion);
                break;
        }
        return true;
    }


    private static void cambioNombre(Scanner in, int posicion){

        Prints.separadorConTexto("Nombre");

        vaciarScanner(in);
         String nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setNombre(nuevoString); // TODO Cambiar
    }
    private static void cambioApellidos(Scanner in, int posicion){

        Prints.separadorConTexto("Apellidos");

        vaciarScanner(in);
        String nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setApellido(nuevoString); // TODO Cambiar
    }
    private static void cambioDNI(Scanner in, int posicion){
        Prints.separadorConTexto("DNI");

        vaciarScanner(in);
        String nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setDNI(nuevoString); // TODO Cambiar
    }
    private static void cambioFechaNacimiento(Scanner in, int posicion) {
        boolean salida = false;
        do {
            try {
                Prints.separadorConTexto("Fecha de nacimiento");
                vaciarScanner(in);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = in.nextLine();
                Date fechaNacimiento = format.parse(fecha);
                Servicios.empleados.get(posicion).setFechaNacimiento(fechaNacimiento); // TODO Cambiar
                salida = true;
            } catch (Exception e){
                System.out.println("Por favor repita la fecha asegurandose de que sigue bien el formato (dd-mm-yyyy)");
                salida = false;
            }
        } while (!salida);
    }
    private static void cambioNacionalidad(Scanner in, int posicion){
        Prints.separadorConTexto("Nacionalidad");

        vaciarScanner(in);
        String nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setNacionalidad(nuevoString); // TODO cambiar

    }
     public static void cambioEstado(Scanner in, int posicion){
         Prints.estados();
         Prints.separadorConTexto("Estado");
         int eleccion = in.nextInt();
         vaciarScanner(in);
         switch (eleccion){ // TODO Cambiar
             case 1:
                 Servicios.empleados.get(posicion).setEstado(Estado.ALTA);
                 break;
             case 2:
                 Servicios.empleados.get(posicion).setEstado(Estado.BAJA);
                 break;
             case 3:
                 Servicios.empleados.get(posicion).setEstado(Estado.EN_TRAMITE);
                 break;
         }
     }
    private static void cambioDireccion(Scanner in, int posicion){
        vaciarScanner(in);

        Prints.separadorConTexto("Calle");
        Servicios.empleados.get(posicion).getDireccion().setCalle(in.nextLine()); // TODO Cambiar

        Prints.separadorConTexto("Numero");
        Servicios.empleados.get(posicion).getDireccion().setNumero(in.nextInt()); // TODO Cambiar

        Prints.separadorConTexto("Bloque");
        vaciarScanner(in);
        Servicios.empleados.get(posicion).getDireccion().setBloque(in.nextLine()); // TODO Cambiar

        Prints.separadorConTexto("Piso");
        Servicios.empleados.get(posicion).getDireccion().setPiso(in.nextLine()); // TODO Cambiar

        Prints.separadorConTexto("Puerta");
        Servicios.empleados.get(posicion).getDireccion().setPuerta(in.nextLine()); // TODO Cambiar

        Prints.separadorConTexto("Código Postal");
        Servicios.empleados.get(posicion).getDireccion().setCodigoPostal(in.nextInt()); // TODO Cambiar

        Prints.separadorConTexto("Localidad");
        vaciarScanner(in);
        Servicios.empleados.get(posicion).getDireccion().setLocalidad(in.nextLine()); // TODO Cambiar

        Prints.separadorConTexto("Provincia");
        Servicios.empleados.get(posicion).getDireccion().setProvincia(in.nextLine()); // TODO Cambiar
    }
}
