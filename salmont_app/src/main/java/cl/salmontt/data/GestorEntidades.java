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
        loadFromCsv("salmont_app\\src\\main\\java\\resources\\UnidadesOperativas.csv", gestorEmpleados);
    }

    // Método que lee un archivo CSV y transforma cada fila en un objeto Empleado
    public void loadFromCsv(String path, GestorDeEmpleados gestorEmpleados) {

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
                .withSkipLines(1) // Se omite la primera línea porque corresponde al encabezado
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(',').build())
                .build()) {

            String[] fila; // Array que almacena cada fila del archivo csv
            while ((fila = reader.readNext()) != null) {
                if (fila.length < 9) // Si la fila no tiene 9 columnas, se omite
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

                for (Persona persona : gestorEmpleados.getPersonas()) {
                    if (persona instanceof Empleado empleado) {
                        if (empleado.getRut().getNumero().equals(fila[9].trim())) {
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
                            Double.parseDouble(fila[5].trim()), // Se obtiene el numero de toneladas del centro cultivo
                            Integer.parseInt(fila[6].trim()), // Se obtiene el numero de dias del centro cultivo
                            gerente);
                    agregarRegistrable(centroCultivo); // se agrega el centro cultivo a la lista de entidades operativas
                } else if (tipoInt == 3) {// tipo 3 es centro venta
                    CentroDeVenta centroVenta = new CentroDeVenta(
                            3,
                            fila[1].trim(), // Se obtiene el nombre del centro venta
                            fila[2].trim(), // Se obtiene la comuna del centro venta
                            Double.parseDouble(fila[7].trim()), // Se obtiene el numero de toneladas del centro venta
                            Double.parseDouble(fila[8].trim()), // Se obtiene el numero de toneladas del centro venta
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
     * filtra si el tipo es 1 busca por nombre de persona, si es 2 busca por nombre
     * de cliente
     */
    public List<Registrable> buscarPorNombre(int tipo, String nombre) {
        List<Registrable> resultados = new ArrayList<>();
        String criterioLower = nombre.trim().toLowerCase();

        for (Registrable persona : entidadesOperativas) {
            if (tipo == 1 && persona instanceof Persona empleado) {
                String nombrePersona = empleado.getNombre().trim().toLowerCase();
                if (nombrePersona.contains(criterioLower)) {
                    resultados.add(persona);
                }
            } else if (tipo == 2 && persona instanceof Cliente cliente) {
                String nombreCliente = cliente.getNombre().trim().toLowerCase();
                if (nombreCliente.contains(criterioLower)) {
                    resultados.add(persona);
                }
            }
        }
        return resultados;
    }

    /**
     * Busca una persona por RUT exacto (da igual si es mayusculas o minusculas y
     * sin espacios extra).
     * filtra si el tipo es 1 busca por rut de empleado, si es 2 busca por rut de
     * cliente
     */
    public Registrable buscarPorRut(int tipo, String rut) {
        String rutCriterio = rut.trim().toLowerCase();

        for (Registrable persona : entidadesOperativas) {
            if (tipo == 1 && persona instanceof Persona empleado) {
                String rutPersona = empleado.getRut().toString().trim().toLowerCase();
                if (rutPersona.equals(rutCriterio)) {
                    return empleado;
                }
            } else if (tipo == 2 && persona instanceof Cliente cliente) {
                String rutCliente = cliente.getRut().toString().trim().toLowerCase();
                if (rutCliente.equals(rutCriterio)) {
                    return cliente;
                }
            }
        }
        return null;
    }

    /**
     * Buscan las ultimas 5 entidades definida en la variable tipo, segun el filtro
     * define el tipo de clases se guardan
     * en la lista de entidades buscadas y las retorna.
     * tipo 1: Empleado
     * tipo 2: Cliente
     * tipo 3: PlantaProceso
     * tipo 4: CentroCultivo
     * tipo 5: CentroDeVenta
     */
    public List<Registrable> buscarUltimosCinco(int tipo) { // se buscan los ultimos 5 empleados en la lista de
        // entidades operativas segun su tipo y tamaño total
        List<Registrable> soloEntidadesBuscadas = new ArrayList<>();

        for (Registrable entidad : entidadesOperativas) {
            if (tipo == 1 && entidad instanceof Empleado) {
                soloEntidadesBuscadas.add(entidad);
            } else if (tipo == 2 && entidad instanceof Cliente) {
                soloEntidadesBuscadas.add(entidad);
            } else if (tipo == 3 && entidad instanceof PlantaProceso) {
                soloEntidadesBuscadas.add(entidad);
            } else if (tipo == 4 && entidad instanceof CentroCultivo) {
                soloEntidadesBuscadas.add(entidad);
            } else if (tipo == 5 && entidad instanceof CentroDeVenta) {
                soloEntidadesBuscadas.add(entidad);
            }
        }
        List<Registrable> resultados = new ArrayList<>();
        int tamañoEntidadesBuscadas = soloEntidadesBuscadas.size();
        int indiceInicio = Math.max(0, tamañoEntidadesBuscadas - 5);

        for (int i = indiceInicio; i < tamañoEntidadesBuscadas; i++) {
            resultados.add(soloEntidadesBuscadas.get(i));
        }

        return resultados;
    }

    /**
     * Buscan el gerente definido en la variable tipo, segun el filtro define el
     * tipo de clases se guardan
     * en la lista de entidades buscadas y las retorna.
     * tipo 1: Gerente de Produccion
     * tipo 2: Gerente de Ventas
     * tipo 3: Gerente de Cultivos
     */
    public Empleado buscarGerente(int tipo) {// se busca el gerente de produccion primerizado en la lista de
                                             // entidades operativas
        for (Registrable entidad : entidadesOperativas) {
            if (tipo == 1 && entidad instanceof Empleado empleado
                    && empleado.getCargo().equals("Gerente de Produccion")) {
                return empleado;
            } else if (tipo == 2 && entidad instanceof Empleado empleado
                    && empleado.getCargo().equals("Gerente de Ventas")) {
                return empleado;
            } else if (tipo == 3 && entidad instanceof Empleado empleado
                    && empleado.getCargo().equals("Gerente de Cultivos")) {
                return empleado;
            }
        }
        return null;
    }

    /**
     * Verifica si el rut existe en la lista de entidades operativas, retorna true
     * si existe y false si no existe
     * tipo 1: Empleado
     * tipo 2: Cliente
     */
    public boolean verificarRut(int tipo, String rut) {
        for (Registrable entidad : entidadesOperativas) {
            if (tipo == 1 && entidad instanceof Empleado empleado && empleado.getRut().getNumero().equals(rut)) {
                return true;
            }
            if (tipo == 2 && entidad instanceof Cliente cliente && cliente.getRut().getNumero().equals(rut)) {
                return true;
            }
        }
        return false;
    }

}
