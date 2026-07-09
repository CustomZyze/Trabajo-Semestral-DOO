package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Panel que permite inscribir participantes (jugadores o equipos) en el
 * torneo activo.
 *
 * Muestra un formulario que se adapta según el tipo de participante
 * seleccionado: si es "Jugador", solicita nombre, contacto y RUT; si es
 * "Equipo", solicita nombre y contacto, asignando automáticamente el
 * límite máximo de integrantes según la disciplina del torneo. Los
 * participantes inscritos se van agregando a una lista visual.
 *
 * También permite generar las llaves del torneo una vez inscritos los
 * participantes necesarios (lo que además programa las fechas de las
 * partidas a partir de la fecha de inicio del torneo), navegar a la
 * gestión de equipos, o volver al menú principal.
 *
 */
public class PanelInscritos extends JPanel {

    /** Campo de texto donde se ingresa el nombre del participante a inscribir. */
    private JTextField txtNombre;

    /** Campo de texto donde se ingresa el contacto del participante a inscribir. */
    private JTextField txtContacto;

    /** Campo de texto donde se ingresa el RUT del participante, usado solo cuando el tipo es Jugador. */
    private JTextField txtRut;

    /** Etiqueta del campo RUT, cuya visibilidad depende del tipo de participante seleccionado. */
    private JLabel lblRut;

    /** Combo que permite elegir el tipo de participante a inscribir: Jugador o Equipo. */
    private JComboBox<String> cbTipo;

    /** Modelo de datos que respalda la lista visual de participantes inscritos. */
    private DefaultListModel<String> modeloLista;

    /** Etiqueta usada para mostrar mensajes de éxito o error al usuario. */
    private JLabel lblMensaje;

    /**
     * Construye el panel de inscripción, inicializando el formulario
     * dinámico (que cambia según el tipo de participante "Jugador" o "Equipo"), la lista de
     * inscritos, y los botones de navegación (Generar Llaves, Gestionar
     * Equipos, Volver).
     *
     * @param ventana referencia a la ventana principal de la aplicación,
     *                usada para acceder al torneo activo y cambiar de panel
     */
    public PanelInscritos(Ventana ventana) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 55));

        JLabel titulo = new JLabel("Inscribir Participantes", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
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

        gbc.gridx = 0; gbc.gridy = 3;
        lblRut = etiqueta("RUT:");
        form.add(lblRut, gbc);
        txtRut = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtRut, gbc);

        cbTipo.addItemListener(e -> {
            boolean esJugador = cbTipo.getSelectedIndex() == 0;
            lblRut.setVisible(esJugador);
            txtRut.setVisible(esJugador);
        });

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        form.add(lblMensaje, gbc);

        RoundedButton btnInscribir = new RoundedButton("Inscribir", 20);
        btnInscribir.setPreferredSize(new Dimension(150, 35));
        btnInscribir.setBackground(new Color(70, 130, 180));
        btnInscribir.addActionListener(e -> {
            try{
                String nombre = txtNombre.getText().trim();
                String contacto = txtContacto.getText().trim();
                String rut = txtRut.getText().trim();
                int limiteMax = ventana.getTorneo().getDisciplina().getMaxJugadores();

                Participante p = cbTipo.getSelectedIndex() == 0
                        ? new Jugador(nombre, contacto, rut)
                        : new Equipo(nombre, contacto, limiteMax);
                ventana.getTorneo().inscribir(p);

                modeloLista.addElement(cbTipo.getSelectedItem() + ": " + nombre);
                txtNombre.setText("");
                txtContacto.setText("");
                txtRut.setText("");
                lblMensaje.setText("Inscrito: " + nombre);
                lblMensaje.setForeground(new Color(100, 220, 100));
            }catch (RuntimeException ex){
                lblMensaje.setText(ex.getMessage());
                lblMensaje.setForeground(Color.RED);
            }

        });
        gbc.gridy = 5;
        form.add(btnInscribir, gbc);

        add(form, BorderLayout.WEST);

        modeloLista = new DefaultListModel<>();
        JList<String> lista = new JList<>(modeloLista);
        lista.setBackground(new Color(30, 30, 45));
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Inscritos",
                0, 0, null, Color.BLACK));
        add(scroll, BorderLayout.CENTER);

        JPanel botonesInf = new JPanel();
        botonesInf.setBackground(new Color(40, 40, 55));

        RoundedButton btnGenerar = new RoundedButton("Generar Llaves", 20);
        btnGenerar.setPreferredSize(new Dimension(170, 35));
        btnGenerar.setBackground(new Color(60, 160, 80));
        btnGenerar.addActionListener(e -> {
            try {
                ventana.getTorneo().generarLlaves();

                LocalDate fechaInicio = ventana.getTorneo().getFechaInicio();
                ventana.getTorneo().programarFechas(fechaInicio);

                ventana.mostrarPanel("LLAVES");

            } catch (RuntimeException ex) {
                lblMensaje.setText(ex.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        });
        botonesInf.add(btnGenerar);

        RoundedButton btnGestionar = new RoundedButton("Gestionar Equipos", 20);
        btnGestionar.setPreferredSize(new Dimension(200, 35));
        btnGestionar.setBackground(new Color(180, 100, 50));
        btnGestionar.addActionListener(e -> ventana.mostrarPanel("GESTION_EQUIPOS"));
        botonesInf.add(btnGestionar);

        RoundedButton btnVolver = new RoundedButton("Volver", 20);
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.setBackground(new Color(100, 100, 130));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("MENU"));
        botonesInf.add(btnVolver);

        add(botonesInf, BorderLayout.SOUTH);
    }

    /**
     * Crea una etiqueta de texto con el estilo visual estándar usado
     * en el formulario del panel.
     *
     * @param texto contenido de la etiqueta
     * @return un JLabel configurado con el estilo del formulario
     */
    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        return lbl;
    }
}