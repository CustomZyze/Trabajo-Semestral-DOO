package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que permite administrar la nómina de jugadores de los equipos
 * inscritos en un torneo.
 *
 * Muestra un formulario para seleccionar un equipo ya inscrito y agregar
 * nuevos jugadores a su roster, respetando el límite máximo de integrantes.
 * Al costado se despliega la lista
 * de jugadores actuales del equipo seleccionado, junto con un título que
 * indica el nombre del equipo y su cantidad de integrantes respecto al máximo.
 *
 * El roster se actualiza automáticamente cada vez que se cambia el equipo
 * seleccionado en el combo, o cuando se agrega un nuevo jugador exitosamente.
 *
 */
public class PanelGestionEquipos extends JPanel {

    /** Combo con los nombres de los equipos inscritos en el torneo actual. */
    private JComboBox<String> cbEquipos;

    /** Campo de texto para ingresar el nombre del nuevo jugador a añadir. */
    private JTextField txtNombreJugador;

    /** Campo de texto para ingresar el contacto del nuevo jugador a añadir. */
    private JTextField txtContactoJugador;

    /** Campo de texto para ingresar el RUT del nuevo jugador a añadir. */
    private JTextField txtRutJugador;

    /** Etiqueta usada para mostrar mensajes de éxito o error al usuario. */
    private JLabel lblMensaje;

    /** Modelo de datos que respalda la lista visual de jugadores del equipo seleccionado. */
    private DefaultListModel<String> modeloRoster;

    /** Lista visual que muestra los nombres de los jugadores del equipo seleccionado. */
    private JList<String> listaRoster;

    /** Contenedor con scroll de la lista de jugadores, cuyo título se actualiza dinámicamente. */
    private JScrollPane scrollRoster;

    /** Referencia a la ventana principal, usada para consultar el torneo activo y navegar entre paneles. */
    private Ventana ventana;

    /**
     * Construye el panel de gestión de equipos, inicializando el formulario
     * de selección de equipo y registro de jugadores, el panel del roster
     * con su título dinámico, y el botón para volver al
     * panel de Inscritos.
     *
     * @param ventana referencia a la ventana principal de la aplicación,
     *                usada para acceder al torneo activo y cambiar de panel
     */
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

    /**
     * Actualiza el texto del borde titulado del panel de roster,
     * usado para mostrar el nombre del equipo y su conteo de
     * integrantes, o un texto genérico cuando no hay equipo seleccionado.
     *
     * @param texto nuevo texto a mostrar como título del borde
     */
    private void actualizarTituloRoster(String texto) {
        scrollRoster.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), texto,
                0, 0, new Font(Font.MONOSPACED, Font.BOLD, 14), new Color(0, 0, 0)));
    }

    /**
     * Carga la lista de equipos inscritos en el torneo actual.
     *
     * Limpia el combo y el roster antes de recargar. Si no hay torneo
     * activo, no realiza ninguna carga. Si se encuentran equipos, se
     * selecciona automáticamente el primero y se actualiza su roster;
     * si no hay equipos inscritos, se muestra un mensaje de error.
     */
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

    /**
     * Busca y retorna el objeto Equipo correspondiente al nombre
     * actualmente seleccionado en el combo de equipos.
     *
     * @return el Equipo seleccionado, o null si no hay selección,
     *         no hay torneo activo, o el participante encontrado
     *         no es una instancia de Equipo
     */
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

    /**
     * Refresca la lista visual de jugadores del equipo actualmente
     * seleccionado, junto con el título que muestra su nombre y
     * conteo de integrantes respecto al máximo permitido por la disciplina.
     *
     * Si no hay equipo seleccionado, se restablece el título genérico.
     * Si el equipo no tiene jugadores, se muestra un mensaje indicándolo
     */
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

    /**
     * Crea un nuevo jugador con los datos ingresados en el formulario
     * y lo agrega al equipo actualmente seleccionado.
     *
     * Si no hay equipo seleccionado, se muestra un mensaje de error y
     * no se realiza ninguna acción. Si la creación del jugador o su
     * incorporación al equipo lanza una excepción (por ejemplo, datos
     * inválidos, equipo lleno o jugador duplicado), se captura y se
     * muestra el mensaje de error correspondiente. Si la operación es
     * exitosa, se limpian los campos del formulario y se refresca el roster.
     */
    private void agregarJugadorAlEquipo() {
        Equipo equipoDestino = obtenerEquipoSeleccionado();
        if (equipoDestino == null) {
            lblMensaje.setText("Selecciona un equipo primero.");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        try{
            String nombre = txtNombreJugador.getText().trim();
            String rut = txtRutJugador.getText().trim();
            String contacto = txtContactoJugador.getText().trim();

            Jugador nuevoJugador = new Jugador(nombre, contacto, rut);
            equipoDestino.agregarIntegrante(nuevoJugador);

            lblMensaje.setText("Jugador " + nombre + " añadido a " + equipoDestino.getNombre());
            lblMensaje.setForeground(new Color(100, 220, 100));

            txtNombreJugador.setText("");
            txtRutJugador.setText("");
            txtContactoJugador.setText("");

            actualizarRoster();
        }catch (RuntimeException ex){
            lblMensaje.setText(ex.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }

    /**
     * Crea una etiqueta de texto con el estilo visual estándar usado
     * en el formulario del panel (color blanco, fuente monoespaciada, negrita).
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