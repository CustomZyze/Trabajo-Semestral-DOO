import LogicaTorneo.*;

import java.util.List;

public class mainLiga {
    public static void main(String[] args) {

        LigaSimple ligaSimple = new LigaSimple();


        Torneo torneo = new Torneo(
                "Liga de Equipos",
                Disciplina.FUTBOL,
                ligaSimple
        );


        Equipo equipo1 = new Equipo("Tigres", "tigres@email.com");
        Equipo equipo2 = new Equipo("Leones", "leones@email.com");
        Equipo equipo3 = new Equipo("Halcones", "halcones@email.com");
        Equipo equipo4 = new Equipo("Lobos", "lobos@email.com");
        Equipo equipo5 = new Equipo("Dragones", "dragones@email.com");
        Equipo equipo6 = new Equipo("Panteras", "panteras@email.com");

        torneo.inscribir(equipo1);
        torneo.inscribir(equipo2);
        torneo.inscribir(equipo3);
        torneo.inscribir(equipo4);
        torneo.inscribir(equipo5);
        torneo.inscribir(equipo6);

        torneo.generarLlaves();
        torneo.mostrarLlaves();

        List<Partida> partidos = torneo.getLlaves();

        System.out.println("\n--- REGISTRANDO RESULTADOS ---");


        partidos.get(0).registrarResultado(2, 0);   // Tigres gana
        partidos.get(1).registrarResultado(1, 1);   // Empate
        partidos.get(2).registrarResultado(3, 1);   // Tigres gana
        partidos.get(3).registrarResultado(2, 1);   // Tigres gana
        partidos.get(4).registrarResultado(4, 0);   // Tigres gana

        partidos.get(5).registrarResultado(2, 1);   // Leones gana
        partidos.get(6).registrarResultado(1, 0);   // Leones gana
        partidos.get(7).registrarResultado(1, 1);   // Empate
        partidos.get(8).registrarResultado(3, 0);   // Leones gana

        partidos.get(9).registrarResultado(2, 0);   // Halcones gana
        partidos.get(10).registrarResultado(1, 0);  // Halcones gana
        partidos.get(11).registrarResultado(2, 2);  // Empate

        partidos.get(12).registrarResultado(2, 1);  // Lobos gana
        partidos.get(13).registrarResultado(1, 0);  // Lobos gana

        partidos.get(14).registrarResultado(1, 0);  // Dragones gana


        ligaSimple.getTablaPosiciones();
    }

}