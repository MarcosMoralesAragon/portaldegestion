package test;
import modelos.Empleado;
import java.util.ArrayList;


public class Prueba {

    public static void main(String[] args) {

        ArrayList<Producto> productos = new ArrayList<>();
        // Le agregamos datos
        productos.add(new Producto("123", "Gansito", 20f));
        productos.add(new Producto("456", "Galletas Chokis", 12f));
        productos.add(new Producto("7879876456", "Doritos", 5.5f));

        Producto busqueda = new Producto("123", "Gansito", 20f);

        Producto encontrado = null;
        // Voy a buscar únicamente comparando el código

        for (int x = 0; x < productos.size(); x++) {
            Producto p = productos.get(x);
            if (p.getCodigo().equals(busqueda.getCodigo())) {
                encontrado = p;
                break; // Terminar ciclo, pues ya lo encontramos
            }
        }
        // Al terminar el ciclo comprobamos si se movió la variable

        System.out.println(encontrado);

    }
}

class Producto {
    private String codigo, nombre;
    private float precio;

    public Producto() {
    }

    public Producto(String codigo, String nombre, float precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }
}
