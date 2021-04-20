package test;

import modelos.Empleado;
import modelos.Estado;
import utilidades.Prints;

import java.util.Scanner;

public class Prueba {

    public static void main(String[] args)  {

        Scanner in = new Scanner(System.in);
        Empleado variableEmpleado = new Empleado(null);

        int eleccion = 3;

        System.out.println(Estado.values()[(eleccion - 1)].getEstado());

    }

}
