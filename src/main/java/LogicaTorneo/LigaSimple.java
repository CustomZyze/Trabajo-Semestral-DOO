package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

public class LigaSimple implements Formato {

    private List<RegistroLiga> tablaPosiciones = new ArrayList<>();

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


    @Override
    public List<Partida> avanzarRonda(List<Partida> rondaActual){
        return new ArrayList<>();
    }

    private RegistroLiga buscarRegistro(Participante participante){
        for(RegistroLiga registro : tablaPosiciones){
            if(registro.getParticipante() == participante){
                return registro;
            }
        }

        return null;
    }


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

    public void mostrarTabla() {
        System.out.println("\n--- TABLA DE POSICIONES ---");

        for (RegistroLiga registro : tablaPosiciones) {
            System.out.println(
                    registro.getParticipante().getNombre()
                            + " | Pts: " + registro.getPuntos()
                            + " | PJ: " + registro.getPartidosJugados()
                            + " | G: " + registro.getGanados()
                            + " | E: " + registro.getEmpatados()
                            + " | P: " + registro.getPerdidos()
            );
        }
    }



}
