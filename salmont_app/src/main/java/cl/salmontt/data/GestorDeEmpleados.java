package cl.salmontt.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import cl.salmontt.model.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

/**
 * Clase GestorDeEmpleados.
 * 
 * Esta clase se encarga de administrar un conjunto de empleados en memoria.
 * Su funcionalidad principal es cargar datos desde un archivo CSV,
 * transformarlos
 * en objetos de tipo {@link Persona} y almacenarlos en una lista interna.
 * 
 * Además, provee métodos para agregar nuevos empleados manualmente y para
 * mostrar
 * en consola todos los empleados cargados. El objetivo es centralizar la lógica
 * de lectura, validación y gestión de empleados, facilitando su uso en
 * aplicaciones
 * que requieran manipulación de datos de personal.
 */
public class GestorDeEmpleados {

    // Lista interna donde se almacenan los empleados cargados
    private List<Persona> personas = new ArrayList<>();

    // Método que lee un archivo CSV y transforma cada fila en un objeto Empleado
    public void loadFromCsv(String path) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
                .withSkipLines(1) // Se omite la primera línea porque corresponde al encabezado
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(',').build())
                .build()) {

            String[] fila;
            while ((fila = reader.readNext()) != null) {
                if (fila.length < 7)
                    continue;

                String rutStr = fila[0].trim();
                String nombre = fila[1].trim();
                String apellido = fila[2].trim();
                String correo = fila[3].trim();
                String sueldoStr = fila[4].trim();
                String cargo = fila[5].trim();
                String direccionCompleta = fila[6].trim();

                double sueldo;
                try {
                    sueldo = Double.parseDouble(sueldoStr);
                } catch (NumberFormatException nfe) {
                    continue;
                }

                if (direccionCompleta.startsWith("\"") && direccionCompleta.endsWith("\"")) {
                    direccionCompleta = direccionCompleta.substring(1, direccionCompleta.length() - 1);
                }

                String[] direccionStr = direccionCompleta.split(",");
                if (direccionStr.length < 3)
                    continue;

                Direccion direccion = new Direccion(
                        direccionStr[0].trim(),
                        direccionStr[1].trim(),
                        direccionStr[2].trim());

                Rut rut = new Rut(rutStr);
                Empleado empleado = new Empleado(nombre, apellido, rut, correo, direccion, sueldo, cargo);
                agregarPersona(empleado);
            }

        } catch (Exception e) {
            System.out.println("Error al cargar los empleados: " + e.getMessage());
        }
    }

    // Método que añade un empleado a la lista
    public void agregarPersona(Persona persona) {
        personas.add(persona);
    }

    public List<Persona> getPersonas() {
        return personas;
    }

}
