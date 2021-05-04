package com.modelos;

public enum Estado {
    ALTA("Alta"),
    BAJA("Baja"),
    EN_TRAMITE("En tramite");

    private String estado;

    Estado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return estado;
    }
}
