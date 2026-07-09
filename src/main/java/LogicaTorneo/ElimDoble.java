package LogicaTorneo;

import java.util.List;
import java.util.ArrayList;


/**
 * Representa el formato de eliminación doble. el cual un
 * participante no queda eliminado al perder una sola vez.
 * Al perder por primera vez, pasa a la llave de perdedores.
 * Si pierde nuevamente en la llave de perdedores, queda eliminado.
 */
public class ElimDoble implements Formato {

    /**
     * Lista de participantes que siguen en la llave de ganadores.
     */
    private List<Participante> llaveWinners = new ArrayList<>();

    /**
     * Lista de participantes que siguen en la llave de perdedores.
     */
    private List<Participante> llaveLosers = new ArrayList<>();

    /**
     * Genera la primera ronda del torneo.
     * Todos los participantes comienzan en la llave de ganadores. Luego se
     * emparejan de dos en dos para crear las partidas iniciales.
     * @param participantes lista de participantes inscritos en el torneo.
     * @return lista de partidas correspondientes a la primera ronda.
     */
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

    /**
     * Genera la siguiente ronda del torneo de eliminación doble.
     * Este metodo revisa los resultados de la ronda actual y separa a los
     * participantes según corresponda:
     * - Los ganadores de la llave de ganadores siguen en winners.
     * - Los perdedores de winners bajan a losers.
     * - Los ganadores de losers siguen compitiendo.
     * - Los perdedores de losers quedan eliminados.
     * @param rondaActual lista de partidas de la ronda actual.
     * @return lista de partidas de la siguiente ronda.
     */
    @Override
    public List<Partida> avanzarRonda(List<Partida> rondaActual) {
        List<Participante> ganadoresEsteTurno = new ArrayList<>();
        List<Participante> caenALosersEsteTurno = new ArrayList<>();
        List<Participante> ganadoresLosersEsteTurno = new ArrayList<>();
        List<Partida> sigRonda = new ArrayList<>();

        for (Partida p : rondaActual) {
            if (p.getEstado() == EstadoPartida.TERMINADA && p.getGanador() != null) {
                Participante ganador = p.getGanador();
                Participante perdedor = (p.getP1() == ganador) ? p.getP2() : p.getP1();

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

    /**
     * Actualiza el resultado de una partida.
     * @param partida partida cuyo resultado podría actualizarse.
     */
    @Override
    public void actualizarResultado(Partida partida) {

    }

    /**
     * Obtiene el campeón del torneo.
     * @param rondaActual lista de partidas de la ronda actual o final.
     * @return participante campeón si existe, o null si todavía no hay campeón.
     */
    @Override
    public Participante obtenerCampeon(List<Partida> rondaActual) {
        if (!rondaActual.isEmpty() && rondaActual.get(0).getGanador() != null) {
            return rondaActual.get(0).getGanador();
        }
        return null;
    }

    /**
     * Indica si el formato tiene tabla de clasificación.
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