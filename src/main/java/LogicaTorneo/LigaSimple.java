package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el formato de torneo de liga simple.
 * En una liga simple, todos los participantes se enfrentan contra todos una vez.
 * Cada resultado actualiza una tabla de posiciones mediante registros de liga.
 * Este formato permite empates y utiliza clasificación.
 */
public class LigaSimple implements Formato {

    /**
     * Tabla de posiciones de la liga.
     */
    private List<RegistroLiga> tablaPosiciones = new ArrayList<>();

    /**
     * Genera el calendario de partidas para una liga simple.
     * Primero limpia la tabla de posiciones y crea un registro para cada
     * participante. Luego genera todas las partidas posibles, haciendo que cada
     * participante juegue contra todos los demás una vez.
     * @param participantes lista de participantes inscritos en la liga.
     * @return lista de partidas generadas para la liga.
     */
    @Override
    public List<Partida> generarCalendario(List<Participante> participantes){
        List<Partida> ronda = new ArrayList<>();

        tablaPosiciones.clear();

        for (Participante p : participantes){
            tablaPosiciones.add(new RegistroLiga(p));
        }

        for (int i = 0; i<participantes.size(); i++){
            for (int j = i+1 ; j<participantes.size(); j++){
                ronda.add(new Partida(participantes.get(i), participantes.get(j)));
            }
        }
        return ronda;
    }

    /**
     * Avanza a la siguiente ronda.
     * @param rondaActual lista de partidas actuales.
     * @return lista vacía, porque la liga simple no avanza por rondas.
     */
    @Override
    public List<Partida> avanzarRonda(List<Partida> rondaActual){
        return new ArrayList<>();
    }

    /**
     * Busca el registro de liga asociado a un participante.
     * Recorre la tabla de posiciones y retorna el registro correspondiente al
     * participante recibido.
     * @param participante participante que se desea buscar en la tabla.
     * @return registro asociado al participante, o null si no se encuentra.
     */
    private RegistroLiga buscarRegistro(Participante participante){
        for(RegistroLiga registro : tablaPosiciones){
            if(registro.getParticipante() == participante){
                return registro;
            }
        }

        return null;
    }

    /**
     * Actualiza la tabla de posiciones según el resultado de una partida.
     * Si no hay ganador, se registra un empate para ambos.
     * @param partida partida cuyo resultado será registrado.
     */
    @Override
    public void actualizarResultado(Partida partida){
        RegistroLiga registro1 = buscarRegistro(partida.getP1());
        RegistroLiga registro2 = buscarRegistro(partida.getP2());

        if (registro1 == null || registro2 == null){
            return;
        }

        if(partida.getGanador() == partida.getP1()){
            registro1.registrarVictoria();
            registro2.registrarDerrota();
        }

        else if(partida.getGanador() == partida.getP2()){
            registro1.registrarDerrota();
            registro2.registrarVictoria();
        }

        else{
            registro1.registrarEmpate();
            registro2.registrarEmpate();
        }


    }

    /**
     * Obtiene el campeón de la liga.
     * El campeón se determina buscando el participante con mayor cantidad de
     * puntos en la tabla de posiciones.
     * @param llaves lista de partidas del torneo.
     * @return participante con mayor puntaje, o null si la tabla está vacía.
     */
    @Override
    public Participante obtenerCampeon(List<Partida> llaves) {

        if (tablaPosiciones.isEmpty()) {
            return null;
        }

        RegistroLiga mejorRegistro = tablaPosiciones.get(0);

        for (RegistroLiga registro : tablaPosiciones) {
            if (registro.getPuntos() > mejorRegistro.getPuntos()) {
                mejorRegistro = registro;
            }
        }

        return mejorRegistro.getParticipante();
    }

    /**
     * Indica si este formato utiliza tabla de clasificación.
     * @return true, porque la liga simple sí tiene tabla de posiciones.
     */
    @Override
    public boolean tieneClasificacion() {
        return true;
    }

    /**
     * Obtiene la tabla de posiciones de la liga.
     * @return lista con los registros de cada participante en la liga.
     */
    @Override
    public List<RegistroLiga> getTablaPosiciones() {
        return tablaPosiciones;
    }

    /**
     * Indica si este formato permite empates.
     * @return true, porque en liga simple se pueden registrar empates.
     */
    @Override
    public boolean hayEmpates(){
        return true;
    }

}
