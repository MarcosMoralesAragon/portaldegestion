package com.ficheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestionFicheros {


    public static void leerFichero (String nombreFichero, Scanner in) throws IOException { //TODO cambiar

        String[] datoSeparado = null;

        in = new Scanner(Paths.get(nombreFichero));
        while (in.hasNextLine()){

            String linea = in.nextLine();
            datoSeparado = linea.split("#");
            Servicios.cargarLista(in, datoSeparado);

        }
    }

    public static void borrarFichero(String nombreFichero){
        File fichero = new File(nombreFichero);
        String resultado = "";

        fichero.delete();

    }

    public static void creadorFicheros(String nombreFichero){

        File fichero = new File(nombreFichero);
        String resultado = "";
        try {
            fichero.createNewFile();

        } catch (IOException e) {
            //
        }

    }

    public static void escribirFichero (String nombreFichero, String cadena) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true));
        writer.append(cadena);
        writer.close();
    }
}
