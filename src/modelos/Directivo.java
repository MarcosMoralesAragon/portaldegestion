package modelos;

public class Directivo extends Empleado{

    private String departamento;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Directivo(String n, String departamento) {
        super(n);
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
