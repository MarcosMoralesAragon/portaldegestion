package com.pruebas;


import com.utilidades.Prints;

import java.util.Scanner;

public class Prueba {

    public static void main(String[] args){
        int contador = 0;
        boolean estaBien = false;
        Scanner in = new Scanner(System.in);
        Prints prints = new Prints();
        prints.escribir("Sisisi");
        do{
            String dni = in.nextLine();
            char[] datoSeparado = dni.toCharArray();
            try {
                for(int i = 0; i <= 6; i++){
                    // Prueba a transformar los 6 primeros caracteres a int usando los parse, los cuales pueden dar
                    // un error, error que uso a mi favor ya que es la manera de controlar si realmente el dato esta bien
                    Integer.parseInt(String.valueOf(datoSeparado[i]));
                }
                try {
                    // Prueba a transformar la letra a int, lo cual tiene que dar error ya que una letra no puede ser numero
                    Integer.parseInt(String.valueOf(datoSeparado[7]));
                } catch (Exception e){
                    estaBien = true;
                }
            } catch (Exception e) {
                estaBien = false;
            }
            if(!estaBien){
                contador ++;
                prints.escribir("DNI incorrecto quedan " + (3-contador) + " intentos");
            }
        } while (contador < 3 && !estaBien);

        if (contador == 3){
            prints.escribir("Te has quedado sin intentos, serás enviado a la páguina principal");
            // TODO crear una excepcion y lanzarla hacia arriba
        } else {
            prints.escribir("DNI valido");
        }
    }
}