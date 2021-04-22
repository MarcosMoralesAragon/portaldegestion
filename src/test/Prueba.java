package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Prueba {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Administrador\\Desktop\\empleados.txt"))) {

            list = stream
            .filter(line -> !line.endsWith(" "))
            .flatMap(string -> Stream.of(string.split("#")))
            .collect(Collectors.toList());
            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

        list.forEach(System.out::println);
    }
}
