package com.modelos;

import com.utilidades.Fecha;

import java.util.ArrayList;
import java.util.Date;

public class Contrato extends ArrayList<Contrato> {

    private int id;   //PK
    private Date fechaInicioContrato;
    private Date fechaFinalContrato;
    private Date fechaFinalizacionEstimada;
    private double salario;
    private Puesto puesto;
    private String codigoEmpleadoAsignado;

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

    public void setId(int id){ this.id = id; }
    public int getId(){ return id; }

    public void setCodigoEmpleadoAsignado(String codigoEmpleadoAsignado) {
        this.codigoEmpleadoAsignado = codigoEmpleadoAsignado;
    }
    public String getCodigoEmpleadoAsignado() {
        return codigoEmpleadoAsignado;
    }

    public String cadenaFormateadaConAlmohadillas() {
        String fechaInicioFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaInicioContrato);
        String fechaFinalFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalContrato);
        String fechaFinalizacionFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalizacionEstimada);

        return id + " / " + fechaInicioFormateada + " / " + fechaFinalFormateada + " / "
                + fechaFinalizacionFormateada + " / " + salario + " / " + puesto + " / " + codigoEmpleadoAsignado + " // ";
    }

    @Override
    public String toString() {
        String fechaAltaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaInicioContrato);
        String fechaBajaFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalContrato);
        String fechaFinalizacionFormateada = Fecha.formateoDeFechaParaFechaCreadoYBorrado(fechaFinalizacionEstimada);

        return id + "#" + fechaAltaFormateada + "#" + fechaBajaFormateada + "#" + "#" + fechaFinalizacionFormateada
                + salario + "#" + puesto + "#" + codigoEmpleadoAsignado +"/";
    }
}
