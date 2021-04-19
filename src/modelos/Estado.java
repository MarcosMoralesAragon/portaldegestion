package modelos;

public class Estado {

    public enum EstadoEmpleado {

        ALTA , BAJA , ENTRAMITE;

    }

    public static void generarEstado (int eleccion){

        EstadoEmpleado empleado = null;

        switch (eleccion){
            case 1:
                 empleado = EstadoEmpleado.ALTA;
                break;
            case 2:
                empleado = EstadoEmpleado.BAJA;
                break;

            case 3:
                empleado = EstadoEmpleado.ENTRAMITE;
                break;
        }
    }

    @Override
    public String toString() {
        return "model.Estado{}";
    }
}
