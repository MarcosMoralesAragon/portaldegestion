package modelos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Empleado {

    private String codigo;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String DNI;
    private Date fechaNacimiento;
    private String nacionalidad;
    private Direccion direccion;
    private Estado estado;

    public Empleado(String nombre) {
        this.nombre = nombre;
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

    public void setPrimerApellido(String a){
        this.primerApellido = a;
    }
    public String getPrimerApellido(){
        return primerApellido;
    }

    public void setSegundoApellido(String a){
        this.segundoApellido = a;
    }
    public String getSegundoApellido(){
        return segundoApellido;
    }

    public void setDNI (String d){
        this.DNI = d;
    }
    public String getDNI(){
        return DNI;
    }

    public void setFechaNacimiento (Date fecha){
        this.fechaNacimiento = fecha;
    }
    public Date getFechaNacimiento (){
        return fechaNacimiento;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setDireccion (Direccion d){
        this.direccion = d;
    }
    public Direccion getDireccion () {
        return direccion;
    }

    public void setEstado (Estado e){
        this.estado = e;
    }
    public Estado getEstado(){
        return estado;
    }



    @Override
    public String toString() {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaNacimientoFormateada = formatoFecha.format(this.fechaNacimiento);

        return  " Código --> " + codigo + " / " +
                " Nombre --> " + nombre + " / " +
                " Apellidos --> " + primerApellido + " " + segundoApellido + " / " +
                " DNI --> " + DNI + " / " +
                " Fecha de Nacimiento --> " + fechaNacimientoFormateada + " / " +
                " Nacionalidad --> " + nacionalidad + " / " +
                " Estado --> " + estado + "\n" +
                " Dirección completa --> " + direccion + " / ";

    }

}


