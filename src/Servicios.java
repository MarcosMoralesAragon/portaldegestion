import modelos.Direccion;
import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.Prints;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Servicios {

    private static final int ESPACIOS = 15;
    public static ArrayList<Empleado> empleados = new ArrayList<>();


    public static void crear(Scanner in) throws ParseException {

        Empleado variableEmpleado = new Empleado(null);

        // Entrada de datos de los empleados
        datosEmpleados(in, variableEmpleado);
        // Entrada de datos de los campos de direccion
       variableEmpleado.setDireccion(datosDireccion(in));

        Prints.separador();
        System.out.println("Su codigo asignado será : ");
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
        boolean salida;
        int recordatorio = 0;

        System.out.println("3. Borrado");

        do {
            Prints.separador();
            System.out.println("Introduzca el numero del empleado que desea borrar");
            System.out.print("> ");
            int posicion = in.nextInt() - 1;

            salida = borrado(posicion, recordatorio, false, in);

        } while (!salida);
        Prints.terminadaAccion();
        Prints.limpiar(ESPACIOS);
    }

    public static void modificar(Scanner in) throws ParseException {

        boolean salida;
        System.out.println("4. Modificar");

        do {
            Prints.separador();
            System.out.println("Introduzca el numero del empleado del que desea cambiar algun dato");
            System.out.print(" > ");
            int posicion = in.nextInt() - 1;

             salida = eleccionDeCambio(in,posicion, false);

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



    private static void datosEmpleados(Scanner in, Empleado variableEmpleado) throws ParseException {

        // Almacena y guarda los datos del empleado.

        System.out.println("1. Crear");
        Prints.separador();
        System.out.println("Introduzca el nombre del empleado que desea crear");
        System.out.print("> ");
        vaciarScanner(in);
        variableEmpleado.setNombre(in.nextLine());

        Prints.separador();
        System.out.println("Introduzca los apellidos del empleado");
        System.out.print("> ");
        variableEmpleado.setApellido(in.nextLine());

        Prints.separador();
        System.out.println("Introduzca el DNI del empleado");
        System.out.print("> ");
        variableEmpleado.setDNI(in.nextLine());

        System.out.println("Fecha de nacimiento");
        System.out.print("> ");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = in.nextLine();
        Date fechaNacimiento = format.parse(fecha);
        variableEmpleado.setFechaNacimiento(fechaNacimiento);

        Prints.separador();
        System.out.println("Introduzca la nacionalidad del empleado");
        System.out.print("> ");
        variableEmpleado.setNacionalidad(in.nextLine());

        Prints.separador();
        Prints.estados();
        System.out.println("Introduzca el estado del empleado");
        System.out.print("> ");
        int eleccion = in.nextInt();
        vaciarScanner(in);
        switch (eleccion){
            case 1:
                variableEmpleado.setEstado(Estado.ALTA);
                break;

            case 2:
                variableEmpleado.setEstado(Estado.BAJA);
                break;

            case 3:
                variableEmpleado.setEstado(Estado.EN_TRAMITE);
                break;
            }
    }

    private static Direccion datosDireccion(Scanner in){

        // Almacena y guarda los datos sobre la direccion

        Direccion variableDireccion = new Direccion();

        Prints.separador();
        System.out.println("Ahora rellene estos campos de la dirección");
        System.out.println("Calle");
        System.out.print("> ");
        variableDireccion.setCalle(in.nextLine());
        Prints.separador();
        System.out.println("Numero");
        System.out.print("> ");
        variableDireccion.setNumero(in.nextInt());
        Prints.separador();
        System.out.println("Bloque");
        System.out.print("> ");
        vaciarScanner(in);
        variableDireccion.setBloque(in.nextLine());
        Prints.separador();
        System.out.println("Piso");
        System.out.print("> ");
        variableDireccion.setPiso(in.nextLine());
        Prints.separador();
        System.out.println("Puerta");
        System.out.print("> ");
        variableDireccion.setPuerta(in.nextLine());
        Prints.separador();
        System.out.println("Codigo Postal");
        System.out.print("> ");
        variableDireccion.setCodigoPostal(in.nextInt());
        Prints.separador();
        System.out.println("Localidad");
        System.out.print("> ");
        vaciarScanner(in);
        variableDireccion.setLocalidad(in.nextLine());
        Prints.separador();
        System.out.println("Provincia");
        System.out.print("> ");
        variableDireccion.setProvincia(in.nextLine());

        return variableDireccion;
    }

    // -------------------------------> FUNCION BORRADO <-----------------------------


    private static boolean borrado (int posicion, int recordatorio, boolean salida, Scanner in){

        System.out.println("Ha seleccionado a " + Servicios.empleados.get(posicion).getNombre() + " ¿Seguro que desea borrar a este empleado?");

        Prints.asegurar();

        Prints.siNo();
        int decision = in.nextInt();

        switch (decision) {
            case 1:
                Servicios.empleados.remove(posicion);
                salida = true;
                break;
            case 2:
                System.out.println("¿Quiere borrar a otro empleado o desea salir?");
                Prints.otroSalir();
                System.out.print(" > ");
                decision = in.nextInt();

                if (decision == 2) {
                    salida = true;
                }
                break;
            default:

                break;
        }
        return salida;
    }

    // -------------------------------> FUNCIONES MODIFICAR   <------------------------


    private static boolean eleccionDeCambio ( Scanner in, int posicion, boolean salida) throws ParseException {

        // El usuario introduce una decison por teclado ( en formato numerico 1 o 2). Este programa divide las acciones, en caso
        // de 1, le redirije a el apartado de modificacion de campos y en caso de 2 le permitira elegir a otro empleado o salir al
        // menu principal
        System.out.println("Ha seleccionado a " + Servicios.empleados.get(posicion).getNombre() + " ¿Seguro que desea cambiar a este empleado?");

        Prints.siNo();
        int decision = in.nextInt();

        switch (decision){
            case 1:
                Prints.separador();
                Prints.eleccionModificar();
                System.out.println("Introduzca el numero del campo que desea editar");
                System.out.print("> ");
                vaciarScanner(in);
                salida = cambioDeCampo(in, in.nextInt(), posicion);


                break;
            case 2:
                System.out.println("¿Quiere modificar a otro empleado o desea salir?");
                Prints.otroSalir();
                System.out.print(" > ");
                decision = in.nextInt();
                if (decision == 2){
                    salida = true;
                }
                break;
            default:

                break;
        }
        return salida;
    }



    private static boolean cambioDeCampo(Scanner in, int decision, int posicion) throws ParseException {

        // El usuario desea el campo a cambiar y el switch le redireccioona a la funcion individual de cada un o para cambiar el parametro
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
        String nuevoString;
        Prints.separador();
        System.out.println("Introduzca el nuevo nombre del empleado");
        System.out.print("> ");

        vaciarScanner(in);
        nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setNombre(nuevoString);
    }
    private static void cambioApellidos(Scanner in, int posicion){
        String nuevoString;
        Prints.separador();
        System.out.println("Introduzca el nuevo Apellido del empleado");
        System.out.print("> ");

        vaciarScanner(in);
        nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setApellido(nuevoString);

    }
    private static void cambioDNI(Scanner in, int posicion){
        Prints.separador();
        System.out.println("Introduzca el nuevo DNI del empleado");
        System.out.print("> ");

        vaciarScanner(in);
        String nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setDNI(nuevoString);

    }
    private static void cambioFechaNacimiento(Scanner in, int posicion) throws ParseException {
        Prints.separador();
        System.out.println("Introduzca la nueva Fecha de nacimiento nombre del empleado");
        System.out.print("> ");

        vaciarScanner(in);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = in.nextLine();
        Date fechaNacimiento = format.parse(fecha);
        Servicios.empleados.get(posicion).setFechaNacimiento(fechaNacimiento);

    }
    private static void cambioNacionalidad(Scanner in, int posicion){
        String nuevoString;
        Prints.separador();
        System.out.println("Introduzca la nueva Nacionalidad del empleado");
        System.out.print("> ");

        vaciarScanner(in);
        nuevoString = in.nextLine();
        Servicios.empleados.get(posicion).setNacionalidad(nuevoString);

    }
     public static void cambioEstado(Scanner in, int posicion){
         Prints.separador();
         Prints.estados();
         System.out.println("Introduzca el nuevo estado del empleado");
         System.out.print("> ");
         int eleccion = in.nextInt();
         vaciarScanner(in);
         switch (eleccion){
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

        Prints.separador();
        System.out.println("Calle");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setCalle(in.nextLine());
        Prints.separador();
        System.out.println("Numero");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setNumero(in.nextInt());
        Prints.separador();
        System.out.println("Bloque");
        System.out.print("> ");
        vaciarScanner(in);
        Servicios.empleados.get(posicion).getDireccion().setBloque(in.nextLine());
        Prints.separador();
        System.out.println("Piso");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setPiso(in.nextLine());
        Prints.separador();
        System.out.println("Puerta");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setPuerta(in.nextLine());
        Prints.separador();
        System.out.println("Codigo Postal");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setCodigoPostal(in.nextInt());
        Prints.separador();
        System.out.println("Localidad");
        System.out.print("> ");
        vaciarScanner(in);
        Servicios.empleados.get(posicion).getDireccion().setLocalidad(in.nextLine());
        Prints.separador();
        System.out.println("Provincia");
        System.out.print("> ");
        Servicios.empleados.get(posicion).getDireccion().setProvincia(in.nextLine());
    }

}
