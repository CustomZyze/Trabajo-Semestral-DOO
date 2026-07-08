package LogicaTorneo;
import java.awt.Color;


public enum EstadoPartida {
    PENDIENTE(new Color(147, 147, 147)),  // Gris claro
    EN_CURSO(new Color(150, 200, 255)),   // Celeste claro
    TERMINADA(new Color(100, 220, 100)),  // Verde
    CANCELADA(new Color(255, 100, 100));// Rojo
    private final Color colorAsociado;

    EstadoPartida(Color colorAsociado) {
        this.colorAsociado = colorAsociado;
    }
    public Color getColorAsociado() {
        return colorAsociado;
    }
}
