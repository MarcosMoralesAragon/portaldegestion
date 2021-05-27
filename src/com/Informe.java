package com;

import com.modelos.Empleado;
import com.utilidades.Fecha;
import com.utilidades.Prints;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Informe {
    public static void generarInforme(ArrayList<Empleado> empleados){
        System.out.println("→ ¿Cuál es el empleado actual de mayor edad?");
        System.out.println(empleadoMasMayor(empleados));
        Prints.limpiar(1);

        System.out.println("→ ¿Cuál es el empleado actual de menor edad?");
        System.out.println(empleadoMasJoven(empleados));
        Prints.limpiar(1);

        System.out.println("→ ¿Cuántos empleados tiene la empresa actualmente?");
        Prints.limpiar(1);
        System.out.println("La empresa cuenta actualmente con : " + empleados.size() + " empleados en plantilla");
    }

    // ¿Cuál es el empleado actual de mayor edad?
    private static Empleado empleadoMasMayor(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        System.out.println("Empleado más mayor  ↓");
        Empleado empleadoMasMayor = new Empleado();
        if (empleados.size() > 1) {
            for (int i = 0; i < empleados.size(); i++) {
                if (i == 0){
                    if (empleados.get(i).getFechaNacimiento().before(empleados.get(i + 1).getFechaNacimiento())){
                        empleadoMasMayor = empleados.get(i);
                    } else {
                        empleadoMasMayor = empleados.get(i + 1);
                    }
                    // Sumo 1 para que pase de ser i = 0 a i = 2 para la próxima referencia ya que he usado i=0 y i=1
                    i++;
                } else if (i >= 2){
                    if (empleados.get(i).getFechaNacimiento().before(empleadoMasMayor.getFechaNacimiento())){
                        empleadoMasMayor = empleados.get(i);
                    }
                }
            }
        } else {
            System.out.println(empleados.get(0));
        }
        return empleadoMasMayor;
    }

    // ¿Cuál es el empleado actual de menor edad?

    private static Empleado empleadoMasJoven(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        System.out.println("Empleado más joven  ↓");
        Empleado empleadoMasJoven = new Empleado();
        if (empleados.size() > 1) {
            for (int i = 0; i < empleados.size(); i++) {
                if (i == 0){
                    if (empleados.get(i).getFechaNacimiento().after(empleados.get(i + 1).getFechaNacimiento())){
                        empleadoMasJoven = empleados.get(i);
                    } else {
                        empleadoMasJoven = empleados.get(i + 1);
                    }
                    // Sumo 1 para que pase de ser i = 0 a i = 2 para la próxima referencia ya que he usado i=0 y i=1
                    i++;
                } else if (i >= 2){
                    if (empleados.get(i).getFechaNacimiento().after(empleadoMasJoven.getFechaNacimiento())){
                        empleadoMasJoven = empleados.get(i);
                    }
                }
            }
        } else {
            System.out.println(empleados.get(0));
        }
        return empleadoMasJoven;
    }

    // ¿Cuántos empleados se han dado de baja el año actual?
    // TODO Extraer el año mediante alguna función del Date.Una vez con esa fecha hacer un array con los empleados que
    //  estan baja y luego seleccionar de esos que el año sea el mismo

    private static void dadoDeBajaEsteAño(ArrayList<Empleado> empleados){

        SimpleDateFormat sacarElAñoDeLaFecha = new SimpleDateFormat("yyyy");
        Date añoActual = Fecha.creaciónFechaActual();
        ArrayList<Empleado> empleadosBorradosEsteAño = new ArrayList<> ();

        for (Empleado empleadoSepardoDelArrayList : empleados) {
            /** if (sacarElAñoDeLaFecha.format(añoActual).equals(sacarElAñoDeLaFecha.format(empleadoSepardoDelArrayList.getFechaBaja()))) {
             empleadosBorradosEsteAño.add(empleadoSepardoDelArrayList);
             } */ // TODO
        }
    }

    // ¿Qué directivo es responsable de más departamentos?
    // ¿Cuál es el directivo responsable del departamento X?
    // ¿Qué empleado tiene más titulaciones en especialidades?
    // ¿Cuál es la titulación de la especialidad más reciente y a quién pertenece?

}
