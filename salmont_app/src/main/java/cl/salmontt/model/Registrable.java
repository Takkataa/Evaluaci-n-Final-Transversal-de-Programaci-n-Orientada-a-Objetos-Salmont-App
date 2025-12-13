package cl.salmontt.model;

/**
 * Interfaz que define el comportamiento de un objeto que puede ser registrado
 * Implementa la interface Registrable y la hereda a sus hijos (Persona,
 * UnidadOperativa)
 */

public interface Registrable {

    public String mostrarResumen();

}
