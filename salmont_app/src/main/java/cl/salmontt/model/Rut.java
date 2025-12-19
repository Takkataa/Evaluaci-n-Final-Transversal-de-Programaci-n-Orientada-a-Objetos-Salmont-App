package cl.salmontt.model;

import cl.salmontt.utils.Validador;

/**
 * Clase Rut. tiene una relacion de composicion con persona.
 * Representa el Rol Único Tributario chileno, utilizado como identificador
 * personal.
 * Incluye validación básica de formato mediante expresión regular.
 */
public class Rut {

    // Almacena el número de RUT en formato texto (ej: "12345678-9")
    private String numero;

    /**
     * Constructor que inicializa el RUT con el valor entregado.
     * 
     * @param numero RUT en formato "XXXXXXXX-Y"
     */
    public Rut(String numero) {
        this.numero = numero;
    }

    /**
     * Valida el formato del RUT.
     * No verifica el dígito verificador, solo el patrón general.
     * 
     * @return true si el formato es válido, false si no lo es
     */
    public boolean esValido() {
        return new Validador<>(numero)
                .isNotNull() // No debe ser nulo
                .isNotEmpty() // No debe estar vacío
                .matches("^\\d{7,8}-[\\dkK]$") // Debe tener 7 u 8 dígitos seguidos de guion y dígito verificador
                .isValido();
    }

    /**
     * Representación textual del RUT.
     * 
     * @return RUT como cadena
     */
    @Override
    public String toString() {
        return numero;
    }

    // Métodos getter y setter para acceder y modificar el RUT

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}