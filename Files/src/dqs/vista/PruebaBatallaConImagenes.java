import javax.swing.*;

public class PruebaBatallaConImagenes {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaIniciarBatallaNueva vista = new VistaIniciarBatallaNueva();
            vista.setVisible(true);
        });
    }
}