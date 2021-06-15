package com.pruebas;

import com.servicios.ServiciosFicheros;

public class Prueba {

    public static void main(String[] args){
        ServiciosFicheros serviciosFicheros = new ServiciosFicheros();
        serviciosFicheros.creadorFicheros("Documento.pdf");
    }
}