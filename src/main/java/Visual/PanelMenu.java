package Visual;
import javax.swing.*;
import java.awt.*;

/**
 * Panel principal del sistema, mostrado al iniciar la aplicación.
 *
 * Presenta el título del sistema y dos opciones principales: crear un
 * nuevo torneo, o ver el torneo actualmente activo. Si no existe un
 * torneo activo al intentar verlo, se muestra un mensaje de error en
 * lugar de navegar al panel de llaves.
 *
 */
public class PanelMenu extends JPanel {

    /**
     * Construye el panel de menú, inicializando el título del sistema
     * y los botones de navegación (Crear Torneo, Ver Torneo Activo),
     * junto con una etiqueta de mensaje usada para avisar cuando no
     * hay torneo activo.
     *
     * @param ventana referencia a la ventana principal de la aplicación,
     *                usada para consultar el torneo activo y cambiar de panel
     */
    public PanelMenu(Ventana ventana) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Sistema de Torneos");
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        RoundedButton btnCrear = crearBoton("Crear Torneo", new Color(70, 130, 180));
        btnCrear.addActionListener(e -> ventana.mostrarPanel("CREAR"));
        gbc.gridy = 1;
        add(btnCrear, gbc);

        JLabel lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        RoundedButton btnVer = crearBoton("Ver Torneo Activo", new Color(60, 160, 80));
        btnVer.addActionListener(e -> {
            if (ventana.getTorneo() == null) {
                lblMensaje.setText("No hay torneo activo.");
            } else {
                lblMensaje.setText("");
                ventana.mostrarPanel("LLAVES");
            }
        });
        gbc.gridy = 2;
        add(btnVer, gbc);

        gbc.gridy = 3;
        add(lblMensaje, gbc);
    }

    /**
     * Crea un botón redondeado con el estilo visual estándar usado en
     * el menú principal.
     *
     * @param texto texto a mostrar en el botón
     * @param color color de fondo del botón
     * @return un RoundedButton configurado con el estilo del menú
     */
    private RoundedButton crearBoton(String texto, Color color) {
        RoundedButton btn = new RoundedButton(texto, 25);
        btn.setPreferredSize(new Dimension(250, 70));
        btn.setBackground(color);
        btn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
        return btn;
    }
}