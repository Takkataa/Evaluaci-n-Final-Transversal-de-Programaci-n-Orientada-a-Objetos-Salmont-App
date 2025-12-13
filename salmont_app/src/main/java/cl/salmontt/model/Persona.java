package cl.salmontt.model;

/**
 * Clase abstracta que representa una persona
 * Implementa la interface Registrable y la hereda a sus hijos
 * posee la informacion necesaria para identificar a la persona
 */

public abstract class Persona implements Registrable {

    private String nombre;
    private String apellido;
    private Rut rut;
    private String correo;
    private Direccion direccion;

    public Persona(String nombre, String apellido, Rut rut, String correo, Direccion direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Rut getRut() {
        return rut;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", direccion="
                + direccion + "]";
    }

    @Override
    public String mostrarResumen() {
        return "Registrando persona: " + this;
    }

}
