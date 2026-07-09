package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando falta información obligatoria.
 */
public class NoInfoException extends RuntimeException {

    /**
     * Recibe un mensaje personalizado que indica qué información falta.
     * @param message mensaje que describe la información faltante.
     */
    public NoInfoException(String message) {
        super(message);
    }
}
