import javax.swing.*;

/** Runner de prueba para la ventana de creación de equipos (selector de enemigos). */
public class PruebaSeleccionEnemigos {
    /** Abre la vista de creación en el hilo de Swing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaCrearEquipos vista = new VistaCrearEquipos();
            vista.setVisible(true);
        });
    }
}