package LogicaTorneo;

import LogicaTorneo.Excepciones.DuplicadoException;
import LogicaTorneo.Excepciones.EquipoLlenoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipoTest {
    private Equipo equipo;
    private Jugador j1;
    private Jugador j2;
    private Jugador j3;

    @BeforeEach
    public void setUp() {
        // Equipo con límite de 2 jugadores
        equipo = new Equipo("Los Pumas", "contacto@pumas.cl", 2);
        j1 = new Jugador("Jugador 1", "c1", "111-1");
        j2 = new Jugador("Jugador 2", "c2", "222-2");
        j3 = new Jugador("Jugador 3", "c3", "333-3");
    }

    @Test
    @DisplayName("estaListoParaJugar es false si no tiene integrantes y true si tiene")
    public void testEstaListoParaJugar() {
        assertFalse(equipo.estaListoParaJugar());
        equipo.agregarIntegrante(j1);
        assertTrue(equipo.estaListoParaJugar());
    }

    @Test
    @DisplayName("Lanza EquipoLlenoException si se supera el límite máximo")
    public void testLimiteMaximoJugadores() {
        equipo.agregarIntegrante(j1);
        equipo.agregarIntegrante(j2);

        assertThrows(EquipoLlenoException.class, () -> {
            equipo.agregarIntegrante(j3);
        });
    }

    @Test
    @DisplayName("No permite que hayan 2 jugadores con mismo nombre en el Equipo")
    public void testEvitarJugadoresDuplicados() {
        equipo.agregarIntegrante(j1);

        assertThrows(DuplicadoException.class, () -> {
            equipo.agregarIntegrante(j1);
        });
    }
}