package com.operalogistica.model;

public class Maritimo extends Envio {

    public Maritimo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return (getPeso() * 1500) + (getDistancia() * 800);
    }

    @Override
    public String getTipo() {
        return "Marítimo";
    }
}
