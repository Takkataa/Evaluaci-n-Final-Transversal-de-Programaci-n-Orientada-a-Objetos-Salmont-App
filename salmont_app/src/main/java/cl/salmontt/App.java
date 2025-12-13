package cl.salmontt;

import javax.swing.SwingUtilities;

import cl.salmontt.data.GestorEntidades;
import cl.salmontt.ui.InterfazUsuario;

/**
 * Clase principal para instanciar y llamar a los metodos de la clase
 * GestorEntidades necesarios para mostrar la lista de unidades operativas de
 * Salmontt, eso incluye la informacion de las personas y las unidades
 * operativas
 *
 */
public class App {
    public static void main(String[] args) {

        GestorEntidades gestorEntidades = new GestorEntidades();
        gestorEntidades.subirInformacion();

        // Inicia la aplicacion
        SwingUtilities.invokeLater(() -> {
            InterfazUsuario ventana = new InterfazUsuario(gestorEntidades);
            ventana.setVisible(true);
        });
    }
}