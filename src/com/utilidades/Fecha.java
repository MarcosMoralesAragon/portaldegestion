package com.utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("NonAsciiCharacters")
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

    public static String formateoDeFechaParaFechaCreadoYBorrado(Date fecha){
        DateFormat format = new SimpleDateFormat("hh : mm : ss a || dd-MM-yyyy");
        String fechaResulado;
        if (fecha != null){
            fechaResulado = format.format(fecha);
        } else {
            fechaResulado = null;
        }
        return fechaResulado;
    }

    public static java.sql.Date cambiarDateADateSQL(Date fecha){
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        return fechaSQL;
    }

    public static Date leerStringDevolviendoFechaFormateada(String fechaFormateada) throws ParseException {
        Date fechaResultado;
        DateFormat format = new SimpleDateFormat("hh : mm : ss a || dd-MM-yyyy");
        if (fechaFormateada != null) {
            fechaResultado = format.parse(fechaFormateada);
        } else {
            fechaResultado = null;
        }
        return fechaResultado;
    }
}
