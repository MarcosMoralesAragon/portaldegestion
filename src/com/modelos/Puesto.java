package com.modelos;

public enum Puesto {
    DIRECTIVO ("Directivo"),
    OFICIAL("Oficial"),
    OPERARIO("Operario"),
    TECNICO("Tecnico");

    private String puesto;

    Puesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return puesto;
    }
}
