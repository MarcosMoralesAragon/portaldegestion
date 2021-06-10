package com.utilidades;


public class Prints {
    public void escribir(String frase){
        System.out.println(frase);
    }
    public void siNo (){
        System.out.println("━━━━━━━━━━");
        System.out.println("|         |");
        System.out.println("| 1. Sí   |");
        System.out.println("| 2. No   |");
        System.out.println("|         |");
        System.out.println("━━━━━━━━━━");
        System.out.print(" > ");
    }
    public void  otroSalir(){
        System.out.println("━━━━━━━━━━━━━━");
        System.out.println("|            |");
        System.out.println("| 1. Salir   |");
        System.out.println("| 2. Otro    |");
        System.out.println("|            |");
        System.out.println("━━━━━━━━━━━━━━");
    }
    public void terminadaAccion(){
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println(" → Acción terminada, volviendo a la selección principal ← ");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }
    public void finDeLaAplicacion(){
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░░             → Cerrando programa, gracias por su uso ←            ░░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }
    public void separadorConTexto(String campo){
        separador();
        System.out.println(campo);
        System.out.print("> ");
    }

    public void separador(){
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    }
    public void main(){
        System.out.println("╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 1. Crear -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 2. Crear Contrato  -_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 3. Listar  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 4. Listar Contrato -_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 5. Listar Papelera  -_-_-_-_-_-_-_-_-_--_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 6. Modificar -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 7. Borrar  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 8. Cargar Papelera -_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 9. Guardar Papelera  -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 10. Restaurar Papelera -_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 11. Vaciar Papelera  -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 12. Guardar Todo -_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 13. Informe  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 14. Guardar a BBDD -_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 15. Actualizar BBDD  -_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 16. Guardar todo a BBDD  -_-_-_-_-_-_-_-_-_╣");
        System.out.println("╠_-_-_-_-_-_-_-_-_-_-_-_- 17. Salir  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_╣");
        System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝");
        System.out.print(" > ");
    }
    public void eleccionModificar() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("|                     |");
        System.out.println("| 1. Datos personales |");
        System.out.println("| 2. Dirreción        |");
        System.out.println("| 3. Estado           |");
        System.out.println("| 4. Todo             |");
        System.out.println("|                     |");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
    }
    public void limpiar(int lineas) {
        for (int i=0; i < lineas; i++) {
            System.out.println();
        }
    }
    public void finalFuncion(){
        limpiar(1);
        terminadaAccion();
        limpiar(1);
    }
    public void introduzcaDatos(){
        separador();
        System.out.println("Introduzca el codigo del empleado");
        System.out.print("> ");
    }
    public void error (){
        System.out.println("Error introduciendo datos, volviendo a el menu principal");
    }
    public void generarContrato(){
        System.out.println("¿Quiere introducir una fecha de finalización estimada?");
        siNo();
    }
}
