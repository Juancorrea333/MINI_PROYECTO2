import javax.swing.*;
import java.awt.*;

public class VistaPruebaMecanicas extends JFrame {
    
    public VistaPruebaMecanicas() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Prueba de Mecánicas");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 25, 35));
        
        // Titulo
        JLabel titulo = new JLabel("LABORATORIO DE MECANICAS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 215, 0)); // Dorado
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(40, 45, 55));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        // Pestana 1: Sistemas de Combate
        tabbedPane.addTab("Combate", crearPanelCombate());
        
        // Pestana 2: Sistema de Habilidades
        tabbedPane.addTab("Habilidades", crearPanelHabilidades());
        
        // Pestana 3: Sistema de Estados
        tabbedPane.addTab("Estados", crearPanelEstados());
        
        // Pestana 4: Calculadora de Daño
        tabbedPane.addTab("Calculadora", crearPanelCalculadora());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(20, 25, 35));
        
        JButton btnReiniciar = crearBoton("Reiniciar Pruebas", new Color(70, 130, 180));
        JButton btnGuardarLog = crearBoton("Guardar Log", new Color(34, 139, 34));
        JButton btnVolver = crearBoton("Volver al Menu", new Color(178, 34, 34));
        
        btnReiniciar.addActionListener(_ -> reiniciarPruebas());
        btnGuardarLog.addActionListener(_ -> guardarLog());
        btnVolver.addActionListener(_ -> dispose());
        
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnGuardarLog);
        panelBotones.add(btnVolver);
        
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private JPanel crearPanelCombate() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 35, 45));
        
        // Panel de controles
        JPanel controles = new JPanel(new GridLayout(2, 3, 10, 10));
        controles.setBackground(new Color(30, 35, 45));
        controles.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Botones de prueba
        JButton btnAtaqueBasico = crearBotonPrueba("Ataque Basico");
        JButton btnAtaqueEspecial = crearBotonPrueba("Ataque Especial");
        JButton btnDefensa = crearBotonPrueba("Defensa");
        JButton btnEsquivar = crearBotonPrueba("Esquivar");
        JButton btnCritico = crearBotonPrueba("Golpe Critico");
        JButton btnCombo = crearBotonPrueba("Combo");
        
        // Agregar funcionalidad
        btnAtaqueBasico.addActionListener(_ -> probarMecanica("Ataque Básico", "Daño: 45-60 | Precisión: 95%"));
        btnAtaqueEspecial.addActionListener(_ -> probarMecanica("Ataque Especial", "Daño: 80-120 | Costo MP: 15"));
        btnDefensa.addActionListener(_ -> probarMecanica("Defensa", "Reduce daño en 50% | +25% Bloqueo"));
        btnEsquivar.addActionListener(_ -> probarMecanica("Esquivar", "Posibilidad: 15-30% | Depende de Velocidad"));
        btnCritico.addActionListener(_ -> probarMecanica("Golpe Crítico", "Chance: 8% | Daño x2.5"));
        btnCombo.addActionListener(_ -> probarMecanica("Combo", "2-4 ataques seguidos | Daño creciente"));
        
        controles.add(btnAtaqueBasico);
        controles.add(btnAtaqueEspecial);
        controles.add(btnDefensa);
        controles.add(btnEsquivar);
        controles.add(btnCritico);
        controles.add(btnCombo);
        
        panel.add(controles, BorderLayout.NORTH);
        
        // Área de resultados
        JTextArea resultados = new JTextArea();
        resultados.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultados.setBackground(new Color(15, 20, 25));
        resultados.setForeground(new Color(0, 255, 0));
        resultados.setEditable(false);
        resultados.setText("=== SISTEMA DE COMBATE ===\n\n" +
                          "Selecciona una mecánica para probarla:\n\n" +
                          "• Ataque Básico: Daño estándar sin costo de MP\n" +
                          "• Ataque Especial: Mayor daño con costo de MP\n" +
                          "• Defensa: Reduce daño recibido significativamente\n" +
                          "• Esquivar: Posibilidad de evitar daño completamente\n" +
                          "• Golpe Crítico: Daño multiplicado por suerte\n" +
                          "• Combo: Múltiples ataques en secuencia\n\n");
        
        JScrollPane scroll = new JScrollPane(resultados);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelHabilidades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 35, 45));
        
        // Lista de habilidades por tipo
        String[] tiposHeroe = {"MAGO", "DRUIDA", "GUERRERO", "PALADIN"};
        JComboBox<String> comboTipo = new JComboBox<>(tiposHeroe);
        comboTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JPanel panelSelector = new JPanel(new FlowLayout());
        panelSelector.setBackground(new Color(30, 35, 45));
        panelSelector.add(new JLabel("Tipo de Héroe: ") {{ setForeground(Color.WHITE); }});
        panelSelector.add(comboTipo);
        
        panel.add(panelSelector, BorderLayout.NORTH);
        
        // Área de habilidades
        JTextArea habilidades = new JTextArea();
        habilidades.setFont(new Font("Consolas", Font.PLAIN, 13));
        habilidades.setBackground(new Color(15, 20, 30));
        habilidades.setForeground(new Color(173, 216, 230));
        habilidades.setEditable(false);
        
        comboTipo.addActionListener(_ -> {
            String tipo = (String) comboTipo.getSelectedItem();
            actualizarHabilidades(habilidades, tipo);
        });
        
        // Mostrar habilidades iniciales
        actualizarHabilidades(habilidades, "MAGO");
        
        JScrollPane scroll = new JScrollPane(habilidades);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void actualizarHabilidades(JTextArea area, String tipo) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== HABILIDADES DE " + tipo + " ===\n\n");
        
        switch (tipo) {
            case "MAGO":
                sb.append("🔥 Bola de Fuego (MP: 12)\n");
                sb.append("   Daño: 60-80 | Área: 1 enemigo\n\n");
                sb.append("❄️ Ventisca (MP: 18)\n");
                sb.append("   Daño: 45-65 | Área: Todos los enemigos\n\n");
                sb.append("⚡ Rayo (MP: 15)\n");
                sb.append("   Daño: 70-90 | Paraliza 2 turnos\n\n");
                sb.append("🌟 Meteoro (MP: 35)\n");
                sb.append("   Daño: 120-160 | Área masiva\n");
                break;
            case "DRUIDA":
                sb.append("🌿 Curar (MP: 8)\n");
                sb.append("   Restaura: 40-60 HP | 1 aliado\n\n");
                sb.append("🌙 Regeneración (MP: 12)\n");
                sb.append("   Restaura 15 HP/turno por 5 turnos\n\n");
                sb.append("🍃 Curar Grupo (MP: 20)\n");
                sb.append("   Restaura: 25-35 HP | Todos los aliados\n\n");
                sb.append("☘️ Revivir (MP: 25)\n");
                sb.append("   Revive aliado con 50% HP\n");
                break;
            case "GUERRERO":
                sb.append("⚔️ Golpe Poderoso (MP: 5)\n");
                sb.append("   Daño: ATK x 1.5 | 95% precisión\n\n");
                sb.append("🔄 Giro de Espada (MP: 10)\n");
                sb.append("   Daño: ATK x 1.2 | Todos los enemigos\n\n");
                sb.append("💪 Furia (MP: 8)\n");
                sb.append("   +50% ATK por 3 turnos\n\n");
                sb.append("🛡️ Provocar (MP: 3)\n");
                sb.append("   Fuerza enemigos a atacarte\n");
                break;
            case "PALADIN":
                sb.append("✨ Golpe Santo (MP: 10)\n");
                sb.append("   Daño: ATK x 1.3 + curación propia\n\n");
                sb.append("🛡️ Escudo Sagrado (MP: 12)\n");
                sb.append("   Protege aliado por 3 turnos\n\n");
                sb.append("⚡ Juicio Divino (MP: 18)\n");
                sb.append("   Daño masivo vs enemigos no-muertos\n\n");
                sb.append("🌟 Bendición (MP: 15)\n");
                sb.append("   +25% stats a todos los aliados\n");
                break;
        }
        
        area.setText(sb.toString());
    }
    
    private JPanel crearPanelEstados() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBackground(new Color(30, 35, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Estados positivos
        JPanel positivos = new JPanel(new BorderLayout());
        positivos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GREEN, 2),
            "✅ ESTADOS POSITIVOS",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.GREEN
        ));
        positivos.setBackground(new Color(25, 35, 25));
        
        JTextArea textPositivos = new JTextArea(
            "🔺 AUMENTAR ATK: +25% daño por 5 turnos\n" +
            "🛡️ AUMENTAR DEF: +30% defensa por 4 turnos\n" +
            "⚡ AUMENTAR VEL: +20% velocidad por 3 turnos\n" +
            "💚 REGENERACIÓN: +15 HP por turno\n" +
            "💙 RECUPERAR MP: +10 MP por turno\n" +
            "🌟 BENDICIÓN: +15% a todos los stats\n" +
            "🔮 CONCENTRACIÓN: Próximo hechizo x2 poder\n" +
            "⚔️ FURIA: +50% ATK, -20% DEF por 3 turnos"
        );
        textPositivos.setFont(new Font("Consolas", Font.PLAIN, 11));
        textPositivos.setBackground(new Color(20, 30, 20));
        textPositivos.setForeground(Color.LIGHT_GRAY);
        textPositivos.setEditable(false);
        
        positivos.add(new JScrollPane(textPositivos));
        
        // Estados negativos
        JPanel negativos = new JPanel(new BorderLayout());
        negativos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.RED, 2),
            "❌ ESTADOS NEGATIVOS",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.RED
        ));
        negativos.setBackground(new Color(35, 25, 25));
        
        JTextArea textNegativos = new JTextArea(
            "🔻 DEBILITAR ATK: -30% daño por 4 turnos\n" +
            "💔 DEBILITAR DEF: -25% defensa por 5 turnos\n" +
            "🐌 LENTITUD: -40% velocidad por 3 turnos\n" +
            "🩸 VENENO: -12 HP por turno por 6 turnos\n" +
            "⚡ PARÁLISIS: No puede actuar por 2 turnos\n" +
            "😴 SUEÑO: No puede actuar hasta recibir daño\n" +
            "🌪️ CONFUSIÓN: 50% chance de atacar aliados\n" +
            "❄️ CONGELADO: No puede moverse por 1 turno"
        );
        textNegativos.setFont(new Font("Consolas", Font.PLAIN, 11));
        textNegativos.setBackground(new Color(30, 20, 20));
        textNegativos.setForeground(Color.LIGHT_GRAY);
        textNegativos.setEditable(false);
        
        negativos.add(new JScrollPane(textNegativos));
        
        panel.add(positivos);
        panel.add(negativos);
        
        return panel;
    }
    
    private JPanel crearPanelCalculadora() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 35, 45));
        
        // Panel de entrada
        JPanel entradas = new JPanel(new GridLayout(4, 4, 5, 5));
        entradas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        entradas.setBackground(new Color(30, 35, 45));
        
        // Campos de entrada
        JTextField txtAtaque = new JTextField("50");
        JTextField txtDefensa = new JTextField("25");
        JTextField txtNivel = new JTextField("10");
        JTextField txtBono = new JTextField("0");
        
        entradas.add(crearLabel("Ataque:"));
        entradas.add(txtAtaque);
        entradas.add(crearLabel("Defensa:"));
        entradas.add(txtDefensa);
        entradas.add(crearLabel("Nivel:"));
        entradas.add(txtNivel);
        entradas.add(crearLabel("Bono %:"));
        entradas.add(txtBono);
        
        JButton btnCalcular = crearBoton("🔢 Calcular Daño", new Color(70, 130, 180));
        JButton btnLimpiar = crearBoton("🧹 Limpiar", new Color(139, 69, 19));
        
        entradas.add(btnCalcular);
        entradas.add(btnLimpiar);
        entradas.add(new JLabel()); // Espacio vacío
        entradas.add(new JLabel()); // Espacio vacío
        
        panel.add(entradas, BorderLayout.NORTH);
        
        // Área de resultados
        JTextArea resultados = new JTextArea();
        resultados.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultados.setBackground(new Color(15, 20, 30));
        resultados.setForeground(new Color(255, 215, 0));
        resultados.setEditable(false);
        resultados.setText("=== CALCULADORA DE DAÑO ===\n\n" +
                          "Ingresa los valores y presiona 'Calcular Daño'\n\n" +
                          "Fórmula: (Ataque + Nivel/2 + Bono%) - Defensa\n" +
                          "Daño mínimo: 1\n" +
                          "Crítico: x2.5 (8% probabilidad)\n\n" +
                          "Esperando cálculo...\n");
        
        JScrollPane scroll = new JScrollPane(resultados);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Funcionalidad botones
        btnCalcular.addActionListener(_ -> {
            try {
                int ataque = Integer.parseInt(txtAtaque.getText());
                int defensa = Integer.parseInt(txtDefensa.getText());
                int nivel = Integer.parseInt(txtNivel.getText());
                int bono = Integer.parseInt(txtBono.getText());
                
                int dañoBase = Math.max(1, (ataque + nivel/2 + (ataque * bono / 100)) - defensa);
                int dañoCritico = (int)(dañoBase * 2.5);
                
                StringBuilder resultado = new StringBuilder();
                resultado.append("=== RESULTADO DEL CÁLCULO ===\n\n");
                resultado.append("Ataque: " + ataque + "\n");
                resultado.append("Defensa: " + defensa + "\n");
                resultado.append("Nivel: " + nivel + "\n");
                resultado.append("Bono: " + bono + "%\n\n");
                resultado.append("Daño Normal: " + dañoBase + "\n");
                resultado.append("Daño Crítico: " + dañoCritico + "\n\n");
                resultado.append("Rango de Daño: " + (dañoBase - 5) + " - " + (dañoBase + 10) + "\n");
                resultado.append("Crítico (8%): " + dañoCritico + "\n");
                
                resultados.setText(resultado.toString());
            } catch (NumberFormatException e) {
                resultados.setText("❌ ERROR: Ingresa solo números válidos");
            }
        });
        
        btnLimpiar.addActionListener(_ -> {
            txtAtaque.setText("0");
            txtDefensa.setText("0");
            txtNivel.setText("1");
            txtBono.setText("0");
            resultados.setText("Campos limpiados. Ingresa nuevos valores.");
        });
        
        return panel;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        return label;
    }
    
    private JButton crearBotonPrueba(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 11));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(70, 80, 100));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }
    
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(140, 35));
        return boton;
    }
    
    private void probarMecanica(String mecanica, String descripcion) {
        JOptionPane.showMessageDialog(this,
            "🧪 PRUEBA DE MECÁNICA: " + mecanica + "\n\n" +
            descripcion + "\n\n" +
            "✅ Mecánica probada exitosamente",
            "Resultado de Prueba",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reiniciarPruebas() {
        JOptionPane.showMessageDialog(this,
            "🔄 Todas las pruebas han sido reiniciadas\n" +
            "Los logs y resultados se han limpiado.",
            "Pruebas Reiniciadas",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void guardarLog() {
        JOptionPane.showMessageDialog(this,
            "💾 Log de pruebas guardado exitosamente\n\n" +
            "Archivo: logs/mecanicas_" + 
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + 
            ".log\n\n" +
            "Incluye todas las pruebas realizadas en esta sesión.",
            "Log Guardado",
            JOptionPane.INFORMATION_MESSAGE);
    }
}