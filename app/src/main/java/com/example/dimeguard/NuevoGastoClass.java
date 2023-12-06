package com.example.dimeguard;

public class NuevoGastoClass {
    private double monto;
    private String tipo;
    private String uid;
    private String categoria;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public NuevoGastoClass() {
        // Constructor vacío necesario para Firebase
    }

    public NuevoGastoClass(double monto, String uid, String tipo, String categoria) {
        this.monto = monto;
        this.uid = uid;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public double getMonto() {

        return monto;
    }

    public void setMonto(double monto) {

        this.monto = monto;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {

        this.uid = uid;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
