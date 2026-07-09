package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase encargada de gestionar los eventos relacionados con una partida.
 * Esta clase implementa el patron Observer, donde distintos
 * objetos pueden subscribirse para recibir una notificación cuando ocurre un
 * cambio importante en una partida.
 */
public class GestorEventosPartida {

    /**
     * Lista de observadores que serán notificados cuando ocurra un evento
     * relacionado con una partida.
     */
    private List<ObservadorPartida> observadores;

    /**
     * Constructor de la clase GestorEventosPartida.
     * Inicializa la lista de observadores vacía.
     */
    public GestorEventosPartida(){
        observadores = new ArrayList<>();
    }

    /**
     * Agrega un observador a la lista de observadores.
     * El observador agregado será notificado cuando se llame al metodo
     * notificar().
     * @param observador objeto que desea recibir notificaciones de partidas.
     */
    public void subscribir(ObservadorPartida observador){
        observadores.add(observador);
    }

    /**
     * Elimina un observador de la lista.
     * Despues de ser eliminado, el observador ya no recibirá notificaciones
     * sobre los eventos de partida.
     * @param observador objeto que se desea dejar de notificar.
     */
    public void desubscribir(ObservadorPartida observador){
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados sobre una partida.
     * Recorre la lista de observadores y llama al metodo actualizar() de cada uno,
     * entregando como informacion la partida que produjo el evento.
     * @param partida partida que generó el evento o cambio.
     */
    public void notificar(Partida partida ){
        for (ObservadorPartida observador : observadores ){
            observador.actualizar(partida);
        }
    }



}

