package dqs.vista;

import java.awt.*;
import javax.swing.*;

/**
 * Vista de demostración de batalla (simulación rápida).
 * Esta clase proporciona una UI simplificada para visualizar barras de vida,
 * un log de batalla y controles básicos. No maneja la lógica real de combate.
 */
public class VistaIniciarBatalla extends JFrame {
    private JProgressBar barraVidaEnemigo1; // Solo se usa esta para la simulación
    private JTextArea logBatalla;
    private JLabel lblTurno;
    private int turnoActual = 1;
    
    /** Constructor: inicializa la UI y ejecuta una simulación automática. */
    public VistaIniciarBatalla() {
        inicializarComponentes();
        simularBatalla();
    }
    
    /** Construye todos los paneles y componentes de la ventana. */
    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Batalla Épica");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 20, 30));
        
        // Panel superior - Info de batalla
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(new Color(30, 30, 45));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("BATALLA EN CURSO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.YELLOW);
        
        lblTurno = new JLabel("Turno " + turnoActual, SwingConstants.CENTER);
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTurno.setForeground(Color.CYAN);
        
        panelInfo.add(titulo, BorderLayout.CENTER);
        panelInfo.add(lblTurno, BorderLayout.SOUTH);
        
        mainPanel.add(panelInfo, BorderLayout.NORTH);
        
        // Panel central - Campo de batalla
        JPanel campoBatalla = new JPanel(new BorderLayout());
        campoBatalla.setBackground(new Color(25, 25, 35));
        
        // Panel izquierdo - Heroes
        JPanel panelHeroes = crearPanelEquipoBatalla("HEROES", true);
        campoBatalla.add(panelHeroes, BorderLayout.WEST);
        
        // Panel central - Log de batalla
        JPanel panelLog = crearPanelLog();
        campoBatalla.add(panelLog, BorderLayout.CENTER);
        
        // Panel derecho - Enemigos
        JPanel panelEnemigos = crearPanelEquipoBatalla("ENEMIGOS", false);
        campoBatalla.add(panelEnemigos, BorderLayout.EAST);
        
        mainPanel.add(campoBatalla, BorderLayout.CENTER);
        
        // Panel inferior - Controles
        JPanel panelControles = crearPanelControles();
        mainPanel.add(panelControles, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /** Crea el panel lateral que contiene los personajes (héroes o enemigos). */
    private JPanel crearPanelEquipoBatalla(String titulo, boolean esHeroes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 50));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(esHeroes ? Color.BLUE : Color.RED, 2),
                titulo,
                0, 0,
                new Font("SansSerif", Font.BOLD, 14),
                esHeroes ? Color.CYAN : Color.ORANGE
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(280, 0));
        
        if (esHeroes) {
            // Agregar heroes
            panel.add(crearPersonajeBatalla("Aragorn", "GUERRERO", 280, 280, true));
            panel.add(Box.createVerticalStrut(10));
            panel.add(crearPersonajeBatalla("Gandalf", "MAGO", 85, 85, true));
            panel.add(Box.createVerticalStrut(10));
            panel.add(crearPersonajeBatalla("Legolas", "DRUIDA", 140, 140, true));
        } else {
            // Agregar enemigos
            panel.add(crearPersonajeBatalla("Golem de Piedra", "GOLEM", 350, 350, false));
            panel.add(Box.createVerticalStrut(10));
            panel.add(crearPersonajeBatalla("Dragon Anciano", "DRAGON", 480, 480, false));
        }
        
