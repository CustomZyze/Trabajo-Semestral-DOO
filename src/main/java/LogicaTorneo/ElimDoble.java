package LogicaTorneo;

import java.util.List;
import java.util.ArrayList;

public class ElimDoble implements Formato {
    private List<Participante> llaveWinners = new ArrayList<>();
    private List<Participante> llaveLosers = new ArrayList<>();

    @Override
    public List<Partida> generarCalendario(List<Participante> participantes) {
        llaveWinners.addAll(participantes);

        List<Partida> primeraRonda = new ArrayList<>();
        for (int i = 0; i < participantes.size(); i += 2) {
            if (i + 1 < participantes.size()) {
                primeraRonda.add(new Partida(participantes.get(i), participantes.get(i + 1)));
            }
        }
        return primeraRonda;
    }

    @Override
    public List<Partida> avanzarRonda(List<Partida> rondaActual) {
        List<Participante> ganadoresEsteTurno = new ArrayList<>();
        List<Participante> caenALosersEsteTurno = new ArrayList<>();
        List<Participante> ganadoresLosersEsteTurno = new ArrayList<>();
        List<Partida> sigRonda = new ArrayList<>();

        for (Partida p : rondaActual) {
            if (p.getEstado() == EstadoPartida.TERMINADA && p.getGanador() != null) {
                Participante ganador = p.getGanador();
                Participante perdedor = (p.getParticipante1() == ganador) ? p.getParticipante2() : p.getParticipante1();

                if (llaveWinners.contains(ganador) && llaveWinners.contains(perdedor)) {
                    ganadoresEsteTurno.add(ganador);
                    caenALosersEsteTurno.add(perdedor);
                    llaveWinners.remove(perdedor);
                    llaveLosers.add(perdedor);
                } else if (llaveLosers.contains(ganador) && llaveLosers.contains(perdedor)) {
                    ganadoresLosersEsteTurno.add(ganador);
                    llaveLosers.remove(perdedor);
                } else {
                    if (llaveWinners.contains(ganador)) {
                        llaveLosers.remove(perdedor);
                    } else {
                        llaveWinners.remove(perdedor);
                        llaveLosers.add(perdedor);
                        ganadoresLosersEsteTurno.add(ganador);
                        caenALosersEsteTurno.add(perdedor);
                    }
                }
            }
        }

        for (int i = 0; i < ganadoresEsteTurno.size(); i += 2) {
            if (i + 1 < ganadoresEsteTurno.size()) {
                sigRonda.add(new Partida(ganadoresEsteTurno.get(i), ganadoresEsteTurno.get(i + 1)));
            }
        }

        List<Participante> paraEmparejarLosers = new ArrayList<>();
        paraEmparejarLosers.addAll(ganadoresLosersEsteTurno);
        paraEmparejarLosers.addAll(caenALosersEsteTurno);

        for (int i = 0; i < paraEmparejarLosers.size(); i += 2) {
            if (i + 1 < paraEmparejarLosers.size()) {
                sigRonda.add(new Partida(paraEmparejarLosers.get(i), paraEmparejarLosers.get(i + 1)));
            }
        }

        if (sigRonda.isEmpty() && llaveWinners.size() == 1 && llaveLosers.size() == 1) {
            sigRonda.add(new Partida(llaveWinners.get(0), llaveLosers.get(0)));
        }
        return sigRonda;
    }
}