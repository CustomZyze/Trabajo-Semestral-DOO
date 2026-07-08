package LogicaTorneo;

public enum Disciplina {
    FUTBOL(11),
    BASKETBALL(5),
    AJEDREZ(1),
    PING_PONG(1),
    LOL(5),
    VALORANT(5);

    private int maxJugadores;

    Disciplina(int i) {
        maxJugadores = i;
    }
    public int getMaxJugadores(){

        return maxJugadores;
    }
}
