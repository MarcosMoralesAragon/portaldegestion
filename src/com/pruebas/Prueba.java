package com.pruebas;

import com.ficheros.GestionBaseDeDatos;
import com.ficheros.GestionFicheros;
import com.ficheros.Servicios;
import com.modelos.Empleado;
import com.utilidades.Alfanumerico;
import com.utilidades.Prints;

import java.util.ArrayList;
import java.util.Scanner;

public class Prueba {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        ArrayList<Empleado> empleados = new ArrayList<>();
        Empleado variableEmpleado = new Empleado();

        System.out.println("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");
        Prints.limpiar(1);
        GestionFicheros.leerFichero("empleados.txt", "empleados");
        Prints.limpiar(1);
        Servicios.guardarMemoriaABaseDeDatos();

         /* GestionBaseDeDatos.cargarFilaBaseDeDatos("FPM_PRUEBA", empleados);

        if(empleados.isEmpty()){
            System.out.println("No hay todavia ningun empleado registrado");
        } else {
            for ( int i = 0; i < empleados.size(); i++){
                System.out.println("Empleado Nº " + (i + 1) + " --> " + empleados.get(i).cadenaFormateadaParaMostrarPorPantalla());
            }
        }*/

    }
}
