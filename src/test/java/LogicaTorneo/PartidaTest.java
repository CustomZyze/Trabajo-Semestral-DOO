package LogicaTorneo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PartidaTest {
    private Partida partida;
    private Participante p1;
    private Participante p2;

    @BeforeEach
    public void setUp() {
        p1 = new Jugador("Player1", "c1", "1");
        p2 = new Jugador("Player2", "c2", "2");
        partida = new Partida(p1, p2);
    }

    @Test
    @DisplayName("Constructor inicia partida en estado PENDIENTE y sin ganador")
    public void testEstadoInicial() {
        assertEquals(EstadoPartida.PENDIENTE, partida.getEstado());
        assertNull(partida.getGanador());
        assertNull(partida.getFecha());
    }

    @Test
    @DisplayName("Registrar resultado con P1 gana cambia estado y define ganador")
    public void testP1Gana() {
        partida.registrarResultado(3, 1);

        assertEquals(EstadoPartida.TERMINADA, partida.getEstado());
        assertEquals(p1, partida.getGanador());
        assertEquals(3, partida.getPuntaje1());
        assertEquals(1, partida.getPuntaje2());
    }

    @Test
    @DisplayName("Registrar resultado con P2 gana cambia estado y define ganador")
    public void testP2Gana() {
        partida.registrarResultado(0, 2);

        assertEquals(EstadoPartida.TERMINADA, partida.getEstado());
        assertEquals(p2, partida.getGanador());
    }

    @Test
    @DisplayName("Registrar empate cambia de estado pero deja ganador en null")
    public void testEmpate() {
        partida.registrarResultado(2, 2);

        assertEquals(EstadoPartida.TERMINADA, partida.getEstado());
        assertNull(partida.getGanador());
    }

    @Test
    @DisplayName("getFechaFormateada retorna 'Sin fecha' si es null")
    public void testFechaNulaFormateada() {
        assertEquals("Sin fecha", partida.getFechaFormateada());
    }

    @Test
    @DisplayName("getFechaFormateada retorna string en formato dd/MM/yyyy HH:mm")
    public void testFechaAsignadaFormateada() {
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 7, 8, 17, 30);
        partida.setFecha(fechaPrueba);

        assertEquals("08/07/2026 17:30", partida.getFechaFormateada());
    }
}