package com.operalogistica.view;

import com.operalogistica.controller.EnvioController;
import com.operalogistica.model.Envio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OperadorLogisticoView extends JFrame {

    private final EnvioController controller;

    private JTextField txtNumero;
    private JTextField txtCliente;
    private JTextField txtPeso;
    private JTextField txtDistancia;
    private JComboBox<String> cbTipo;
    private JTable tablaEnvios;
    private DefaultTableModel modelTabla;

    public OperadorLogisticoView(EnvioController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Operador Logístico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Panel Superior: Botones de Acción ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregarIcon = new JButton("+ Agregar");
        JButton btnRetirarIcon = new JButton("- Retirar");
        
        btnAgregarIcon.setToolTipText("Agregar Envío");
        btnRetirarIcon.setToolTipText("Retirar Envío");

        panelTop.add(btnAgregarIcon);
        panelTop.add(btnRetirarIcon);

        // --- Panel Formulario ---
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Envío"));

        panelForm.add(new JLabel("Número (Código):"));
        txtNumero = new JTextField();
        panelForm.add(txtNumero);

        panelForm.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});
        panelForm.add(cbTipo);

        panelForm.add(new JLabel("Cliente:"));
        txtCliente = new JTextField();
        panelForm.add(txtCliente);

        panelForm.add(new JLabel("Distancia en Km:"));
        txtDistancia = new JTextField();
        panelForm.add(txtDistancia);

        panelForm.add(new JLabel("Peso en Kg:"));
        txtPeso = new JTextField();
        panelForm.add(txtPeso);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesForm.add(btnGuardar);
        panelBotonesForm.add(btnCancelar);

        panelForm.add(panelBotonesForm);

        // --- Panel Central: Tabla ---
        String[] columnas = {"Tipo", "Código", "Cliente", "Peso", "Distancia", "Costo"};
        modelTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEnvios = new JTable(modelTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEnvios);

        // Contenedor Norte
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelTop, BorderLayout.NORTH);
        panelNorte.add(panelForm, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);

        // --- Eventos ---
        btnGuardar.addActionListener(e -> guardarEnvio());
        btnAgregarIcon.addActionListener(e -> guardarEnvio());
        btnRetirarIcon.addActionListener(e -> retirarEnvioSeleccionado());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    private void guardarEnvio() {
        try {
            String codigo = txtNumero.getText().trim();
            String cliente = txtCliente.getText().trim();
            String tipo = (String) cbTipo.getSelectedItem();

            if (codigo.isEmpty() || cliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double peso = Double.parseDouble(txtPeso.getText().trim());
            double distancia = Double.parseDouble(txtDistancia.getText().trim());

            boolean exito = controller.registrarEnvio(tipo, codigo, cliente, peso, distancia);

            if (exito) {
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Envío registrado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error: El código ya existe o el tipo es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso y Distancia deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retirarEnvioSeleccionado() {
        int filaSeleccionada = tablaEnvios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String codigo = (String) modelTabla.getValueAt(filaSeleccionada, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Desea retirar el envío con código " + codigo + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarEnvio(codigo);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un envío de la tabla para retirarlo.", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modelTabla.setRowCount(0);
        for (Envio e : controller.listarEnvios()) {
            Object[] fila = {
                e.getTipo(),
                e.getCodigo(),
                e.getCliente(),
                e.getPeso(),
                e.getDistancia(),
                e.calcularTarifa()
            };
            modelTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNumero.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        cbTipo.setSelectedIndex(0);
    }
}
