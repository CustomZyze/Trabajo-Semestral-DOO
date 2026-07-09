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
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
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

        RoundedButton btnCargar = new RoundedButton("Cargar Partidas", 20);
        btnCargar.setPreferredSize(new Dimension(180, 35));
        btnCargar.setBackground(new Color(70, 130, 180));
        btnCargar.addActionListener(e -> cargarPartidas());
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(btnCargar, gbc);

        RoundedButton btnRegistrar = new RoundedButton("Registrar", 20);
        btnRegistrar.setPreferredSize(new Dimension(140, 35));
        btnRegistrar.setBackground(new Color(180, 100, 50));
        btnRegistrar.addActionListener(e -> registrar());
        gbc.gridx = 1;
        add(btnRegistrar, gbc);

        RoundedButton btnVolver = new RoundedButton("Ver Llaves", 20);
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.setBackground(new Color(84, 152, 72));
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

            Partida partidaActual = ventana.getTorneo().getPartidasPendientes().get(idx);

            ventana.getTorneo().registrarResultadosPartida(partidaActual, p1, p2);

            lblMensaje.setText("Resultado registrado.");
            lblMensaje.setForeground(new Color(100, 220, 100));
            txtPuntaje1.setText("");
            txtPuntaje2.setText("");
            cargarPartidas();
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Ingresa puntajes válidos.");
            lblMensaje.setForeground(Color.RED);
        } catch (RuntimeException ex){
            lblMensaje.setText( ex.getMessage());
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