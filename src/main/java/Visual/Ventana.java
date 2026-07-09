package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación y punto de entrada del sistema.
 *
 * Actúa como contenedor central de todos los paneles de la interfaz,
 * usando un CardLayout para alternar entre ellos según el flujo de
 * navegación del usuario.
 *
 * También mantiene la referencia al torneo actualmente activo, la cual
 * es consultada y modificada por los distintos paneles a medida que se
 * crea, inscribe participantes, genera llaves y registra resultados.
 *
 */
public class Ventana extends JFrame {

    /** Panel contenedor que aloja todos los paneles de la aplicación bajo el mismo CardLayout. */
    private JPanel contenedor;

    /** Layout que permite alternar entre los distintos paneles registrados en el contenedor. */
    private CardLayout cardLayout;

    /** Torneo actualmente activo en la aplicación, o null si aún no se ha creado ninguno. */
    private Torneo torneo;

    /**
     * Construye la ventana principal, configurando el título, tamaño y
     * comportamiento de cierre. Inicializa el contenedor con CardLayout
     * y registra los paneles iniciales (Menú y Crear Torneo). Los demás
     * paneles del sistema se agregan dinámicamente una vez creado un
     * torneo, mediante agregarPanel.
     *
     */
    public Ventana() {
        setTitle("Sistema de Torneos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.add(new PanelMenu(this), "MENU");
        contenedor.add(new PanelCrearTorneo(this), "CREAR");

        add(contenedor, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        cardLayout.show(contenedor, "MENU");
    }

    /**
     * Muestra el panel identificado con el nombre indicado, ocultando
     * el panel actualmente visible.
     *
     * @param nombre identificador del panel a mostrar, tal como fue
     *               registrado previamente mediante agregarPanel o en
     *               el constructor de la ventana
     */
    public void mostrarPanel(String nombre) {
        cardLayout.show(contenedor, nombre);
    }

    /**
     * Registra un nuevo panel dentro del contenedor de la ventana,
     * asociándolo a un nombre identificador para su posterior
     * visualización mediante mostrarPanel.
     *
     * @param panel  panel a agregar al contenedor
     * @param nombre identificador con el que se registrará el panel
     */
    public void agregarPanel(JPanel panel, String nombre) {
        contenedor.add(panel, nombre);
    }

    /**
     * Retorna el torneo actualmente activo en la aplicación.
     *
     * @return el torneo activo, o null si aún no se ha creado ninguno
     */
    public Torneo getTorneo() { return torneo; }

    /**
     * Establece el torneo activo de la aplicación.
     *
     * @param torneo torneo a establecer como activo
     */
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }

    /**
     * Punto de entrada de la aplicación. Crea e inicializa la ventana
     * principal del sistema.
     *
     * @param args argumentos de línea de comandos, no utilizados
     */
    public static void main(String[] args) {
        new Ventana();
    }
}