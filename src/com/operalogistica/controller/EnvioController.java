package com.operalogistica.controller;

import com.operalogistica.model.Envio;
import com.operalogistica.service.EnvioService;

import java.util.List;

public class EnvioController {
    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    public boolean registrarEnvio(String tipo, String codigo, String cliente, double peso, double distancia) {
        return envioService.agregarEnvio(tipo, codigo, cliente, peso, distancia);
    }

    public boolean eliminarEnvio(String codigo) {
        return envioService.retirarEnvio(codigo);
    }

    public List<Envio> listarEnvios() {
        return envioService.obtenerTodos();
    }
}
