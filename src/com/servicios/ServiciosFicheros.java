package com.servicios;

import com.utilidades.Prints;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class ServiciosFicheros {

    public boolean leerFichero (String nombreFichero,String palabra){
        String[] datoSeparado;
        boolean fraseConfirmaciconDeLectura = false;
        Scanner in = null;
        boolean archivoLeido = false;
        Prints prints = new Prints();
        try {
            in = new Scanner(Paths.get(nombreFichero));
            if (!in.hasNextLine()){
                prints.escribir("El archivo ''" + nombreFichero + "'' esta vacio. Revise el contenido y reintente");
            }
            while (in.hasNextLine()){
                String linea = in.nextLine();
                datoSeparado = linea.split("#");
                try {
                    ServiciosGeneral servicios = new ServiciosGeneral();
                    if ("fichero".equals(palabra)){
                        servicios.cargarLista(datoSeparado,palabra);
                    } else if ("papelera".equals(palabra)){
                        servicios.cargarLista(datoSeparado,palabra);
                    }
                    fraseConfirmaciconDeLectura = true;
                    archivoLeido = true;
                } catch (Exception e){
                    prints.escribir("Fallo cargando el archivo ''" + nombreFichero + "''. Los datos del interior tienen defectos");
                    fraseConfirmaciconDeLectura = false;
                }
            }
        } catch (IOException e) {
            prints.escribir("Fallo con el archivo "+ nombreFichero+" , revise que existe y que este dentro de la carpeta del proyecto");
        } finally {
            if (in != null){
                in.close();
            }
        }
        if (fraseConfirmaciconDeLectura){
            prints.escribir("Archivo " + nombreFichero + " cargado");
            prints.limpiar(1);
        }
        return archivoLeido;
    }

    public boolean borrarFichero(String nombreFichero){
        File fichero = new File(nombreFichero);
        return fichero.delete();
    }

    public boolean creadorFicheros(String nombreFichero){

        File fichero = new File(nombreFichero);
        boolean creado = false;
        try {
            creado = fichero.createNewFile();
        } catch (IOException e) {
            Prints prints = new Prints();
            prints.escribir("No se ha podido crear el archivo"); }
        return creado;
    }

    public void escribirFichero (String nombreFichero, String cadena) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true));
        writer.append(cadena);
        writer.close();
    }
}
