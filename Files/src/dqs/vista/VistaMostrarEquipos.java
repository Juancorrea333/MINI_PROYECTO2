package dqs.vista;

import java.awt.*;
import javax.swing.*;

/**
 * Vista para mostrar los equipos registrados y estadísticas.
 * Incluye pestañas para héroes, enemigos y estadísticas, y botones
 * para actualizar/exportar los datos.
 */
public class VistaMostrarEquipos extends JFrame {
    
    /** Constructor: prepara la UI para mostrar los equipos. */
    public VistaMostrarEquipos() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Mostrar Equipos");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 40));
        
        // Título
        JLabel titulo = new JLabel("EQUIPOS REGISTRADOS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(Color.CYAN);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Panel central con tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(40, 40, 60));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Tab Heroes
        JPanel panelHeroes = crearPanelEquipo("HEROES", true);
        tabbedPane.addTab("Heroes", panelHeroes);
        
        // Tab Enemigos
        JPanel panelEnemigos = crearPanelEquipo("ENEMIGOS", false);
        tabbedPane.addTab("Enemigos", panelEnemigos);
        
        // Tab Estadisticas
        JPanel panelStats = crearPanelEstadisticas();
        tabbedPane.addTab("Estadisticas", panelStats);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel de botones inferior
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(25, 25, 40));
        
        JButton btnActualizar = crearBoton("Actualizar");
        JButton btnExportar = crearBoton("Exportar");
        JButton btnVolver = crearBoton("Volver");
        
        btnActualizar.addActionListener(e -> actualizarDatos());
        btnExportar.addActionListener(e -> exportarEquipos());
        btnVolver.addActionListener(e -> dispose());
        
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnExportar);
        buttonPanel.add(btnVolver);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    /** Crea un panel con el listado textual del equipo (heroes o enemigos). */
    private JPanel crearPanelEquipo(String titulo, boolean esHeroes) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(35, 35, 55));
        
        // Área de texto para mostrar equipos
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(new Color(20, 20, 30));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Contenido simulado
        StringBuilder contenido = new StringBuilder();
        contenido.append(titulo).append("\n");
        contenido.append("═".repeat(40)).append("\n\n");
        
        if (esHeroes) {
            contenido.append("1. Aragorn (GUERRERO)\n");
            contenido.append("   HP: 280 | MP: 45 | ATK: 48 | DEF: 32\n");
            contenido.append("   Especialidad: Tanque y daño físico\n\n");
            
            contenido.append("2. Gandalf (MAGO)\n");
            contenido.append("   HP: 85 | MP: 285 | ATK: 38 | DEF: 18\n");
            contenido.append("   Especialidad: Hechizos de área\n\n");
            
            contenido.append("3. Legolas (DRUIDA)\n");
            contenido.append("   HP: 140 | MP: 180 | ATK: 35 | DEF: 25\n");
            contenido.append("   Especialidad: Curación y soporte\n\n");
            
            contenido.append("4. Arthas (PALADIN)\n");
            contenido.append("   HP: 165 | MP: 85 | ATK: 42 | DEF: 38\n");
            contenido.append("   Especialidad: Equilibrio entre ataque y defensa\n\n");
            
            contenido.append("5. [Posición vacía]\n");
        } else {
            contenido.append("1. Golem de Piedra (GOLEM)\n");
            contenido.append("   HP: 350 | MP: 0 | ATK: 55 | DEF: 45\n");
            contenido.append("   Tipo: Tanque pesado\n\n");
            
            contenido.append("2. Orco Salvaje (ORCO)\n");
            contenido.append("   HP: 220 | MP: 0 | ATK: 42 | DEF: 28\n");
            contenido.append("   Tipo: Daño físico medio\n\n");
            
            contenido.append("3. Dragón Anciano (DRAGON)\n");
            contenido.append("   HP: 480 | MP: 0 | ATK: 75 | DEF: 55\n");
            contenido.append("   Tipo: Jefe final\n\n");
            
            contenido.append("4. [Posición vacía]\n");
            contenido.append("5. [Posición vacía]\n");
        }
        
        textArea.setText(contenido.toString());
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(esHeroes ? Color.BLUE : Color.RED, 2),
            titulo,
            0, 0,
            new Font("SansSerif", Font.BOLD, 16),
            esHeroes ? Color.CYAN : Color.ORANGE
        ));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    /** Crea el panel que muestra tarjetas con estadísticas resumidas. */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(35, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tarjetas de estadisticas
        panel.add(crearTarjetaEstadistica("Total Heroes", "4/5", Color.BLUE));
        panel.add(crearTarjetaEstadistica("Total Enemigos", "3/5", Color.RED));
        panel.add(crearTarjetaEstadistica("Poder Promedio Heroes", "2,847", Color.GREEN));
        panel.add(crearTarjetaEstadistica("Poder Promedio Enemigos", "3,245", Color.ORANGE));
        
        return panel;
    }
    
    /** Genera una tarjeta visual para una estadística concreta. */
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(new Color(50, 50, 70));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setForeground(color);
        
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblValor.setForeground(Color.WHITE);
        
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    /** Crea botones estilizados usados en esta vista. */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(70, 70, 90));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(120, 35));
        return boton;
    }
    
    /** Acción simulada para actualizar datos (placeholder). */
    private void actualizarDatos() {
        JOptionPane.showMessageDialog(this, 
            """
            Datos actualizados correctamente.
            Se han sincronizado todos los equipos registrados.""", 
            "Actualizacion Exitosa", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Simula la exportación de equipos en distintos formatos. */
    private void exportarEquipos() {
        String[] opciones = {"Archivo de Texto", "Hoja de Calculo", "Base de Datos"};
        String seleccion = (String) JOptionPane.showInputDialog(this,
            "Selecciona el formato de exportacion:",
            "Exportar Equipos",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);
        
        if (seleccion != null) {
            JOptionPane.showMessageDialog(this,
                "Equipos exportados exitosamente en formato:\n" + seleccion +
                "\n\nArchivo guardado en: /exports/equipos_" + 
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + 
                ".txt",
                "Exportacion Completada",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}