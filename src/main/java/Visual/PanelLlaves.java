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
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        panelPartidas = new JPanel();
        panelPartidas.setLayout(new BoxLayout(panelPartidas, BoxLayout.Y_AXIS));
        panelPartidas.setBackground(new Color(30, 30, 45));

        JScrollPane scroll = new JScrollPane(panelPartidas);
        add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        botones.setBackground(new Color(40, 40, 55));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> actualizar());
        botones.add(btnActualizar);

        JButton btnResultados = new JButton("Registrar Resultados");
        btnResultados.setBackground(new Color(180, 100, 50));
        btnResultados.setForeground(Color.WHITE);
        btnResultados.setFocusPainted(false);
        btnResultados.addActionListener(e -> ventana.mostrarPanel("RESULTADOS"));
        botones.add(btnResultados);

        JButton btnClasif = new JButton("Ver Clasificación");
        btnClasif.setFocusPainted(false);
        btnClasif.addActionListener(e -> ventana.mostrarPanel("CLASIFICACION"));
        botones.add(btnClasif);

        JButton btnVolver = new JButton("Menú");
        btnVolver.setFocusPainted(false);
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
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            fila.setBackground(i % 2 == 0
                    ? new Color(35, 35, 50)
                    : new Color(45, 45, 65));

            String estado = switch (p.getEstado()) {
                case TERMINADA -> "";
                case EN_CURSO  -> "";
                case CANCELADA -> "";
                default        -> "";
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
            lbl.setForeground(p.getEstado() == EstadoPartida.TERMINADA
                    ? new Color(100, 220, 100)
                    : Color.WHITE);
            lbl.setFont(new Font("Arial", Font.PLAIN, 14));
            fila.add(lbl);
            panelPartidas.add(fila);
        }

        if (ventana.getTorneo().torneoFinalizado()) {
            Participante campeon = ventana.getTorneo().getCampeon();
            if (campeon != null) {
                JLabel lblCampeon = new JLabel("¡CAMPEÓN: " + campeon.getNombre().toUpperCase() + "!", SwingConstants.CENTER);
                lblCampeon.setFont(new Font("Arial", Font.BOLD, 26));
                lblCampeon.setForeground(new Color(255, 215, 0)); // Color dorado
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