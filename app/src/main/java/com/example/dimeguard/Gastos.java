package com.example.dimeguard;

public class Gastos {
    String tipo, fecha, categoría;
    double montoG;
    public Gastos(){}

    public Gastos(String tipo, String fecha, String categoría, double montoG) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.categoría = categoría;
        this.montoG = montoG;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
    }

    public double getMontoG() {
        return montoG;
    }

    public void setMontoG(double montoG) {
        this.montoG = montoG;
    }
}
