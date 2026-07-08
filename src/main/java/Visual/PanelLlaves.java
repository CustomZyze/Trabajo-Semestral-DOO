package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelLlaves extends JPanel {
    private JPanel panelPartidas;
    private Ventana ventana;

    public PanelLlaves(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 40, 55));

        JLabel titulo = new JLabel("Llaves del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        panelPartidas = new JPanel();
        panelPartidas.setLayout(new BoxLayout(panelPartidas, BoxLayout.Y_AXIS));
        panelPartidas.setBackground(new Color(30, 30, 45));

        JScrollPane scroll = new JScrollPane(panelPartidas);
        add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        botones.setBackground(new Color(40, 40, 55));

        RoundedButton btnActualizar = new RoundedButton("Actualizar", 20);
        btnActualizar.setPreferredSize(new Dimension(130, 40));
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.addActionListener(e -> actualizar());
        botones.add(btnActualizar);

        RoundedButton btnResultados = new RoundedButton("Registrar Resultados", 20);
        btnResultados.setPreferredSize(new Dimension(190, 40));
        btnResultados.setBackground(new Color(180, 100, 50));
        btnResultados.addActionListener(e -> ventana.mostrarPanel("RESULTADOS"));
        botones.add(btnResultados);

        RoundedButton btnClasif = new RoundedButton("Ver Clasificación", 20);
        btnClasif.setPreferredSize(new Dimension(170, 40));
        btnClasif.setBackground(new Color(84, 152, 72));
        btnClasif.addActionListener(e -> ventana.mostrarPanel("CLASIFICACION"));
        botones.add(btnClasif);

        RoundedButton btnVolver = new RoundedButton("Menú", 20);
        btnVolver.setPreferredSize(new Dimension(110, 40));
        btnVolver.setBackground(new Color(119, 87, 230));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("MENU"));
        botones.add(btnVolver);

        add(botones, BorderLayout.SOUTH);
    }

    public void actualizar() {
        panelPartidas.removeAll();
        if (ventana.getTorneo() == null) return;

        List<Partida> llaves = ventana.getTorneo().getLlaves();
        for (int i = 0; i < llaves.size(); i++) {
            Partida p = llaves.get(i);
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            fila.setBackground(p.getEstado().getColorAsociado());
            String estado = switch (p.getEstado()) {
                case TERMINADA -> "✓";
                case EN_CURSO  -> "▶";
                case CANCELADA -> "✕";
                default        -> "•";
            };

            String texto = String.format("  %s  %s  vs  %s  |  %d - %d  |  %s",
                    estado,
                    p.getP1().getNombre(),
                    p.getP2().getNombre(),
                    p.getPuntaje1(),
                    p.getPuntaje2(),
                    p.getEstado()
            );

            JLabel lbl = new JLabel(texto);


            lbl.setForeground(Color.BLACK);
            lbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

            fila.add(lbl);
            panelPartidas.add(fila);
        }

        if (ventana.getTorneo().torneoFinalizado()) {
            Participante campeon = ventana.getTorneo().getCampeon();
            if (campeon != null) {
                JLabel lblCampeon = new JLabel("¡CAMPEÓN: " + campeon.getNombre().toUpperCase() + "!", SwingConstants.CENTER);
                lblCampeon.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
                lblCampeon.setForeground(new Color(255, 215, 0));
                lblCampeon.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

                JPanel panelCampeon = new JPanel(new BorderLayout());
                panelCampeon.setBackground(new Color(30, 30, 45));
                panelCampeon.add(lblCampeon, BorderLayout.CENTER);
                panelPartidas.add(panelCampeon);
            }
        }
        panelPartidas.revalidate();
        panelPartidas.repaint();
    }
}