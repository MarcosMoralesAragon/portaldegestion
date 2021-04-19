package test;

import modelos.Empleado;
import modelos.Estado;

import java.util.Scanner;

public class Prueba {

    public static void main(String[] args)  {

        Scanner in = new Scanner(System.in);
        Empleado variableEmpleado = new Empleado(null);

        eleccionAlta(in.nextInt(),variableEmpleado);
        System.out.println(variableEmpleado.getEstado());

    }

    private static void eleccionAlta(int eleccion, Empleado empleado){
        switch (eleccion){
            case 1:
                empleado.setEstado(Estado.ALTA);
                break;

            case 2:
                empleado.setEstado(Estado.BAJA);
                break;

            case 3:
                empleado.setEstado(Estado.EN_TRAMITE);
                break;
        }
    }
}
