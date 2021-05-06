package com.pruebas;

import com.ficheros.GestionFicheros;
import com.ficheros.Servicios;
import com.modelos.Empleado;
import com.utilidades.Alfanumerico;
import com.utilidades.Fecha;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Prueba {

    public static void main(String[] args){
        Date fecha =null;
        try {
            fecha = Fecha.leerStringDevolviendoFechaFormateada("00 : 00 : 00 AM // 00-00-0000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        System.out.println(getYearFormat.format(fecha));
    }
}
