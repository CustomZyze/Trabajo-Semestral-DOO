package LogicaTorneo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LigaSimpleTest {

    private LigaSimple liga;
    private Participante p1;
    private Participante p2;
    private Participante p3;
    private Participante p4;
    private List<Participante> participantes;

    @BeforeEach
    public void setUp() {
        liga = new LigaSimple();
        p1 = new Jugador("P1", "c1", "1");
        p2 = new Jugador("P2", "c2", "2");
        p3 = new Jugador("P3", "c3", "3");
        p4 = new Jugador("P4", "c4", "4");

        participantes = Arrays.asList(p1, p2, p3, p4);
    }

    @Test
    @DisplayName("generarCalendario crea partidos de todos contra todos y inicializa la tabla")
    public void testGenerarCalendario() {
        List<Partida> calendario = liga.generarCalendario(participantes);

        // Deberia ser (n * (n - 1) / 2) y dar 6 partidos
        assertEquals(6, calendario.size());

        // La tabla de posiciones debe tener 4 registros (uno por participante)
        assertEquals(4, liga.getTablaPosiciones().size());
    }

    @Test
    @DisplayName("actualizarResultado modifica correctamente los puntos en la tabla")
    public void testActualizarResultado() {
        liga.generarCalendario(participantes);

        Partida partida = new Partida(p1, p2);
        partida.registrarResultado(3, 1);

        liga.actualizarResultado(partida);

        RegistroLiga regP1 = liga.getTablaPosiciones().stream().filter(r -> r.getParticipante() == p1).findFirst().get();
        RegistroLiga regP2 = liga.getTablaPosiciones().stream().filter(r -> r.getParticipante() == p2).findFirst().get();

        assertEquals(3, regP1.getPuntos());
        assertEquals(1, regP1.getGanados());

        assertEquals(0, regP2.getPuntos());
        assertEquals(1, regP2.getPerdidos());
    }

    @Test
    @DisplayName("obtenerCampeon retorna al participante con mayor puntaje")
    public void testObtenerCampeon() {
        liga.generarCalendario(participantes);

        // P3 gana (3 puntos)
        Partida partida1 = new Partida(p3, p1);
        partida1.registrarResultado(2, 0);
        liga.actualizarResultado(partida1);

        // P4 empata (1 punto)
        Partida partida2 = new Partida(p4, p2);
        partida2.registrarResultado(1, 1);
        liga.actualizarResultado(partida2);

        // Campeón debería ser P3 por puntos
        Participante campeon = liga.obtenerCampeon(null); // LigaSimple no usa lista

        assertEquals(p3, campeon);
    }

    @Test
    @DisplayName("avanzarRonda siempre retorna lista vacía en formato Liga")
    public void testAvanzarRonda() {
        List<Partida> sigRonda = liga.avanzarRonda(null);
        assertTrue(sigRonda.isEmpty());
    }

    @Test
    @DisplayName("Validación de características del formato")
    public void testCaracteristicasFormato() {
        assertTrue(liga.tieneClasificacion());
        assertTrue(liga.hayEmpates());
    }
}