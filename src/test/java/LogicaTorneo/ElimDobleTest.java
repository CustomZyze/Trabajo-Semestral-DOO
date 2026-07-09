package LogicaTorneo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElimDobleTest {
    private ElimDoble formato;
    private Participante p1, p2, p3, p4;
    private List<Participante> participantes;

    @BeforeEach
    public void setUp() {
        formato = new ElimDoble();
        p1 = new Jugador("P1", "c", "1");
        p2 = new Jugador("P2", "c", "2");
        p3 = new Jugador("P3", "c", "3");
        p4 = new Jugador("P4", "c", "4");
        participantes = Arrays.asList(p1, p2, p3, p4);
    }

    @Test
    @DisplayName("generarCalendario crea ronda e ingresa a todos en llaveWinners")
    public void testGenerarCalendario() {
        List<Partida> primeraRonda = formato.generarCalendario(participantes);

        assertEquals(2, primeraRonda.size());
        assertEquals(p1, primeraRonda.get(0).getP1());
        assertEquals(p2, primeraRonda.get(0).getP2());
    }

    @Test
    @DisplayName("avanzarRonda manda a perdedores a llaveLosers y mantiene ganadores")
    public void testAvanzarRondaTransicionLosers() {
        List<Partida> primeraRonda = formato.generarCalendario(participantes);

        // P1 gana a P2. P3 gana a P4.
        primeraRonda.get(0).registrarResultado(2, 0);
        primeraRonda.get(1).registrarResultado(2, 0);

        List<Partida> segundaRonda = formato.avanzarRonda(primeraRonda);

        // 2 partidas: Winners (P1 vs P3) y Losers (P2 vs P4)
        assertEquals(2, segundaRonda.size());

        // Verificamos ganadores
        assertEquals(p1, segundaRonda.get(0).getP1());
        assertEquals(p3, segundaRonda.get(0).getP2());

        // Verificamos perdedores
        assertEquals(p2, segundaRonda.get(1).getP1());
        assertEquals(p4, segundaRonda.get(1).getP2());
    }

    @Test
    @DisplayName("Gran Final se genera cuando queda 1 en Winners y 1 en Losers")
    public void testGeneracionGranFinal() {
        // 1. (P1 y P3 ganan, P2 y P4 pierden 1 vez)
        List<Partida> r1 = formato.generarCalendario(participantes);
        r1.get(0).registrarResultado(2, 0);
        r1.get(1).registrarResultado(2, 0);

        // 2. (P1 manda a P3 a losers. P2 mata a P4)
        List<Partida> r2 = formato.avanzarRonda(r1);
        r2.get(0).registrarResultado(2, 0);
        r2.get(1).registrarResultado(2, 0);

        // 3. (P2 mata a P3)
        List<Partida> r3 = formato.avanzarRonda(r2);
        r3.get(0).registrarResultado(2, 0);

        // 4. P1 (invicto) y P2 (campeón losers)
        List<Partida> granFinal = formato.avanzarRonda(r3);

        assertEquals(1, granFinal.size());
        assertTrue((granFinal.get(0).getP1() == p1 && granFinal.get(0).getP2() == p2) ||
                (granFinal.get(0).getP1() == p2 && granFinal.get(0).getP2() == p1));
    }
}