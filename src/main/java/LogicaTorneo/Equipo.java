package LogicaTorneo;


import LogicaTorneo.Excepciones.DuplicadoException;
import LogicaTorneo.Excepciones.EquipoLlenoException;

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
            throw new EquipoLlenoException(limiteMaximo);
        }

        for(Jugador jugador : jugadores ){
            if (jugador.getNombre().equalsIgnoreCase(p.getNombre())){
                throw new DuplicadoException(jugador.getNombre());
            }
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
            System.out.print("*");
            j.verDetalles();
        }
    }
}
