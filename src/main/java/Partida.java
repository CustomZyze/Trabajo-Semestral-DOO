import java.util.ArrayList;
import java.util.List;

public class Partida {
    private Participante p1;
    private Participante p2;
    private int puntaje1;
    private int puntaje2;
    private Participante ganador;
    private EstadoPartida estado;
    private GestorEventosPartida eventos;


    public Partida(Participante p1,Participante p2){
        this.p1 = p1;
        this.p2 = p2;
        estado = EstadoPartida.PENDIENTE;
        this.eventos = new GestorEventosPartida();
    }

    public void agregarObservador(ObservadorPartida observador){
        eventos.subscribir(observador);
    }

    public void eliminarObservador(ObservadorPartida observador){
        eventos.desubscribir(observador);
    }

    public void registrarResultado(int puntaje1, int puntaje2){
        this.puntaje1 = puntaje1;
        this.puntaje2 = puntaje2;
        estado = EstadoPartida.TERMINADA;

        if (puntaje1 > puntaje2){
            ganador = p1;
        } else if (puntaje2 > puntaje1){
            ganador = p2;
        } else {
            ganador = null;
        }

        if (ganador != null) {
            System.out.println("Termino partido. \nPuntaje: " + getPuntaje1()+ " a " +getPuntaje2()+ "\nGanador: " + ganador.getNombre());
        }

        eventos.notificar(this);

    }

    public int getPuntaje1() {
        return puntaje1;
    }

    public void setPuntaje1(int puntaje1) {
        this.puntaje1 = puntaje1;
    }
    public String getp1() {
        return p1.getNombre();
    }
    public String getp2() {
        return p2.getNombre();
    }

    public int getPuntaje2() {
        return puntaje2;
    }

    public void setPuntaje2(int puntaje2) {
        this.puntaje2 = puntaje2;
    }

    public Participante getGanador() {
        return ganador;
    }
    public Participante getParticipante1() {
        return p1;
    }

    public Participante getParticipante2() {
        return p2;
    }
    public EstadoPartida getEstado() {
        return estado;
    }

}
