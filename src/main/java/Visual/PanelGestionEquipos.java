package Visual;

import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;

public class PanelGestionEquipos extends JPanel {
    private JComboBox<String> cbEquipos;
    private JTextField txtNombreJugador;
    private JTextField txtContactoJugador;
    private JTextField txtRutJugador;
    private JLabel lblMensaje;
    private JLabel lblConteoJugadores;
    private Ventana ventana;

    public PanelGestionEquipos(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Añadir Jugadores a Equipo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(etiqueta("Seleccionar Equipo:"), gbc);
        cbEquipos = new JComboBox<>();
        gbc.gridx = 1;
        add(cbEquipos, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(etiqueta("Nombre Jugador:"), gbc);
        txtNombreJugador = new JTextField(15);
        gbc.gridx = 1;
        add(txtNombreJugador, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(etiqueta("RUT:"), gbc);
        txtRutJugador = new JTextField(15);
        gbc.gridx = 1;
        add(txtRutJugador, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(etiqueta("Contacto:"), gbc);
        txtContactoJugador = new JTextField(15);
        gbc.gridx = 1;
        add(txtContactoJugador, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(lblMensaje, gbc);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setBackground(new Color(40, 40, 55));

        JButton btnActualizarListado = new JButton("Cargar Equipos");
        btnActualizarListado.setFocusPainted(false);
        btnActualizarListado.addActionListener(e -> cargarEquipos());
        panelBotones.add(btnActualizarListado);

        JButton btnAñadir = new JButton("Añadir Jugador");
        btnAñadir.setBackground(new Color(70, 130, 180));
        btnAñadir.setForeground(Color.WHITE);
        btnAñadir.setFocusPainted(false);
        btnAñadir.addActionListener(e -> agregarJugadorAlEquipo());
        panelBotones.add(btnAñadir);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> ventana.mostrarPanel("INSCRITOS"));
        panelBotones.add(btnVolver);

        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    private void cargarEquipos() {
        cbEquipos.removeAllItems();
        if (ventana.getTorneo() == null) return;

        for (Participante p : ventana.getTorneo().getInscritos()) {
            if (p instanceof Equipo) {
                cbEquipos.addItem(p.getNombre());
            }
        }
        if (cbEquipos.getItemCount() == 0) {
            lblMensaje.setText("No hay equipos inscritos.");
            lblMensaje.setForeground(Color.RED);
        } else {
            lblMensaje.setText("Equipos cargados.");
            lblMensaje.setForeground(Color.WHITE);
        }
    }

    private void agregarJugadorAlEquipo() {
        int idx = cbEquipos.getSelectedIndex();
        if (idx < 0) {
            lblMensaje.setText("Selecciona un equipo primero.");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        String nombre = txtNombreJugador.getText().trim();
        String rut = txtRutJugador.getText().trim();
        String contacto = txtContactoJugador.getText().trim();

        if (nombre.isEmpty() || rut.isEmpty()) {
            lblMensaje.setText("El nombre y RUT son obligatorios.");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        String nombreEquipoSeleccionado = (String) cbEquipos.getSelectedItem();
        Equipo equipoDestino = null;

        for (Participante p : ventana.getTorneo().getInscritos()) {
            if (p instanceof Equipo && p.getNombre().equals(nombreEquipoSeleccionado)) {
                equipoDestino = (Equipo) p;
                break;
            }
        }

        if (equipoDestino != null) {
            Jugador nuevoJugador = new Jugador(nombre, contacto, rut);
            equipoDestino.agregarJugador(nuevoJugador);

            lblMensaje.setText("Jugador " + nombre + " añadido a " + equipoDestino.getNombre());
            lblMensaje.setForeground(new Color(100, 220, 100));

            txtNombreJugador.setText("");
            txtRutJugador.setText("");
            txtContactoJugador.setText("");
        }
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }
}