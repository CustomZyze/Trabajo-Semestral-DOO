package LogicaTorneo.Excepciones;

/**
 * Clase base para las excepciones propias del sistema de torneos.
 * Es abstracta porque no se debería lanzar directamente, sino que sirve
 * como clase padre para excepciones más especificas.
 */
public abstract class TorneoException extends RuntimeException {

    /**
     * Recibe un mensaje que luego será usado por las excepciones hijas.
     * @param message mensaje que describe el error ocurrido.
     */
    public TorneoException(String message) {
        super(message);
    }
}
