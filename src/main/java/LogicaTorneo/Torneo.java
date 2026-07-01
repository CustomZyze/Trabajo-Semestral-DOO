package LogicaTorneo;

import java.util.ArrayList;
import java.util.List;

public class Torneo implements ObservadorPartida {
    private String nombre;
    private Disciplina disciplina;
    private List<Participante> inscritos;
    private List<Partida> llaves;
    private Formato formato;

    public Torneo(String nombre, Disciplina disciplina, Formato formato) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.formato = formato;
        this.inscritos = new ArrayList<>();
        this.llaves = new ArrayList<>();
    }

    public void inscribir(Participante p) {
        this.inscritos.add(p);
        System.out.println(p.getNombre() + " se ha inscrito en " + this.nombre);
    }

    public void generarLlaves() {
        llaves = formato.generarCalendario(this.inscritos);
        for (Partida partida : llaves) {
            partida.agregarObservador(this);
        }
        System.out.println("\nLLaves generadas para el torneo: " + this.nombre);
    }

    public void mostrarLlaves() {
        System.out.println("--- Lista de Partidas ---");
        for (Partida p : llaves) {
            System.out.println(p.getP1().getNombre() + " vs " + p.getP2().getNombre() + " | Estado: " + p.getEstado());
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public List<Participante> getInscritos() { return inscritos; }
    public List<Partida> getLlaves() { return llaves; }

    public Formato getFormato() { return formato; }
    public void setFormato(Formato formato) { this.formato = formato; }

    @Override
    public void actualizar(Partida partida){
        System.out.println("\nFinalizo el encuentro entre :");
        System.out.println(partida.getP1().getNombre() + " vs " + partida.getP2().getNombre());

        if (partida.getGanador() != null) {
            System.out.println("Clasifica: " + partida.getGanador().getNombre());
        }

        if (partidasCompletas()) {
            continuarTorneo();
        } else {
            System.out.println("Aun faltan partidas por terminar en esta ronda\n");
        }

    }

    private boolean partidasCompletas(){
        for (Partida partida: llaves){
            if (partida.getEstado() != EstadoPartida.TERMINADA){
                return false;
            }
        }
        return true;
    }

    private void continuarTorneo(){
        List<Partida> siguienteRonda= formato.avanzarRonda(llaves);
        if(!siguienteRonda.isEmpty() ){
            llaves = siguienteRonda;
            for (Partida partida : llaves){
                partida.agregarObservador(this);
            }
            System.out.println("\n--- Siguiente ronda ---");
            mostrarLlaves();
        }else{
            Participante campeon = llaves.get(0).getGanador();
            if( campeon != null){
                System.out.println("\n--- LogicaTorneo.Torneo finalizado ---");
                System.out.println("CAMPEON: " + campeon.getNombre());
            }

        }
    }
}
