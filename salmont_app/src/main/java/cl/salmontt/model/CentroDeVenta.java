package cl.salmontt.model;

/**
 * Clase que representa un centro de venta, hereda de la clase UnidadOperativa
 *
 */

public class CentroDeVenta extends UnidadOperativa {

    private double toneladasDisponiblesVenta;
    private double toneladasVendidas;

    public CentroDeVenta(int tipo, String nombre, String comuna, double toneladasDisponiblesVenta,
            double toneladasVendidas,
            Empleado Gerente) {
        super(tipo, nombre, comuna, Gerente);
        this.toneladasDisponiblesVenta = toneladasDisponiblesVenta;
        this.toneladasVendidas = toneladasVendidas;
    }

    public double getToneladasDisponiblesVenta() {
        return toneladasDisponiblesVenta;
    }

    public void setToneladasDisponiblesVenta(double toneladasDisponiblesVenta) {
        this.toneladasDisponiblesVenta = toneladasDisponiblesVenta;
    }

    public double getToneladasVendidas() {
        return toneladasVendidas;
    }

    public void setToneladasVendidas(double toneladasVendidas) {
        this.toneladasVendidas = toneladasVendidas;
    }

    @Override
    public String toString() {
        return "CentroDeVenta [toneladasDisponiblesVenta=" + toneladasDisponiblesVenta + ", toneladasVendidas="
                + toneladasVendidas + ", gerente=" + getGerente().getNombre() + " " + getGerente().getApellido() + "]";
    }

    @Override
    public String mostrarResumen() {
        return "Tipo: Planta de proceso\n"
                + "Nombre: " + getNombre() + "\n"
                + "Comuna: " + getComuna() + "\n"
                + "Toneladas disponibles: " + toneladasDisponiblesVenta + "\n"
                + "Toneladas vendidas: " + toneladasVendidas + "\n"
                + "Gerente: " + getGerente().getNombre() + " " + getGerente().getApellido() + "\n";
    }

}
