package cl.salmontt.utils;

/**
 * Clase utilitaria para validar entradas del usuario.
 */
public class ValidadorInputs {

    // --- Métodos de VALIDACIÓN que aceptan String/double y devuelven un String
    // (mensaje de error) o null (valido) ---

    public static String validarRut(String rut) {
        if (!new Validador<>(rut)
                .isNotNull()
                .isNotEmpty()
                .matches("^\\d{7,8}-[\\dkK]$")
                .isValido()) {
            return "RUT inválido (formato 12345678-9).";
        }
        return null; // Válido
    }

    public static String validarSueldo(String sueldoStr) {
        try {
            double sueldo = Double.parseDouble(sueldoStr);
            if (!new Validador<>(sueldo).esMayor(0).isValido()) {
                return "El sueldo debe ser un número mayor que 0.";
            }
            return null; // Válido
        } catch (NumberFormatException e) {
            return "Sueldo inválido. Debe ser un número.";
        }
    }

    public static String validarFormatoNumerico(String numeroStr) {
        try {
            int numero = Integer.parseInt(numeroStr);
            if (!new Validador<>(numero).esMayor(0).isValido()) {
                return "El numero debe ser un número mayor que 0.";
            }
            return null; // Válido
        } catch (NumberFormatException e) {
            return "Sueldo inválido. Debe ser un número.";
        }
    }

    // Expresión regular:
    // ^\+569 -> Debe empezar por +569
    // \s? -> Espacio opcional
    // \d{8} -> Exactamente 8 dígitos numéricos
    public static String validarTelefono(String numeroStr) {
        if (numeroStr == null) {
            return "El número no puede estar vacío.";
        }

        String regex = "^\\+569\\s?\\d{8}$";

        if (numeroStr.matches(regex)) {
            return null; // Es válido
        } else {
            return "Formato inválido. Debe ser +569 seguido de 8 dígitos (Ej: +569 1234 5678).";
        }
    }

    public static String validarCorreo(String correo) {
        if (!new Validador<>(correo)
                .isNotNull()
                .isNotEmpty()
                .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
                .isValido()) {
            return "Correo inválido.";
        }
        return null;
    }

    /**
     * Valida un texto que puede contener letras, números y espacios (ej:
     * direcciones).
     * 
     * @return mensaje de error o null si es válido
     */
    public static String validarTextoConNumeros(String texto, String campo) {
        // Expresión regular que permite letras, números, espacios y caracteres básicos
        // de puntuación (.,-)
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s.,-]*$";

        if (!new Validador<>(texto)
                .isNotNull()
                .isNotEmpty()
                .maxLength(50)
                .matches(regex)
                .isValido()) {
            return campo + " inválido. Solo se permiten letras, números, espacios y caracteres básicos.";
        }
        return null;
    }

    /**
     * Valida nombres/apellidos compuestos.
     * Permite letras, espacios, guiones y apóstrofes.
     * 
     * @return mensaje de error o null si es válido
     */
    public static String validarNombreCompuesto(String nombre) {
        // Expresión regular mejorada para nombres compuestos (acepta tildes, guiones,
        // apóstrofes)
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ'\\-\\s]+$";

        if (!new Validador<>(nombre)
                .isNotNull()
                .isNotEmpty()
                .maxLength(50)
                .matches(regex)
                .isValido()) {
            return "Nombre/Apellido inválido. Use solo letras, espacios, guiones o apóstrofes.";
        }
        return null;
    }
}
