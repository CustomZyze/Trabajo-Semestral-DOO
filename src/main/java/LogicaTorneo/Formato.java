package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz que define el comportamiento general de un formato de torneo.
 * Cualquier formato de torneo, como liga simple, eliminacion directa o
 * eliminacion doble, debe implementar los metodos principales definidos aqui.
 */

public interface Formato {

    /**
     * Genera el calendario inicial de partidas segun el formato del torneo.
     * @param participantes lista de participantes inscritos.
     * @return lista de partidas generadas.
     */
    List<Partida> generarCalendario(List<Participante> participantes);

    /**
     * Genera la siguiente ronda o etapa del torneo.
     * @param rondaActual lista de partidas de la ronda actual.
     * @return lista de partidas de la siguiente ronda.
     */
    List<Partida> avanzarRonda(List<Partida> rondaActual);

    /**
     * Actualiza el resultado de una partida dentro del formato.
     * Algunos formatos, como liga simple, pueden necesitar actualizar una tabla
     * de posiciones. Otros formatos pueden no requerir lógica en este metodo.
     * @param partida partida cuyo resultado será actualizado.
     */
    void actualizarResultado(Partida partida);

    /**
     * Obtiene el campeon del torneo segun las reglas del formato.
     * @param rondaActual lista de partidas actuales o finales.
     * @return participante campeón, o null si todavía no existe.
     */
    Participante obtenerCampeon(List<Partida> rondaActual);

    /**
     * Indica si el formato utiliza tabla de clasificacion.
     * @return true si el formato tiene clasificación, false en caso contrario.
     */
    public default boolean tieneClasificacion() {
        return false;
    }

    /**
     * Obtiene la tabla de posiciones del formato.
     * @return lista de registros de liga.
     */
    public default List<RegistroLiga> getTablaPosiciones() {
        return new ArrayList<>();
    }

    /**
     * Indica si el formato permite empates.
     * @return true si el formato permite empates, false en caso contrario.
     */
    public default boolean hayEmpates(){

        return false;
    }
}
