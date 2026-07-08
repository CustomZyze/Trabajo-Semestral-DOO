package LogicaTorneo.Excepciones;

public class EquipoNoListoException extends RuntimeException {
    public EquipoNoListoException(String nombre) {
        super("El equipo " + nombre + "no tiene integrantes");
    }
}
