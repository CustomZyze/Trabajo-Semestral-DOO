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
}