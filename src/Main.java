import utilidades.Prints;

import java.text.ParseException;
import java.util.*;

/**
 * @author MarcosMoralesAragon
 **/
public class Main {
    public static void main(String[] args) throws ParseException {

        Scanner in = new Scanner(System.in);

        int recordatorio = 0;
        boolean salida = false;

        System.out.println("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");

        while (!salida){
            System.out.println(" ");
            System.out.println("Porfavor, introduzca a continuación el numero de la acción que desea realizar");


            if (recordatorio == -1) {
                System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
                System.out.println(" --> Asegurese de que el número introducido coincide con los parámetros <--");
            }

            Prints.separador();
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Borrar");
            System.out.println("4. Modificar");
            System.out.println("5. Salir");
            Prints.separador();
            System.out.print(" > ");

            int eleccion = in.nextInt();

            switch (eleccion){
                case 1:
                        Servicios.crear(in);
                    break;
                case 2:
                        Servicios.listado();
                    break;

                case 3:
                        Servicios.borrado(in);
                    break;

                case 4:
                        Servicios.modificar(in);
                    break;

                case 5:
                    System.out.println("  → Cerrando programa, gracias por su uso ←");
                    salida = true;
                    break;
                default:
                    recordatorio = -1;
            }
        }
    }
}