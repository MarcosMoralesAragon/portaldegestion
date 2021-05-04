package com.pruebas;

import com.modelos.Empleado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Prueba {

    public static void main(String[] args) throws ParseException {

        Empleado variableEmpleado = new Empleado(null);

        Date fecha1 = new Date();
        DateFormat format = new SimpleDateFormat("hh:mm:ss a // dd-MM-yyyy");
        String prueba = format.format(fecha1);
        System.out.println(prueba);

        variableEmpleado.setFechaBaja(fecha1);
        System.out.println(variableEmpleado.getFechaBaja());
        System.out.println(format.format(variableEmpleado.getFechaBaja()));

        variableEmpleado.setFechaAlta(format.parse("null"));
        System.out.println(variableEmpleado.getFechaAlta());
    }

}
