package LogicaTorneo;

import LogicaTorneo.Excepciones.NoInfoException;

public class Jugador extends Participante {
    private String rut;

    public Jugador(String nombre, String contacto, String rut) {
        super(nombre, contacto);

        if(rut == null || rut.isBlank()){
            throw new NoInfoException("Rut no encontrado");
        }

        this.rut = rut;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    @Override
    public boolean estaListoParaJugar(){
        return true;
    };

    @Override
    public void verDetalles() {
        System.out.println("Jugador: " + getNombre() + " (RUT: " + rut + ")");
    }
}
