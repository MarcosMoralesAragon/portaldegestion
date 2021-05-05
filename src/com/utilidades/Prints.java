package com.utilidades;

import com.ficheros.Servicios;

import java.util.Scanner;

public class Prints {

    public static void siNo (){
        System.out.println("━━━━━━━━━━");
        System.out.println("|         |");
        System.out.println("| 1. Sí   |");
        System.out.println("| 2. No   |");
        System.out.println("|         |");
        System.out.println("━━━━━━━━━━");
        System.out.print(" > ");
    }
    public static void  otroSalir(){
        System.out.println("━━━━━━━━━━━━━━");
        System.out.println("|            |");
        System.out.println("| 1. Salir   |");
        System.out.println("| 2. Otro    |");
        System.out.println("|            |");
        System.out.println("━━━━━━━━━━━━━━");
    }
    public static void terminadaAccion(){
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println(" → Acción terminada, volviendo a la selección principal ← ");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }
    public static void separadorConTexto(String campo){
        Prints.separador();
        System.out.println(campo);
        System.out.print("> ");
    }

    public static void separador(){
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    }
    public static void main(){
        System.out.println("╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 1. Crear -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 2. Listar  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 3. Borrar  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 4. Modificar _-_-_-_-_-_-_-_-_-_-_-_-_--_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 5. Guardar Empleados -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 6. Guardar Todo  -_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 7. Listar Papelera -_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 8. Guardar Papelera  -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 9. Recuperar papelera  -_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 10. Vaciar papelera  -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 11. Salir  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝");
        System.out.print(" > ");
    }
    public static void eleccionModificar() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("|                     |");
        System.out.println("| 1. Datos personales |");
        System.out.println("| 2. Dirreción        |");
        System.out.println("| 3. Estado           |");
        System.out.println("| 4. Todo             |");
        System.out.println("|                     |");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
    }
    public static void limpiar(int lineas) {
        for (int i=0; i < lineas; i++) {
            System.out.println();
        }
    }

    public static void finalFuncion(){
        limpiar(1);
        terminadaAccion();
        limpiar(1);
    }
    public static void introduzcaDatos(Scanner in){
        Servicios.vaciarScanner(in);
        Prints.separador();
        System.out.println("Introduzca el codigo del empleado");
        System.out.print("> ");
    }

    public static void error (){
        System.out.println("Error introduciendo datos, vuelva a intentarlo");
    }
}
