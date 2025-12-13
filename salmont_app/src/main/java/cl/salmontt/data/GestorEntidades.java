package cl.salmontt.data;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import cl.salmontt.model.*;

/**
 * Clase encargada de gestionar las unidades operativas de Salmontt, tanto de
 * agregarlas como de buscarlas
 * ademas de mostrarlas de poder mostrarlas en consola, buscarlas por nombre,
 * buscarlas por rut,
 * buscar las ultimas 5 unidades operativas, buscar las ultimas 5 unidades
 * operativas de cada tipo,
 * buscar el gerente de produccion, buscar el gerente de cultivos, buscar el
 * gerente de ventas.
 * Se relaciona directamente con la interface de usuario y el App Main del
 * programa
 */

public class GestorEntidades {
    // Lista de entidades operativas que se cargan desde un archivo csv
    private List<Registrable> entidadesOperativas = new ArrayList<>();

    // Metodo que integra la lista de empleados con la lista de unidades operativas
    // mediante la interface Registrable
    public void integracionDeEmpleados(GestorDeEmpleados gestorDeEmpleados) {
        entidadesOperativas.addAll(gestorDeEmpleados.getPersonas());
    }

    // Metodo que carga la informacion de los empleados y las unidades operativas
    public void subirInformacion() {
        GestorDeEmpleados gestorEmpleados = new GestorDeEmpleados();
        gestorEmpleados.loadFromCsv("salmont_app\\src\\main\\java\\resources\\Empleados.csv");
        integracionDeEmpleados(gestorEmpleados);
        loadFromCsv(
                "salmont_app\\src\\main\\java\\resources\\UnidadesOperativas.csv",
                gestorEmpleados);
    }

