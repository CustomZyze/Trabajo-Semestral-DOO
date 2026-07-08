package LogicaTorneo.Excepciones;

public class EquipoLlenoException extends TorneoException {
    public EquipoLlenoException(int limite) {
        super("El equipo ya alcanzó el límite máximo de " + limite + " jugadores permitidos.");
    }
}
