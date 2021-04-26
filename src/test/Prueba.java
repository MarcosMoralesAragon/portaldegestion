package test;

import modelos.Empleado;
import modelos.Estado;
import utilidades.Alfanumerico;
import utilidades.GestionFicheros;
import utilidades.Prints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Prueba {

    public static void main(String[] args) {

        int camposRellenadosTotales = 0;

        System.out.println(GestionFicheros.leerFichero("empleados.txt").size());

        do {
            int camposRellenados;
            camposRellenados = 7;
            camposRellenados += 8;
            camposRellenadosTotales += camposRellenados;

            System.out.println(camposRellenadosTotales);

        } while (camposRellenadosTotales < GestionFicheros.leerFichero("empleados.txt").size());


    }
}
