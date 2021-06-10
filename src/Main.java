import com.servicios.ServiciosGeneral;
import com.utilidades.Prints;
import java.util.*;

/**
 * @author MarcosMoralesAragon
 **/
public class Main {
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        boolean salida = false;
        Prints prints = new Prints();

        prints.escribir("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");
        prints.limpiar(1);

        /** ficheroEstaCorrectoParaListar = GestionFicheros.leerFichero("empleados.txt", "empleados");
        Prints.limpiar(1); */
        ServiciosGeneral Servicios = new ServiciosGeneral();

        Servicios.cargarEmpleadosDesdeBaseDeDatos();

        Servicios.listarEmpleados("");

        while (!salida){
            prints.escribir("\n" + "Porfavor, introduzca a continuación el numero de la acción que desea realizar");
            prints.main();
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
                        Servicios.cargarPapelera();
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
                    Servicios.guardarTodosLosCambios();
                case 17:
                    prints.limpiar(1);
                    prints.finDeLaAplicacion();
                    salida = true;
                    break;
                default:
                    break;
            }
        }
    }
}