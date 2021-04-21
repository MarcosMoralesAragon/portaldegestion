import utilidades.Prints;

import java.text.ParseException;
import java.util.*;

/**
 * @author MarcosMoralesAragon
 **/
public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int recordatorio = 0;
        boolean salida = false;

        System.out.println("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");

        while (!salida){
            System.out.println("\n" + "Porfavor, introduzca a continuación el numero de la acción que desea realizar");

           Prints.main();
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
                        Servicios.papelera();
                    break;

                case 5:
                        Servicios.modificar(in);
                    break;
                case 6:
                        System.out.println("  → Cerrando programa, gracias por su uso ←");
                        salida = true;
                    break;
            }
        }
    }
}