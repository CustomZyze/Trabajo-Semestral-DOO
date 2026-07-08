package LogicaTorneo.Excepciones;

public class DuplicadoException extends RuntimeException {
    public DuplicadoException(String nombre) {
        super("Ya hay un participante con el nombre de: " + nombre );
    }
}
