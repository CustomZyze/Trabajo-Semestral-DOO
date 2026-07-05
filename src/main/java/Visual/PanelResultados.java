package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelResultados extends JPanel {
    private JComboBox<String> cbPartidas;
    private JTextField txtPuntaje1;
    private JTextField txtPuntaje2;
    private JLabel lblMensaje;
    private Ventana ventana;

    public PanelResultados(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Registrar Resultado");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(etiqueta("Partida:"), gbc);
        cbPartidas = new JComboBox<>();
        gbc.gridx = 1;
        add(cbPartidas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(etiqueta("Puntaje J1:"), gbc);
        txtPuntaje1 = new JTextField(5);
        gbc.gridx = 1;
        add(txtPuntaje1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(etiqueta("Puntaje J2:"), gbc);
        txtPuntaje2 = new JTextField(5);
        gbc.gridx = 1;
        add(txtPuntaje2, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblMensaje, gbc);

        JButton btnCargar = new JButton("Cargar Partidas");
        btnCargar.setBackground(new Color(70, 130, 180));
        btnCargar.setForeground(Color.WHITE);
        btnCargar.setFocusPainted(false);
        btnCargar.addActionListener(e -> cargarPartidas());
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(btnCargar, gbc);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(60, 160, 80));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrar());
        gbc.gridx = 1;
        add(btnRegistrar, gbc);

        JButton btnVolver = new JButton("Ver Llaves");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> ventana.mostrarPanel("LLAVES"));
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnVolver, gbc);
    }

    private void cargarPartidas() {
        cbPartidas.removeAllItems();
        if (ventana.getTorneo() == null) return;
        for (Partida p : ventana.getTorneo().getPartidasPendientes()) {
            cbPartidas.addItem(p.getP1().getNombre() + " vs " + p.getP2().getNombre());
        }
    }

    private void registrar() {
        int idx = cbPartidas.getSelectedIndex();
        if (idx < 0) { lblMensaje.setText("Selecciona una partida."); return; }

        try {
            int p1 = Integer.parseInt(txtPuntaje1.getText().trim());
            int p2 = Integer.parseInt(txtPuntaje2.getText().trim());

            // solo llama al modelo, sin lógica de filtrado aquí
            ventana.getTorneo().getPartidasPendientes().get(idx).registrarResultado(p1, p2);

            lblMensaje.setText("Resultado registrado.");
            txtPuntaje1.setText("");
            txtPuntaje2.setText("");
            cargarPartidas();
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Ingresa puntajes válidos.");
            lblMensaje.setForeground(Color.RED);
        }
    }
}