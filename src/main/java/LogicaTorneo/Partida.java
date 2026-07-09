package LogicaTorneo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una partida entre dos participantes dentro de un torneo.
 * Una partida guarda a los dos participantes enfrentados, sus puntajes,
 * el ganador, el estado actual de la partida y la fecha programada.
 */
public class Partida {
    private Participante p1;
    private Participante p2;
    private int puntaje1;
    private int puntaje2;
    private Participante ganador;
    private EstadoPartida estado;
    private GestorEventosPartida eventos;
    private LocalDateTime fecha;

    /**
     * Constructor de la clase Partida.
     * Crea una partida entre dos participantes, sin fecha asignada
     * y con estado inicial PENDIENTE.
     * @param p1 primer participante de la partida.
     * @param p2 segundo participante de la partida.
     */
    public Partida(Participante p1,Participante p2){
        this.p1 = p1;
        this.p2 = p2;
        this.fecha = null;
        estado = EstadoPartida.PENDIENTE;
        this.eventos = new GestorEventosPartida();
    }

    /**
     * Agrega un observador a la partida.
     * El observador será notificado cuando se registre un resultado.
     * @param observador objeto que desea observar los cambios de la partida.
     */
    public void agregarObservador(ObservadorPartida observador){
        eventos.subscribir(observador);
    }

    /**
     * Elimina un observador de la partida.
     * El observador eliminado dejará de recibir notificaciones de esta partida.
     * @param observador objeto que se desea eliminar de la lista de observadores.
     */
    public void eliminarObservador(ObservadorPartida observador){
        eventos.desubscribir(observador);
    }

    /**
     * Registra el resultado final de una partida.
     * Asigna los puntajes recibidos, cambia el estado de la partida a TERMINADA
     * y determina el ganador segun el puntaje mas alto.
     * Si ambos puntajes son iguales, el ganador queda como null, representando
     * un empate.
     * @param puntaje1 puntaje del primer participante.
     * @param puntaje2 puntaje del segundo participante.
     */
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


    /**
     * Obtiene la fecha y hora programada para la partida.
     * @return fecha y hora de la partida, o null si no tiene fecha asignada.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Asigna una fecha y hora a la partida.
     * @param fecha nueva fecha y hora de la partida.
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha de la partida en formato legible.
     * @return fecha formateada como dd/MM/yyyy HH:mm, o "Sin fecha".
     */
    public String getFechaFormateada() {
        if (fecha == null) {
            return "Sin fecha";
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formato);
    }

    /**
     * Obtiene el puntaje del primer participante.
     * @return puntaje del primer participante.
     */
    public int getPuntaje1() {
        return puntaje1;
    }

    /**
     * Modifica el puntaje del primer participante.
     * @param puntaje1 nuevo puntaje del primer participante.
     */
    public void setPuntaje1(int puntaje1) {
        this.puntaje1 = puntaje1;
    }

    /**
     * Obtiene el primer participante de la partida.
     * @return primer participante.
     */
    public Participante getP1() {
        return p1;
    }

    /**
     * Obtiene el segundo participante de la partida.
     * @return segundo participante.
     */
    public Participante getP2() {
        return p2;
    }

    /**
     * Obtiene el puntaje del segundo participante.
     * @return puntaje del segundo participante.
     */
    public int getPuntaje2() {
        return puntaje2;
    }

    /**
     * Modifica el puntaje del segundo participante.
     * @param puntaje2 nuevo puntaje del segundo participante.
     */
    public void setPuntaje2(int puntaje2) {
        this.puntaje2 = puntaje2;
    }

    /**
     * Obtiene el ganador de la partida.
     * @return participante ganador, o null si hubo empate.
     */
    public Participante getGanador() {
        return ganador;
    }

    /**
     * Obtiene el estado actual de la partida.
     * @return estado de la partida.
     */
    public EstadoPartida getEstado() {
        return estado;
    }
}
