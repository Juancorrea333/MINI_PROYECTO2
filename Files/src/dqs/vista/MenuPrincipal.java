package dqs.vista;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Ventana principal del juego.
 * Contiene el menú de navegación hacia las distintas vistas (crear equipos,
 * mostrar equipos, iniciar batalla y pruebas de mecánicas).
 * Esta clase se encarga únicamente de la interfaz; la lógica de juego
 * reside en las clases del paquete `dqs.modelos` y en `BatallaManager`.
 */
public class MenuPrincipal extends JFrame {

    private BufferedImage backgroundImage;

    /** Constructor: inicializa la ventana y componentes visuales. */
    public MenuPrincipal() {
        setTitle("Dragon Quest VIII - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar ventana
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        // Cargar imagen de fondo
        try {
            // Intentar múltiples rutas posibles
            File imgFile = null;
            String[] posiblesRutas = {
                "src/dqs/utilidades/Fondo de menú princi.png",
                "Files/src/dqs/utilidades/Fondo de menú princi.png",
                "../src/dqs/utilidades/Fondo de menú princi.png",
                "../../src/dqs/utilidades/Fondo de menú princi.png"
            };
            
            for (String ruta : posiblesRutas) {
                File f = new File(ruta);
                if (f.exists()) {
                    imgFile = f;
                    break;
                }
            }
            
            if (imgFile != null) {
                backgroundImage = ImageIO.read(imgFile);
                System.out.println("✓ Imagen de fondo cargada desde: " + imgFile.getAbsolutePath());
            } else {
                System.err.println("⚠️ No se encontró la imagen de fondo en ninguna ruta conocida");
            }
        } catch (IOException e) {
            System.err.println("⚠️ Error al cargar la imagen de fondo: " + e.getMessage());
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
        JButton btnPruebas = crearBoton("4. Prueba de Mecanicas");
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
    btnSalir.addActionListener(e -> System.exit(0));}

    // Listeners para los demás botones
    /* 
    btnCrear.addActionListener(e -> abrirVentanaCrearEquipos());
    btnMostrar.addActionListener(e -> mostrarEquipos());
    btnBatalla.addActionListener(e -> iniciarBatalla());
    btnPruebas.addActionListener(e -> probarMecanicas());
    }
*/
    // Metodos de navegacion entre vistas
    /* 
    private void abrirVentanaCrearEquipos() {
        VistaCrearEquipos vistaCrearEquipos = new VistaCrearEquipos();
        vistaCrearEquipos.setVisible(true);
    }

    private void mostrarEquipos() {
        VistaMostrarEquipos vistaMostrarEquipos = new VistaMostrarEquipos();
        vistaMostrarEquipos.setVisible(true);
    }

    private void iniciarBatalla() {
        VistaIniciarBatallaNueva vistaIniciarBatalla = new VistaIniciarBatallaNueva();
        vistaIniciarBatalla.setVisible(true);
        this.setVisible(false);
    }

    private void probarMecanicas() {
        VistaPruebaMecanicas vistaPruebaMecanicas = new VistaPruebaMecanicas();
        vistaPruebaMecanicas.setVisible(true);
    }*/

    /** Crea un botón estilizado usado por el menú principal. */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0, 0, 0, 120)); // Semi-transparente negro
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setOpaque(false);
        boton.setContentAreaFilled(true);

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setForeground(new Color(255, 255, 150));
                boton.setBackground(new Color(50, 50, 0, 150)); // Fondo dorado semi-transparente
                boton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 150), 2));
                boton.repaint(); // Forzar repintado
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setForeground(Color.WHITE);
                boton.setBackground(new Color(0, 0, 0, 120)); // Volver al fondo original
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                boton.repaint(); // Forzar repintado
            }
        });
        return boton;
    }

    /** Punto de entrada para ejecutar únicamente la ventana del menú. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}
