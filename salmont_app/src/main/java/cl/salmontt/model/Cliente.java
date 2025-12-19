package cl.salmontt.model;

public class Cliente extends Persona {

    private String numerodeContacto;

    public Cliente(String nombre, String apellido, Rut rut, String correo, Direccion direccion,
            String numerodeContacto) {
        super(nombre, apellido, rut, correo, direccion);
        this.numerodeContacto = numerodeContacto;
    }

    public String getNumerodeContacto() {
        return numerodeContacto;
    }

    public void setNumerodeContacto(String numerodeContacto) {
        this.numerodeContacto = numerodeContacto;
    }

    @Override
    public String toString() {
        return "Cliente [nombre=" + getNombre() + ", apellido=" + getApellido() + ", correo=" + getCorreo()
                + ", direccion="
                + getDireccion() + ", numerodeContacto=" + getNumerodeContacto() + "]";
    }

    @Override
    public String mostrarResumen() {
        return "Nombre: " + getNombre() + " " + getApellido() + "\n"
                + "Rut: " + getRut() + "\n"
                + "Correo: " + getCorreo() + "\n"
                + "Direccion: " + getDireccion() + "\n"
                + "NumerodeContacto: " + getNumerodeContacto() + "\n";
    }

}
