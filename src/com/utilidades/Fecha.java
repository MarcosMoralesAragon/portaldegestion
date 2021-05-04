package com.utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fecha {

    public static Date fecha(String fechaIntroducida) throws ParseException {
        Date fechaNacimiento;
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        fechaNacimiento = format.parse(fechaIntroducida);
        return fechaNacimiento;
    }

    public static Date creaciónFechaActual(){
        return new Date();
    }

    public static String formatoFechaCreacionYBorrado (Date fecha){
        DateFormat format = new SimpleDateFormat("hh : mm : ss a // dd-MM-yyyy");
        return format.format(fecha);
    }

    public static Date leerFechaConFormato(String fechaFormateada) throws ParseException {
        Date fecha;
        DateFormat format = new SimpleDateFormat("hh : mm : ss a // dd-MM-yyyy");
        fecha = format.parse(fechaFormateada);
        return fecha;
    }

}
