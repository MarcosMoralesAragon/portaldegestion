package com.pruebas;


import com.ficheros.GestionFicheros;
import com.modelos.Empleado;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Prueba {

    public static final ArrayList<Empleado> empleados = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        /** File fichero = new File("asdfsadf");
        try {
            // A partir del objeto File creamos el fichero físicamente
            if (fichero.createNewFile())
                System.out.println("El fichero se ha creado correctamente");
            else
                System.out.println("No ha podido ser creado el fichero");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }*/

        GestionFicheros.borrarFichero("asdfsadf");


    }
}
