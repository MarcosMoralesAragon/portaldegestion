package com.pruebas;

import com.*;
import com.ficheros.GestionFicheros;
import com.modelos.Empleado;
import com.utilidades.Fecha;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Prueba {
    public static void main(String[] args){
        DateFormat format = new SimpleDateFormat("yyyy");
        Date fecha = Fecha.creaciónFechaActual();
        String fechaResulado = format.format(fecha);
        System.out.println(fechaResulado);
    }
}
