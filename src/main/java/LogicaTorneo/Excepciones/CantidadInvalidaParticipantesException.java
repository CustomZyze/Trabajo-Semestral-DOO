package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando la cantidad de participantes de un torneo
 * no cumple con las reglas necesarias.
 */
public class CantidadInvalidaParticipantesException extends RuntimeException {

    /**
     * Recibe un mensaje personalizado que explica cuál fue el problema
     * con la cantidad de participantes.
     * @param message mensaje que describe el error ocurrido.
     */
    public CantidadInvalidaParticipantesException(String message) {
        super(message);
    }
}
