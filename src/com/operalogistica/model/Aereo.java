package com.operalogistica.model;

public class Aereo extends Envio {

    public Aereo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return (getPeso() * 5000) + (getDistancia() * 3000) + 20000;
    }

    @Override
    public String getTipo() {
        return "Aéreo";
    }
}
