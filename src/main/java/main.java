import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        System.out.println("Prueba Elim Doble: ");

        Participante p1 = new Jugador("T1","","");
        Participante p2 = new Jugador("DCG","","");
        Participante p3 = new Jugador("TLAW","","");
        Participante p4 = new Jugador("KC","","");

        Formato formatoDoble = new ElimDoble();
        Torneo torneo = new Torneo("Torneo de Prueba", Disciplina.LOL, formatoDoble);

        torneo.inscribir(p1);
        torneo.inscribir(p2);
        torneo.inscribir(p3);
        torneo.inscribir(p4);

        torneo.generarLlaves();

        System.out.println("\n[RONDA 1]");
        torneo.mostrarLlaves();

        System.out.println("\nResultados ronda 1:");
        List<Partida> ronda1 = new ArrayList<>(torneo.getLlaves());
        ronda1.get(0).registrarResultado(3, 1);
        ronda1.get(1).registrarResultado(1, 3);

        System.out.println("\n[RONDA 2]");
        torneo.mostrarLlaves();

        System.out.println("\nResultados ronda 2:");
        List<Partida> ronda2 = new ArrayList<>(torneo.getLlaves());
        ronda2.get(0).registrarResultado(3, 0);
        ronda2.get(1).registrarResultado(2, 0);

        System.out.println("\n[RONDA 3 (Final Losers)]");
        torneo.mostrarLlaves();

        System.out.println("\nResultados ronda 3:");
        List<Partida> ronda3 = new ArrayList<>(torneo.getLlaves());
        ronda3.get(0).registrarResultado(1, 2);

        System.out.println("\n[RONDA 4 (Gran Final)]");
        torneo.mostrarLlaves();

        System.out.println("\nResultados ronda 4:");
        List<Partida> granFinal = new ArrayList<>(torneo.getLlaves());
        granFinal.get(0).registrarResultado(3, 1);

        System.out.println("\nFIN");
    }
}
