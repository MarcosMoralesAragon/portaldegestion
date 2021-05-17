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
        boolean ficheroEstaCorrectoParaListar;

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
            int eleccion = Servicios.transformaStringAIntDevuelveInt(in);
            switch (eleccion){
                case 1:
                        Servicios.crear(in);
                    break;
                case 2:
                        Servicios.crearContrato(in);
                    break;
                case 3:
                        Servicios.listarEmpleados("3. ");
                    break;

                case 4:
                        Servicios.borrado(in);
                    break;

                case 5:
                        Servicios.modificar(in);
                    break;

                case 6:
                        Servicios.guardarEmpleados("empleados.txt");
                    break;
                case 7:
                        Servicios.guardarTodo();
                    break;

                case 8:
                        Servicios.listarPapelera();
                    break;
                case 9:
                        Servicios.guardarPapelera("copiaDeSeguridad");
                    break;
                case 10:
                        Servicios.recuperarPapelera();
                    break;
                case 11:
                        Servicios.restaurarPapelera();
                    break;
                case 12:
                        Servicios.vaciarPapelera();
                    break;
                case 13:
                        Servicios.informe();
                    break;
                case 14:
                    Prints.limpiar(1);
                        Prints.finDeLaAplicacion();
                        salida = true;
                    break;
                default:
                    break;
            }
        }
    }
}