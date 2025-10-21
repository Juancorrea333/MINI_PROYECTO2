import javax.swing.*;

public class PruebaSeleccionEnemigos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaCrearEquipos vista = new VistaCrearEquipos();
            vista.setVisible(true);
        });
    }
}