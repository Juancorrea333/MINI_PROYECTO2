import javax.swing.*;
import java.awt.*;

public class VistaIniciarBatallaNueva extends JFrame {
    private String[] equipoHeroes;
    private String[] equipoEnemigos;
    private BatallaManager batallaManager;
    private JPanel panelEstado;
    private JTextArea areaLog;
    private JButton btnIniciarBatalla;
    private JButton btnCargarEquipos;
    private JButton btnVolverMenu;
    
    public VistaIniciarBatallaNueva() {
        inicializarComponentes();
        equipoHeroes = new String[5];
        equipoEnemigos = new String[5];
    }
    
    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Batalla Real");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        
        // Configurar fondo
        getContentPane().setBackground(new Color(20, 20, 30));
        
        // Panel superior - Estado de la batalla
        panelEstado = new JPanel(new GridLayout(2, 5, 10, 5));
        panelEstado.setBackground(new Color(30, 30, 40));
        panelEstado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.CYAN, 2),
            "Estado de la Batalla",
            0, 0, new Font("Arial", Font.BOLD, 14), Color.CYAN));
        
        inicializarPanelEstado();
        
        add(panelEstado, BorderLayout.NORTH);
        
        // Panel central - Log de batalla
        areaLog = new JTextArea();
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaLog.setBackground(new Color(15, 15, 25));
        areaLog.setForeground(Color.WHITE);
        areaLog.setEditable(false);
        areaLog.setText("=== SISTEMA DE BATALLA REAL ===\n" +
                        "1. Primero carga equipos de héroes y enemigos\n" +
                        "2. Haz clic en 'Iniciar Batalla' para comenzar\n" +
                        "3. Selecciona acciones para cada héroe en su turno\n" +
                        "4. Los enemigos actuarán automáticamente\n\n" +
                        "Tipos de Héroes disponibles:\n" +
                        "- MAGO: Alto MP, hechizos poderosos\n" +
                        "- DRUIDA: Curación y magia de la naturaleza\n" +
                        "- GUERRERO: Alto HP, ataques físicos fuertes\n" +
                        "- PALADIN: Equilibrado, puede curar y atacar\n\n" +
                        "¡Prepárate para la batalla!\n\n");
        
        JScrollPane scrollPane = new JScrollPane(areaLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GREEN, 2),
            "Log de Batalla",
            0, 0, new Font("Arial", Font.BOLD, 14), Color.GREEN));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior - Controles
        JPanel panelControles = crearPanelControles();
        add(panelControles, BorderLayout.SOUTH);
        
        // Inicializar BatallaManager
        batallaManager = new BatallaManager(this, panelEstado, areaLog);
        
        setVisible(true);
    }
    
    private void inicializarPanelEstado() {
        // Primera fila - Héroes
        for (int i = 0; i < 5; i++) {
            JPanel panelHeroe = new JPanel(new BorderLayout());
            panelHeroe.setBackground(new Color(40, 40, 50));
            panelHeroe.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            panelHeroe.setPreferredSize(new Dimension(120, 80));
            
            // Imagen del héroe (placeholder inicialmente)
            JLabel lblImagenHeroe = new JLabel("", SwingConstants.CENTER);
            lblImagenHeroe.setPreferredSize(new Dimension(60, 60));
            
            // Texto del héroe
            JLabel lblTextoHeroe = new JLabel("Héroe " + (i + 1) + ": [Vacío]", SwingConstants.CENTER);
            lblTextoHeroe.setFont(new Font("Arial", Font.BOLD, 10));
            lblTextoHeroe.setForeground(Color.LIGHT_GRAY);
            
            panelHeroe.add(lblImagenHeroe, BorderLayout.CENTER);
            panelHeroe.add(lblTextoHeroe, BorderLayout.SOUTH);
            panelEstado.add(panelHeroe);
        }
        
        // Segunda fila - Enemigos
        for (int i = 0; i < 5; i++) {
            JPanel panelEnemigo = new JPanel(new BorderLayout());
            panelEnemigo.setBackground(new Color(40, 40, 50));
            panelEnemigo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            panelEnemigo.setPreferredSize(new Dimension(120, 80));
            
            // Imagen del enemigo (placeholder inicialmente)
            JLabel lblImagenEnemigo = new JLabel("", SwingConstants.CENTER);
            lblImagenEnemigo.setPreferredSize(new Dimension(60, 60));
            
            // Texto del enemigo
            JLabel lblTextoEnemigo = new JLabel("Enemigo " + (i + 1) + ": [Vacío]", SwingConstants.CENTER);
            lblTextoEnemigo.setFont(new Font("Arial", Font.BOLD, 10));
            lblTextoEnemigo.setForeground(Color.LIGHT_GRAY);
            
            panelEnemigo.add(lblImagenEnemigo, BorderLayout.CENTER);
            panelEnemigo.add(lblTextoEnemigo, BorderLayout.SOUTH);
            panelEstado.add(panelEnemigo);
        }
    }
    
    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(20, 20, 30));
        
        btnCargarEquipos = new JButton("Cargar Equipos");
        btnCargarEquipos.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarEquipos.setBackground(new Color(0, 120, 215));
        btnCargarEquipos.setForeground(Color.WHITE);
        btnCargarEquipos.setFocusPainted(false);
        btnCargarEquipos.addActionListener(e -> cargarEquipos());
        
        btnIniciarBatalla = new JButton("Iniciar Batalla");
        btnIniciarBatalla.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciarBatalla.setBackground(new Color(0, 150, 0));
        btnIniciarBatalla.setForeground(Color.WHITE);
        btnIniciarBatalla.setFocusPainted(false);
        btnIniciarBatalla.setEnabled(false);
        btnIniciarBatalla.addActionListener(e -> iniciarBatalla());
        
        btnVolverMenu = new JButton("Volver al Menú");
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolverMenu.setBackground(new Color(150, 0, 0));
        btnVolverMenu.setForeground(Color.WHITE);
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.addActionListener(e -> volverAlMenu());
        
        panel.add(btnCargarEquipos);
        panel.add(btnIniciarBatalla);
        panel.add(btnVolverMenu);
        
        return panel;
    }
    
    private void cargarEquipos() {
        // Crear diálogo para cargar equipos
        JDialog dialog = new JDialog(this, "Cargar Equipos", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panelPrincipal = new JPanel(new GridLayout(2, 1, 10, 10));
        panelPrincipal.setBackground(new Color(30, 30, 40));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de héroes
        JPanel panelHeroes = new JPanel(new BorderLayout());
        panelHeroes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            "Equipo de Héroes",
            0, 0, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        panelHeroes.setBackground(new Color(30, 30, 40));
        
        JTextArea areaHeroes = new JTextArea(8, 40);
        areaHeroes.setFont(new Font("Arial", Font.PLAIN, 12));
        areaHeroes.setBackground(new Color(40, 40, 50));
        areaHeroes.setForeground(Color.WHITE);
        areaHeroes.setText("Introduce los héroes (uno por línea):\n" +
                          "Aragorn (GUERRERO)\n" +
                          "Gandalf (MAGO)\n" +
                          "Legolas (DRUIDA)\n" +
                          "Gimli (PALADIN)");
        
        panelHeroes.add(new JScrollPane(areaHeroes), BorderLayout.CENTER);
        
        // Panel de enemigos
        JPanel panelEnemigos = new JPanel(new BorderLayout());
        panelEnemigos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.RED, 2),
            "Equipo de Enemigos",
            0, 0, new Font("Arial", Font.BOLD, 14), Color.RED));
        panelEnemigos.setBackground(new Color(30, 30, 40));
        
        JTextArea areaEnemigos = new JTextArea(8, 40);
        areaEnemigos.setFont(new Font("Arial", Font.PLAIN, 12));
        areaEnemigos.setBackground(new Color(40, 40, 50));
        areaEnemigos.setForeground(Color.WHITE);
        areaEnemigos.setText("Introduce los enemigos (uno por línea):\n" +
                            "Orco Salvaje (ORCO)\n" +
                            "Troll Gigante (TROLL)\n" +
                            "Dragon Rojo (DRAGON)");
        
        panelEnemigos.add(new JScrollPane(areaEnemigos), BorderLayout.CENTER);
        
        panelPrincipal.add(panelHeroes);
        panelPrincipal.add(panelEnemigos);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(30, 30, 40));
        
        JButton btnCargar = new JButton("Cargar Equipos");
        btnCargar.setBackground(new Color(0, 150, 0));
        btnCargar.setForeground(Color.WHITE);
        btnCargar.addActionListener(ev -> {
            String[] lineasHeroes = areaHeroes.getText().split("\n");
            String[] lineasEnemigos = areaEnemigos.getText().split("\n");
            
            // Limpiar equipos
            equipoHeroes = new String[5];
            equipoEnemigos = new String[5];
            
            // Procesar héroes
            int indexHeroe = 0;
            for (String linea : lineasHeroes) {
                linea = linea.trim();
                if (!linea.isEmpty() && !linea.startsWith("Introduce") && indexHeroe < 5) {
                    if (linea.contains("(") && linea.contains(")")) {
                        equipoHeroes[indexHeroe] = linea;
                        indexHeroe++;
                    }
                }
            }
            
            // Procesar enemigos
            int indexEnemigo = 0;
            for (String linea : lineasEnemigos) {
                linea = linea.trim();
                if (!linea.isEmpty() && !linea.startsWith("Introduce") && indexEnemigo < 5) {
                    if (linea.contains("(") && linea.contains(")")) {
                        equipoEnemigos[indexEnemigo] = linea;
                        indexEnemigo++;
                    }
                }
            }
            
            actualizarPanelEstado();
            btnIniciarBatalla.setEnabled(verificarEquipos());
            
            if (verificarEquipos()) {
                JOptionPane.showMessageDialog(dialog, "¡Equipos cargados exitosamente!");
            } else {
                JOptionPane.showMessageDialog(dialog, "Necesitas al menos un héroe y un enemigo.");
            }
            
            dialog.dispose();
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(150, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        panelBotones.add(btnCargar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private boolean verificarEquipos() {
        boolean hayHeroes = false, hayEnemigos = false;
        
        for (String heroe : equipoHeroes) {
            if (heroe != null && !heroe.trim().isEmpty()) {
                hayHeroes = true;
                break;
            }
        }
        
        for (String enemigo : equipoEnemigos) {
            if (enemigo != null && !enemigo.trim().isEmpty()) {
                hayEnemigos = true;
                break;
            }
        }
        
        return hayHeroes && hayEnemigos;
    }
    
    private void actualizarPanelEstado() {
        Component[] componentes = panelEstado.getComponents();
        
        // Actualizar héroes (primeros 5 componentes)
        for (int i = 0; i < 5; i++) {
            JPanel panelHeroe = (JPanel) componentes[i];
            JLabel lblImagen = (JLabel) panelHeroe.getComponent(0); // Imagen
            JLabel lblTexto = (JLabel) panelHeroe.getComponent(1);  // Texto
            
            if (equipoHeroes[i] != null && !equipoHeroes[i].trim().isEmpty()) {
                String nombre = equipoHeroes[i].split(" \\(")[0];
                String tipoCompleto = equipoHeroes[i];
                
                // Extraer tipo del héroe
                String tipo = extraerTipoHeroe(tipoCompleto);
                
                // Actualizar imagen
                ImageIcon imagenHeroe = ImagenUtil.obtenerImagenHeroeBoton(tipo);
                if (imagenHeroe != null) {
                    lblImagen.setIcon(imagenHeroe);
                } else {
                    lblImagen.setIcon(null);
                }
                
                // Actualizar texto
                lblTexto.setText("<html><center>" + nombre + "<br>(" + tipo + ")</center></html>");
                lblTexto.setForeground(Color.GREEN);
                panelHeroe.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            } else {
                lblImagen.setIcon(null);
                lblTexto.setText("Héroe " + (i + 1) + ": [Vacío]");
                lblTexto.setForeground(Color.LIGHT_GRAY);
                panelHeroe.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
        }
        
        // Actualizar enemigos (siguientes 5 componentes)
        for (int i = 0; i < 5; i++) {
            JPanel panelEnemigo = (JPanel) componentes[i + 5];
            JLabel lblImagen = (JLabel) panelEnemigo.getComponent(0); // Imagen
            JLabel lblTexto = (JLabel) panelEnemigo.getComponent(1);  // Texto
            
            if (equipoEnemigos[i] != null && !equipoEnemigos[i].trim().isEmpty()) {
                String nombre = equipoEnemigos[i].split(" \\(")[0];
                String tipoCompleto = equipoEnemigos[i];
                
                // Extraer tipo del enemigo
                String tipo = extraerTipoEnemigo(tipoCompleto);
                
                // Actualizar imagen
                ImageIcon imagenEnemigo = ImagenUtil.obtenerImagenEnemigoBoton(tipo);
                if (imagenEnemigo != null) {
                    lblImagen.setIcon(imagenEnemigo);
                } else {
                    lblImagen.setIcon(null);
                }
                
                // Actualizar texto
                lblTexto.setText("<html><center>" + nombre + "<br>(" + tipo + ")</center></html>");
                lblTexto.setForeground(Color.RED);
                panelEnemigo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else {
                lblImagen.setIcon(null);
                lblTexto.setText("Enemigo " + (i + 1) + ": [Vacío]");
                lblTexto.setForeground(Color.LIGHT_GRAY);
                panelEnemigo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        }
    }
    
    private void iniciarBatalla() {
        if (!verificarEquipos()) {
            JOptionPane.showMessageDialog(this,
                "Necesitas al menos un héroe y un enemigo para iniciar la batalla.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        btnIniciarBatalla.setEnabled(false);
        btnCargarEquipos.setEnabled(false);
        
        // Actualizar el panel de estado con las barras de salud del BatallaManager
        panelEstado.removeAll();
        panelEstado.setLayout(new GridLayout(2, 5, 5, 5));
        
        // Agregar barras de salud de héroes
        for (JLabel label : batallaManager.getHeroHealthBars()) {
            label.setOpaque(true);
            label.setBackground(new Color(40, 40, 50));
            label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
            label.setFont(new Font("Arial", Font.PLAIN, 10));
            panelEstado.add(label);
        }
        
        // Agregar barras de salud de enemigos
        for (JLabel label : batallaManager.getEnemyHealthBars()) {
            label.setOpaque(true);
            label.setBackground(new Color(40, 40, 50));
            label.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            label.setFont(new Font("Arial", Font.PLAIN, 10));
            panelEstado.add(label);
        }
        
        panelEstado.revalidate();
        panelEstado.repaint();
        
        // Iniciar la batalla con los equipos cargados
        batallaManager.iniciarBatallaConEquipos(equipoHeroes, equipoEnemigos);
    }
    
    private String extraerTipoHeroe(String heroeCompleto) {
        // Extrae el tipo entre paréntesis, ej: "Aragorn (GUERRERO)" -> "GUERRERO"
        if (heroeCompleto.contains("(") && heroeCompleto.contains(")")) {
            int inicio = heroeCompleto.lastIndexOf("(") + 1;
            int fin = heroeCompleto.lastIndexOf(")");
            String tipo = heroeCompleto.substring(inicio, fin).toUpperCase().trim();
            
            // Mapear tipos comunes
            switch (tipo) {
                case "WARRIOR":
                case "GUERRERO":
                    return "GUERRERO";
                case "MAGE":
                case "WIZARD":
                case "MAGO":
                    return "MAGO";
                case "DRUID":
                case "DRUIDA":
                    return "DRUIDA";
                case "PALADIN":
                    return "PALADIN";
                default:
                    return tipo;
            }
        }
        return "GUERRERO"; // Por defecto
    }
    
    private String extraerTipoEnemigo(String enemigoCompleto) {
        // Extrae el tipo entre paréntesis, ej: "Orco Salvaje (ORCO)" -> "ORCO"
        if (enemigoCompleto.contains("(") && enemigoCompleto.contains(")")) {
            int inicio = enemigoCompleto.lastIndexOf("(") + 1;
            int fin = enemigoCompleto.lastIndexOf(")");
            String tipo = enemigoCompleto.substring(inicio, fin).toUpperCase().trim();
            
            // Mapear tipos comunes
            switch (tipo) {
                case "ORC":
                case "ORCO":
                    return "ORCO";
                case "TROLL":
                    return "TROLL";
                case "DRAGON":
                    return "DRAGON";
                case "UNDEAD":
                case "NOMUERTO":
                    return "NOMUERTO";
                case "GOLEM":
                    return "GOLEM";
                default:
                    return tipo;
            }
        }
        return "ORCO"; // Por defecto
    }
    
    private void volverAlMenu() {
        dispose();
    }
}