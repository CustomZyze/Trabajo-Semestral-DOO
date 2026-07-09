package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Panel que permite crear un nuevo torneo.
 *
 * Solicita al usuario el nombre del torneo, la disciplina, el formato
 * (Eliminatoria Simple, Eliminatoria Doble o Liga Simple) y la fecha de
 * inicio. Al confirmar la creación, se instancia un nuevo Torneo con el
 * Formato correspondiente, se asocia a la ventana principal, y se registran
 * los demás paneles del sistema (Inscritos, Llaves, Resultados, Clasificación
 * y Gestión de Equipos) para que queden disponibles durante el torneo.
 *
 * Si ocurre un error durante la creación (por ejemplo, datos inválidos),
 * se captura la excepción y se muestra el mensaje correspondiente en pantalla.
 *
 */
public class PanelCrearTorneo extends JPanel {

    /** Campo de texto donde se ingresa el nombre del torneo. */
    private JTextField txtNombre;

    /** Combo con las disciplinas disponibles, definidas en el enum Disciplina. */
    private JComboBox<Disciplina> cbDisciplina;

    /** Combo con los formatos de torneo disponibles (Eliminatoria Simple, Eliminatoria Doble, Liga Simple). */
    private JComboBox<String> cbFormato;

    /** Etiqueta usada para mostrar mensajes de éxito o error al usuario. */
    private JLabel lblMensaje;

    /** Selector de fecha usado para definir la fecha de inicio del torneo. */
    private JSpinner spFechaInicio;

    /**
     * Construye el panel de creación de torneo, inicializando el formulario
     * (nombre, disciplina, formato, fecha de inicio) y los botones de
     * navegación (Volver, Crear).
     *
     * Al presionar "Crear", se valida y construye el objeto Torneo, se
     * registra en la ventana principal, se agregan todos los paneles
     * dependientes del torneo, y se navega automáticamente al panel de
     * Inscritos.
     *
     * @param ventana referencia a la ventana principal de la aplicación,
     *                usada para registrar el torneo creado y navegar entre paneles
     */
    public PanelCrearTorneo(Ventana ventana) {
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Crear Nuevo Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        titulo.setForeground(new Color(120, 200, 255));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
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

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(etiqueta("Fecha inicio:"), gbc);

        SpinnerDateModel modeloFecha = new SpinnerDateModel(
                new Date(),
                null,
                null,
                Calendar.DAY_OF_MONTH
        );

        spFechaInicio = new JSpinner(modeloFecha);
        spFechaInicio.setEditor(new JSpinner.DateEditor(spFechaInicio, "dd/MM/yyyy"));

        gbc.gridx = 1;
        add(spFechaInicio, gbc);

        lblMensaje = new JLabel("");
        lblMensaje.setForeground(new Color(100, 220, 100));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(lblMensaje, gbc);

        RoundedButton btnVolver = new RoundedButton("Volver", 20);
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.setBackground(new Color(100, 100, 130));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("MENU"));
        gbc.gridy = 5; gbc.gridwidth = 1; gbc.gridx = 0;
        add(btnVolver, gbc);

        RoundedButton btnCrear = new RoundedButton("Crear", 20);
        btnCrear.setPreferredSize(new Dimension(150, 35));
        btnCrear.setBackground(new Color(70, 130, 180));
        btnCrear.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();

                Disciplina disc = (Disciplina) cbDisciplina.getSelectedItem();
                Formato fmt = switch (cbFormato.getSelectedIndex()) {
                    case 1  -> new ElimDoble();
                    case 2  -> new LigaSimple();
                    default -> new ElimDirecta();
                };
                Date fechaSeleccionada = (Date) spFechaInicio.getValue();

                LocalDate fechaInicio = fechaSeleccionada
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                Torneo torneo = new Torneo(nombre, disc, fmt);
                torneo.setFechaInicio(fechaInicio);

                ventana.setTorneo(torneo);

                ventana.agregarPanel(new PanelInscritos(ventana), "INSCRITOS");
                ventana.agregarPanel(new PanelLlaves(ventana), "LLAVES");
                ventana.agregarPanel(new PanelResultados(ventana), "RESULTADOS");
                ventana.agregarPanel(new PanelClasificacion(ventana), "CLASIFICACION");
                ventana.agregarPanel(new PanelGestionEquipos(ventana), "GESTION_EQUIPOS");

                lblMensaje.setText("Torneo '" + nombre + "' creado.");
                lblMensaje.setForeground(new Color(100, 220, 100));
                ventana.mostrarPanel("INSCRITOS");

            } catch (RuntimeException ex) {
                lblMensaje.setText(ex.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        });
        gbc.gridx = 1;
        add(btnCrear, gbc);
    }

    /**
     * Crea una etiqueta de texto con el estilo visual estándar usado
     * en los formularios del panel.
     *
     * @param texto contenido de la etiqueta
     * @return un JLabel configurado con el estilo del formulario
     */
    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        return lbl;
    }
}