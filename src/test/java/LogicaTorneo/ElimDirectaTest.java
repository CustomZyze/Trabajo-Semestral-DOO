package LogicaTorneo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ElimDirectaTest {
    private ElimDirecta formato;
    private Participante p1, p2, p3, p4;
    private List<Participante> participantes;

    @BeforeEach
    public void setUp() {
        formato = new ElimDirecta();
        p1 = new Jugador("P1", "c", "1");
        p2 = new Jugador("P2", "c", "2");
        p3 = new Jugador("P3", "c", "3");
        p4 = new Jugador("P4", "c", "4");
        participantes = Arrays.asList(p1, p2, p3, p4);
    }

    @Test
    @DisplayName("generarCalendario empareja participantes de dos en dos")
    public void testGenerarCalendario() {
        List<Partida> primeraRonda = formato.generarCalendario(participantes);

        assertEquals(2, primeraRonda.size());
        assertEquals(p1, primeraRonda.get(0).getP1());
        assertEquals(p2, primeraRonda.get(0).getP2());
        assertEquals(p3, primeraRonda.get(1).getP1());
        assertEquals(p4, primeraRonda.get(1).getP2());
    }

    @Test
    @DisplayName("avanzarRonda genera nueva ronda solo con ganadores")
    public void testAvanzarRondaConMultiplesGanadores() {
        List<Partida> primeraRonda = formato.generarCalendario(participantes);

        // P1 y P3 ganan sus partidas
        primeraRonda.get(0).registrarResultado(2, 0);
        primeraRonda.get(1).registrarResultado(3, 1);

        List<Partida> segundaRonda = formato.avanzarRonda(primeraRonda);

        assertEquals(1, segundaRonda.size());
        assertEquals(p1, segundaRonda.get(0).getP1());
        assertEquals(p3, segundaRonda.get(0).getP2());
    }

    @Test
    @DisplayName("avanzarRonda retorna lista vacía si ya hay campeón")
    public void testAvanzarRondaConUnGanador() {
        List<Partida> finalRonda = new ArrayList<>();
        Partida partidaFinal = new Partida(p1, p3);
        partidaFinal.registrarResultado(1, 0);
        finalRonda.add(partidaFinal);

        List<Partida> siguienteRonda = formato.avanzarRonda(finalRonda);

        assertTrue(siguienteRonda.isEmpty());
    }

    @Test
    @DisplayName("obtenerCampeon retorna el ganador de la última partida")
    public void testObtenerCampeon() {
        List<Partida> finalRonda = new ArrayList<>();
        Partida partidaFinal = new Partida(p2, p4);
        partidaFinal.registrarResultado(0, 2);
        finalRonda.add(partidaFinal);

        Participante campeon = formato.obtenerCampeon(finalRonda);

        assertEquals(p4, campeon);
    }

    @Test
    @DisplayName("Validación del formato")
    public void testCaracteristicasFormato() {
        assertFalse(formato.tieneClasificacion());
        assertTrue(formato.getTablaPosiciones().isEmpty());
    }
}