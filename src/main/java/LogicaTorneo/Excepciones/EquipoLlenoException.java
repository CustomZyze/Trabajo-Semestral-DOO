package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando se intenta agregar un jugador
 * a un equipo que ya alcanzó su límite máximo de integrantes.
 */
public class EquipoLlenoException extends TorneoException {

    /**
     * Crea un mensaje indicando cuál es el límite máximo permitido
     * para el equipo.
     * @param limite cantidad máxima de jugadores permitidos en el equipo.
     */
    public EquipoLlenoException(int limite) {
        super("El equipo ya alcanzó el límite máximo de " + limite + " jugadores permitidos.");
    }
}
