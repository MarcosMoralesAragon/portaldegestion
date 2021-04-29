package com.pruebas;


import com.modelos.Empleado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;


public class Prueba {

    public static final ArrayList<Empleado> empleados = new ArrayList<>();



    public static void main(String[] args) throws ParseException {
        Date fecha1 = new Date();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaNacimiento = format.parse(String.valueOf(fecha1));
        System.out.println(fechaNacimiento);
    }
}
