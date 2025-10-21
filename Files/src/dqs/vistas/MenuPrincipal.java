import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MenuPrincipal extends JFrame {

    private BufferedImage backgroundImage;

    public MenuPrincipal() {
        setTitle("Dragon Quest VIII - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar ventana
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        // Cargar imagen de fondo
        try {
            backgroundImage = ImageIO.read(new File("Files/src/dqs/utilidades/Fondo de menú princi.png"));
        } catch (IOException e) {
            System.err.println("⚠️ No se pudo cargar la imagen de fondo: " + e.getMessage());
        }

        // Panel personalizado con fondo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setOpaque(false);

        // Título
        JLabel titulo = new JLabel("MENÚ PRINCIPAL");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        // Crear botones estilo flat
        JButton btnCrear = crearBoton("1. Crear Equipos");
        JButton btnMostrar = crearBoton("2. Mostrar Equipos");
        JButton btnBatalla = crearBoton("3. Iniciar Batalla");
        JButton btnPruebas = crearBoton("4. Prueba de Mecánicas");
        JButton btnSalir = crearBoton("5. Salir");

        // Layout con espaciado vertical
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        backgroundPanel.add(titulo, gbc);

        gbc.gridy++;
        backgroundPanel.add(btnCrear, gbc);

        gbc.gridy++;
        backgroundPanel.add(btnMostrar, gbc);

        gbc.gridy++;
        backgroundPanel.add(btnBatalla, gbc);

        gbc.gridy++;
        backgroundPanel.add(btnPruebas, gbc);

        gbc.gridy++;
        backgroundPanel.add(btnSalir, gbc);

        add(backgroundPanel, BorderLayout.CENTER);

        // Acción básica para "Salir"
        btnSalir.addActionListener(_ -> System.exit(0));

        // Listeners para los demás botones
        btnCrear.addActionListener(_ -> abrirVentanaCrearEquipos());
        btnMostrar.addActionListener(_ -> mostrarEquipos());
        btnBatalla.addActionListener(_ -> iniciarBatalla());
        btnPruebas.addActionListener(_ -> probarMecanicas());
    }

    // Métodos temporales para funcionalidad de botones
    private void abrirVentanaCrearEquipos() {
        JOptionPane.showMessageDialog(this, 
            "🛡️ Función 'Crear Equipos' en desarrollo.\n" +
            "Próximamente podrás crear y personalizar tus equipos.", 
            "Crear Equipos", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarEquipos() {
        JOptionPane.showMessageDialog(this, 
            "👥 Función 'Mostrar Equipos' en desarrollo.\n" +
            "Aquí podrás ver todos los equipos creados.", 
            "Mostrar Equipos", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void iniciarBatalla() {
        JOptionPane.showMessageDialog(this, 
            "⚔️ Función 'Iniciar Batalla' en desarrollo.\n" +
            "¡Prepárate para épicas batallas de Dragon Quest!", 
            "Iniciar Batalla", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void probarMecanicas() {
        JOptionPane.showMessageDialog(this, 
            "🧪 Función 'Prueba de Mecánicas' en desarrollo.\n" +
            "Aquí podrás probar las mecánicas del juego.", 
            "Prueba de Mecánicas", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0, 0, 0, 0)); // Transparente
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setForeground(new Color(255, 255, 150));
                boton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 150), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setForeground(Color.WHITE);
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}