    // Método que lee un archivo CSV y transforma cada fila en un objeto Empleado
    public void loadFromCsv(String path, GestorDeEmpleados gestorDeEmpleados) {

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
                .withSkipLines(1) // Se omite la primera línea porque corresponde al encabezado
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(',').build())
                .build()) {

            String[] fila; // Array que almacena cada fila del archivo csv
            while ((fila = reader.readNext()) != null) {
                if (fila.length < 5) // Si la fila no tiene 5 columnas, se omite
                    continue;

                int tipoInt; // Variable que almacena el tipo de unidad operativa indicada en el archivo csv
                String tipo = fila[0].trim(); // Se obtiene el tipo de unidad operativa

                try {
                    tipoInt = Integer.parseInt(tipo); // Se convierte el tipo de unidad operativa a entero
                    if (tipoInt != 1 && tipoInt != 2 && tipoInt != 3) // Si el tipo de unidad operativa no es 1, 2 o 3,
                                                                      // se omite
                        continue;
                } catch (NumberFormatException nfe) { // Si el tipo de unidad operativa no es un numero, se omite
                    continue;
                }
                Empleado gerente = null; // Variable que almacena el gerente de la unidad operativa

                for (Persona persona : gestorDeEmpleados.getPersonas()) {
                    if (persona instanceof Empleado empleado) {
                        if (empleado.getRut().getNumero().equals(fila[5].trim())) {
                            gerente = empleado; // Se asigna el gerente de la unidad operativa segun el rut indicado en
                                                // el archivo csv
                            break;
                        }
                    }
                }

                if (tipoInt == 1) {// tipo 1 es planta proceso
                    PlantaProceso plantaProceso = new PlantaProceso(
                            1,
                            fila[1].trim(), // Se obtiene el nombre de la planta proceso
                            fila[2].trim(), // Se obtiene la comuna de la planta proceso
                            Double.parseDouble(fila[3].trim()), // Se obtiene las toneladas de la planta proceso
                            Integer.parseInt(fila[4].trim()), // Se obtiene el numero de dias de la planta proceso
                            gerente);
                    agregarRegistrable(plantaProceso); // se agrega la planta proceso a la lista de entidades operativas
                } else if (tipoInt == 2) {// tipo 2 es centro cultivo
                    CentroCultivo centroCultivo = new CentroCultivo(
                            2,
                            fila[1].trim(), // Se obtiene el nombre del centro cultivo
                            fila[2].trim(), // Se obtiene la comuna del centro cultivo
                            Double.parseDouble(fila[3].trim()), // Se obtiene el numero de toneladas del centro cultivo
                            Integer.parseInt(fila[4].trim()), // Se obtiene el numero de dias del centro cultivo
                            gerente);
                    agregarRegistrable(centroCultivo); // se agrega el centro cultivo a la lista de entidades operativas
                } else if (tipoInt == 3) {// tipo 3 es centro venta
                    CentroDeVenta centroVenta = new CentroDeVenta(
                            3,
                            fila[1].trim(), // Se obtiene el nombre del centro venta
                            fila[2].trim(), // Se obtiene la comuna del centro venta
                            Double.parseDouble(fila[3].trim()), // Se obtiene el numero de toneladas del centro venta
                            Double.parseDouble(fila[4].trim()), // Se obtiene el numero de toneladas del centro venta
                            gerente);
                    agregarRegistrable(centroVenta); // se agrega el centro venta a la lista de entidades operativas
                }
            }

        } catch (Exception e) {
            System.out.println("Error al cargar los empleados: " + e.getMessage());
        }
    }

    public void agregarRegistrable(Registrable unidadOperativa) { // se agrega la unidad operativa a la lista de
                                                                  // entidades operativas
        entidadesOperativas.add(unidadOperativa);
    }

    public int contarEmpleados() { // se cuentan los empleados en la lista de entidades operativas
        int contador = 0;
        for (Registrable persona : entidadesOperativas) {
            if (persona instanceof Empleado) {
                contador++;
            }
        }
        return contador;
    }

    public int contarPlantasProceso() { // se cuentan las plantas proceso en la lista de entidades operativas
        int contador = 0;
        for (Registrable entidadOperativa : entidadesOperativas) {
            if (entidadOperativa instanceof PlantaProceso) {
                contador++;
            }
        }
        return contador;
    }

    public int contarCentrosCultivo() { // se cuentan los centros cultivo en la lista de entidades operativas
        int contador = 0;
        for (Registrable entidadOperativa : entidadesOperativas) {
            if (entidadOperativa instanceof CentroCultivo) {
                contador++;
            }
        }
        return contador;
    }

    public int contarCentrosVenta() { // se cuentan los centros venta en la lista de entidades operativas
        int contador = 0;
        for (Registrable entidadOperativa : entidadesOperativas) {
            if (entidadOperativa instanceof CentroDeVenta) {
                contador++;
            }
        }
        return contador;
    }

    public void mostrarUnidadesOperativas() { // se muestran las unidades operativas en la lista de entidades operativas
                                              // por consola
        System.out.println("Lista De Entidades Operativas:\n");
        int i = 1;
        int j = 1;
        for (Registrable entidadOperativa : entidadesOperativas) {
            if (entidadOperativa instanceof Empleado) {
                System.out.println("--------------------------------");
                System.out.println("-------- Empleado " + j + " --------\n");
                entidadOperativa.mostrarResumen();
                System.out.println(" ");
                j++;
            } else if (entidadOperativa instanceof UnidadOperativa) {
                System.out.println("--------------------------------");
                System.out.println("-------- Unidad " + i + " --------\n");
                entidadOperativa.mostrarResumen();
                System.out.println(" ");
                i++;
            }

        }
    }

    /**
     * Busca de personas por nombre (da igual si es mayusculas o minusculas y sin
     * espacios extra).
     */
    public List<Registrable> buscarPorNombre(String nombre) {
        List<Registrable> resultados = new ArrayList<>();
        String criterioLower = nombre.trim().toLowerCase();

        for (Registrable persona : entidadesOperativas) {
            if (persona instanceof Persona empleado) {
                String nombrePersona = empleado.getNombre().trim().toLowerCase();
                if (nombrePersona.contains(criterioLower)) {
                    resultados.add(persona);
                }
            }
        }
        return resultados;
    }

    /**
     * Busca una persona por RUT exacto (da igual si es mayusculas o minusculas y
     * sin espacios extra).
     */
    public Registrable buscarPorRut(String rut) {
        String rutCriterio = rut.trim().toLowerCase();

        for (Registrable persona : entidadesOperativas) {
            if (persona instanceof Persona empleado) {
                String rutPersona = empleado.getRut().toString().trim().toLowerCase();
                if (rutPersona.equals(rutCriterio)) {
                    return empleado;
                }
            }
        }
        return null;
    }

    public List<Registrable> buscarUltimosCincoEmpleados() { // se buscan los ultimos 5 empleados en la lista de
                                                             // entidades operativas segun su tipo y tamaño total
        List<Registrable> soloEmpleados = new ArrayList<>();

        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof Empleado) {
                soloEmpleados.add(entidad);
            }
        }
        List<Registrable> resultados = new ArrayList<>();
        int tamañoEmpleados = soloEmpleados.size();
        int indiceInicio = Math.max(0, tamañoEmpleados - 5);

        for (int i = indiceInicio; i < tamañoEmpleados; i++) {
            resultados.add(soloEmpleados.get(i));
        }

        return resultados;
    }

    public List<Registrable> buscarUltimosCincoPlantas() { // se buscan los ultimos 5 plantas en la lista de entidades
                                                           // operativas segun su tipo y tamaño total
        List<Registrable> soloPlantas = new ArrayList<>();

        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof PlantaProceso) {
                soloPlantas.add(entidad);
            }
        }

        List<Registrable> resultados = new ArrayList<>();
        int tamañoPlantas = soloPlantas.size();
        int indiceInicio = Math.max(0, tamañoPlantas - 5);
        for (int i = indiceInicio; i < tamañoPlantas; i++) {
            resultados.add(soloPlantas.get(i));
        }

        return resultados;
    }

    public List<Registrable> buscarUltimosCincoCentrosCultivos() { // se buscan los ultimos 5 centros cultivo en la
                                                                   // lista de entidades operativas segun su tipo y
                                                                   // tamaño total
        List<Registrable> soloCentrosCultivos = new ArrayList<>();

        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof CentroCultivo) {
                soloCentrosCultivos.add(entidad);
            }
        }

        List<Registrable> resultados = new ArrayList<>();
        int tamañoCentrosCultivos = soloCentrosCultivos.size();
        int indiceInicio = Math.max(0, tamañoCentrosCultivos - 5);

        for (int i = indiceInicio; i < tamañoCentrosCultivos; i++) {
            resultados.add(soloCentrosCultivos.get(i));
        }

        return resultados;
    }

    public List<Registrable> buscarUltimosCincoCentrosVentas() { // se buscan los ultimos 5 centros ventas en la lista
                                                                 // de entidades operativas segun su tipo y tamaño total
        List<Registrable> soloCentrosVentas = new ArrayList<>();

        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof CentroDeVenta) {
                soloCentrosVentas.add(entidad);
            }
        }

        List<Registrable> resultados = new ArrayList<>();
        int tamañoCentrosVentas = soloCentrosVentas.size();

        int indiceInicio = Math.max(0, tamañoCentrosVentas - 5);

        for (int i = indiceInicio; i < tamañoCentrosVentas; i++) {
            resultados.add(soloCentrosVentas.get(i));
        }

        return resultados;
    }

    public Empleado buscarGerenteDeProduccion() {// se busca el gerente de produccion primerizado en la lista de
                                                 // entidades operativas
        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof Empleado empleado && empleado.getCargo().equals("Gerente de Produccion")) {
                return empleado;
            }
        }
        return null;
    }

    public Empleado buscarGerenteDeCultivos() {// se busca el gerente de cultivos primerizado en la lista de entidades
                                               // operativas
        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof Empleado empleado && empleado.getCargo().equals("Gerente de Cultivos")) {
                return empleado;
            }
        }
        return null;
    }

    public Empleado buscarGerenteDeVentas() {// se busca el gerente de ventas primerizado en la lista de entidades
                                             // operativas
        for (Registrable entidad : entidadesOperativas) {
            if (entidad instanceof Empleado empleado && empleado.getCargo().equals("Gerente de Ventas")) {
                return empleado;
            }
        }
        return null;
    }
}
