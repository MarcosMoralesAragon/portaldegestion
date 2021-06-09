package com.pruebas;

import com.servicios.GestionFicheros;

public class Prueba {
    public static void main(String[] args){
        GestionFicheros gestionFicheros = new GestionFicheros();
        gestionFicheros.borrarFichero("Funciones que ya no se usan.txt");
    }
}