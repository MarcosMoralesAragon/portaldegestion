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
        GestionFicheros gestionFicheros = new GestionFicheros();
        gestionFicheros.borrarFichero("Funciones que ya no se usan.txt");
    }
}