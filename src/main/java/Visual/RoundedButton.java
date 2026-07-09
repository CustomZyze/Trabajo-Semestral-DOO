package Visual;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Esta clase extiende JButton para modificar su apariencia visual.
 * En lugar de usar el botón rectangular normal de Swing, dibuja un botón
 * con esquinas redondeadas y efectos visuales al pasar el mouse o presionarlo.
 */
public class RoundedButton extends JButton {

    /**
     * Radio usado para redondear las esquinas del botón.
     */
    private int radio;

    /**
     * Constructor del botón redondeado.
     * Configura el texto del botón, el radio de las esquinas y algunos
     * aspectos visuales.
     * @param texto texto que se mostrará en el botón.
     * @param radio nivel de redondeo de las esquinas.
     */
    public RoundedButton(String texto, int radio) {
        super(texto);
        this.radio = radio;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Dibuja el contenido del botón.
     * Este metodo se sobrescribe para pintar manualmente el fondo del botón
     * con forma redondeada.
     * Cambia el color según el estado del botón:
     * - Si está presionado, usa un color más oscuro.
     * - Si el mouse está encima, usa un color más claro.
     * - Si está en estado normal, usa el color de fondo definido.
     * @param g objeto Graphics usado para dibujar el componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radio, radio));
        g2.dispose();
        super.paintComponent(g);
    }

    /**
     * Evita que Swing dibuje el borde tradicional del botón.
     * @param g objeto Graphics usado para dibujar.
     */
    @Override
    protected void paintBorder(Graphics g) {
    }

    /**
     * Define el área real donde el botón detecta clics.
     * Este metodo verifica si el punto esta dentro de
     * la forma redondeada del botón.
     * Esto hace que el área clickeable coincida mejor con la forma visual.
     * @param x coordenada horizontal del punto.
     * @param y coordenada vertical del punto.
     * @return true si el punto está dentro del botón redondeado, false en caso contrario.
     */
    @Override
    public boolean contains(int x, int y) {
        RoundRectangle2D forma = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radio, radio);
        return forma.contains(x, y);
    }
}