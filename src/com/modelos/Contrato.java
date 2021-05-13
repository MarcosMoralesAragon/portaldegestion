package com.modelos;

import com.utilidades.Fecha;

import java.util.ArrayList;
import java.util.Date;

public class Contrato extends ArrayList<Contrato> {

    private Date fechaInicioContrato;
    private Date fechaFinalContrato;
    private Date fechaFinalizacionEstimada;
    private double salario;
    private Puesto puesto;
    private String codigo;

    public Contrato(){}

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }
    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaFinalContrato(Date fechaFinalContrato) {
        this.fechaFinalContrato = fechaFinalContrato;
    }
    public Date getFechaFinalContrato() {
        return fechaFinalContrato;
    }

    public void setFechaFinalizacionEstimada(Date setFechaFinalizacionEstimada) {
        this.fechaFinalizacionEstimada = fechaFinalContrato;
    }
    public Date getFechaFinalizacionEstimada() {
        return fechaFinalizacionEstimada;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    public double getSalario() {
        return salario;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    public Puesto getPuesto() {
        return puesto;
    }

    public void setCodigo(String codigo){ this.codigo = codigo; }
    public String getCodigo(){ return codigo; }

    public String cadenaFormateadaConAlmohadillas() {

        String fechaAltaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaInicioContrato);
        String fechaBajaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalContrato);

        return fechaAltaFormateada + " # " + fechaBajaFormateada + " # " + salario + " * " + puesto + " # " + codigo;

    }

    @Override
    public String toString() {

        String fechaInicioFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaInicioContrato);
        String fechaFinalFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalContrato);
        String fechaFinalizacionFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalizacionEstimada);

        return fechaInicioFormateada + " / " + fechaFinalFormateada + " / " + fechaFinalizacionEstimada + " / " + salario
                + " / " + puesto + " / " + codigo;
    }
}
