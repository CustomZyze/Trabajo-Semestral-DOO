import java.util.ArrayList;
import java.util.List;

public class Equipo extends Participante {
    private List<Jugador> jugadores;

    public Equipo(String nombre, String contacto) {
        super(nombre, contacto);
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        this.jugadores.add(jugador);
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    @Override
    public void verDetalles() {
        System.out.println("Equipo: " + getNombre() + " | Contacto: " + getContacto());
        System.out.println("Roster de jugadores:");
        for (Jugador j : jugadores) {
            System.out.println(" - " + j.getNombre());
        }
    }
}
