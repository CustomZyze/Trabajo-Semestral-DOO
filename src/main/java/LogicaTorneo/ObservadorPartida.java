package LogicaTorneo;

/**
 * Interfaz que representa a un observador de partidas.
 * Forma parte del patrón Observer. Cualquier clase que implemente esta interfaz
 * podrá ser notificada cuando ocurra un evento relacionado con una partida.
 */
public interface ObservadorPartida {

    /**
     * Metodo que se ejecuta cuando ocurre un cambio o evento en una partida.
     * @param partida partida que generó la notificación.
     */
    void actualizar(Partida partida);
}
