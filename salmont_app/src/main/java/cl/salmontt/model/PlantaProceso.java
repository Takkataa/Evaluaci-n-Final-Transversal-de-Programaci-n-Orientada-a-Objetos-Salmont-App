package cl.salmontt.model;

/**
 * Clase que representa una planta de proceso, hereda de la clase
 * UnidadOperativa y posee la informacion necesaria para identificar a la planta
 * de proceso
 *
 */

public class PlantaProceso extends UnidadOperativa {

    private double toneladasDeProduccion;
    private int tiempoDeProduccionDias;

    public PlantaProceso(int tipo, String nombre, String comuna, double toneladasDeProduccion,
            int tiempoDeProduccionDias, Empleado Gerente) {
        super(tipo, nombre, comuna, Gerente);
        this.toneladasDeProduccion = toneladasDeProduccion;
        this.tiempoDeProduccionDias = tiempoDeProduccionDias;
    }

    public double getToneladasDeProduccion() {
        return toneladasDeProduccion;
    }

    public void setToneladasDeProduccion(double toneladasDeProduccion) {
        this.toneladasDeProduccion = toneladasDeProduccion;
    }

    public int getTiempoDeProduccionDias() {
        return tiempoDeProduccionDias;
    }

    public void setTiempoDeProduccionDias(int tiempoDeProduccionDias) {
        this.tiempoDeProduccionDias = tiempoDeProduccionDias;
    }

    @Override
    public String mostrarResumen() {
        return "Tipo: Planta de proceso\n"
                + "Nombre: " + getNombre() + "\n"
                + "Comuna: " + getComuna() + "\n"
                + "Toneladas de produccion: " + toneladasDeProduccion + "\n"
                + "Tiempo de produccion en dias: " + tiempoDeProduccionDias + "\n"
                + "Gerente: " + getGerente().getNombre() + " " + getGerente().getApellido() + "\n";
    }

    @Override
    public String toString() {
        return "PlantaProceso [nombre=" + getNombre() + ", comuna=" + getComuna() + ", toneladasDeProduccion="
                + toneladasDeProduccion + ", tiempoDeProduccionDias=" + tiempoDeProduccionDias + ", gerente="
                + getGerente().getNombre() + " " + getGerente().getApellido() + "]";
    }

}
