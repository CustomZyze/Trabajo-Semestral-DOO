package LogicaTorneo;
public class RegistroLiga {
    private Participante participante;
    private int puntos;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;

    public RegistroLiga(Participante participante){
        this.participante = participante;
        this.puntos = 0;
        this.partidosJugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
    }
    public void registrarVictoria(){
        partidosJugados++;
        ganados++;
        puntos += 3;
    }

    public void registrarDerrota(){
        partidosJugados++;
        perdidos++;
    }

    public void registrarEmpate(){
        partidosJugados++;
        empatados++;
        puntos += 1;
    }

    public int getPuntos(){
        return puntos;
    }

    public int getPartidosJugados(){
        return partidosJugados;
    }

    public int getGanados(){
        return ganados;
    }

    public int getPerdidos(){
        return perdidos;
    }

    public int getEmpatados(){
        return empatados;
    }

    public Participante getParticipante(){
        return participante;
    }

}
