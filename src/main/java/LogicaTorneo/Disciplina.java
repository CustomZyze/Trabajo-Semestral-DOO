package LogicaTorneo;

/**
 * Representa las disciplinas disponibles para crear un torneo.
 * Cada disciplina tiene asociado un número máximo de jugadores permitido,
 * lo que sirve para controlar el tamaño de los equipos según el deporte o juego.
 */
public enum Disciplina {
    FUTBOL(11),
    BASKETBALL(5),
    AJEDREZ(1),
    PING_PONG(1),
    LOL(5),
    VALORANT(5);

    private int maxJugadores;


    /**
     * Constructor del enum Disciplina.
     * @param i cantidad máxima de jugadores para la disciplina.
     */
    Disciplina(int i) {
        maxJugadores = i;
    }

    /**
     * Obtiene la cantidad máxima de jugadores permitidos.
     * @return número máximo de jugadores de la disciplina.
     */
    public int getMaxJugadores(){

        return maxJugadores;
    }
}
