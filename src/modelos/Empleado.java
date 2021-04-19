package modelos;

import java.util.Date;

public class Empleado {

    private String codigo;
    private String nombre;
    private String apellido;
    private String DNI;
    private Date fechaNacimiento;
    private String nacionalidad;
    private Direccion direccion;
    private Estado estado;

    public Empleado(String n) {
        this.nombre = n;
    }

    public void setNombre(String n){
        this.nombre = n;
    }
    public String getNombre(){
        return nombre;
    }

    public void setCodigo (String c){ this.codigo = c; }
    public String getCodigo (){
        return codigo;
    }

    public void setApellido(String a){
        this.apellido = a;
    }
    public String getApellido(){
        return apellido;
    }

    public void setDNI (String d){ this.DNI = d; }
    public String getDNI(){ return DNI; }

    public void setFechaNacimiento (Date fecha){
        this.fechaNacimiento = fecha;
    }
    public Date getFechaNacimiento (){
        return fechaNacimiento;
    }

    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public String getNacionalidad() { return nacionalidad; }

    public void setDireccion (Direccion d){
        this.direccion = d;
    }
    public Direccion getDireccion () {
        return direccion;
    }

    public void setEstado (Estado e){ this.estado = e; }
    public Estado getEstado(){ return estado;  }

    @Override
    public String toString() {
        return  " Código --> " + codigo + " / " +
                " Nombre --> " + nombre + " / " +
                " Apellido --> " + apellido + " / " +
                " DNI --> " + DNI + " / " +
                " Fecha de Nacimiento --> " + fechaNacimiento + " / " +
                " Nacionalidad --> " + nacionalidad + " / " +
                " Estado --> " + bonitoEstado(estado) + "\n" +
                " Dirección completa --> " + direccion + " / ";

    }
    public String bonitoEstado (Estado estado){
        String frase = null;

        switch (estado){
            case ALTA:
                frase = "Alta";
                break;

            case BAJA:
                frase = "Baja";
                break;

            case EN_TRAMITE:
                frase = "En tramite";
                break;
        }
        return frase;
    }
}


