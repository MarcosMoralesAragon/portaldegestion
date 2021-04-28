package com.utilidades;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {

    public static Date fecha(String fechaIntroducida){
        boolean salida;
        Date fechaNacimiento = null;
        do {
            try{
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                fechaNacimiento = format.parse(fechaIntroducida);
                salida = true;
            } catch (Exception e){
                System.out.println("Por favor repita la fecha asegurandose de que sigue bien el formato (dd-mm-yyyy)");
                salida = false;
            }
        } while (!salida);
        return fechaNacimiento;
    }
}
