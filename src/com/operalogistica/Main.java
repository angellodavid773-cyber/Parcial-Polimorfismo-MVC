package com.operalogistica;

import com.operalogistica.controller.EnvioController;
import com.operalogistica.service.EnvioService;
import com.operalogistica.view.OperadorLogisticoView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnvioService service = new EnvioService();
            EnvioController controller = new EnvioController(service);
            OperadorLogisticoView view = new OperadorLogisticoView(controller);
            view.setVisible(true);
        });
    }
}
