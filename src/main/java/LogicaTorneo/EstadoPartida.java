package LogicaTorneo;
import java.awt.Color;

/**
 * Representa los posibles estados de una partida.
 * Cada estado tiene asociado un color, lo que permite mostrar visualmente
 * el estado de una partida en la interfaz gráfica.
 */
public enum EstadoPartida {

    PENDIENTE(new Color(147, 147, 147)),  // Gris claro
    EN_CURSO(new Color(150, 200, 255)),   // Celeste claro
    TERMINADA(new Color(100, 220, 100)),  // Verde
    CANCELADA(new Color(255, 100, 100));  // Rojo
    private final Color colorAsociado;


    /**
     * Constructor del enum EstadoPartida.
     * @param colorAsociado color que representa visualmente el estado.
     */
    EstadoPartida(Color colorAsociado) {
        this.colorAsociado = colorAsociado;
    }

    /**
     * Obtiene el color asociado al estado de la partida.
     * @return color correspondiente al estado.
     */
    public Color getColorAsociado() {
        return colorAsociado;
    }
}
