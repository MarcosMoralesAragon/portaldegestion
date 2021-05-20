package com.pruebas;

import com.ficheros.Servicios;
import com.ficheros.GestionFicheros;
import com.modelos.Empleado;
import com.utilidades.Prints;
import java.util.ArrayList;
import java.util.Scanner;

public class Prueba {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        ArrayList<Empleado> empleados = new ArrayList<>();
        Empleado variableEmpleado = new Empleado();

        Servicios.cargarEmpleadosDesdeBaseDeDatos();
        Servicios.listarEmpleados("");

    }
}
