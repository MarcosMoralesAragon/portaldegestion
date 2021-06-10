package com.pruebas;



public class Prueba {

    public static Exception exception = new Exception();

    public static void main(String[] args){
        String palabra = "Palabra";
        try {
            palabra = "Ka";
            jeje();
        } catch (Exception e){
            System.out.println("Pillao");
        }
        System.out.println(palabra);
    }
    public static void jeje() throws Exception {
        throw exception;
    }
}