package utilidades;

public class Alfanumerico {


    public static String generar() {

        // Genera el codigo alfanumerico de manera aleatroia entre numeros y letras minusculas

        int digitos = 0;
        String resultado = "";
        do {
            switch ((int)(Math.random()*2)){
                case 0:
                    resultado += Alfanumerico.numAleatorio();
                    break;
                case 1:
                    resultado += Alfanumerico.letraAleatoria();
                    break;
            }
            digitos ++;
        }while (digitos < 6);
        return resultado;
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
