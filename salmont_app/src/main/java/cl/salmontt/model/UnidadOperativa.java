package cl.salmontt.model;

/**
 * Clase abstracta que representa una unidad operativa
 * Implementa la interface Registrable y la hereda a sus hijos
 * posee la informacion necesaria para identificar a la unidad operativa y su
 * encargado (Gerente)
 */

public abstract class UnidadOperativa implements Registrable {

    public static final int PLANTA_PROCESO = 1;
    public static final int CENTRO_CULTIVO = 2;
    public static final int CENTRO_VENTA = 3;

    private int TipoUnidadOperativa;
    private String nombre;
    private String comuna;
    private Empleado Gerente;

    protected UnidadOperativa(int TipoUnidadOperativa, String nombre, String comuna, Empleado Gerente) {
        this.TipoUnidadOperativa = TipoUnidadOperativa;
        this.nombre = nombre;
        this.comuna = comuna;
        this.Gerente = Gerente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public int getTipoUnidadOperativa() {
        return TipoUnidadOperativa;
    }

    public Empleado getGerente() {
        return Gerente;
    }

    public void setGerente(Empleado Gerente) {
        this.Gerente = Gerente;
    }

    @Override
    public String toString() {
        return "UnidadOperativa [nombre=" + nombre + ", comuna=" + comuna + "]";
    }

    @Override
    public String mostrarResumen() {
        return "Registrando unidad operativa: " + this;
    }
}
