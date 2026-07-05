package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;

public class PanelCrearTorneo extends JPanel {
    private JTextField txtNombre;
    private JComboBox<Disciplina> cbDisciplina;
    private JComboBox<String> cbFormato;
    private JLabel lblMensaje;

    public PanelCrearTorneo(Ventana ventana) {
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Crear Torneo");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(etiqueta("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(etiqueta("Disciplina:"), gbc);
        cbDisciplina = new JComboBox<>(Disciplina.values());
        gbc.gridx = 1;
        add(cbDisciplina, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(etiqueta("Formato:"), gbc);
        cbFormato = new JComboBox<>(new String[]{"Eliminatoria Simple", "Eliminatoria Doble", "Liga Simple"});
        gbc.gridx = 1;
        add(cbFormato, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblMensaje, gbc);

        JButton btnCrear = new JButton("Crear");
        btnCrear.setBackground(new Color(70, 130, 180));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                lblMensaje.setText("Ingresa un nombre.");
                lblMensaje.setForeground(Color.RED);
                return;
            }
            Disciplina disc = (Disciplina) cbDisciplina.getSelectedItem();
            Formato fmt = switch (cbFormato.getSelectedIndex()) {
                case 1  -> new ElimDoble();
                case 2  -> new LigaSimple();
                default -> new ElimDirecta();
            };
            Torneo torneo = new Torneo(nombre, disc, fmt);
            ventana.setTorneo(torneo);

            ventana.agregarPanel(new PanelInscritos(ventana), "INSCRITOS");
            ventana.agregarPanel(new PanelLlaves(ventana), "LLAVES");
            ventana.agregarPanel(new PanelResultados(ventana), "RESULTADOS");
            ventana.agregarPanel(new PanelClasificacion(ventana), "CLASIFICACION");

            lblMensaje.setText("Torneo '" + nombre + "' creado.");
            lblMensaje.setForeground(new Color(100, 220, 100));
            ventana.mostrarPanel("INSCRITOS");
        });
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        add(btnCrear, gbc);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> ventana.mostrarPanel("MENU"));
        gbc.gridx = 1;
        add(btnVolver, gbc);
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }
}