package LogicaTorneo;

import LogicaTorneo.Excepciones.NoInfoException;

public abstract class Participante {
    private String nombre;
    private String contacto;

    public Participante(String nombre, String contacto) {

        if(nombre == null || nombre.isBlank()){
            throw new NoInfoException("Nombre no detectado");
        }

        if(contacto == null || contacto.isBlank()){
            throw new NoInfoException("Contacto no encontrado");
        }

        this.nombre = nombre;
        this.contacto = contacto;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public abstract boolean estaListoParaJugar();
    public abstract void verDetalles();
    public boolean aceptaIntegrantes() {
        return false;
    }
    public void agregarIntegrante(Participante p) {
        throw new UnsupportedOperationException("Este participante no admite integrantes.");
    }
}
