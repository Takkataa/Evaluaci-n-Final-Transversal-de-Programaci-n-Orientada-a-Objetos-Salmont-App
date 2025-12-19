package cl.salmontt.ui;

import cl.salmontt.data.*;
import cl.salmontt.model.*;
import cl.salmontt.utils.ValidadorInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase que representa la interfaz grafica de usuario
 * Implementa la interface ActionListener y hereda su comportamiento de la clase
 * JFrame
 * necesaria para implementar la funcionalidad de ventana y los botones de la
 * UI, es decir su lado grafico
 * en el se puede interactuar con el usuario y realizar acciones como crear
 * objetos, buscar y mostrar informacion.
 */
public class InterfazUsuario extends JFrame implements ActionListener {

    // Constantes para identificar los "paneles" que componen la UI
    final static String MENU_PRINCIPAL_PANEL = "Menu Principal";
    final static String GESTION_PERSONAS_PANEL = "Gestion Personas";
    final static String GESTION_UNIDADES_PANEL = "Gestion Unidades Operativas";
    final static String OPCIONES_PLANTAS_PANEL = "Opciones Plantas";
    final static String OPCIONES_CENTROS_CULTIVO_PANEL = "Opciones Centros Cultivo";
    final static String OPCIONES_CENTROS_VENTA_PANEL = "Opciones Centros Venta";
    final static String GESTION_EMPLEADOS_PANEL = "Gestion Empleados";
    final static String GESTION_CLIENTES_PANEL = "Gestion Clientes";

    // Componentes principales de la UI
    private JPanel cardPanel;
    private JPanel mainStatsPanel;
    private CardLayout cl;
    private GestorEntidades gestorEntidades;

    // Define los JLabels que necesitan ser actualizados como variables de
    // instancia, contadores globales de los objetos
    private JLabel countEmpLabel;
    private JLabel countPlantasLabel;
    private JLabel countCentrosLabel;
    private JLabel countCentrosVentaLabel;

    // Botones usados para la navegación
    // para el ActionListener, es decir para que los botones tengan funcionalidad
    private JButton btnGestionPersonas;
    private JButton btnGestionUnidadesOperativas;

    private JButton btnVolverUnidadesOp;

    private JButton btnGestionEmpleados;
    private JButton btnGestionClientes;
    private JButton btnVolverPersonas;

    // Botones específicos del menú de empleados que necesitan ser accesibles
    private JButton btnCrearEmpleado;
    private JButton btnBuscarRutEmpleados;
    private JButton btnBuscarNombreEmpleados;
    private JButton btnUltimosCincoEmpleados;
    private JButton btnVolverEmpleados;

    // Botones específicos del menú de clientes que necesitan ser accesibles
    private JButton btnCrearCliente;
    private JButton btnBuscarRutClientes;
    private JButton btnBuscarNombreClientes;
    private JButton btnUltimosCincoClientes;
    private JButton btnVolverClientes;

    // Botones específicos del menú de unidades operativas
    private JButton btnGestionPlantas;
    private JButton btnGestionCentrosCultivos;
    private JButton btnGestionCentrosVentas;

    // Botones específicos del menú de plantas de produccion
    private JButton btnCrearPlantas;
    private JButton btnUltimosCincoPlantas;
    private JButton btnVolverPlantas;

    // Botones específicos del menú de centros cultivos
    private JButton btnCrearCentrosCultivos;
    private JButton btnUltimosCincoCentrosCultivos;
    private JButton btnVolverCentrosCultivos;

    // Botones específicos del menú de centros de ventas
    private JButton btnCrearCentrosVentas;
    private JButton btnUltimosCincoCentrosVentas;
    private JButton btnVolverCentrosVentas;

    public InterfazUsuario(GestorEntidades gestorEntidades) {
        this.gestorEntidades = gestorEntidades; // Guardamos la referencia al gestor

        setTitle("Salmontt App");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Configurar CardLayout (Es el que permite cambiar entre paneles) y el
        // contenedor principal
        cl = new CardLayout();
        cardPanel = new JPanel(cl);

        this.mainStatsPanel = statsPanel(gestorEntidades); // panel inferior que muestra estadisticas de los objetos
                                                           // creados

        // 2. Crear los distintos paneles/vistas
        JPanel panelMenuPrincipal = crearPanelMenuPrincipal();
        JPanel panelGestionUnidades = crearPanelGestionUnidades();
        JPanel panelGestionPersonas = crearPanelGestionPersonas();

        JPanel panelOpcionesPlantas = crearPanelOpcionesPlantas();
        JPanel panelOpcionesCentrosCultivos = crearPanelOpcionesCentrosCultivos();
        JPanel panelOpcionesCentrosVentas = crearPanelOpcionesCentrosVentas();
        JPanel panelGestionEmpleados = crearPanelGestionEmpleados();
        JPanel panelGestionClientes = crearPanelGestionClientes();

        // 3. Añadir los paneles al CardLayout con sus nombres clave
        cardPanel.add(panelMenuPrincipal, MENU_PRINCIPAL_PANEL);
        cardPanel.add(panelGestionUnidades, GESTION_UNIDADES_PANEL);
        cardPanel.add(panelGestionPersonas, GESTION_PERSONAS_PANEL);
        cardPanel.add(panelOpcionesPlantas, OPCIONES_PLANTAS_PANEL);
        cardPanel.add(panelOpcionesCentrosCultivos, OPCIONES_CENTROS_CULTIVO_PANEL);
        cardPanel.add(panelOpcionesCentrosVentas, OPCIONES_CENTROS_VENTA_PANEL);
        cardPanel.add(panelGestionClientes, GESTION_CLIENTES_PANEL);
        cardPanel.add(panelGestionEmpleados, GESTION_EMPLEADOS_PANEL);

        // 4. Añadir el cardPanel al JFrame
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(this.mainStatsPanel, BorderLayout.SOUTH);

        // Mostrar el menú principal al inicio
        cl.show(cardPanel, MENU_PRINCIPAL_PANEL);
    }

