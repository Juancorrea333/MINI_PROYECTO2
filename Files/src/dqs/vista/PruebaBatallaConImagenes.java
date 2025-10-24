import javax.swing.*;

/** Pequeño runner para abrir la vista de batalla nueva y probar imágenes. */
public class PruebaBatallaConImagenes {
    /** Ejecuta la vista de batalla en el hilo de eventos de Swing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaIniciarBatallaNueva vista = new VistaIniciarBatallaNueva();
            vista.setVisible(true);
        });
    }
}