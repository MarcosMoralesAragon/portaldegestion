package com.ficheros;

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
        empleadoMasMayor(empleados);
        Prints.limpiar(1);

        System.out.println("→ ¿Cuál es el empleado actual de menor edad?");
        empleadoMasJoven(empleados);
        Prints.limpiar(1);

        System.out.println("→ ¿Cuántos empleados tiene la empresa actualmente?");
        Prints.limpiar(1);
        System.out.println("La empresa cuenta actualmente con : " + empleados.size() + " empleados en plantilla");
    }
    // ¿Cuál es el empleado actual de mayor edad?

    private static void empleadoMasMayor(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        try {
            System.out.println("Empleado más mayor  ↓");
            System.out.println(empleadoConMasEdad(empleados).cadenaFormateadaParaMostrarPorPantalla());
        }catch (NullPointerException e){
            System.out.println("Al compararse las fechas se ha encontrado que ningun empleado es mas viejo que la fecha actual del equipo");
        }
    }

    private static Empleado empleadoConMasEdad(ArrayList<Empleado> empleados){
        Date fechaMasBaja = Fecha.creaciónFechaActual();
        Empleado empleadoMasMayor = new Empleado();
        for (Empleado empleadoSepardoDelArrayList : empleados) {
            if (empleadoSepardoDelArrayList.getFechaNacimiento().before(fechaMasBaja)) {
                empleadoMasMayor = empleadoSepardoDelArrayList;
                fechaMasBaja = empleadoSepardoDelArrayList.getFechaNacimiento();
            }
        }
        return empleadoMasMayor;
    }

    // ¿Cuál es el empleado actual de menor edad?

    private static void empleadoMasJoven(ArrayList<Empleado> empleados){
        Prints.limpiar(1);
        try {
            System.out.println("Empleado más joven  ↓");
            System.out.println(empleadoConMenosEdad(empleados).cadenaFormateadaParaMostrarPorPantalla());

        } catch (NullPointerException e) {
            System.out.println("Al compararse las fechas se ha encontrado que ningun empleado es mas joven que la fecha : 30-11-0002");
        }
    }

    private static Empleado empleadoConMenosEdad(ArrayList<Empleado> empleados){
        Date fechaMasBaja = null;
        String fecha = "30-11-0002";
        try {
            fechaMasBaja = Fecha.fecha(fecha);
        } catch (ParseException e){ // Nunca deberia de darse este caso porque la fecha esta introducida como una variable y no como un Scanner
            System.out.println("Error en la fecha base");
        }
        Empleado empleadoMasJoven = new Empleado();
        for (Empleado empleadoSepardoDelArrayList : empleados) {
            if (empleadoSepardoDelArrayList.getFechaNacimiento().after(fechaMasBaja)) {
                empleadoMasJoven = empleadoSepardoDelArrayList;
                fechaMasBaja = empleadoSepardoDelArrayList.getFechaNacimiento();
            }
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
