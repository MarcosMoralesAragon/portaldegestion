package com.pruebas;

import com.*;
import com.ficheros.GestionFicheros;
import com.modelos.Empleado;
import com.utilidades.Fecha;
import com.utilidades.GeneradorCodigos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Prueba {
    public static void main(String[] args){
        int contador = 0;
        while(contador < 5){
            GeneradorCodigos generadorCodigos = new GeneradorCodigos();
            System.out.println(generadorCodigos.generarCodigoDirección());
            contador++;
        }
    }
}
