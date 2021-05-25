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

        /** ficheroEstaCorrectoParaListar = GestionFicheros.leerFichero("empleados.txt", "empleados");
        Prints.limpiar(1); */

        ficheroEstaCorrectoParaListar = true;
        Servicios.cargarEmpleadosDesdeBaseDeDatos();

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
                        Servicios.crearEmpleado(in);
                    break;
                case 2:
                        Servicios.crearContrato(in);
                    break;
                case 3:
                        Servicios.listarEmpleados("3");
                    break;

                case 4:
                        Servicios.listarContratos();
                    break;

                case 5:
                        Servicios.listarPapelera();
                    break;

                case 6:
                        Servicios.modificar(in);
                    break;
                case 7:
                        Servicios.borrado(in);
                    break;
                case 8:
                        Servicios.recuperarPapelera();
                    break;
                case 9:
                        Servicios.guardarPapelera("copiaDeSeguridad.txt");
                    break;
                case 10:
                        Servicios.restaurarPapelera();
                    break;
                case 11:
                        Servicios.vaciarPapelera();
                    break;
                case 12:
                        Servicios.guardarTodo();
                    break;
                case 13:
                        Servicios.informe();
                    break;
                case 14:
                        Servicios.guardarMemoriaABaseDeDatos();
                    break;
                case 15:
                        Servicios.updateEmpleadosABaseDeDatos();
                    break;
                case 16:
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