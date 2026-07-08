package Visual;
import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public PanelMenu(Ventana ventana) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Sistema de Torneos");
        titulo.setFont(new Font("Consolas", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        RoundedButton btnCrear = crearBoton("Crear Torneo", new Color(70, 130, 180));
        btnCrear.addActionListener(e -> ventana.mostrarPanel("CREAR"));
        gbc.gridy = 1;
        add(btnCrear, gbc);

        RoundedButton btnVer = crearBoton("Ver Torneo Activo", new Color(60, 160, 80));
        btnVer.addActionListener(e -> ventana.mostrarPanel("LLAVES"));
        gbc.gridy = 2;
        add(btnVer, gbc);
    }

    private RoundedButton crearBoton(String texto, Color color) {
        RoundedButton btn = new RoundedButton(texto, 25);
        btn.setPreferredSize(new Dimension(250, 70));
        btn.setBackground(color);
        btn.setFont(new Font("Consolas", Font.BOLD, 19));
        return btn;
    }
}