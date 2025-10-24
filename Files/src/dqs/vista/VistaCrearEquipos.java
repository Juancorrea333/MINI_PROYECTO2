package dqs.vista;
import java.awt.*;
import javax.swing.*;

/**
 * Vista para crear y gestionar equipos de héroes y enemigos.
 * Permite crear héroes/enemigos mediante selectores visuales y mostrar
 * los equipos actualmente configurados.
 */
public class VistaCrearEquipos extends JFrame {
    private final String[] equipoHeroes;
    private final String[] equipoEnemigos;
    
    /** Constructor: inicializa los arreglos y componentes visuales. */
    public VistaCrearEquipos() {
        equipoHeroes = new String[5];
        equipoEnemigos = new String[5];
        inicializarComponentes();
    }
    
    /** Construye la interfaz gráfica: botones principales y layout. */
    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Crear Equipos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal con fondo oscuro
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));
        
        // Título
        JLabel titulo = new JLabel("CREAR EQUIPOS DE BATALLA", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Panel central con opciones
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        contentPanel.setBackground(new Color(30, 30, 50));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Botones principales con iconos
        JButton btnCrearHeroes = crearBotonConIcono("CREAR HEROES", new Color(50, 100, 200), "crear");
        JButton btnCrearEnemigos = crearBotonConIcono("CREAR ENEMIGOS", new Color(200, 50, 50), "crear");
        JButton btnVerEquipos = crearBotonConIcono("VER EQUIPOS", new Color(100, 150, 100), "ver");
        JButton btnVolver = crearBotonConIcono("VOLVER AL MENU", new Color(100, 100, 100), "");
        
        // Agregar funcionalidad a los botones
        btnCrearHeroes.addActionListener(e -> abrirCreadorHeroes());
        btnCrearEnemigos.addActionListener(e -> abrirCreadorEnemigos());
        btnVerEquipos.addActionListener(e -> mostrarEquipos());
        btnVolver.addActionListener(e -> dispose());
        
        contentPanel.add(btnCrearHeroes);
        contentPanel.add(btnCrearEnemigos);
        contentPanel.add(btnVerEquipos);
        contentPanel.add(btnVolver);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Panel informativo
        JLabel info = new JLabel("<html><center>Crea tus equipos de heroes y enemigos<br>para comenzar las batallas epicas</center></html>");
        info.setForeground(Color.LIGHT_GRAY);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(info, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /** Crea un botón con icono opcional (usa ImagenUtil para iconos). */
    private JButton crearBotonConIcono(String texto, Color colorFondo, String tipoIcono) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(200, 80));
        
        // Agregar icono si se especifica
        if (!tipoIcono.isEmpty()) {
            ImageIcon icono = ImagenUtil.crearIconoBoton(tipoIcono, Color.WHITE);
            boton.setIcon(icono);
            boton.setHorizontalTextPosition(SwingConstants.RIGHT);
            boton.setIconTextGap(10);
        }
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        
        return boton;
    }
    
    /** Abre el flujo interactivo para crear hasta 5 héroes. */
    private void abrirCreadorHeroes() {
        for (int i = 0; i < 5; i++) {
            if (equipoHeroes[i] == null) {
                String tipo = mostrarSelectorHeroeConImagenes(i + 1);
                
                if (tipo != null) {
                    String nombre = JOptionPane.showInputDialog(this, "Nombre del héroe:");
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        equipoHeroes[i] = nombre + " (" + tipo + ")";
                        JOptionPane.showMessageDialog(this, "Heroe " + nombre + " creado en la posicion " + (i + 1));
                    }
                } else {
                    break; // Usuario canceló
                }
            }
        }
    }
    
    /** Muestra un diálogo visual con opciones de héroes y devuelve el tipo elegido. */
    private String mostrarSelectorHeroeConImagenes(int posicion) {
        JDialog dialog = new JDialog(this, "Seleccionar Héroe - Posición " + posicion, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Título
        JLabel titulo = new JLabel("Selecciona un tipo de héroe:", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titulo, gbc);
        
        // Panel para los botones de selección
        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        botonesPanel.setOpaque(false);
        
        String[] tipos = {"MAGO", "DRUIDA", "GUERRERO", "PALADIN"};
        final String[] tipoSeleccionado = {null};
        ButtonGroup grupo = new ButtonGroup();
        
        for (String tipo : tipos) {
            JToggleButton botonHeroe = new JToggleButton();
            botonHeroe.setLayout(new BorderLayout());
            botonHeroe.setPreferredSize(new Dimension(120, 150));
            botonHeroe.setBackground(new Color(50, 50, 50));
            botonHeroe.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2));
            
            // Imagen del héroe
            ImageIcon imagenIcon = ImagenUtil.obtenerImagenHeroe(tipo);
            if (imagenIcon != null) {
                // Redimensionar imagen para el botón
                Image imagen = imagenIcon.getImage();
                Image imagenRedimensionada = imagen.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
                
                JLabel labelImagen = new JLabel(new ImageIcon(imagenRedimensionada), SwingConstants.CENTER);
                botonHeroe.add(labelImagen, BorderLayout.CENTER);
            }
            
            // Texto del tipo
            JLabel labelTipo = new JLabel(tipo, SwingConstants.CENTER);
            labelTipo.setFont(new Font("Arial", Font.BOLD, 12));
            labelTipo.setForeground(Color.WHITE);
            botonHeroe.add(labelTipo, BorderLayout.SOUTH);
            
            // Acción del botón
            botonHeroe.addActionListener(e -> {
                tipoSeleccionado[0] = tipo;
                botonHeroe.setBackground(new Color(0, 120, 215));
                botonHeroe.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 200), 3));
            });
            
            grupo.add(botonHeroe);
            botonesPanel.add(botonHeroe);
        }
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(botonesPanel, gbc);
        
        // Botones de confirmación
        JPanel botonesConfirmacion = new JPanel(new FlowLayout());
        botonesConfirmacion.setOpaque(false);
        
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(new Color(0, 150, 0));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConfirmar.addActionListener(e -> {
            if (tipoSeleccionado[0] != null) {
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Por favor selecciona un tipo de héroe.");
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(150, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.addActionListener(e -> {
            tipoSeleccionado[0] = null;
            dialog.dispose();
        });
        
        botonesConfirmacion.add(btnConfirmar);
        botonesConfirmacion.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(botonesConfirmacion, gbc);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setSize(320, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        return tipoSeleccionado[0];
    }
    
    /** Abre el flujo para crear enemigos en el equipo (hasta 5). */
    private void abrirCreadorEnemigos() {
        for (int i = 0; i < 5; i++) {
            if (equipoEnemigos[i] == null) {
                String tipoEnemigo = mostrarSelectorEnemigoConImagenes(i + 1);
                
                if (tipoEnemigo != null) {
                    equipoEnemigos[i] = "Enemigo " + (i + 1) + " (" + tipoEnemigo + ")";
                    JOptionPane.showMessageDialog(this, "Enemigo creado en la posicion " + (i + 1));
                } else {
                    break;
                }
            }
        }
    }
    
    /** Muestra un diálogo visual con botones de enemigos y devuelve el tipo elegido. */
    private String mostrarSelectorEnemigoConImagenes(int posicion) {
        String[] tiposEnemigos = {"GOLEM", "ORCO", "TROLL", "NOMUERTO", "DRAGON"};
        String[] tipoSeleccionado = {null};
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Enemigo - Posicion " + posicion, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        
        // Panel principal con fondo rojo oscuro
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(80, 20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Titulo
        JLabel titulo = new JLabel("Selecciona un tipo de Enemigo");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelPrincipal.add(titulo, gbc);
        
        // Botones de enemigos
        gbc.gridwidth = 1;
        for (int i = 0; i < tiposEnemigos.length; i++) {
            String tipo = tiposEnemigos[i];
            
            JButton btnEnemigo = new JButton();
            btnEnemigo.setPreferredSize(new Dimension(120, 120));
            btnEnemigo.setBackground(new Color(120, 40, 40));
            btnEnemigo.setBorder(BorderFactory.createLineBorder(new Color(200, 60, 60), 2));
            btnEnemigo.setFocusPainted(false);
            
            // Cargar imagen del enemigo
            ImageIcon imagenEnemigo = ImagenUtil.obtenerImagenEnemigoBoton(tipo);
            if (imagenEnemigo != null) {
                btnEnemigo.setIcon(imagenEnemigo);
            }
            
            // Texto del boton
            btnEnemigo.setText("<html><center>" + tipo + "</center></html>");
            btnEnemigo.setForeground(Color.WHITE);
            btnEnemigo.setFont(new Font("Arial", Font.BOLD, 12));
            btnEnemigo.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnEnemigo.setHorizontalTextPosition(SwingConstants.CENTER);
            
            // Hover effect
            btnEnemigo.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnEnemigo.setBackground(new Color(160, 60, 60));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnEnemigo.setBackground(new Color(120, 40, 40));
                }
            });
            
            btnEnemigo.addActionListener(e -> {
                tipoSeleccionado[0] = tipo;
                dialog.dispose();
            });
            
            gbc.gridx = i % 3;
            gbc.gridy = (i / 3) + 1;
            panelPrincipal.add(btnEnemigo, gbc);
        }
        
        // Boton cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(100, 30, 30));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(150, 50, 50)));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(btnCancelar, gbc);
        
        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        return tipoSeleccionado[0];
    }
    
    /** Muestra una ventana con el listado textual de los equipos actuales. */
    private void mostrarEquipos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EQUIPOS CREADOS ===\n\n");
        
        sb.append("EQUIPO DE HEROES:\n");
        for (int i = 0; i < equipoHeroes.length; i++) {
            sb.append(i + 1);
            if (equipoHeroes[i] != null) {
                sb.append(". ").append(equipoHeroes[i]).append("\n");
            } else {
                sb.append(". [Vacio]\n");
            }
        }
        
        sb.append("\nEQUIPO DE ENEMIGOS:\n");
        for (int i = 0; i < equipoEnemigos.length; i++) {
            sb.append(i + 1);
            if (equipoEnemigos[i] != null) {
                sb.append(". ").append(equipoEnemigos[i]).append("\n");
            } else {
                sb.append(". [Vacio]\n");
            }
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(40, 40, 60));
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Equipos Creados", JOptionPane.INFORMATION_MESSAGE);
    }
}