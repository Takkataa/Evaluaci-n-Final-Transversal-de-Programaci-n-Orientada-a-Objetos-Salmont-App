package cl.salmontt.model;

import cl.salmontt.utils.Validador;

/**
 * Clase Direccion. Tiene una relacion de composicion con Persona
 * Representa una dirección física compuesta por calle con numero, comuna y
 * región.
 * Incluye validaciones básicas para asegurar que los datos ingresados sean
 * válidos.
 */
public class Direccion {

    // Atributos que componen la dirección
    private String calle;
    private String comuna;
    private String region;

    /**
     * Constructor de la clase Direccion.
     * 
     * @param calle  Nombre de la calle
     * @param comuna Comuna correspondiente
     * @param ciudad Región o ciudad (se guarda como 'region')
     */
    public Direccion(String calle, String comuna, String ciudad) {
        this.calle = calle;
        this.comuna = comuna;
        this.region = ciudad; // Se almacena en el atributo 'region'
    }

    /**
     * Método que valida si los campos de la dirección son válidos.
     * Aplica reglas de no nulidad, no vacío y longitud máxima.
     * 
     * @return true si todos los campos son válidos, false en caso contrario
     */
    public boolean esValida() {
        return new Validador<>(calle)
                .isNotNull()
                .isNotEmpty()
                .maxLength(100)
                .isValido()
                && new Validador<>(comuna)
                        .isNotNull()
                        .isNotEmpty()
                        .maxLength(50)
                        .isValido()
                && new Validador<>(region)
                        .isNotNull()
                        .isNotEmpty()
                        .maxLength(50)
                        .isValido();
    }

    /**
     * Representación textual de la dirección.
     * 
     * @return dirección en formato "calle, comuna, región"
     */
    @Override
    public String toString() {
        return calle + ", " + comuna + ", " + region;
    }

    // Métodos getter para acceder a los atributos

    public String getCalle() {
        return calle;
    }

    public String getComuna() {
        return comuna;
    }

    public String getRegion() {
        return region;
    }

    // Métodos setter para modificar los atributos

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
