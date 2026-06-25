public class main {
    public static void main(String[] args) {
        Jugador j1 = new Jugador("Benja","bomba", "12312");
        Jugador j2 = new Jugador("Lucas", "loco", "12435");

        Partida p = new Partida(j1,j2);
        System.out.println(p.getPuntaje1()+" "+ p.getPuntaje2());
        System.out.println(p.getEstado());
        p.registrarResultado(2,1);
    }
}
