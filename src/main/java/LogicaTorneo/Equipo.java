package LogicaTorneo;


import LogicaTorneo.Excepciones.DuplicadoException;
import LogicaTorneo.Excepciones.EquipoLlenoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo dentro del sistema de torneos.
 * Un equipo es un tipo de Participante que puede contener jugadores.
 * Posee un límite máximo de integrantes, el cual depende de la disciplina.
 */
public class Equipo extends Participante {

    /**
     * Lista de jugadores que forman parte del equipo.
     */
    private List<Jugador> jugadores;

    /**
     * Cantidad máxima de jugadores permitidos en el equipo.
     */
    private int limiteMaximo;

    /**
     * Constructor de la clase Equipo.
     * @param nombre nombre del equipo.
     * @param contacto información de contacto del equipo.
     * @param limiteMaximo cantidad máxima de jugadores permitidos.
     */
    public Equipo(String nombre, String contacto, int limiteMaximo) {
        super(nombre, contacto);
        this.jugadores = new ArrayList<>();
        this.limiteMaximo= limiteMaximo;
    }

    /**
     * Indica si el equipo acepta integrantes.
     * @return true, ya que el equipo acepta integrantes.
     */
    @Override
    public boolean aceptaIntegrantes() {
        return true;
    }

    /**
     * Agrega un integrante al equipo.Primero verifica que
     * el equipo no esté lleno. Luego revisa que no exista
     * otro jugador con el mismo nombre dentro del equipo.
     * Si pasa ambas validaciones, agrega el jugador a la lista.
     * @param p participante que se desea agregar como integrante.
     * @throws EquipoLlenoException si el equipo ya alcanzó su límite máximo.
     * @throws DuplicadoException si ya existe un jugador con el mismo nombre.
     */
    @Override
    public void agregarIntegrante(Participante p) {

        if(jugadores.size() >= limiteMaximo){
            throw new EquipoLlenoException(limiteMaximo);
        }

        for(Jugador jugador : jugadores ){
            if (jugador.getNombre().equalsIgnoreCase(p.getNombre())){
                throw new DuplicadoException(jugador.getNombre());
            }
        }

        this.jugadores.add((Jugador) p);

    }

    /**
     * Obtiene la lista de jugadores del equipo.
     * @return lista de jugadores inscritos en el equipo.
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Indica si el equipo está listo para jugar.
     * @return true si el equipo tiene jugadores, false si está vacío.
     */
    @Override
    public boolean estaListoParaJugar(){
        return !jugadores.isEmpty();
    };

    /**
     * Muestra por consola los detalles del equipo.
     * Imprime el nombre, contacto y el listado de jugadores registrados.
     */
    @Override
    public void verDetalles() {
        System.out.println("Equipo: " + getNombre() + " | Contacto: " + getContacto());
        System.out.println("Roster de jugadores:");
        for (Jugador j : jugadores) {
            System.out.print("*");
            j.verDetalles();
        }
    }
}
