package com.modelos;

import com.utilidades.Fecha;

import java.util.Date;

public class Contrato {

    private Date fechaInicioContrato;
    private Date fechaFinalContrato;
    private double salario;
    private Puesto puesto;

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

    @Override
    public String toString() {

        String fechaAltaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaInicioContrato);
        String fechaBajaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalContrato);

        return "#" + fechaAltaFormateada + "#" + fechaBajaFormateada + "#" + salario + "#" + puesto;
    }

}
