package com.modelos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Date fechaAlta;
    private ArrayList<Contrato> contratos = new ArrayList<>(); // TODO

    public Empleado(){}

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

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setContratos(ArrayList<Contrato> contratos) {
        this.contratos = contratos;
    }
    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public String cadenaFormateadaParaMostrarPorPantalla (){

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaNacimientoFormateada = null;
        if (fechaNacimiento != null){
            fechaNacimientoFormateada = formatoFecha.format(this.fechaNacimiento);
        }

        return  " Código --> " + codigo + " / " +
                " Nombre --> " + nombre + " / " +
                " Apellidos --> " + primerApellido + " " + segundoApellido + " / " +
                " DNI --> " + DNI + " / " +
                " Fecha de Nacimiento --> " + fechaNacimientoFormateada + " / " +
                " Nacionalidad --> " + nacionalidad + " / " +
                " Estado --> " + estado + "\n" +
                " Dirección completa --> " + direccion + " / " + "\n" +
                " Contratos --> " + getContratos(); // TODO Mostrar como es debido
    }

    @Override
    public String toString() {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaNacimientoFormateada = null;
        if (fechaNacimiento != null){
            fechaNacimientoFormateada = formatoFecha.format(this.fechaNacimiento);
        }

        return codigo + "#" + nombre + "#" + primerApellido + "#" + segundoApellido + "#" + DNI + "#" +
                fechaNacimientoFormateada + "#" + nacionalidad + "#" + estado + "#" +
                direccion.cadenaConAlmohadillaDireccion() + contratos + "\n";
    }
}