package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

public interface Formato {
    List<Partida> generarCalendario(List<Participante> participantes);
    List<Partida> avanzarRonda(List<Partida> rondaActual);
    void actualizarResultado(Partida partida);
    Participante obtenerCampeon(List<Partida> rondaActual);
    public default boolean tieneClasificacion() {
        return false;
    }
    public default List<RegistroLiga> getTablaPosiciones() {
        return new ArrayList<>(); // Retorna lista vacía por defecto
    }
    public default boolean hayEmpates(){

        return false;
    }
}
