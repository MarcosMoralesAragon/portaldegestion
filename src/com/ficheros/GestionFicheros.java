package com.ficheros;

import com.utilidades.Prints;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;



public class GestionFicheros {


    public static void leerFichero (String nombreFichero,String palabra){ //TODO cambiar

        String[] datoSeparado = null;

        Scanner in = null;
        try {
            in = new Scanner(Paths.get(nombreFichero));

            while (in.hasNextLine()){

                String linea = in.nextLine();
                datoSeparado = linea.split("#");
                if (palabra.equals("empleados")){
                    Servicios.cargarLista(in, datoSeparado,palabra);
                } else if (palabra.equals("papelera")){
                    Servicios.cargarLista(in,datoSeparado,palabra);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert in != null;
            in.close();
        }

        System.out.println("Archivo " + nombreFichero + " cargado");
        Prints.limpiar(1);
    }

    public static boolean borrarFichero(String nombreFichero){
        File fichero = new File(nombreFichero);
        boolean borrado = fichero.delete();
        return borrado;
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
