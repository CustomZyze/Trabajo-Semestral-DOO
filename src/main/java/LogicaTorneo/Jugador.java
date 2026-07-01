package LogicaTorneo;

public class Jugador extends Participante {
    private String rut;

    public Jugador(String nombre, String contacto, String rut) {
        super(nombre, contacto);
        this.rut = rut;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    @Override
    public void verDetalles() {
        System.out.println("LogicaTorneo.Jugador: " + getNombre() + " (RUT: " + rut + ")");
    }
}
