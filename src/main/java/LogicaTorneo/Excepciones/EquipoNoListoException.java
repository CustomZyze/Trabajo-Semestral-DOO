package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando un equipo no está listo para jugar.
 */
public class EquipoNoListoException extends RuntimeException {

    /**
     * Crea un mensaje indicando qué equipo no tiene integrantes suficientes
     * para participar.
     * @param nombre nombre del equipo que no está listo para jugar.
     */
    public EquipoNoListoException(String nombre) {
        super("El equipo " + nombre + " no tiene integrantes");
    }
}
