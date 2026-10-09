package com.operalogistica.model;

public class Terrestre extends Envio {

    public Terrestre(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return (getPeso() * 2000) + (getDistancia() * 1000);
    }

    @Override
    public String getTipo() {
        return "Terrestre";
    }
}
