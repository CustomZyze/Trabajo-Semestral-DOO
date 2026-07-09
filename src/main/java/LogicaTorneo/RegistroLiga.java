package LogicaTorneo;

/**
 * Representa el registro de un participante dentro de una liga.
 * Esta clase guarda la información necesaria para construir una tabla
 * de posiciones, como puntos, partidos jugados, victorias, empates y derrotas.
 */
public class RegistroLiga {
    private Participante participante;
    private int puntos;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;

    /**
     * Constructor de la clase RegistroLiga.
     * Crea un registro inicial para un participante con todos sus valores en cero.
     * @param participante participante al que pertenece este registro.
     */
    public RegistroLiga(Participante participante){
        this.participante = participante;
        this.puntos = 0;
        this.partidosJugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
    }

    /**
     * Registra una victoria para el participante.
     */
    public void registrarVictoria(){
        partidosJugados++;
        ganados++;
        puntos += 3;
    }

    /**
     * Registra una derrota para el participante.
     */
    public void registrarDerrota(){
        partidosJugados++;
        perdidos++;
    }

    /**
     * Registra un empate para el participante.
     */
    public void registrarEmpate(){
        partidosJugados++;
        empatados++;
        puntos += 1;
    }

    /**
     * Obtiene los puntos acumulados.
     * @return puntos del participante.
     */
    public int getPuntos(){
        return puntos;
    }

    /**
     * Obtiene la cantidad de partidos jugados.
     * @return partidos jugados.
     */
    public int getPartidosJugados(){
        return partidosJugados;
    }

    /**
     * Obtiene la cantidad de partidos ganados.
     * @return partidos ganados.
     */
    public int getGanados(){
        return ganados;
    }

    /**
     * Obtiene la cantidad de partidos perdidos.
     * @return partidos perdidos.
     */
    public int getPerdidos(){
        return perdidos;
    }

    /**
     * Obtiene la cantidad de partidos empatados.
     * @return partidos empatados.
     */
    public int getEmpatados(){
        return empatados;
    }

    /**
     * Obtiene el participante asociado al registro.
     * @return participante de este registro.
     */

    public Participante getParticipante(){
        return participante;
    }

}
