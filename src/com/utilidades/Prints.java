package com.utilidades;

public class Prints {

    public static void asegurar() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" --> Asegurese de que el número introducido coincide con los parámetros <--");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public static void siNo (){
        System.out.println("━━━━━━━━━━");
        System.out.println("|        |");
        System.out.println("| 1. Sí  |");
        System.out.println("| 2. No  |");
        System.out.println("|        |");
        System.out.println("━━━━━━━━━━");
        System.out.print(" > ");
    }

    public static void  otroSalir(){
        System.out.println("━━━━━━━━━━━━━");
        System.out.println("|            |");
        System.out.println("| 1. Otro    |");
        System.out.println("| 2. Salir   |");
        System.out.println("|            |");
        System.out.println("━━━━━━━━━━━━━");
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
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 1. Crear -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 2. Listar -_-_-_-_-_-_-_-_-_-_-_-_-_-_-╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 3. Borrar -_-_-_-_-_-_-_-_-_-_-_-_-_-_-╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 4. Papelera -_-_-_-_-_-_-_-_-_-_-_-_-_-╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 5. Modificar _-_-_-_-_-_-_-_-_-_-_-_-_-╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 6. Guardar Papelera -_-_-_-_-_-_-_-_-_-_-_-╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 7. Guardar Empleados -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 8. Salir -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
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

    public static void estados() {
        System.out.println("━━━━━━━━━━━━━━━━━━");
        System.out.println("|                |");
        System.out.println("| 1. Alta        |");
        System.out.println("| 2. Baja        |");
        System.out.println("| 3. En tramite  |");
        System.out.println("|                |");
        System.out.println("━━━━━━━━━━━━━━━━━━");
    }

    public static void saltoLinea(){
        System.out.println("\n");
    }
}
