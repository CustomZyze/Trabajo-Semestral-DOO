package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando se intenta registrar un participante
 * con un nombre que ya existe.
 * Sirve para evitar duplicados dentro de un torneo o dentro de un equipo.
 */
public class DuplicadoException extends RuntimeException {

    /**
     * Crea un mensaje indicando que ya existe un participante con el nombre recibido.
     * @param nombre nombre del participante duplicado.
     */
    public DuplicadoException(String nombre) {
        super("Ya hay un participante con el nombre de: " + nombre );
    }
}
