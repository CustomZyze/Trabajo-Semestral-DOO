package LogicaTorneo;

import java.util.List;

public interface Formato {
    List<Partida> generarCalendario(List<Participante> participantes);
    List<Partida> avanzarRonda(List<Partida> rondaActual);
    void actualizarResultado(Partida partida);
    Participante obtenerCampeon(List<Partida> rondaActual);
}
