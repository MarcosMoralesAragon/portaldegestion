import com.ficheros.GestionFicheros;
import com.ficheros.Servicios;
import com.utilidades.Prints;
import java.util.*;

/**
 * @author MarcosMoralesAragon
 **/
public class Main {
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        boolean salida = false;
        boolean ficheroEstaCorrectoParaListar = false;

        System.out.println("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");

        Prints.limpiar(1);

        ficheroEstaCorrectoParaListar = GestionFicheros.leerFichero("empleados.txt", "empleados");
        Prints.limpiar(1);

        if (ficheroEstaCorrectoParaListar){
            Servicios.listarEmpleados("");
        } else {
            System.out.println("El archivo no ha sido cargado con exito, no se pueden listar los datos");
        }

        while (!salida){
            System.out.println("\n" + "Porfavor, introduzca a continuación el numero de la acción que desea realizar");
            Prints.main();
            int eleccion = in.nextInt();
            switch (eleccion){
                case 1:
                        Servicios.crear(in);
                    break;
                case 2:
                        Servicios.listarEmpleados("2. ");
                    break;

                case 3:
                        Servicios.borrado(in);
                    break;

                case 4:
                        Servicios.modificar(in);
                    break;

                case 5:
                        Servicios.guardarEmpleados("empleados.txt");
                    break;
                case 6:
                        Servicios.guardarTodo();
                    break;

                case 7:
                        Servicios.listarPapelera();
                    break;
                case 8:
                        Servicios.guardarPapelera("copiaDeSeguridad");
                    break;
                case 9:
                        Servicios.recuperarPapelera();
                    break;
                case 10:
                        Servicios.vaciarPapelera();
                    break;
                case 11:
                        System.out.println("→ Cerrando programa, gracias por su uso ←");
                        salida = true;
                    break;
                default:
                    break;
            }
        }
    }
}