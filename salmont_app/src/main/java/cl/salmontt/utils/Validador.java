package cl.salmontt.utils;

/**
 * Clase genérica Validador<T>.
 * Permite aplicar una cadena de validaciones sobre un valor de tipo T.
 * Se utiliza para validar distintos tipos de datos como String, Number, etc.,
 * de forma fluida y reutilizable.
 */
public class Validador<T> {

    // Valor a validar
    private final T valor;

    // Estado de validez acumulado (true si todas las validaciones son exitosas)
    private boolean valido = true;

    /**
     * Constructor que recibe el valor a validar.
     * 
     * @param valor Valor genérico sobre el cual se aplicarán las validaciones
     */
    public Validador(T valor) {
        this.valor = valor;
    }

    /**
     * Valida que el valor no sea nulo.
     * 
     * @return instancia actual para encadenamiento
     */
    public Validador<T> isNotNull() {
        if (valor == null)
            valido = false;
        return this;
    }

    /**
     * Valida que el valor no sea una cadena vacía o solo espacios.
     * Solo aplica si el valor es de tipo String.
     * 
     * @return instancia actual para encadenamiento
     */
    public Validador<T> isNotEmpty() {
        if (valor instanceof String && ((String) valor).trim().isEmpty())
            valido = false;
        return this;
    }

    /**
     * Valida que la longitud de la cadena no exceda el máximo permitido.
     * Solo aplica si el valor es de tipo String.
     * 
     * @param max longitud máxima permitida
     * @return instancia actual para encadenamiento
     */
    public Validador<T> maxLength(int max) {
        if (valor instanceof String && ((String) valor).length() > max)
            valido = false;
        return this;
    }

    /**
     * Valida que la cadena cumpla con una expresión regular.
     * Solo aplica si el valor es de tipo String.
     * 
     * @param regex expresión regular a evaluar
     * @return instancia actual para encadenamiento
     */
    public Validador<T> matches(String regex) {
        if (valor instanceof String && !((String) valor).matches(regex))
            valido = false;
        return this;
    }

    /**
     * Valida que el valor numérico sea mayor que el mínimo especificado.
     * Solo aplica si el valor es de tipo Number.
     * 
     * @param min valor mínimo permitido
     * @return instancia actual para encadenamiento
     */
    public Validador<T> esMayor(double min) {
        if (valor instanceof Number && ((Number) valor).doubleValue() <= min)
            valido = false;
        return this;
    }

    /**
     * Retorna el estado final de validez del valor tras aplicar todas las
     * validaciones.
     * 
     * @return true si todas las validaciones fueron exitosas, false si alguna falló
     */
    public boolean isValido() {
        return valido;
    }
}