package com.pruebas;

import com.*;
import com.ficheros.GestionFicheros;
import com.modelos.Empleado;
import com.utilidades.Fecha;

import java.util.ArrayList;
import java.util.Iterator;

public class Prueba {
    public static void main(String[] args){
        ArrayList<Empleado> empleadosLista = new ArrayList<Empleado>();
        Iterator<Empleado> listaIteradaEmpleados = null;

        GestionFicheros.leerFichero("empleados.txt", "fichero");

        empleadosLista = Servicios.empleados;
        System.out.println(Servicios.codigoExiste(empleadosLista, 0, "10fy96", "empleados"));
    }
}
