package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando se intenta registrar un puntaje inválido.
 */
public class PuntajeInvalidoException extends TorneoException {

    /**
     * Recibe un mensaje personalizado que explica por qué el puntaje
     * ingresado no es válido.
     * @param message mensaje que describe el error del puntaje.
     */
    public PuntajeInvalidoException(String message) {
        super(message);
    }
}
