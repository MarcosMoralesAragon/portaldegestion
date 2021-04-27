package utilidades;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestionFicheros {

    public static List leerFichero (String nombreFichero) {

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(nombreFichero))) {
            list = stream
                    .flatMap(string -> Stream.of(string.split("#")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void escribirFichero (String nombreFichero, String cadena) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true));
        writer.append(cadena);
        writer.close();
    }
}
