package LogicaTorneo;

public abstract class Participante {
    private String nombre;
    private String contacto;

    public Participante(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public abstract void verDetalles();
}
