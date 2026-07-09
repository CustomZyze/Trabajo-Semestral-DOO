package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el formato de eliminación directa en el cual los participantes
 * se enfrentan en partidas de dos en dos. El ganador de cada partida avanza
 * a la siguiente ronda, mientras que el perdedor queda eliminado del torneo.
 */
public class ElimDirecta implements Formato {

    /**
     * Genera el calendario inicial de partidas para una ronda de eliminación directa.
     * Los participantes se emparejan en el orden en que vienen dentro de la lista.
     * @param participantes lista de participantes inscritos en el torneo.
     * @return lista de partidas generadas para la ronda.
     */
    @Override
    public List<Partida> generarCalendario(List<Participante> participantes){
        List<Partida> ronda = new ArrayList<>();

        for (int i = 0; i<participantes.size(); i+=2){
            if (i+1 < participantes.size()){
                ronda.add(new Partida(participantes.get(i), participantes.get(i+1)));
            }
        }
        return ronda;
    }

    /**
     * Genera la siguiente ronda a partir de los ganadores de la ronda actual en la
     * cual solo se consideran las partidas terminadas que tengan un ganador definido.
     * Si queda más de un ganador, se genera una nueva ronda. Si queda uno o
     * ninguno, se retorna una lista vacia.
     * @param rondaActual lista de partidas de la ronda actual.
     * @return lista de partidas correspondientes a la siguiente ronda.
     */
    @Override
    public List<Partida> avanzarRonda(List<Partida> rondaActual) {
        List<Participante> ganadores = new ArrayList<>();

        for (Partida p : rondaActual) {
            if (p.getEstado() == EstadoPartida.TERMINADA && p.getGanador() != null) {
                ganadores.add(p.getGanador());
            }
        }
        if (ganadores.size() > 1) {
            return generarCalendario(ganadores);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Actualiza el resultado de una partida.
     * @param partida partida cuyo resultado podria actualizarse.
     */
    @Override
    public void actualizarResultado(Partida partida) {

    }

    /**
     * Obtiene el campeón del torneo.
     *Si la ultima ronda tiene una partida con ganador,
     * ese ganador se considera campeon.
     *
     * @param rondaActual lista de partidas de la ronda actual o final.
     * @return participante campeón si existe, o null si aún no hay campeón.
     */
    @Override
    public Participante obtenerCampeon(List<Partida> rondaActual) {
        if (!rondaActual.isEmpty() && rondaActual.get(0).getGanador() != null) {
            return rondaActual.get(0).getGanador();
        }
        return null;
    }

    /**
     * Indica si este formato posee tabla de clasificación.
     * @return false, porque no tiene clasificación.
     */
    @Override
    public boolean tieneClasificacion() {
        return false;
    }

    /**
     * Obtiene la tabla de posiciones.
     * @return lista vacía.
     */
    @Override
    public List<RegistroLiga> getTablaPosiciones() {
        return List.of();
    }
}
