package modelos;

public enum Estado {
    ALTA("Alta"),
    BAJA("Baja"),
    EN_TRAMITE("En tramite");

    private String estado;

    Estado(String estado) {
        this.estado = estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return estado;
    }
}
