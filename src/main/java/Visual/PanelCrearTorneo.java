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

        // título
        JLabel titulo = new JLabel("Crear Torneo");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titulo, gbc);

        // nombre
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(etiqueta("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        // disciplina
        gbc.gridx = 0; gbc.gridy = 2;
        add(etiqueta("Disciplina:"), gbc);
        cbDisciplina = new JComboBox<>(Disciplina.values());
        gbc.gridx = 1;
        add(cbDisciplina, gbc);

        // formato
        gbc.gridx = 0; gbc.gridy = 3;
        add(etiqueta("Formato:"), gbc);
        cbFormato = new JComboBox<>(new String[]{"Eliminatoria Simple", "Eliminatoria Doble", "Liga Simple"});
        gbc.gridx = 1;
        add(cbFormato, gbc);

        // mensaje
        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblMensaje, gbc);