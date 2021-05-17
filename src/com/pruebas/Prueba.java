package com.pruebas;

import com.ficheros.GestionBaseDeDatos;
import com.ficheros.GestionFicheros;
import com.ficheros.Servicios;
import com.modelos.Empleado;
import com.utilidades.Alfanumerico;
import com.utilidades.Prints;

import java.util.Scanner;

public class Prueba {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        System.out.println(Alfanumerico.generar());
        Empleado variableEmpleado = new Empleado();
        boolean salida = false;
        boolean ficheroEstaCorrectoParaListar;

        System.out.println("Bienvenido al portal de gestión, ahora tendra acceso al panel de control de acciones.");
        Prints.limpiar(1);
        ficheroEstaCorrectoParaListar = GestionFicheros.leerFichero("empleados.txt", "empleados");
        Prints.limpiar(1);

        Servicios.guardarMemoriaABaseDeDatos();
    }
}
