package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;

public class PanelInscritos extends JPanel {
    private JTextField txtNombre;
    private JTextField txtContacto;
    private JComboBox<String> cbTipo;
    private DefaultListModel<String> modeloLista;
    private JLabel lblMensaje;

    public PanelInscritos(Ventana ventana) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 55));
        
        JLabel titulo = new JLabel("Inscribir Participantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(etiqueta("Tipo:"), gbc);
        cbTipo = new JComboBox<>(new String[]{"Jugador", "Equipo"});
        gbc.gridx = 1;
        form.add(cbTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(etiqueta("Nombre:"), gbc);
        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(etiqueta("Contacto:"), gbc);
        txtContacto = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtContacto, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(lblMensaje, gbc);

        JButton btnInscribir = new JButton("Inscribir");
        btnInscribir.setBackground(new Color(70, 130, 180));
        btnInscribir.setForeground(Color.WHITE);
        btnInscribir.setFocusPainted(false);
        btnInscribir.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String contacto = txtContacto.getText().trim();
            if (nombre.isEmpty()) { lblMensaje.setText("Ingresa un nombre."); return; }

            Participante p = cbTipo.getSelectedIndex() == 0
                    ? new Jugador(nombre, contacto, "")
                    : new Equipo(nombre, contacto);

            ventana.getTorneo().inscribir(p);
            modeloLista.addElement(cbTipo.getSelectedItem() + ": " + nombre);
            txtNombre.setText("");
            txtContacto.setText("");
            lblMensaje.setText("Inscrito: " + nombre);
        });
        gbc.gridy = 4;
        form.add(btnInscribir, gbc);

        add(form, BorderLayout.WEST);
        
        modeloLista = new DefaultListModel<>();
        JList<String> lista = new JList<>(modeloLista);
        lista.setBackground(new Color(30, 30, 45));
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Inscritos",
                0, 0, null, Color.WHITE));
        add(scroll, BorderLayout.CENTER);
        
        JPanel botonesInf = new JPanel();
        botonesInf.setBackground(new Color(40, 40, 55));

        JButton btnGenerar = new JButton("Generar Llaves");
        btnGenerar.setBackground(new Color(60, 160, 80));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> {
            if (ventana.getTorneo().getInscritos().size() < 2) {
                lblMensaje.setText("Mínimo 2 participantes.");
                return;
            }
            for (Participante p : ventana.getTorneo().getInscritos()) {
                if (!p.estaListoParaJugar()) {
                    lblMensaje.setText("Error: El participante '" + p.getNombre() + "' no está listo.");
                    lblMensaje.setForeground(Color.RED);
                    return;
                }
            }
            ventana.getTorneo().generarLlaves();
            ventana.mostrarPanel("LLAVES");
        });
        botonesInf.add(btnGenerar);

        JButton btnGestionar = new JButton("Gestionar Equipos");
        btnGestionar.setBackground(new Color(180, 100, 50));
        btnGestionar.setForeground(Color.WHITE);
        btnGestionar.setFocusPainted(false);
        btnGestionar.addActionListener(e -> ventana.mostrarPanel("GESTION_EQUIPOS"));
        botonesInf.add(btnGestionar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> ventana.mostrarPanel("MENU"));
        botonesInf.add(btnVolver);

        add(botonesInf, BorderLayout.SOUTH);
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }
}