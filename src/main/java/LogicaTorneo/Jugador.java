package LogicaTorneo;

import LogicaTorneo.Excepciones.NoInfoException;

/**
 * Representa a un jugador individual dentro del sistema de torneos.
 * Un jugador es un tipo de Participante, por lo que hereda atributos como
 * nombre , contacto y RUT, lo que permite identificarlo.
 */
public class Jugador extends Participante {

    /**
     * RUT del jugador.
     */
    private String rut;


    /**
     * Crea un jugador con nombre, contacto y RUT. Si el RUT viene vacío o nulo,
     * se lanza una excepción para evitar registrar jugadores sin identificación.
     * @param nombre nombre del jugador.
     * @param contacto información de contacto del jugador.
     * @param rut RUT del jugador.
     * @throws NoInfoException si el RUT es nulo o está vacío.
     */
    public Jugador(String nombre, String contacto, String rut) {
        super(nombre, contacto);

        if(rut == null || rut.isBlank()){
            throw new NoInfoException("Rut no encontrado");
        }

        this.rut = rut;
    }

    /**
     * Obtiene el RUT del jugador.
     * @return RUT del jugador.
     */
    public String getRut() { return rut; }

    /**
     * Modifica el RUT del jugador.
     * @param rut nuevo RUT del jugador.
     */
    public void setRut(String rut) { this.rut = rut; }

    /**
     * Indica si el jugador está listo para participar en una partida.
     * @return true, porque el jugador está listo para jugar.
     */
    @Override
    public boolean estaListoParaJugar(){
        return true;
    };

    /**
     * Muestra por consola los detalles del jugador.
     * Imprime el nombre, RUT y contacto del jugador.
     */
    @Override
    public void verDetalles() {
        System.out.println("Jugador: " + getNombre() + " \nRUT: " + getRut() + "\nContacto: " + getContacto());
    }
}
