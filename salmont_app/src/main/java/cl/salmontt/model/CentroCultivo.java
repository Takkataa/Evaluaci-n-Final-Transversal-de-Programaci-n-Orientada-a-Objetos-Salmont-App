package cl.salmontt.model;

/**
 * clase que representa un centro de cultivo, hereda de la clase UnidadOperativa
 *
 */

public class CentroCultivo extends UnidadOperativa {

    private double capacidadDelProcesoToneladas;
    private int tiempoDeCultivoDias;

    // Constuctor de la clase
    public CentroCultivo(int tipo, String nombre, String comuna, double capacidadDelProcesoToneladas,
            int tiempoDeCultivoDias, Empleado Gerente) {
        super(tipo, nombre, comuna, Gerente);
        this.capacidadDelProcesoToneladas = capacidadDelProcesoToneladas;
        this.tiempoDeCultivoDias = tiempoDeCultivoDias;
    }

    public double getCapacidadDelProcesoToneladas() {
        return capacidadDelProcesoToneladas;
    }

    public void setCapacidadDelProcesoToneladas(double capacidadDelProcesoToneladas) {
        this.capacidadDelProcesoToneladas = capacidadDelProcesoToneladas;
    }

    public int getTiempoDeCultivoDias() {
        return tiempoDeCultivoDias;
    }

    public void setTiempoDeCultivoDias(int tiempoDeCultivoDias) {
        this.tiempoDeCultivoDias = tiempoDeCultivoDias;
    }

    @Override
    public String mostrarResumen() {
        return "Tipo: Centro de cultivo\n"
                + "Nombre: " + getNombre() + "\n"
                + "Comuna: " + getComuna() + "\n"
                + "Capacidad del proceso en toneladas: " + capacidadDelProcesoToneladas + "\n"
                + "Tiempo de cultivo en dias: " + tiempoDeCultivoDias + "\n"
                + "Gerente: " + getGerente().getNombre() + " " + getGerente().getApellido() + "\n";
    }

    @Override
    public String toString() {
        return "CentroCultivo [nombre=" + getNombre() + ", comuna=" + getComuna() + ", capacidadDelProcesoToneladas="
                + capacidadDelProcesoToneladas + ", tiempoDeCultivoDias=" + tiempoDeCultivoDias + ", gerente="
                + getGerente().getNombre() + " " + getGerente().getApellido() + "]";
    }

}
