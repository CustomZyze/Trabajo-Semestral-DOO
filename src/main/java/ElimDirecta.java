import java.util.ArrayList;
import java.util.List;
public class ElimDirecta implements Formato{
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
}
