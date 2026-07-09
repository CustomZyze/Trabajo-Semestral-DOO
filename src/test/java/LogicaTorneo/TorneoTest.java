package LogicaTorneo;

import LogicaTorneo.Excepciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TorneoTest {
    private Torneo torneo;
    private Formato formatoSinEmpates;

    @BeforeEach
    public void setUp() {
        formatoSinEmpates = new Formato() {
            @Override
            public List<Partida> generarCalendario(List<Participante> inscritos) {
                return new java.util.ArrayList<>();
            }
            @Override
            public boolean hayEmpates() { return false; }
            @Override
            public void actualizarResultado(Partida partida) {}
            @Override
            public List<Partida> avanzarRonda(List<Partida> llaves) { return new java.util.ArrayList<>(); }
            @Override
            public Participante obtenerCampeon(List<Partida> llaves) { return null; }
            @Override
            public boolean tieneClasificacion() { return false; }
            @Override
            public List<RegistroLiga> getTablaPosiciones() { return null; }
        };
        torneo = new Torneo("Torneo Copa", Disciplina.LOL, formatoSinEmpates);
    }

    @Test
    @DisplayName("Rechazar nombre vacío o con puros espacios en Torneo")
    public void testConstructorTorneoNombreVacio() {
        assertThrows(NoInfoException.class, () -> {
            new Torneo("   ", Disciplina.LOL, formatoSinEmpates);
        }, "El constructor debería rechazar nombres con espacios en blanco.");
    }

    @Test
    @DisplayName("Rechazar RUT vacío en Jugador")
    public void testConstructorJugadorRutVacio() {
        assertThrows(NoInfoException.class, () -> {
            new Jugador("Sebastián", "s@udec.cl", "");
        }, "El constructor de Jugador debería rechazar un RUT vacío.");
    }

    @Test
    @DisplayName("Impedir el registro de participantes duplicados en Torneo")
    public void testInscribirParticipanteDuplicado() {
        Participante p1 = new Jugador("Dartz", "dartz@mail.com", "121-1");
        Participante p2 = new Jugador("DARTZ", "otro@mail.com", "234-2");

        torneo.inscribir(p1);

        assertThrows(DuplicadoException.class, () -> {
            torneo.inscribir(p2);
        }, "El torneo no debería permitir dos participantes con mismo nombre");
    }

    @Test
    @DisplayName("Impedir que se inscriban mas de 32 participantes")
    public void testInscribirPasaDelMaximo() {
        for (int i = 1; i <= 32; i++) {
            torneo.getInscritos().add(new Jugador("Jugador" + i, "contacto", "rut" + i));
        }
        Participante extra = new Jugador("Jugador33", "contacto", "rut33");
        assertThrows(CantidadInvalidaParticipantesException.class, () -> {
            torneo.inscribir(extra);
        }, "Debería arrojar excepción al inscribir al participante número 33.");
    }

    @Test
    @DisplayName("No permitir mas del limite de jugadores en Equipos")
    public void testEquipoPasaDelMaximo() {
        Equipo equipo = new Equipo("Azules", "azu@gmail.com", 2); // Límite de 2 jugadores
        Jugador j1 = new Jugador("Carl", "c@gmail.com", "1");
        Jugador j2 = new Jugador("Lucas", "l@gmail.com", "2");
        Jugador j3 = new Jugador("Marcos", "m@gmail.com", "3");

        equipo.agregarIntegrante(j1);
        equipo.agregarIntegrante(j2);

        assertThrows(EquipoLlenoException.class, () -> {
            equipo.agregarIntegrante(j3);
        }, "El equipo no deberia admitir mas si tiene un limite de 2");
    }

    @Test
    @DisplayName("No dejar que hayan Equipos vacios para un Torneo")
    public void testGenerarLlavesConEquipoVacio() {
        Equipo equipoVacio = new Equipo("Informatica", "inf@udec.cl", 5);
        Participante p2 = new Jugador("Luis", "l@mail.com", "4");

        torneo.inscribir(equipoVacio); // Equipo sin integrantes
        torneo.inscribir(p2);

        assertThrows(EquipoNoListoException.class, () -> {
            torneo.generarLlaves();
        }, "No deberia partir el torneo si un equipo inscrito no tiene jugadores");
    }


    @Test
    @DisplayName("No permitir empezar el torneo si no hay suficientes jugadores")
    public void testGenerarLlavesSinSuficientesInterantes() {
        Participante p1 = new Jugador("Dartz", "contacto", "1");
        torneo.inscribir(p1); // Solo un inscrito

        assertThrows(CantidadInvalidaParticipantesException.class, () -> {
            torneo.generarLlaves();
        }, "El torneo requiere un mínimo de 2 participantes");
    }

    @Test
    @DisplayName("No dejar que se creen Torneo con tamaños que no soporten el formato")
    public void testGenerarLlavesNoPotenciaDeDos() {
        // Inscribimos 3 jugadores (3 no es potencia de 2)
        torneo.inscribir(new Jugador("J1", "c", "1"));
        torneo.inscribir(new Jugador("J2", "c", "2"));
        torneo.inscribir(new Jugador("J3", "c", "3"));

        assertThrows(CantidadInvalidaParticipantesException.class, () -> {
            torneo.generarLlaves();
        }, "Formatos de eliminación tienen ser de tamaño potencia de 2 (2, 4, 8, 16, 32).");
    }

    @Test
    @DisplayName("No permitir resultados negativos dentro de las partidas")
    public void testRegistrarResultadoNegativo() {
        Partida partidaTest = new Partida(
                new Jugador("J1", "c", "1"),
                new Jugador("J2", "c", "2")
        );

        assertThrows(PuntajeInvalidoException.class, () -> {
            torneo.registrarResultadosPartida(partidaTest, 3, -1);
        }, "Los puntajes no pueden ser numeros negativos");
    }

    @Test
    @DisplayName("Evitar Empates en Partidas del formato Eliminatoria")
    public void testRegistrarEmpateEnEliminatoria() {
        Partida partidaTest2 = new Partida(
                new Jugador("J1", "c", "1"),
                new Jugador("J2", "c", "2")
        );
        assertThrows(EmpateException.class, () -> {
            torneo.registrarResultadosPartida(partidaTest2, 2, 2);
        }, "El Formato no admite un empate entre equipos");
    }
    @Test
    @DisplayName("programarFechas asigna bloques de las 17:00 y 20:00 alternadamente")
    public void testHorariosAlternadosFechas() {
        Formato formatoPrueba = new Formato() {
            @Override
            public List<Partida> generarCalendario(List<Participante> inscritos) {
                List<Partida> lista = new java.util.ArrayList<>();
                lista.add(new Partida(inscritos.get(0), inscritos.get(1)));
                lista.add(new Partida(inscritos.get(2), inscritos.get(3)));
                return lista;
            }
            @Override public boolean hayEmpates() { return false; }
            @Override public void actualizarResultado(Partida partida) {}
            @Override public List<Partida> avanzarRonda(List<Partida> llaves) { return new java.util.ArrayList<>(); }
            @Override public Participante obtenerCampeon(List<Partida> llaves) { return null; }
            @Override public boolean tieneClasificacion() { return false; }
            @Override public List<RegistroLiga> getTablaPosiciones() { return null; }
        };

        Torneo torneoFechas = new Torneo("Torneo Fechas", Disciplina.LOL, formatoPrueba);
        torneoFechas.inscribir(new Jugador("J1", "c", "1"));
        torneoFechas.inscribir(new Jugador("J2", "c", "2"));
        torneoFechas.inscribir(new Jugador("J3", "c", "3"));
        torneoFechas.inscribir(new Jugador("J4", "c", "4"));
        torneoFechas.generarLlaves();

        LocalDate fechaInicio = LocalDate.of(2026, 11, 10);
        torneoFechas.programarFechas(fechaInicio);
        List<Partida> partidas = torneoFechas.getLlaves();


        assertEquals(LocalTime.of(17, 0), partidas.get(0).getFecha().toLocalTime());
        assertEquals(fechaInicio, partidas.get(0).getFecha().toLocalDate());

        assertEquals(LocalTime.of(20, 0), partidas.get(1).getFecha().toLocalTime());
        assertEquals(fechaInicio, partidas.get(1).getFecha().toLocalDate());
    }
}