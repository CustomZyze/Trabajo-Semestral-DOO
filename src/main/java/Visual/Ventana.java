package Visual;
import LogicaTorneo.*;
import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    private JPanel contenedor;
    private CardLayout cardLayout;
    private Torneo torneo;

    public Ventana() {
        setTitle("Sistema de Torneos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // agregar paneles
        contenedor.add(new PanelMenu(this), "MENU");
        contenedor.add(new PanelCrearTorneo(this), "CREAR");

        add(contenedor, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        cardLayout.show(contenedor, "MENU");
    }
}