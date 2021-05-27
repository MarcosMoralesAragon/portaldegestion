package com.utilidades;

public class GeneradorCodigos {


    public int generarCodigoDirección(){

        int resultado = 0;
        String stringResultado = "";
        int digitos = (int)(Math.random() * 9 + 1);
        do {
            stringResultado += numAleatorio();
            digitos --;
        }while (digitos > 0);
        resultado = Integer.parseInt(stringResultado);
     return resultado;
    }

    public String generarCodigoEmpleados() {

        // Genera el codigo alfanumerico de manera aleatroia entre numeros y letras minusculas

        int digitos = 0;
        StringBuilder resultado = new StringBuilder();
        do {
            switch ((int)(Math.random()*2)){
                case 0:
                    resultado.append(numAleatorio());
                    break;
                case 1:
                    resultado.append(letraAleatoria());
                    break;
            }
            digitos ++;
        }while (digitos < 6);
        return resultado.toString();
    }
    // --------------> FUNCIONES GENERAR <--------------
    private static String letraAleatoria() {
        String palabra = "";
        int codigoAscii = (int)Math.floor(Math.random()*(122 - 97)+97);
        palabra += (char) codigoAscii;
        return palabra;
    }
    private static String numAleatorio() {
        String palabra = "";
        int numeroaleatorio = (int)(Math.random()*10);
        palabra += numeroaleatorio;
        return palabra;
    }
}
