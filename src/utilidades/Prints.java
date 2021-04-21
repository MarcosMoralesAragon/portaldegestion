package utilidades;

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
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_-_-_- 6. Salir -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝");
        System.out.print(" > ");
    }

    public static void eleccionModificar() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("|                     |");
        System.out.println("| 1. Nombre           |");
        System.out.println("| 2. Apellidos        |");
        System.out.println("| 3. DNI              |");
        System.out.println("| 4. Fecha nacimiento |");
        System.out.println("| 5. Nacionalidad     |");
        System.out.println("| 6. Estado           |");
        System.out.println("| 7. Dirección        |");
        System.out.println("| 8. Todos            |");
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
