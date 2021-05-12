package com.pruebas;

import com.ficheros.GestionBaseDeDatos;
import com.ficheros.GestionFicheros;
import com.ficheros.Servicios;
import com.modelos.Contrato;
import com.modelos.Empleado;
import com.modelos.Puesto;
import com.utilidades.Alfanumerico;
import com.utilidades.Fecha;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Prueba {

    public static void main(String[] args){
        Connection connection = GestionBaseDeDatos.cargarBaseDeDatos();
        GestionBaseDeDatos.pintarTablaBaseDeDatos(connection,"FPM_Prueba");
        GestionBaseDeDatos.guardarDatosBaseDeDato(connection,"FPM_PRUEBA", 2, "Sergio", "Garcia", "Nomelose", "12345L");
    }
}
