package cl.salmontt.model;

/**
 * Clase que representa un empleado, hereda de la clase Persona
 * posee la informacion nececaria para identificar al empleado,
 * su sueldo y cargo *
 */

public class Empleado extends Persona {

    private double sueldo;
    private String cargo;

    public Empleado(String nombre, String apellido, Rut rut, String correo, Direccion direccion, double sueldo,
            String cargo) {
        super(nombre, apellido, rut, correo, direccion);
        this.sueldo = sueldo;
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + getNombre() + ", apellido=" + getApellido() + ", correo=" + getCorreo()
                + ", direccion="
                + getDireccion() + ", sueldo=" + getSueldo() + ", cargo=" + getCargo() + "]";
    }

    @Override
    public String mostrarResumen() {
        return "Nombre: " + getNombre() + " " + getApellido() + "\n"
                + "Rut: " + getRut() + "\n"
                + "Correo: " + getCorreo() + "\n"
                + "Direccion: " + getDireccion() + "\n"
                + "Sueldo: " + getSueldo() + "\n"
                + "Cargo: " + getCargo() + "\n";
    }

}
