package LogicaTorneo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroLigaTest {
    private RegistroLiga registro;
    private Participante jugadorPrueba;

    @BeforeEach
    public void setUp() {
        jugadorPrueba = new Jugador("TestPlayer", "contacto@test.com", "11.111.111-1");
        registro = new RegistroLiga(jugadorPrueba);
    }

    @Test
    @DisplayName("Una victoria suma 3 puntos y 1 partido ganado")
    public void testRegistrarVictoria() {
        registro.registrarVictoria();

        assertEquals(3, registro.getPuntos());
        assertEquals(1, registro.getPartidosJugados());
        assertEquals(1, registro.getGanados());
        assertEquals(0, registro.getPerdidos());
        assertEquals(0, registro.getEmpatados());
    }

    @Test
    @DisplayName("Una derrota suma 0 puntos y 1 partido perdido")
    public void testRegistrarDerrota() {
        registro.registrarDerrota();

        assertEquals(0, registro.getPuntos());
        assertEquals(1, registro.getPartidosJugados());
        assertEquals(0, registro.getGanados());
        assertEquals(1, registro.getPerdidos());
        assertEquals(0, registro.getEmpatados());
    }

    @Test
    @DisplayName("Un empate suma 1 punto y 1 partido empatado")
    public void testRegistrarEmpate() {
        registro.registrarEmpate();

        assertEquals(1, registro.getPuntos());
        assertEquals(1, registro.getPartidosJugados());
        assertEquals(0, registro.getGanados());
        assertEquals(0, registro.getPerdidos());
        assertEquals(1, registro.getEmpatados());
    }

    @Test
    @DisplayName("Múltiples resultados se acumulan correctamente")
    public void testAcumulacionResultados() {
        registro.registrarVictoria();
        registro.registrarEmpate();
        registro.registrarDerrota();
        registro.registrarVictoria();

        assertEquals(7, registro.getPuntos());
        assertEquals(4, registro.getPartidosJugados());
        assertEquals(2, registro.getGanados());
        assertEquals(1, registro.getEmpatados());
        assertEquals(1, registro.getPerdidos());
    }
}