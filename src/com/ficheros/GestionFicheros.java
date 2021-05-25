package com.ficheros;

import com.utilidades.Prints;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;



public class GestionFicheros {

    public static boolean leerFichero (String nombreFichero,String palabra){
        String[] datoSeparado;
        boolean fraseConfirmaciconDeLectura = false;
        Scanner in = null;
        boolean archivoLeido = false;
        try {
            in = new Scanner(Paths.get(nombreFichero));
            if (!in.hasNextLine()){
                System.out.println("El archivo ''" + nombreFichero + "'' esta vacio. Revise el contenido y reintente");
            }
            while (in.hasNextLine()){
                String linea = in.nextLine();
                datoSeparado = linea.split("#");
                try {
                    if (palabra.equals("fichero")){
                        Servicios.cargarLista(datoSeparado,palabra);
                    } else if (palabra.equals("papelera")){
                        Servicios.cargarLista(datoSeparado,palabra);
                    }
                    fraseConfirmaciconDeLectura = true;
                    archivoLeido = true;
                } catch (Exception e){
                   System.out.println("Fallo cargando el archivo ''" + nombreFichero + "''. Los datos del interior tienen defectos");
                   fraseConfirmaciconDeLectura = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Fallo con el archivo "+ nombreFichero+" , revise que existe y que este dentro de la carpeta del proyecto");
        } finally {
            if (in != null){
                in.close();
            }
        }
        if (fraseConfirmaciconDeLectura){
            System.out.println("Archivo " + nombreFichero + " cargado");
            Prints.limpiar(1);
        }
        return archivoLeido;
    }

    public static boolean borrarFichero(String nombreFichero){
        File fichero = new File(nombreFichero);
        return fichero.delete();
    }

    public static boolean creadorFicheros(String nombreFichero){

        File fichero = new File(nombreFichero);
        boolean creado = false;
        try {
            creado = fichero.createNewFile();
        } catch (IOException e) {
            System.out.println("No se ha podido crear el archivo");
        }
        return creado;
    }

    public static void escribirFichero (String nombreFichero, String cadena) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true));
        writer.append(cadena);
        writer.close();
    }
}
