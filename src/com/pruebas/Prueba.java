package com.pruebas;

import com.ficheros.Servicios;
import com.ficheros.GestionFicheros;
import com.modelos.Empleado;
import com.utilidades.Prints;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prueba {

    public static HashMap<String, Empleado> empleadosBorrados = new HashMap<>();

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        ArrayList<Empleado> empleados = new ArrayList<>();
        Empleado variableEmpleado = new Empleado();
        ResultSet set = null;
        for (Map.Entry<String, Empleado> entry : empleadosBorrados.entrySet()) {
            pruebita(entry.getValue().getNombre());
        }
        /* Servicios.cargarEmpleadosDesdeBaseDeDatos();
        Servicios.listarEmpleados(""); */
    }

    public static void pruebita(String entry){

    }
}
