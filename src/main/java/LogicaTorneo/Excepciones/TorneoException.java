package LogicaTorneo.Excepciones;

public abstract class TorneoException extends RuntimeException {
    public TorneoException(String message) {
        super(message);
    }
}
