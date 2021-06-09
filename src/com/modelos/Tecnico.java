package com.modelos;

import java.util.Date;

public class Tecnico extends Operario{

    private String titulo;
    private Date fechaDeFinalizacion;


    public Date getFechaDeFinalizacion() {
       return fechaDeFinalizacion;
   }

    public void setFechaDeFinalizacion(Date fechaDeFinalizacion) {
       this.fechaDeFinalizacion = fechaDeFinalizacion;
    }



    public String getTitulo() {
        return titulo;
   }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Tecnico() {}

    @Override
    public String toString() {
        return super.toString();
    }

}
