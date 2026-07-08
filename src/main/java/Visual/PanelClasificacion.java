package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelClasificacion extends JPanel {
    private JPanel panelTabla;
    private Ventana ventana;

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

    private void mostrarCampeonEliminatoria() {
        mostrarMensaje("La clasificación no está disponible para este formato.");
    }

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

    private JPanel crearFila(int indice, RegistroLiga registro) {
        JPanel fila = new JPanel(new GridLayout(1, 7));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        fila.setBackground(indice % 2 == 0
                ? new Color(35, 35, 50)
                : new Color(45, 45, 65));

        Color colorTexto = indice == 0
                ? new Color(255, 215, 0)
                : Color.WHITE;

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