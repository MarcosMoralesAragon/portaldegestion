package modelos;

public class Directivo extends Empleado{

    private String departamentoACargo;

    public String getDepartamento() {
        return departamentoACargo;
    }

    public void setDepartamento(String departamento) {
        this.departamentoACargo = departamento;
    }

    public Directivo(String n, String departamento) {
        super(n);
        this.departamentoACargo = departamento;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
