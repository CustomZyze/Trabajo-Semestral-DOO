package LogicaTorneo;

import LogicaTorneo.Excepciones.NoInfoException;


/**
 * Clase abstracta que representa a un participante dentro de un torneo.
 * Un participante puede ser un jugador individual o un equipo. Esta clase define
 * los atributos comunes que todos los participantes deben tener, como nombre y
 * contacto.
 */
public abstract class Participante {

    /**
     * Nombre del participante.
     */
    private String nombre;

    /**
     * Información de contacto del participante.
     */
    private String contacto;

    /**
     * Constructor de la clase Participante.
     * Valida que el nombre y el contacto no sean nulos ni estén vacíos antes de
     * crear el participante.
     * @param nombre nombre del participante.
     * @param contacto información de contacto del participante.
     * @throws NoInfoException si el nombre o contacto son nulos o están vacíos.
     */
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

    /**
     * Obtiene el nombre del participante.
     * @return nombre del participante.
     */
    public String getNombre() { return nombre; }

    /**
     * Modifica el nombre del participante.
     * @param nombre nuevo nombre del participante.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }


    /**
     * Obtiene la información de contacto del participante.
     * @return contacto del participante.
     */
    public String getContacto() { return contacto; }

    /**
     * Modifica la información de contacto del participante.
     * @param contacto nuevo contacto del participante.
     */
    public void setContacto(String contacto) { this.contacto = contacto; }

    /**
     * Indica si el participante está listo para jugar.
     * @return true si está listo para jugar, false en caso contrario.
     */
    public abstract boolean estaListoParaJugar();

    /**
     * Muestra los detalles del participante.
     * Cada clase hija define cómo mostrar su información.
     */
    public abstract void verDetalles();

    /**
     * Indica si el participante acepta integrantes.
     * @return false por defecto.
     */
    public boolean aceptaIntegrantes() {
        return false;
    }

    /**
     * Agrega un integrante al participante.
     * La clase Equipo sobrescribe este metodo para permitir agregar jugadores.
     * @param p participante que se desea agregar como integrante.
     * @throws UnsupportedOperationException si el participante no admite integrantes.
     */
    public void agregarIntegrante(Participante p) {
        throw new UnsupportedOperationException("Este participante no admite integrantes.");
    }
}