        return panel;
    }
    
    /** Crea el panel que representa un personaje con barra de vida. */
    private JPanel crearPersonajeBatalla(String nombre, String tipo, int hpActual, int hpMaximo, boolean esHeroe) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(45, 45, 60));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(esHeroe ? Color.BLUE : Color.RED, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // Nombre y tipo
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTipo = new JLabel("(" + tipo + ")");
        lblTipo.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblTipo.setForeground(Color.LIGHT_GRAY);
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Barra de vida
        JProgressBar barraVida = new JProgressBar(0, hpMaximo);
        barraVida.setValue(hpActual);
        barraVida.setStringPainted(true);
        barraVida.setString(hpActual + "/" + hpMaximo + " HP");
        barraVida.setFont(new Font("SansSerif", Font.PLAIN, 10));
        barraVida.setForeground(Color.GREEN);
        barraVida.setBackground(Color.DARK_GRAY);
        
        // Guardar referencias para actualizar despues (actualmente solo se usa barraVidaEnemigo1)
        // if (esHeroe && nombre.equals("Aragorn")) barraVidaHeroe1 = barraVida;
        // if (esHeroe && nombre.equals("Gandalf")) barraVidaHeroe2 = barraVida;
        if (!esHeroe && nombre.contains("Golem")) barraVidaEnemigo1 = barraVida;
        // if (!esHeroe && nombre.contains("Dragon")) barraVidaEnemigo2 = barraVida;
        
        panel.add(lblNombre);
        panel.add(lblTipo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(barraVida);
        
        return panel;
    }
    
    /** Crea el panel central con el log de batalla (texto). */
    private JPanel crearPanelLog() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.YELLOW, 2),
            "LOG DE BATALLA",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.YELLOW
        ));
        
        logBatalla = new JTextArea();
        logBatalla.setFont(new Font("Consolas", Font.PLAIN, 12));
        logBatalla.setBackground(new Color(15, 15, 25));
        logBatalla.setForeground(Color.WHITE);
        logBatalla.setEditable(false);
        logBatalla.setText("""
                          Batalla iniciada!
                          Los heroes se enfrentan a poderosos enemigos...
                          
                          Turno 1:
                          • Aragorn ataca al Golem de Piedra por 45 de daño
                          • Golem contraataca a Aragorn por 38 de daño
                          • Gandalf lanza Bola de Fuego al Dragon por 62 de daño
                          • Dragon usa Aliento de Fuego en area por 55 de daño
                          
                          Turno 2:
                          • Legolas cura a Aragorn por 35 HP
                          • Aragorn usa Golpe Poderoso al Golem por 68 de daño
                          • Golem esta gravemente herido...
                          
                          """);
        
        JScrollPane scrollPane = new JScrollPane(logBatalla);
        scrollPane.setPreferredSize(new Dimension(400, 0));
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    /** Crea los controles inferiores (siguiente turno, autoplay, etc.). */
    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(30, 30, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton btnSiguienteTurno = crearBoton("Siguiente Turno", new Color(70, 130, 70));
        JButton btnAutoPlayBatalla = crearBoton("Auto-Batalla", new Color(130, 70, 70));
        JButton btnPausarBatalla = crearBoton("Pausar", new Color(130, 130, 70));
        JButton btnTerminarBatalla = crearBoton("Volver al Menu", new Color(70, 70, 130));
        
        btnSiguienteTurno.addActionListener(e -> siguienteTurno());
        btnAutoPlayBatalla.addActionListener(e -> autoBatalla());
        btnPausarBatalla.addActionListener(e -> pausarBatalla());
        btnTerminarBatalla.addActionListener(e -> dispose());
        
        panel.add(btnSiguienteTurno);
        panel.add(btnAutoPlayBatalla);
        panel.add(btnPausarBatalla);
        panel.add(btnTerminarBatalla);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(150, 35));
        return boton;
    }
    
    /** Simulación simple que reduce la vida de un enemigo periódicamente. */
    private void simularBatalla() {
        // Simular daño inicial en las barras de vida
        Timer timer = new Timer(1000, e -> {
            if (barraVidaEnemigo1 != null && barraVidaEnemigo1.getValue() > 100) {
                barraVidaEnemigo1.setValue(barraVidaEnemigo1.getValue() - 15);
                barraVidaEnemigo1.setString(barraVidaEnemigo1.getValue() + "/" + barraVidaEnemigo1.getMaximum() + " HP");
            }
        });
        timer.start();
    }
    
    /** Avanza la simulación un turno y agrega una entrada al log. */
    private void siguienteTurno() {
        turnoActual++;
        lblTurno.setText("Turno " + turnoActual);
        
        String[] acciones = {
            "• Aragorn usa Corte Devastador por 72 de daño\n",
            "• Gandalf invoca Rayo Celestial por 85 de daño\n",
            "• Legolas dispara Flecha Perforante por 58 de daño\n",
            "• Golem golpea con Puño de Piedra por 45 de daño\n",
            "• Dragon ruge intimidando a todos los heroes\n"
        };
        
        String accion = acciones[(int) (Math.random() * acciones.length)];
        logBatalla.append("Turno " + turnoActual + ":\n" + accion + "\n");
        logBatalla.setCaretPosition(logBatalla.getDocument().getLength());
    }
    
    /** Muestra información sobre el modo de auto-batalla. */
    private void autoBatalla() {
        JOptionPane.showMessageDialog(this,
            """
            Modo Auto-Batalla activado
            La batalla continuara automaticamente cada 2 segundos.
            
            Puedes pausar en cualquier momento.""",
            "Auto-Batalla",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Muestra un mensaje indicando que la batalla está pausada. */
    private void pausarBatalla() {
        JOptionPane.showMessageDialog(this,
            """
            Batalla pausada
            Usa 'Siguiente Turno' para continuar manualmente.""",
            "Batalla Pausada",
            JOptionPane.INFORMATION_MESSAGE);
    }
}