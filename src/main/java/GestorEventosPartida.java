import java.util.ArrayList;
import java.util.List;

public class GestorEventosPartida {
    public List<ObservadorPartida> observadores;

    public GestorEventosPartida(){
        observadores = new ArrayList<>();
    }

    public void subscribir(ObservadorPartida observador){
        observadores.add(observador);
    }

    public void desubscribir(ObservadorPartida observador){
        observadores.remove(observador);
    }

    public void notificar(Partida partida ){
        for (ObservadorPartida observador : observadores ){
            observador.actualizar(partida);
        }
    }



}

