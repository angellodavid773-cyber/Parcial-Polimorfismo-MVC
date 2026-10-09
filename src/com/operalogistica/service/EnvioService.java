package com.operalogistica.service;

import com.operalogistica.model.Aereo;
import com.operalogistica.model.Envio;
import com.operalogistica.model.Maritimo;
import com.operalogistica.model.Terrestre;

import java.util.ArrayList;
import java.util.List;

public class EnvioService {
    private final List<Envio> listaEnvios = new ArrayList<>();

    public boolean agregarEnvio(String tipo, String codigo, String cliente, double peso, double distancia) {
        for (Envio e : listaEnvios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return false;
            }
        }

        Envio nuevoEnvio = null;
        switch (tipo.toLowerCase()) {
            case "terrestre":
                nuevoEnvio = new Terrestre(codigo, cliente, peso, distancia);
                break;
            case "aereo":
            case "aéreo":
                nuevoEnvio = new Aereo(codigo, cliente, peso, distancia);
                break;
            case "maritimo":
            case "marítimo":
            case "fluvial":
                nuevoEnvio = new Maritimo(codigo, cliente, peso, distancia);
                break;
            default:
                return false;
        }

        return listaEnvios.add(nuevoEnvio);
    }

    public boolean retirarEnvio(String codigo) {
        return listaEnvios.removeIf(envio -> envio.getCodigo().equalsIgnoreCase(codigo));
    }

    public List<Envio> obtenerTodos() {
        return new ArrayList<>(listaEnvios);
    }
}
