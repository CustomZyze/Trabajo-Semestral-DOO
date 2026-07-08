package Visual;

import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelGestionEquipos extends JPanel {
    private JComboBox<String> cbEquipos;
    private JTextField txtNombreJugador;
    private JTextField txtContactoJugador;
    private JTextField txtRutJugador;
    private JLabel lblMensaje;
    private DefaultListModel<String> modeloRoster;
    private JList<String> listaRoster;
    private JScrollPane scrollRoster;
    private Ventana ventana;

    public PanelGestionEquipos(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 55));

        JLabel titulo = new JLabel("Añadir Jugadores a Equipo", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(etiqueta("Seleccionar Equipo:"), gbc);
        cbEquipos = new JComboBox<>();
        cbEquipos.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                actualizarRoster();
            }
        });
        gbc.gridx = 1;
        form.add(cbEquipos, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(etiqueta("Nombre Jugador:"), gbc);
        txtNombreJugador = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtNombreJugador, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(etiqueta("RUT:"), gbc);
        txtRutJugador = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtRutJugador, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(etiqueta("Contacto:"), gbc);
        txtContactoJugador = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtContactoJugador, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        form.add(lblMensaje, gbc);

        RoundedButton btnActualizarListado = new RoundedButton("Cargar Equipos", 20);
        btnActualizarListado.setPreferredSize(new Dimension(180, 35));
        btnActualizarListado.setBackground(new Color(100, 100, 130));
        btnActualizarListado.addActionListener(e -> cargarEquipos());
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        form.add(btnActualizarListado, gbc);

        RoundedButton btnAñadir = new RoundedButton("Añadir Jugador", 20);
        btnAñadir.setPreferredSize(new Dimension(180, 35));
        btnAñadir.setBackground(new Color(70, 130, 180));
        btnAñadir.addActionListener(e -> agregarJugadorAlEquipo());
        gbc.gridx = 1;
        form.add(btnAñadir, gbc);

        add(form, BorderLayout.WEST);

        JPanel panelRoster = new JPanel(new BorderLayout());
        panelRoster.setBackground(new Color(40, 40, 55));

        modeloRoster = new DefaultListModel<>();
        listaRoster = new JList<>(modeloRoster);
        listaRoster.setBackground(new Color(30, 30, 45));
        listaRoster.setForeground(Color.WHITE);
        listaRoster.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        scrollRoster = new JScrollPane(listaRoster);
        actualizarTituloRoster("Jugadores del equipo");
        panelRoster.add(scrollRoster, BorderLayout.CENTER);

        add(panelRoster, BorderLayout.CENTER);

        JPanel panelInf = new JPanel();
        panelInf.setBackground(new Color(40, 40, 55));

        RoundedButton btnVolver = new RoundedButton("Volver", 20);
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.setBackground(new Color(100, 100, 130));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("INSCRITOS"));
        panelInf.add(btnVolver);

        add(panelInf, BorderLayout.SOUTH);
    }

    private void actualizarTituloRoster(String texto) {
        scrollRoster.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), texto,
                0, 0, new Font(Font.MONOSPACED, Font.BOLD, 14), new Color(0, 0, 0)));
    }

    private void cargarEquipos() {
        cbEquipos.removeAllItems();
        modeloRoster.clear();
        actualizarTituloRoster("Jugadores del equipo");
        if (ventana.getTorneo() == null) return;

        for (Participante p : ventana.getTorneo().getInscritos()) {
            if (p.aceptaIntegrantes()) {
                cbEquipos.addItem(p.getNombre());
            }
        }

        if (cbEquipos.getItemCount() == 0) {
            lblMensaje.setText("No hay equipos inscritos.");
            lblMensaje.setForeground(Color.RED);
        } else {
            lblMensaje.setText("Equipos cargados.");
            lblMensaje.setForeground(Color.WHITE);
            cbEquipos.setSelectedIndex(0);
            actualizarRoster();
        }
    }

    private Equipo obtenerEquipoSeleccionado() {
        String nombreSeleccionado = (String) cbEquipos.getSelectedItem();
        if (nombreSeleccionado == null || ventana.getTorneo() == null) return null;

        for (Participante p : ventana.getTorneo().getInscritos()) {
            if (p.aceptaIntegrantes() && p.getNombre().equals(nombreSeleccionado) && p instanceof Equipo) {
                return (Equipo) p;
            }
        }
        return null;
    }

    private void actualizarRoster() {
        modeloRoster.clear();
        Equipo equipo = obtenerEquipoSeleccionado();

        if (equipo == null) {
            actualizarTituloRoster("Jugadores del equipo");
            return;
        }

        List<Jugador> jugadores = equipo.getJugadores();
        int maximo = ventana.getTorneo().getDisciplina().getMaxJugadores();
        actualizarTituloRoster(equipo.getNombre() + "  (" + jugadores.size() + "/" + maximo + ")");

        if (jugadores.isEmpty()) {
            modeloRoster.addElement("(Sin jugadores aún)");
        } else {
            for (Jugador j : jugadores) {
                modeloRoster.addElement(j.getNombre());
            }
        }
    }

    private void agregarJugadorAlEquipo() {
        Equipo equipoDestino = obtenerEquipoSeleccionado();
        if (equipoDestino == null) {
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

        try {
            Jugador nuevoJugador = new Jugador(nombre, contacto, rut);
            equipoDestino.agregarIntegrante(nuevoJugador);

            lblMensaje.setText("Jugador " + nombre + " añadido a " + equipoDestino.getNombre());
            lblMensaje.setForeground(new Color(100, 220, 100));

            txtNombreJugador.setText("");
            txtRutJugador.setText("");
            txtContactoJugador.setText("");

            actualizarRoster();
        } catch (IllegalArgumentException exception) {
            lblMensaje.setText(exception.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        return lbl;
    }
}