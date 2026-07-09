package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra la tabla de clasificación de un torneo.
 *
 * Si el formato del torneo activo maneja clasificación (por ejemplo, LigaSimple),
 * se despliega una tabla con las posiciones, partidos jugados, ganados, empatados,
 * perdidos y puntos de cada participante, ordenada de mayor a menor puntaje.
 * Las primeras tres posiciones se destacan con colores diferentes.
 *
 * Si el formato no maneja clasificación (por ejemplo, eliminatoria simple o doble),
 * se muestra un mensaje en su lugar.
 *
 * Este panel se comunica con la ventana principal para volver a la vista de llaves
 * o al menú.
 *
 * @author
 * @see Ventana
 * @see Formato
 * @see RegistroLiga
 */
public class PanelClasificacion extends JPanel {

    /** Panel contenedor donde se renderizan el encabezado y las filas de la tabla, o el mensaje informativo. */
    private JPanel panelTabla;

    /** Referencia a la ventana principal, usada para consultar el torneo activo y navegar entre paneles. */
    private Ventana ventana;

    /**
     * Construye el panel de clasificación, inicializando el título, el área de la
     * tabla y los botones de navegación (Actualizar, Ver Llaves, Menú).
     *
     * @param ventana referencia a la ventana principal de la aplicación,
     *                usada para acceder al torneo activo y cambiar de panel
     */
    public PanelClasificacion(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 55));

        JLabel titulo = new JLabel("Tabla de Clasificación", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBackground(new Color(30, 30, 45));
        add(new JScrollPane(panelTabla), BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        botones.setBackground(new Color(40, 40, 55));

        RoundedButton btnActualizar = new RoundedButton("Actualizar", 20);
        btnActualizar.setPreferredSize(new Dimension(140, 35));
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.addActionListener(e -> actualizar());
        botones.add(btnActualizar);

        RoundedButton btnVolver = new RoundedButton("Ver Llaves", 20);
        btnVolver.setPreferredSize(new Dimension(140, 35));
        btnVolver.setBackground(new Color(84, 152, 72));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("LLAVES"));
        botones.add(btnVolver);

        RoundedButton btnMenu = new RoundedButton("Menú", 20);
        btnMenu.setPreferredSize(new Dimension(110, 35));
        btnMenu.setBackground(new Color(151, 99, 219));
        btnMenu.addActionListener(e -> ventana.mostrarPanel("MENU"));
        botones.add(btnMenu);

        add(botones, BorderLayout.SOUTH);
    }

    /**
     * Refresca el contenido de la tabla de clasificación.
     *
     * Limpia el panel de la tabla y vuelve a construirlo según el estado actual del torneo:
     * si no hay torneo activo, muestra un mensaje ; si el formato del torneo
     * tiene clasificación, muestra la tabla de posiciones; si el formato no tiene
     * clasificación, muestra un mensaje.
     *
     * Este metodo se invoca al presionar el botón "Actualizar".
     */
    public void actualizar() {
        panelTabla.removeAll();

        if (ventana.getTorneo() == null) {
            mostrarMensaje("No hay torneo activo.");
            return;
        }

        Formato fmt = ventana.getTorneo().getFormato();

        if (fmt.tieneClasificacion()) {
            mostrarTablaLiga(fmt.getTablaPosiciones());
        } else {
            mostrarCampeonEliminatoria();
        }

        panelTabla.revalidate();
        panelTabla.repaint();
    }

    /**
     * Construye y agrega al panel la tabla de posiciones de una liga,
     * ordenando los registros de mayor a menor puntaje.
     *
     * Si la lista de registros está vacía, se muestra un mensaje
     * en lugar de la tabla.
     *
     * @param registros lista de registros de liga a mostrar; se ordena
     *                   internamente por puntaje descendente
     */
    private void mostrarTablaLiga(List<RegistroLiga> registros) {
        if (registros.isEmpty()) {
            mostrarMensaje("No hay registros aún.");
            return;
        }

        registros.sort((a, b) -> b.getPuntos() - a.getPuntos());
        panelTabla.add(crearEncabezado());

        for (int i = 0; i < registros.size(); i++) {
            panelTabla.add(crearFila(i, registros.get(i)));
        }
    }

    /**
     * Muestra un mensaje indicando que el formato actual del torneo
     * no soporta tabla de clasificación.
     */
    private void mostrarCampeonEliminatoria() {
        mostrarMensaje("La clasificación no está disponible para este formato.");
    }

    /**
     * Crea la fila de encabezado de la tabla, con las columnas: posición (#),
     * Participante, Partidos Jugados (PJ), Ganados (G), Empatados (E),
     * Perdidos (P) y Puntos (Pts).
     *
     * @return un JPanel configurado como fila de encabezado
     */
    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new GridLayout(1, 7));
        header.setBackground(new Color(60, 60, 90));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        for (String col : new String[]{"#", "Participante", "PJ", "G", "E", "P", "Pts"}) {
            JLabel lbl = new JLabel(col, SwingConstants.CENTER);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
            header.add(lbl);
        }
        return header;
    }

    /**
     * Crea una fila de la tabla correspondiente a un registro de liga,
     * aplicando colores especiales de fondo y texto según la posición.
     *
     * @param indice   posición del registro en la tabla ordenada (0 = primer lugar)
     * @param registro registro de liga con las estadísticas del participante
     * @return un JPanel configurado como fila de la tabla, con los valores de
     *         posición, nombre, partidos jugados, ganados, empatados, perdidos y puntos
     */
    private JPanel crearFila(int indice, RegistroLiga registro) {
        JPanel fila = new JPanel(new GridLayout(1, 7));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        Color fondoFila = switch (indice) {
            case 0 -> new Color(50, 45, 20);
            case 1 -> new Color(94, 94, 112);
            case 2 -> new Color(45, 30, 20);
            default -> indice % 2 == 0
                    ? new Color(35, 35, 50)
                    : new Color(45, 45, 65);
        };
        fila.setBackground(fondoFila);

        Color colorTexto = switch (indice) {
            case 0 -> new Color(255, 215, 0);
            case 1 -> new Color(192, 192, 192);
            case 2 -> new Color(205, 127, 50);
            default -> Color.WHITE;
        };

        String[] valores = {
                String.valueOf(indice + 1),
                registro.getParticipante().getNombre(),
                String.valueOf(registro.getPartidosJugados()),
                String.valueOf(registro.getGanados()),
                String.valueOf(registro.getEmpatados()),
                String.valueOf(registro.getPerdidos()),
                String.valueOf(registro.getPuntos())
        };

        for (String val : valores) {
            JLabel lbl = new JLabel(val, SwingConstants.CENTER);
            lbl.setForeground(colorTexto);
            lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
            fila.add(lbl);
        }
        return fila;
    }

    /**
     * Agrega un mensaje de texto centrado al panel de la tabla, usado para
     * comunicar estados sin datos (sin torneo activo, sin registros, o
     * formato sin clasificación).
     *
     * @param texto mensaje a mostrar en el panel
     */
    private void mostrarMensaje(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(Color.GRAY);
        lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTabla.add(lbl);
        panelTabla.revalidate();
        panelTabla.repaint();
    }
}