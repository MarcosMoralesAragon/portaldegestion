package com.ficheros;

import com.utilidades.Prints;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;



public class GestionFicheros {


    public static void leerFichero (String nombreFichero,String palabra){
        String[] datoSeparado;
        boolean frase = true;
        Scanner in = null;
        try {
            in = new Scanner(Paths.get(nombreFichero));

            while (in.hasNextLine()){

                String linea = in.nextLine();
                datoSeparado = linea.split("#");
                try {
                    if (palabra.equals("empleados")){
                        Servicios.cargarLista(datoSeparado,palabra);
                    } else if (palabra.equals("papelera")){
                        Servicios.cargarLista(datoSeparado,palabra);
                    }
                } catch (Exception e){
                    System.out.println("Fallo cargando los archivos, revise los datos en el interior");
                    frase = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert in != null;
            in.close();
        }
        if (frase){
            System.out.println("Archivo " + nombreFichero + " cargado");
            Prints.limpiar(1);
        }
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
