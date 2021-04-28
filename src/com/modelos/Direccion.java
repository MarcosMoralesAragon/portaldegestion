package com.modelos;

public class Direccion {

    private String calle;
    private int numero;
    private String bloque;
    private String piso;
    private String puerta;
    private int codigoPostal;
    private String localidad;
    private String provincia;

    public Direccion (){

    }

    public void setCalle (String c){
        this.calle = c;
    }
    public String getCalle (){
        return calle;
    }

    public void setNumero (int n){
        this.numero = n;
    }
    public int getNumero (){
        return numero;
    }

    public void setBloque (String b){
        this.bloque = b;
    }
    public String getBloque (){
        return bloque;
    }

    public void setPiso(String piso){
        this.piso = piso;
    }
    public String getPiso (){
        return piso;
    }

    public void setPuerta(String puerta){
        this.puerta = puerta;
    }
    public String getPuerta (){
        return puerta;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }
    public String getLocalidad (){
        return localidad;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }
    public String getProvincia (){
        return provincia;
    }

    public void setCodigoPostal (int  cp){
        this.codigoPostal = cp;
    }
    public int getCodigoPostal (){
        return codigoPostal;
    }

    public String cadenaConAlmohadillaDireccion(){

        return calle + "#" + numero + "#" + bloque + "#" + piso + "#" + puerta + "#" + localidad + "#" + provincia
                + "#" + codigoPostal;
    }

    @Override
    public String toString() {
        return  " Calle --> " + calle + " / " +
                " Numero --> " + numero + " / " +
                " Bloque --> " + bloque + " / " +
                " Piso --> " + piso + " / " +
                " Puerta --> " + puerta + " / " +
                " Codigo Postal --> " + codigoPostal + " / " +
                " Localidad --> " + localidad + " / " +
                " Provincia --> " + provincia ;
    }
}