    // --- Métodos de Creación de Paneles ---

    private JPanel crearPanelMenuPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        // panel Header es comun en todos los paneles, lo creamos y añadimos a este
        // panel específico
        panel.add(headerPanel("Bienvenido a Salmontt App", "Seleccione una opción:"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JPanel buttonGridPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        btnGestionPersonas = new JButton("Gestión de Personas");
        btnGestionUnidadesOperativas = new JButton("Gestión de Unidades Operativas");

        // Añadir ActionListeners a los botones
        btnGestionPersonas.addActionListener(this);
        btnGestionUnidadesOperativas.addActionListener(this);

        btnGestionPersonas.setPreferredSize(new Dimension(250, 45));
        btnGestionUnidadesOperativas.setPreferredSize(new Dimension(250, 45));

        buttonGridPanel.add(btnGestionPersonas);
        buttonGridPanel.add(btnGestionUnidadesOperativas);
        centerPanel.add(buttonGridPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGestionUnidades() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.add(headerPanel("Gestión de Unidades Operativas", "Seleccione una opción:"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JPanel buttonGridPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        btnGestionPlantas = new JButton("Gestión de Plantas de producción");
        btnGestionCentrosCultivos = new JButton("Gestión de Centros de cultivos");
        btnGestionCentrosVentas = new JButton("Gestión de Centros de ventas");
        btnVolverUnidadesOp = new JButton("Volver al Menú Principal");

        // Estilo básico para botones
        btnGestionPlantas.setPreferredSize(new Dimension(250, 40));
        btnGestionCentrosCultivos.setPreferredSize(new Dimension(250, 40));
        btnGestionCentrosVentas.setPreferredSize(new Dimension(250, 40));
        btnVolverUnidadesOp.setPreferredSize(new Dimension(250, 40));

        // Asignar ActionListener a los botones
        btnVolverUnidadesOp.addActionListener(this);
        btnGestionPlantas.addActionListener(this);
        btnGestionCentrosCultivos.addActionListener(this);
        btnGestionCentrosVentas.addActionListener(this);

        buttonGridPanel.add(btnGestionPlantas);
        buttonGridPanel.add(btnGestionCentrosCultivos);
        buttonGridPanel.add(btnGestionCentrosVentas);
        buttonGridPanel.add(btnVolverUnidadesOp);

        centerPanel.add(buttonGridPanel);// se crean dos para que el panel tenga un aspecto mas ordenado
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGestionPersonas() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Personas", "Seleccione una opción:"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        btnGestionEmpleados = new JButton("Gestionar Empleados");
        btnGestionClientes = new JButton("Gestionar Clientes");
        btnVolverPersonas = new JButton("Volver al Menú Principal");

        // Añadir ActionListeners a los nuevos botones
        btnGestionEmpleados.addActionListener(this);
        btnGestionClientes.addActionListener(this);
        btnVolverPersonas.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnGestionEmpleados.setPreferredSize(buttonSize);
        btnGestionClientes.setPreferredSize(buttonSize);
        btnVolverPersonas.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnGestionEmpleados);
        buttonGridPanel.add(btnGestionClientes);
        buttonGridPanel.add(btnVolverPersonas);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGestionEmpleados() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Empleados", "Administración y Búsqueda"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnCrearEmpleado = new JButton("Crear Nuevo Empleado");
        btnBuscarRutEmpleados = new JButton("Buscar Empleado por RUT");
        btnBuscarNombreEmpleados = new JButton("Buscar Empleado por Nombre");
        btnUltimosCincoEmpleados = new JButton("Mostrar 5 Últimos Empleados");
        btnVolverEmpleados = new JButton("Volver al Menú Anterior");

        // Añadir ActionListeners a los nuevos botones
        btnCrearEmpleado.addActionListener(this);
        btnBuscarRutEmpleados.addActionListener(this);
        btnBuscarNombreEmpleados.addActionListener(this);
        btnUltimosCincoEmpleados.addActionListener(this);
        btnVolverEmpleados.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnCrearEmpleado.setPreferredSize(buttonSize);
        btnBuscarRutEmpleados.setPreferredSize(buttonSize);
        btnBuscarNombreEmpleados.setPreferredSize(buttonSize);
        btnUltimosCincoEmpleados.setPreferredSize(buttonSize);
        btnVolverEmpleados.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnCrearEmpleado);
        buttonGridPanel.add(btnBuscarRutEmpleados);
        buttonGridPanel.add(btnBuscarNombreEmpleados);
        buttonGridPanel.add(btnUltimosCincoEmpleados);
        buttonGridPanel.add(btnVolverEmpleados);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGestionClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Clientes", "Administración y Búsqueda"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnCrearCliente = new JButton("Crear Nuevo Cliente");
        btnBuscarRutClientes = new JButton("Buscar Cliente por RUT");
        btnBuscarNombreClientes = new JButton("Buscar Cliente por Nombre");
        btnUltimosCincoClientes = new JButton("Mostrar 5 Últimos Clientes");
        btnVolverClientes = new JButton("Volver al Menú Anterior");

        // Añadir ActionListeners a los nuevos botones
        btnCrearCliente.addActionListener(this);
        btnBuscarRutClientes.addActionListener(this);
        btnBuscarNombreClientes.addActionListener(this);
        btnUltimosCincoClientes.addActionListener(this);
        btnVolverClientes.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnCrearCliente.setPreferredSize(buttonSize);
        btnBuscarRutClientes.setPreferredSize(buttonSize);
        btnBuscarNombreClientes.setPreferredSize(buttonSize);
        btnUltimosCincoClientes.setPreferredSize(buttonSize);
        btnVolverClientes.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnCrearCliente);
        buttonGridPanel.add(btnBuscarRutClientes);
        buttonGridPanel.add(btnBuscarNombreClientes);
        buttonGridPanel.add(btnUltimosCincoClientes);
        buttonGridPanel.add(btnVolverClientes);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelOpcionesPlantas() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Plantas", "Administración y Búsqueda"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnCrearPlantas = new JButton("Crear Nueva Planta de Producción");
        btnUltimosCincoPlantas = new JButton("Mostrar 5 Últimas Plantas de Producción");
        btnVolverPlantas = new JButton("Volver al Menú Anterior");

        // Añadir ActionListeners a los nuevos botones
        btnCrearPlantas.addActionListener(this);
        btnUltimosCincoPlantas.addActionListener(this);
        btnVolverPlantas.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnCrearPlantas.setPreferredSize(buttonSize);
        btnUltimosCincoPlantas.setPreferredSize(buttonSize);
        btnVolverPlantas.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnCrearPlantas);
        buttonGridPanel.add(btnUltimosCincoPlantas);
        buttonGridPanel.add(btnVolverPlantas);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelOpcionesCentrosCultivos() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Centros de Cultivos", "Administración y Búsqueda"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnCrearCentrosCultivos = new JButton("Crear Nuevo Centro de Cultivo");
        btnUltimosCincoCentrosCultivos = new JButton("Mostrar 5 Últimos Centros de Cultivo");
        btnVolverCentrosCultivos = new JButton("Volver al Menú Anterior");

        // Añadir ActionListeners a los nuevos botones
        btnCrearCentrosCultivos.addActionListener(this);
        btnUltimosCincoCentrosCultivos.addActionListener(this);
        btnVolverCentrosCultivos.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnCrearCentrosCultivos.setPreferredSize(buttonSize);
        btnUltimosCincoCentrosCultivos.setPreferredSize(buttonSize);
        btnVolverCentrosCultivos.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnCrearCentrosCultivos);
        buttonGridPanel.add(btnUltimosCincoCentrosCultivos);
        buttonGridPanel.add(btnVolverCentrosCultivos);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelOpcionesCentrosVentas() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));

        panel.add(headerPanel("Gestión de Centros de Ventas", "Administración y Búsqueda"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Usamos GridLayout para los botones en el centro (5 filas, 1 columna)
        JPanel buttonGridPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        btnCrearCentrosVentas = new JButton("Crear Nuevo Centro de Ventas");
        btnUltimosCincoCentrosVentas = new JButton("Mostrar 5 Últimos Centros de Ventas");
        btnVolverCentrosVentas = new JButton("Volver al Menú Anterior");

        // Añadir ActionListeners a los nuevos botones
        btnCrearCentrosVentas.addActionListener(this);
        btnUltimosCincoCentrosVentas.addActionListener(this);
        btnVolverCentrosVentas.addActionListener(this);

        // Establecer un tamaño preferido
        Dimension buttonSize = new Dimension(250, 40);
        btnCrearCentrosVentas.setPreferredSize(buttonSize);
        btnUltimosCincoCentrosVentas.setPreferredSize(buttonSize);
        btnVolverCentrosVentas.setPreferredSize(buttonSize);

        buttonGridPanel.add(btnCrearCentrosVentas);
        buttonGridPanel.add(btnUltimosCincoCentrosVentas);
        buttonGridPanel.add(btnVolverCentrosVentas);

        centerPanel.add(buttonGridPanel);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    // --- Implementación del ActionListener para manejar la navegación ---
    @Override
    public void actionPerformed(ActionEvent e) {
        // --- Lógica de Navegación (CardLayout) ---
        if (e.getSource() == btnGestionPersonas) {
            cl.show(cardPanel, GESTION_PERSONAS_PANEL);
            setTitle("Salmontt App - Gestión Personas");
            setSize(400, 400); // Ajustar tamaño para la nueva vista si es necesario
        } else if (e.getSource() == btnGestionUnidadesOperativas) {
            cl.show(cardPanel, GESTION_UNIDADES_PANEL);
            setTitle("Salmontt App - Gestión Unidades");
            setSize(400, 450);
        } else if (e.getSource() == btnVolverUnidadesOp || e.getSource() == btnVolverPersonas) {
            // Ambos botones de "Volver" regresan al menú principal
            cl.show(cardPanel, MENU_PRINCIPAL_PANEL);
            setTitle("Salmontt App");
            setSize(400, 350);
        }

        else if (e.getSource() == btnGestionEmpleados) {
            cl.show(cardPanel, GESTION_EMPLEADOS_PANEL);
            setTitle("Salmontt App - Gestión Empleados");
            setSize(400, 500);
        } else if (e.getSource() == btnGestionClientes) {
            cl.show(cardPanel, GESTION_CLIENTES_PANEL);
            setTitle("Salmontt App - Gestión Clientes");
            setSize(400, 500);
        }

        else if (e.getSource() == btnCrearEmpleado) {
            mostrarDialogoCrearEmpleado();
        } else if (e.getSource() == btnBuscarRutEmpleados) {
            String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT a buscar:");
            if (rut != null && !rut.isEmpty()) {
                Registrable empleado = gestorEntidades.buscarPorRut(1, rut);
                if (empleado != null) {
                    JOptionPane.showMessageDialog(this,
                            "Resultado de búsqueda por RUT: \n" + empleado.mostrarResumen());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún empleado con el RUT: " + rut);
                }
            }
        } else if (e.getSource() == btnBuscarNombreEmpleados) {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre a buscar:");
            if (nombre != null && !nombre.isEmpty()) {
                List<Registrable> empleados = gestorEntidades.buscarPorNombre(1, nombre);
                if (!empleados.isEmpty()) {
                    String resultados = "Resultados de búsqueda por nombre:\n";
                    for (Registrable empleado : empleados) {
                        resultados += empleado.mostrarResumen() + "\n";
                    }
                    JOptionPane.showMessageDialog(this, resultados);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron empleados con el nombre: " + nombre);
                }
            }
        } else if (e.getSource() == btnUltimosCincoEmpleados) {
            // Llama a tu método para obtener los últimos 5:
            List<Registrable> ultimos = gestorEntidades.buscarUltimosCinco(1);
            if (!ultimos.isEmpty()) {
                String resultados = "Ultimos 5 empleados creados:  \n";
                for (Registrable empleado : ultimos) {
                    resultados += empleado.mostrarResumen() + "\n";
                }
                JOptionPane.showMessageDialog(this, resultados);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron empleados creados.");
            }
            // logica para los botones del panel de gestion de clientes
        } else if (e.getSource() == btnVolverEmpleados) {
            cl.show(cardPanel, GESTION_PERSONAS_PANEL);
            setTitle("Salmontt App - Gestión Personas");
            setSize(400, 400);
        } else if (e.getSource() == btnCrearCliente) {
            mostrarDialogoCrearCliente();
        } else if (e.getSource() == btnBuscarRutClientes) {
            String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT a buscar:");
            if (rut != null && !rut.isEmpty()) {
                Registrable cliente = gestorEntidades.buscarPorRut(2, rut);
                if (cliente != null) {
                    JOptionPane.showMessageDialog(this,
                            "Resultado de búsqueda por RUT: \n" + cliente.mostrarResumen());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con el RUT: " + rut);
                }
            }
        } else if (e.getSource() == btnBuscarNombreClientes) {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre a buscar:");
            if (nombre != null && !nombre.isEmpty()) {
                List<Registrable> clientes = gestorEntidades.buscarPorNombre(2, nombre);
                if (!clientes.isEmpty()) {
                    String resultados = "Resultados de búsqueda por nombre:\n";
                    for (Registrable cliente : clientes) {
                        resultados += cliente.mostrarResumen() + "\n";
                    }
                    JOptionPane.showMessageDialog(this, resultados);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron clientes con el nombre: " + nombre);
                }
            }
        } else if (e.getSource() == btnUltimosCincoClientes) {
            // Llama a tu método para obtener los últimos 5:
            List<Registrable> ultimos = gestorEntidades.buscarUltimosCinco(2);
            if (!ultimos.isEmpty()) {
                String resultados = "Ultimos 5 clientes creados:  \n";
                for (Registrable cliente : ultimos) {
                    resultados += cliente.mostrarResumen() + "\n";
                }
                JOptionPane.showMessageDialog(this, resultados);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron clientes creados.");
            }

        } else if (e.getSource() == btnVolverClientes) {
            cl.show(cardPanel, GESTION_PERSONAS_PANEL);
            setTitle("Salmontt App - Gestión Personas");
            setSize(400, 400);
        }
        // logica para los botones del panel de gestion de unidades operativas
        else if (e.getSource() == btnGestionPlantas) {
            cl.show(cardPanel, OPCIONES_PLANTAS_PANEL);
            setTitle("Salmontt App - Gestión Plantas de Producción");
            setSize(400, 400);
        } else if (e.getSource() == btnGestionCentrosCultivos) {
            cl.show(cardPanel, OPCIONES_CENTROS_CULTIVO_PANEL);
            setTitle("Salmontt App - Gestión Centros de Cultivos");
            setSize(400, 400);
        } else if (e.getSource() == btnGestionCentrosVentas) {
            cl.show(cardPanel, OPCIONES_CENTROS_VENTA_PANEL);
            setTitle("Salmontt App - Gestión Centros de Ventas");
            setSize(400, 400);
        }

        // logica para los botones del panel de gestion de plantas de produccion
        else if (e.getSource() == btnCrearPlantas) {
            mostrarDialogoCrearPlanta();
        } else if (e.getSource() == btnUltimosCincoPlantas) {
            List<Registrable> ultimos = gestorEntidades.buscarUltimosCinco(3);
            if (!ultimos.isEmpty()) {
                String resultados = "Ultimas 5 plantas creadas:  \n";
                for (Registrable planta : ultimos) {
                    resultados += planta.mostrarResumen() + "\n";
                }
                JOptionPane.showMessageDialog(this, resultados);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron plantas creadas.");
            }
        } else if (e.getSource() == btnVolverPlantas) {
            cl.show(cardPanel, MENU_PRINCIPAL_PANEL);
            setTitle("Salmontt App");
            setSize(400, 350);
        }

        // logica para los botones del panel de gestion de centros de cultivo
        else if (e.getSource() == btnCrearCentrosCultivos) {
            mostrarDialogoCrearCentroCultivo();
        } else if (e.getSource() == btnUltimosCincoCentrosCultivos) {
            List<Registrable> ultimos = gestorEntidades.buscarUltimosCinco(4);
            if (!ultimos.isEmpty()) {
                String resultados = "Ultimos 5 centros de cultivo creados:  \n";
                for (Registrable centroCultivo : ultimos) {
                    resultados += centroCultivo.mostrarResumen() + "\n";
                }
                JOptionPane.showMessageDialog(this, resultados);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron centros de cultivo creados.");
            }
        } else if (e.getSource() == btnVolverCentrosCultivos) {
            cl.show(cardPanel, MENU_PRINCIPAL_PANEL);
            setTitle("Salmontt App");
            setSize(400, 350);
        }

        // logica para los botones del panel de gestion de centros de ventas
        else if (e.getSource() == btnCrearCentrosVentas) {
            mostrarDialogoCrearCentroVenta();
        } else if (e.getSource() == btnUltimosCincoCentrosVentas) {
            List<Registrable> ultimos = gestorEntidades.buscarUltimosCinco(5);
            if (!ultimos.isEmpty()) {
                String resultados = "Ultimos 5 centros de ventas creados:  \n";
                for (Registrable centroVenta : ultimos) {
                    resultados += centroVenta.mostrarResumen() + "\n";
                }
                JOptionPane.showMessageDialog(this, resultados);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron centros de ventas creados.");
            }
        } else if (e.getSource() == btnVolverCentrosVentas) {
            cl.show(cardPanel, MENU_PRINCIPAL_PANEL);
            setTitle("Salmontt App");
            setSize(400, 350);
        }

    }

    // logica para mostrar el dialogo de creacion de plantas de produccion
    private void mostrarDialogoCrearPlanta() {
        // Creamos el diálogo de forma local e instantánea
        JDialog dialog = new JDialog(this, "Crear Nueva Planta de produccion", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        // Definimos los campos de texto que usaremos
        JTextField txtNombre = new JTextField();
        JTextField txtComuna = new JTextField();
        JTextField txtToneladasDeProduccion = new JTextField();
        JTextField txtTiempoDeProduccionDias = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Comuna:"));
        formPanel.add(txtComuna);
        formPanel.add(new JLabel("--- Informacion de produccion ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Toneladas de produccion:"));
        formPanel.add(txtToneladasDeProduccion);
        formPanel.add(new JLabel("Tiempo de produccion en dias:"));
        formPanel.add(txtTiempoDeProduccionDias);
        formPanel.add(new JLabel("Gerente de Produccion: Asignado automaticamente"));
        dialog.add(formPanel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Planta de produccion");

        // Usamos una CLASE INTERNA ANÓNIMA para manejar el evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = null; // Variable para almacenar mensajes de error

                // 1. Obtener datos
                String nombre = txtNombre.getText();
                String comuna = txtComuna.getText();
                String toneladasDeProduccionStr = txtToneladasDeProduccion.getText();
                String tiempoDeProduccionDiasStr = txtTiempoDeProduccionDias.getText();

                // 2. Usar la lógica de validación de ValidadorInputs
                if ((error = ValidadorInputs.validarNombreCompuesto(nombre)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(comuna)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(toneladasDeProduccionStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(tiempoDeProduccionDiasStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // si no hay error, procedemos a crear la planta de produccion
                try {
                    double toneladasDeProduccion = Double.parseDouble(toneladasDeProduccionStr);
                    int tiempoDeProduccionDias = Integer.parseInt(tiempoDeProduccionDiasStr);
                    // Usar el constructor de Empleado y el gestor de la clase principal
                    // Normalizamos a mayúsculas
                    PlantaProceso nuevaPlantaProceso = new PlantaProceso(
                            1,
                            nombre.toUpperCase(),
                            comuna.toUpperCase(),
                            toneladasDeProduccion,
                            tiempoDeProduccionDias,
                            gestorEntidades.buscarGerente(1));

                    gestorEntidades.agregarRegistrable(nuevaPlantaProceso);

                    JOptionPane.showMessageDialog(dialog, "Planta de produccion creada exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Se actualizan los contadores
                    actualizarContadores();

                    // se cierra el diálogo después de guardar
                    dialog.dispose();

                } catch (Exception ex) {
                    // Capturar cualquier error remanente
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al guardar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    private void mostrarDialogoCrearCentroCultivo() {
        // Creamos el diálogo de forma local e instantánea
        JDialog dialog = new JDialog(this, "Crear Nueva Centro de cultivo", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        // Definimos los campos de texto que usaremos
        JTextField txtNombre = new JTextField();
        JTextField txtComuna = new JTextField();
        JTextField txtCapacidadEnToneladas = new JTextField();
        JTextField txtTiempoDeCultivoDias = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Comuna:"));
        formPanel.add(txtComuna);
        formPanel.add(new JLabel("--- Informacion de produccion ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Toneladas de produccion:"));
        formPanel.add(txtCapacidadEnToneladas);
        formPanel.add(new JLabel("Tiempo de produccion en dias:"));
        formPanel.add(txtTiempoDeCultivoDias);
        formPanel.add(new JLabel("Gerente de Cultivos: Asignado automaticamente"));
        dialog.add(formPanel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Centro de cultivo");

        // Usamos una CLASE INTERNA ANÓNIMA para manejar el evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = null; // Variable para almacenar mensajes de error

                // 1. Obtener datos crudos
                String nombre = txtNombre.getText();
                String comuna = txtComuna.getText();
                String capacidadEnToneladasStr = txtCapacidadEnToneladas.getText();
                String tiempoDeCultivoDiasStr = txtTiempoDeCultivoDias.getText();

                // 2. se usa la lógica de validación de ValidadorInputs
                if ((error = ValidadorInputs.validarNombreCompuesto(nombre)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(comuna)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(capacidadEnToneladasStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(tiempoDeCultivoDiasStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 3. Si todo es válido, procesar la creación del centro de cultivo
                try {
                    double capacidadEnToneladas = Double.parseDouble(capacidadEnToneladasStr);
                    int tiempoDeCultivoDias = Integer.parseInt(tiempoDeCultivoDiasStr);
                    // Normalizamos a mayúsculas como hacías en la consola
                    CentroCultivo nuevoCentroDeCultivo = new CentroCultivo(
                            2,
                            nombre.toUpperCase(),
                            comuna.toUpperCase(),
                            capacidadEnToneladas,
                            tiempoDeCultivoDias,
                            gestorEntidades.buscarGerente(2));

                    gestorEntidades.agregarRegistrable(nuevoCentroDeCultivo);

                    JOptionPane.showMessageDialog(dialog, "Centro de cultivo creado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Se actualizan los contadores
                    actualizarContadores();

                    // se cierra el diálogo después de guardar
                    dialog.dispose();

                } catch (Exception ex) {
                    // Capturar cualquier error remanente
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al guardar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    private void mostrarDialogoCrearCentroVenta() {
        // Creamos el diálogo de forma local e instantánea
        JDialog dialog = new JDialog(this, "Crear Nueva Centro de venta", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        // Definimos los campos de texto que usaremos
        JTextField txtNombre = new JTextField();
        JTextField txtComuna = new JTextField();
        JTextField txtToneladasDisponiblesVenta = new JTextField();
        JTextField txtToneladasVendidas = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Comuna:"));
        formPanel.add(txtComuna);
        formPanel.add(new JLabel("--- Informacion de produccion ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Toneladas disponibles de venta:"));
        formPanel.add(txtToneladasDisponiblesVenta);
        formPanel.add(new JLabel("Toneladas vendidas:"));
        formPanel.add(txtToneladasVendidas);
        formPanel.add(new JLabel("Gerente de ventas: Asignado automaticamente"));
        dialog.add(formPanel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Centro de venta");

        // Usamos una CLASE INTERNA ANÓNIMA para manejar el evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = null; // Variable para almacenar mensajes de error

                // 1. Obtener datos crudos
                String nombre = txtNombre.getText();
                String comuna = txtComuna.getText();
                String toneladasDisponiblesVentaStr = txtToneladasDisponiblesVenta.getText();
                String toneladasVendidasStr = txtToneladasVendidas.getText();

                // 2. Usar la lógica de validación de ValidadorInputs
                if ((error = ValidadorInputs.validarNombreCompuesto(nombre)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(comuna)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(toneladasDisponiblesVentaStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarFormatoNumerico(toneladasVendidasStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 3. Si todo es válido, procesar la creación del centro de venta
                try {
                    // Crear objetos complejos
                    double toneladasDisponiblesVenta = Double.parseDouble(toneladasDisponiblesVentaStr);
                    double toneladasVendidas = Double.parseDouble(toneladasVendidasStr);
                    // Normalizamos a mayúsculas como hacías en la consola
                    CentroDeVenta nuevoCentroDeVenta = new CentroDeVenta(
                            3,
                            nombre.toUpperCase(),
                            comuna.toUpperCase(),
                            toneladasDisponiblesVenta,
                            toneladasVendidas,
                            gestorEntidades.buscarGerente(3));

                    gestorEntidades.agregarRegistrable(nuevoCentroDeVenta);

                    JOptionPane.showMessageDialog(dialog, "Centro de venta creado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Se actualizan los contadores
                    actualizarContadores();

                    // Cerrar el diálogo después de guardar
                    dialog.dispose();

                } catch (Exception ex) {
                    // Capturar cualquier error remanente
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al guardar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    private void mostrarDialogoCrearEmpleado() {
        // Creamos el diálogo de forma local e instantánea
        JDialog dialog = new JDialog(this, "Crear Nuevo Empleado", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        // Definimos los campos de texto que usaremos
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtSueldo = new JTextField();
        JTextField txtCargo = new JTextField();
        JTextField txtCalle = new JTextField();
        JTextField txtComuna = new JTextField();
        JTextField txtRegion = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellido);
        formPanel.add(new JLabel("RUT:"));
        formPanel.add(txtRut);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(txtCorreo);
        formPanel.add(new JLabel("--- Dirección ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Calle y Número:"));
        formPanel.add(txtCalle);
        formPanel.add(new JLabel("Comuna:"));
        formPanel.add(txtComuna);
        formPanel.add(new JLabel("Región:"));
        formPanel.add(txtRegion);
        formPanel.add(new JLabel("--- Detalles ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Sueldo:"));
        formPanel.add(txtSueldo);
        formPanel.add(new JLabel("Cargo:"));
        formPanel.add(txtCargo);

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Empleado");

        // Usamos una CLASE INTERNA ANÓNIMA para manejar el evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = null; // Variable para almacenar mensajes de error

                // 1. Obtener datos crudos
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String rutStr = txtRut.getText();
                String correo = txtCorreo.getText();
                String sueldoStr = txtSueldo.getText();
                String cargo = txtCargo.getText();
                String calle = txtCalle.getText();
                String comuna = txtComuna.getText();
                String region = txtRegion.getText();

                // 2. Usar la lógica de validación de ValidadorInputs
                if ((error = ValidadorInputs.validarNombreCompuesto(nombre)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(apellido)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarRut(rutStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarCorreo(correo)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarSueldo(sueldoStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarTextoConNumeros(calle, "Calle")) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(comuna)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(region)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(cargo)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 3. Si todo es válido, procesar la creación del empleado
                try {

                    if (gestorEntidades.verificarRut(1, rutStr)) {
                        JOptionPane.showMessageDialog(dialog, "El empleado ya se encuentra registrado", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Crear objetos complejos
                    double sueldo = Double.parseDouble(sueldoStr);
                    Rut rut = new Rut(rutStr);
                    Direccion dir = new Direccion(calle.toUpperCase(), comuna.toUpperCase(), region.toUpperCase());

                    // Usar el constructor de Empleado y el gestor de la clase principal
                    // Normalizamos a mayúsculas como hacías en la consola
                    Empleado nuevoEmpleado = new Empleado(
                            nombre.toUpperCase(),
                            apellido.toUpperCase(),
                            rut,
                            correo,
                            dir,
                            sueldo,
                            cargo.toUpperCase());

                    gestorEntidades.agregarRegistrable(nuevoEmpleado);

                    JOptionPane.showMessageDialog(dialog, "Empleado creado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Se actualizan los contadores
                    actualizarContadores();

                    // Cerrar el diálogo después de guardar
                    dialog.dispose();

                } catch (Exception ex) {
                    // Capturar cualquier error remanente, por ejemplo del constructor de Rut
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al guardar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    private void mostrarDialogoCrearCliente() {
        // Creamos el diálogo de forma local e instantánea
        JDialog dialog = new JDialog(this, "Crear Nuevo Cliente", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        // Definimos los campos de texto que usaremos
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCalle = new JTextField();
        JTextField txtComuna = new JTextField();
        JTextField txtRegion = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellido);
        formPanel.add(new JLabel("RUT:"));
        formPanel.add(txtRut);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(txtCorreo);
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(txtTelefono);
        formPanel.add(new JLabel("--- Dirección ---"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Calle y Número:"));
        formPanel.add(txtCalle);
        formPanel.add(new JLabel("Comuna:"));
        formPanel.add(txtComuna);
        formPanel.add(new JLabel("Región:"));
        formPanel.add(txtRegion);

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Empleado");

        // Usamos una CLASE INTERNA ANÓNIMA para manejar el evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error = null; // Variable para almacenar mensajes de error

                // 1. Obtener datos crudos
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String rutStr = txtRut.getText();
                String correo = txtCorreo.getText();
                String telefono = txtTelefono.getText();
                String calle = txtCalle.getText();
                String comuna = txtComuna.getText();
                String region = txtRegion.getText();

                // 2. Usar la lógica de validación de ValidadorInputs
                if ((error = ValidadorInputs.validarNombreCompuesto(nombre)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(apellido)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarRut(rutStr)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarCorreo(correo)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarTelefono(telefono)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarTextoConNumeros(calle, "Calle")) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(comuna)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((error = ValidadorInputs.validarNombreCompuesto(region)) != null) {
                    JOptionPane.showMessageDialog(dialog, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 3. Si todo es válido, procesar la creación del empleado
                try {
                    // Verificar si el rut ya existe
                    if (gestorEntidades.verificarRut(2, rutStr)) {
                        JOptionPane.showMessageDialog(dialog, "El cliente ya se encuentra registrado", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Crear objetos complejos
                    Rut rut = new Rut(rutStr);
                    Direccion dir = new Direccion(calle.toUpperCase(), comuna.toUpperCase(), region.toUpperCase());

                    // Usar el constructor de Empleado y el gestor de la clase principal
                    // Normalizamos a mayúsculas como hacías en la consola
                    Cliente nuevoCliente = new Cliente(
                            nombre.toUpperCase(),
                            apellido.toUpperCase(),
                            rut,
                            correo,
                            dir,
                            telefono);

                    gestorEntidades.agregarRegistrable(nuevoCliente);

                    JOptionPane.showMessageDialog(dialog, "Cliente creado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Se actualizan los contadores
                    actualizarContadores();

                    // Cerrar el diálogo después de guardar
                    dialog.dispose();

                } catch (Exception ex) {
                    // Capturar cualquier error remanente, por ejemplo del constructor de Rut
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al guardar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // metodo para crear el panel de encabezado general del programa y para que
    // pueda ser reutilizado
    public JPanel headerPanel(String titulo, String subTitulo) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(2, 1)); // Cambiado a 2, 1 para que el título y subtítulo estén en filas
                                                     // separadas
        headerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel(titulo, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel(subTitulo, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        return headerPanel;
    }

    // metodo para crear el panel de estadisticas y para que pueda ser reutilizado
    public JPanel statsPanel(GestorEntidades gestorEntidades) {
        JPanel statsPanel = new JPanel();
        // BoxLayout es útil para apilar elementos verticalmente
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.DARK_GRAY);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade margen

        this.countEmpLabel = new JLabel("Cantidad de empleados: " + gestorEntidades.contarEmpleados());
        this.countPlantasLabel = new JLabel("Cantidad de Plantas Proceso: " + gestorEntidades.contarPlantasProceso());
        this.countCentrosLabel = new JLabel("Cantidad de Centros Cultivo: " + gestorEntidades.contarCentrosCultivo());
        this.countCentrosVentaLabel = new JLabel(
                "Cantidad de Centros Venta: " + gestorEntidades.contarCentrosVenta());

        // Cambiar color de texto para contraste con fondo oscuro
        this.countEmpLabel.setForeground(Color.WHITE);
        this.countPlantasLabel.setForeground(Color.WHITE);
        this.countCentrosLabel.setForeground(Color.WHITE);
        this.countCentrosVentaLabel.setForeground(Color.WHITE);

        statsPanel.add(this.countEmpLabel);
        statsPanel.add(this.countPlantasLabel);
        statsPanel.add(this.countCentrosLabel);
        statsPanel.add(this.countCentrosVentaLabel);
        return statsPanel;
    }

    // metodo para mantener actualizados los contadores del panel inferior
    public void actualizarContadores() {
        this.countEmpLabel.setText("Cantidad de empleados: " + gestorEntidades.contarEmpleados());
        this.countPlantasLabel.setText("Cantidad de Plantas Proceso: " + gestorEntidades.contarPlantasProceso());
        this.countCentrosLabel.setText("Cantidad de Centros Cultivo: " + gestorEntidades.contarCentrosCultivo());
        this.countCentrosVentaLabel.setText("Cantidad de Centros Venta: " + gestorEntidades.contarCentrosVenta());

        // Actualizar el panel de estadísticas
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
