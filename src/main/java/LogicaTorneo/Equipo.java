package LogicaTorneo;


import java.util.ArrayList;
import java.util.List;

public class Equipo extends Participante {
    private List<Jugador> jugadores;
    private int limiteMaximo;

    public Equipo(String nombre, String contacto, int limiteMaximo) {
        super(nombre, contacto);
        this.jugadores = new ArrayList<>();
        this.limiteMaximo= limiteMaximo;
    }

    @Override
    public boolean aceptaIntegrantes() {
        return true;
    }

    @Override
    public void agregarIntegrante(Participante p) {
        if(jugadores.size() >= limiteMaximo){
            throw new IllegalArgumentException("Supera cantidad max jugadores aceptados");
        }
        this.jugadores.add((Jugador) p);

    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public boolean estaListoParaJugar(){
        return !jugadores.isEmpty();
    };

    @Override
    public void verDetalles() {
        System.out.println("Equipo: " + getNombre() + " | Contacto: " + getContacto());
        System.out.println("Roster de jugadores:");
        for (Jugador j : jugadores) {
            System.out.println(" - " + j.getNombre());
        }
    }
}
