package com.servicios;

import com.modelos.Contrato;
import com.modelos.Empleado;
import com.utilidades.Fecha;
import com.utilidades.Prints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiciosInformes {
    public void generarInforme(ArrayList<Empleado> empleados){
        Prints prints = new Prints();
        System.out.println("→ ¿Cuál es el empleado actual de mayor edad?");
        System.out.println(empleadoMasMayor(empleados));
        prints.limpiar(1);

        System.out.println("→ ¿Cuál es el empleado actual de menor edad?");
        System.out.println(empleadoMasJoven(empleados));
        prints.limpiar(1);

        System.out.println("→ ¿Cuántos empleados tiene la empresa actualmente?");
        prints.limpiar(1);
        System.out.println("La empresa cuenta actualmente con : " + empleados.size() + " empleados en plantilla");
    }

    // ¿Cuál es el empleado actual de mayor edad?
    private Empleado empleadoMasMayor(ArrayList<Empleado> empleados){
        Prints prints = new Prints();
        prints.limpiar(1);
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

    private Empleado empleadoMasJoven(ArrayList<Empleado> empleados){
        Prints prints = new Prints();
        prints.limpiar(1);
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

    private Set<String> dadoDeBajaEsteAño(ArrayList<Empleado> empleados){
        Set<String> hashset = new HashSet<>();
        for (int i = 0; i < empleados.size(); i++){
            if(empleados.get(i).getContratos() != null){
                Iterator<Contrato> listaIterada = empleados.get(i).getContratos().iterator();
                boolean salida = false;
                String añoActual = Fecha.extraerElAñoDeUnaFecha(Fecha.creaciónFechaActual());
                while (listaIterada.hasNext() || !salida){
                    if (Fecha.extraerElAñoDeUnaFecha(listaIterada.next().getFechaFinalContrato()).equals(añoActual)){
                        hashset.add(empleados.get(i).getCodigo());
                        salida = true;
                    }
                }
            }
        }
        return hashset;
    }

    // ¿Qué directivo es responsable de más departamentos?
    // ¿Cuál es el directivo responsable del departamento X?
    // ¿Qué empleado tiene más titulaciones en especialidades?
    // ¿Cuál es la titulación de la especialidad más reciente y a quién pertenece?

}
