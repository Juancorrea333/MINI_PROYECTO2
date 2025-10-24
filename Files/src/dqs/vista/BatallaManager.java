package dqs.vista;

import javax.swing.*;
import dqs.modelos.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrador de la lógica de la batalla y la interacción con la UI.
 *
 * Recibe referencias a la ventana principal y al área de log para mostrar
 * mensajes durante la batalla. Orquesta turnos de héroes y enemigos y
 * actualiza las barras de salud mostradas en la interfaz.
 */
public class BatallaManager {
    private JFrame parentFrame;
    private JTextArea logArea;
    private Batalla batalla;
    private JLabel[] heroHealthBars;
    private JLabel[] enemyHealthBars;
    private Heroe heroeActual;
    private int turnoActual;
    private boolean batallaEnCurso;
    
    /**
     * Constructor.
     * @param parent ventana padre usada para diálogos
     * @param logPanel panel opcional (no usado internamente actualmente)
     * @param logArea área de texto donde se escribirán los mensajes de batalla
     */
    public BatallaManager(JFrame parent, JPanel logPanel, JTextArea logArea) {
        this.parentFrame = parent;
        this.logArea = logArea;
        this.batalla = new Batalla();
        this.batallaEnCurso = false;
        inicializarBarrasSalud();
    }
    
    private void inicializarBarrasSalud() {
        heroHealthBars = new JLabel[4];
        enemyHealthBars = new JLabel[3];
        
        for (int i = 0; i < 4; i++) {
            heroHealthBars[i] = new JLabel("Héroe " + (i + 1) + ": [Vacío]");
        }
        for (int i = 0; i < 3; i++) {
            enemyHealthBars[i] = new JLabel("Enemigo " + (i + 1) + ": [Vacío]");
        }
    }
    
    /** Inicia la batalla construyendo héroes y enemigos desde los arrays dados. */
    public void iniciarBatallaConEquipos(String[] heroes, String[] enemigos) {
        // Resetear batalla
        batalla = new Batalla();
        turnoActual = 0;
        batallaEnCurso = true;
        
        // Crear héroes desde los strings del equipo
        for (int i = 0; i < heroes.length && i < 5; i++) {
            if (heroes[i] != null && !heroes[i].trim().isEmpty()) {
                Heroe heroe = crearHeroeDesdeString(heroes[i], i + 1);
                if (heroe != null) {
                    batalla.agregarHeroe(heroe, i);
                }
            }
        }
        
        // Crear enemigos desde los strings del equipo
        for (int i = 0; i < enemigos.length && i < 5; i++) {
            if (enemigos[i] != null && !enemigos[i].trim().isEmpty()) {
                Enemigo enemigo = crearEnemigoDesdeString(enemigos[i], i + 1);
                if (enemigo != null) {
                    batalla.agregarEnemigo(enemigo, i);
                }
            }
        }
        
        // Verificar que hay al menos un héroe y un enemigo
        if (!verificarEquiposValidos()) {
            JOptionPane.showMessageDialog(parentFrame,
                "Necesitas al menos un héroe y un enemigo para iniciar la batalla.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            batallaEnCurso = false;
            return;
        }
        
        actualizarBarrasSalud();
        agregarLogMensaje("¡La batalla ha comenzado!");
        mostrarEstadoInicial();
        iniciarTurnoHeroe();
    }
    
    /** Crea una instancia de Heroe a partir de un string con formato 'Nombre (TIPO)'. */
    private Heroe crearHeroeDesdeString(String heroeString, int numero) {
        try {
            // Formato: "Nombre (TIPO)"
            String[] partes = heroeString.split(" \\(");
            if (partes.length >= 2) {
                String nombre = partes[0].trim();
                String tipoStr = partes[1].replace(")", "").trim();
                
                Tipo_Heroe tipo = Tipo_Heroe.valueOf(tipoStr);
                
                // Crear héroe con stats basados en el tipo
                int hp, mp, ataque, defensa, velocidad;
                switch (tipo) {
                    case MAGO:
                        hp = 80; mp = 150; ataque = 45; defensa = 20; velocidad = 25;
                        break;
                    case DRUIDA:
                        hp = 120; mp = 200; ataque = 35; defensa = 25; velocidad = 20;
                        break;
                    case GUERRERO:
                        hp = 200; mp = 50; ataque = 50; defensa = 40; velocidad = 15;
                        break;
                    case PALADIN:
                        hp = 180; mp = 100; ataque = 45; defensa = 35; velocidad = 18;
                        break;
                    default:
                        hp = 100; mp = 100; ataque = 40; defensa = 30; velocidad = 20;
                }
                
                return new Heroe(nombre, tipo, hp, mp, ataque, defensa, velocidad);
            }
        } catch (Exception e) {
            agregarLogMensaje("Error creando héroe: " + heroeString);
        }
        return null;
    }
    
    /** Crea una instancia de Enemigo a partir de un string con formato 'Nombre (TIPO)'. */
    private Enemigo crearEnemigoDesdeString(String enemigoString, int numero) {
        try {
            // Formato: "Nombre (TIPO)"
            String[] partes = enemigoString.split(" \\(");
            if (partes.length >= 2) {
                String nombre = partes[0].trim();
                String tipoStr = partes[1].replace(")", "").trim();
                
                Tipo_Enemigo tipo = Tipo_Enemigo.valueOf(tipoStr);
                return Enemigo.crearEnemigo(tipo, nombre);
            }
        } catch (Exception e) {
            agregarLogMensaje("Error creando enemigo: " + enemigoString);
        }
        return null;
    }
    
    /** Verifica que exista al menos un héroe y un enemigo en sus equipos. */
    private boolean verificarEquiposValidos() {
        boolean hayHeroes = false, hayEnemigos = false;
        
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null) {
                hayHeroes = true;
                break;
            }
        }
        
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null) {
                hayEnemigos = true;
                break;
            }
        }
        
        return hayHeroes && hayEnemigos;
    }
    
    /** Muestra en el log el estado inicial de héroes y enemigos. */
    private void mostrarEstadoInicial() {
        agregarLogMensaje("\n=== ESTADO INICIAL ===");
        
        agregarLogMensaje("\nHÉROES:");
        Heroe[] heroes = batalla.getEquipoHeroes();
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null) {
                agregarLogMensaje((i + 1) + ". " + heroes[i].getNombre() + 
                    " [" + heroes[i].getTipo() + "] HP: " + heroes[i].getHp() + 
                    " MP: " + heroes[i].getMp());
            }
        }
        
        agregarLogMensaje("\nENEMIGOS:");
        Enemigo[] enemigos = batalla.getEquipoEnemigos();
        for (int i = 0; i < enemigos.length; i++) {
            if (enemigos[i] != null) {
                agregarLogMensaje((i + 1) + ". " + enemigos[i].getNombre() + 
                    " [" + enemigos[i].getTipo() + "] HP: " + enemigos[i].getHp() + 
                    " MP: " + enemigos[i].getMp());
            }
        }
    }
    
    /** Inicia el turno del siguiente héroe vivo y muestra su menú de acciones. */
    private void iniciarTurnoHeroe() {
        if (!batallaEnCurso) return;
        
        // Buscar el siguiente héroe vivo
        Heroe[] heroes = batalla.getEquipoHeroes();
        heroeActual = null;
        
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null && heroes[i].esta_vivo()) {
                heroeActual = heroes[i];
                break;
            }
        }
        
        if (heroeActual == null) {
            // No hay héroes vivos
            finalizarBatalla("¡Los enemigos han ganado!");
            return;
        }
        
        agregarLogMensaje("\n=== TURNO DE " + heroeActual.getNombre().toUpperCase() + " ===");
        agregarLogMensaje("HP: " + heroeActual.getHp() + " | MP: " + heroeActual.getMp());
        
        mostrarMenuAcciones();
    }
    
    /** Muestra un diálogo modal con las acciones disponibles para el héroe actual. */
    private void mostrarMenuAcciones() {
        JDialog dialogAcciones = new JDialog(parentFrame, "Acciones de " + heroeActual.getNombre(), true);
        dialogAcciones.setLayout(new GridBagLayout());
        dialogAcciones.getContentPane().setBackground(new Color(40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel titulo = new JLabel("¿Qué acción desea realizar?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        dialogAcciones.add(titulo, gbc);
        
        // Botones de acciones
        List<JButton> botones = new ArrayList<>();
        
        // Atacar (siempre disponible)
        JButton btnAtacar = crearBotonAccion("Atacar Enemigo", Color.RED);
        btnAtacar.addActionListener(e -> {
            dialogAcciones.dispose();
            mostrarSeleccionEnemigo();
        });
        botones.add(btnAtacar);
        
        // Acciones específicas por tipo
        Tipo_Heroe tipo = heroeActual.getTipo();
        
        if (tipo == Tipo_Heroe.GUERRERO || tipo == Tipo_Heroe.PALADIN) {
            JButton btnDefender = crearBotonAccion("Defender Aliado", Color.BLUE);
            btnDefender.addActionListener(e -> {
                dialogAcciones.dispose();
                mostrarSeleccionAliado("defender");
            });
            botones.add(btnDefender);
            
            JButton btnProvocar = crearBotonAccion("Provocar Enemigo", Color.ORANGE);
            btnProvocar.addActionListener(e -> {
                dialogAcciones.dispose();
                mostrarSeleccionEnemigo("provocar");
            });
            botones.add(btnProvocar);
        }
        
        if (tipo == Tipo_Heroe.DRUIDA || tipo == Tipo_Heroe.PALADIN) {
            JButton btnCurar = crearBotonAccion("Curar Aliado", Color.GREEN);
            btnCurar.addActionListener(e -> {
                dialogAcciones.dispose();
                mostrarSeleccionAliado("curar");
            });
            botones.add(btnCurar);
            
            JButton btnRestaurarMana = crearBotonAccion("Restaurar Mana", Color.CYAN);
            btnRestaurarMana.addActionListener(e -> {
                dialogAcciones.dispose();
                mostrarSeleccionAliado("mana");
            });
            botones.add(btnRestaurarMana);
        }
        
        if (tipo == Tipo_Heroe.PALADIN) {
            JButton btnRevivir = crearBotonAccion("Revivir Aliado", Color.MAGENTA);
            btnRevivir.addActionListener(e -> {
                dialogAcciones.dispose();
                mostrarSeleccionAliado("revivir");
            });
            botones.add(btnRevivir);
        }
        
        // Pasar turno
        JButton btnPasar = crearBotonAccion("Pasar Turno", Color.GRAY);
        btnPasar.addActionListener(e -> {
            dialogAcciones.dispose();
            agregarLogMensaje(heroeActual.getNombre() + " pasa su turno.");
            finalizarTurnoHeroe();
        });
        botones.add(btnPasar);
        
        // Añadir botones al diálogo
        int fila = 1;
        for (int i = 0; i < botones.size(); i++) {
            gbc.gridx = i % 2;
            gbc.gridy = fila + (i / 2);
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            dialogAcciones.add(botones.get(i), gbc);
        }
        
        dialogAcciones.setSize(400, 300);
        dialogAcciones.setLocationRelativeTo(parentFrame);
        dialogAcciones.setVisible(true);
    }
    
    /** Crea un botón estilizado para las acciones del héroe. */
    private JButton crearBotonAccion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }
    
    /** Atajo para mostrar el selector de enemigos con la acción 'atacar'. */
    private void mostrarSeleccionEnemigo() {
        mostrarSeleccionEnemigo("atacar");
    }
    
    /** Muestra lista de enemigos vivos y ejecuta la acción seleccionada. */
    private void mostrarSeleccionEnemigo(String accion) {
        List<Enemigo> enemigosVivos = new ArrayList<>();
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null && enemigo.esta_vivo()) {
                enemigosVivos.add(enemigo);
            }
        }
        
        if (enemigosVivos.isEmpty()) {
            finalizarBatalla("¡Los héroes han ganado!");
            return;
        }
        
        String[] opciones = new String[enemigosVivos.size()];
        for (int i = 0; i < enemigosVivos.size(); i++) {
            Enemigo enemigo = enemigosVivos.get(i);
            opciones[i] = enemigo.getNombre() + " [" + enemigo.getTipo() + "] HP: " + enemigo.getHp();
        }
        
        String seleccion = (String) JOptionPane.showInputDialog(
            parentFrame,
            "Selecciona el enemigo:",
            "Selección de Objetivo",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion != null) {
            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(seleccion)) {
                    Enemigo objetivo = enemigosVivos.get(i);
                    ejecutarAccionContraEnemigo(accion, objetivo);
                    break;
                }
            }
        } else {
            // Cancelado, mostrar menú de acciones otra vez
            mostrarMenuAcciones();
        }
    }
    
    /** Muestra lista de aliados (vivos o caídos) según la acción y ejecuta la selección. */
    private void mostrarSeleccionAliado(String accion) {
        List<Heroe> heroesDisponibles = new ArrayList<>();
        
        // Para revivir, buscar héroes muertos; para otras acciones, héroes vivos
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null) {
                if (accion.equals("revivir") && !heroe.esta_vivo()) {
                    heroesDisponibles.add(heroe);
                } else if (!accion.equals("revivir") && heroe.esta_vivo()) {
                    heroesDisponibles.add(heroe);
                }
            }
        }
        
        if (heroesDisponibles.isEmpty()) {
            String mensaje = accion.equals("revivir") ? 
                "No hay héroes caídos para revivir." : 
                "No hay héroes disponibles.";
            JOptionPane.showMessageDialog(parentFrame, mensaje);
            mostrarMenuAcciones();
            return;
        }
        
        String[] opciones = new String[heroesDisponibles.size()];
        for (int i = 0; i < heroesDisponibles.size(); i++) {
            Heroe heroe = heroesDisponibles.get(i);
            opciones[i] = heroe.getNombre() + " [" + heroe.getTipo() + "] HP: " + heroe.getHp() + " MP: " + heroe.getMp();
        }
        
        String seleccion = (String) JOptionPane.showInputDialog(
            parentFrame,
            "Selecciona el aliado:",
            "Selección de Aliado",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion != null) {
            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(seleccion)) {
                    Heroe objetivo = heroesDisponibles.get(i);
                    ejecutarAccionConAliado(accion, objetivo);
                    break;
                }
            }
        } else {
            mostrarMenuAcciones();
        }
    }
    
    /** Ejecuta la acción seleccionada contra un enemigo objetivo. */
    private void ejecutarAccionContraEnemigo(String accion, Enemigo objetivo) {
        switch (accion) {
            case "atacar":
                heroeActual.atacar(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " ataca a " + objetivo.getNombre() + "!");
                break;
            case "provocar":
                heroeActual.provocarEnemigo(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " provoca a " + objetivo.getNombre() + "!");
                break;
        }
        
        actualizarBarrasSalud();
        finalizarTurnoHeroe();
    }
    
    /** Ejecuta la acción seleccionada sobre un aliado objetivo. */
    private void ejecutarAccionConAliado(String accion, Heroe objetivo) {
        switch (accion) {
            case "defender":
                heroeActual.defender(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " defiende a " + objetivo.getNombre() + "!");
                break;
            case "curar":
                heroeActual.curar(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " cura a " + objetivo.getNombre() + "!");
                break;
            case "mana":
                heroeActual.restaurarMana(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " restaura mana a " + objetivo.getNombre() + "!");
                break;
            case "revivir":
                heroeActual.revivir(objetivo);
                agregarLogMensaje(heroeActual.getNombre() + " revive a " + objetivo.getNombre() + "!");
                break;
        }
        
        actualizarBarrasSalud();
        finalizarTurnoHeroe();
    }
    
    /** Finaliza el turno del héroe y pone en marcha el turno enemigo / siguiente héroe. */
    private void finalizarTurnoHeroe() {
        // Verificar victoria
        if (verificarVictoria()) return;
        
        // Turno de enemigos
        ejecutarTurnoEnemigos();
        
        // Verificar victoria después del turno enemigo
        if (verificarVictoria()) return;
        
        // Siguiente turno de héroe
        Timer timer = new Timer(1500, e -> iniciarTurnoHeroe());
        timer.setRepeats(false);
        timer.start();
    }
    
    /** Ejecuta las acciones de los enemigos (IA simple) contra héroes vivos. */
    private void ejecutarTurnoEnemigos() {
        agregarLogMensaje("\n=== TURNO DE LOS ENEMIGOS ===");
        
        Enemigo[] enemigos = batalla.getEquipoEnemigos();
        for (Enemigo enemigo : enemigos) {
            if (enemigo != null && enemigo.esta_vivo()) {
                // IA simple: atacar a un héroe al azar
                List<Heroe> heroesVivos = new ArrayList<>();
                for (Heroe heroe : batalla.getEquipoHeroes()) {
                    if (heroe != null && heroe.esta_vivo()) {
                        heroesVivos.add(heroe);
                    }
                }
                
                if (!heroesVivos.isEmpty()) {
                    Heroe objetivo = heroesVivos.get((int)(Math.random() * heroesVivos.size()));
                    enemigo.atacar(objetivo);
                    agregarLogMensaje(enemigo.getNombre() + " ataca a " + objetivo.getNombre() + "!");
                }
            }
        }
        
        actualizarBarrasSalud();
    }
    
    /** Comprueba condiciones de victoria/derrota y finaliza la batalla si procede. */
    private boolean verificarVictoria() {
        boolean heroesVivos = false, enemigosVivos = false;
        
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && heroe.esta_vivo()) {
                heroesVivos = true;
                break;
            }
        }
        
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null && enemigo.esta_vivo()) {
                enemigosVivos = true;
                break;
            }
        }
        
        if (!heroesVivos) {
            finalizarBatalla("¡Los enemigos han ganado!");
            return true;
        } else if (!enemigosVivos) {
            finalizarBatalla("¡Los héroes han ganado!");
            return true;
        }
        
        return false;
    }
    
    /** Finaliza la batalla, registra el resultado y muestra un diálogo informativo. */
    private void finalizarBatalla(String resultado) {
        batallaEnCurso = false;
        agregarLogMensaje("\n=== FIN DE LA BATALLA ===");
        agregarLogMensaje(resultado);
        
        JOptionPane.showMessageDialog(parentFrame, resultado, "¡Batalla Terminada!", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Actualiza las etiquetas que muestran HP/MP y estado (vivo/muerto). */
    private void actualizarBarrasSalud() {
        // Actualizar barras de héroes
        Heroe[] heroes = batalla.getEquipoHeroes();
        for (int i = 0; i < heroHealthBars.length; i++) {
            if (i < heroes.length && heroes[i] != null) {
                Heroe heroe = heroes[i];
                String estado = heroe.esta_vivo() ? "VIVO" : "MUERTO";
                heroHealthBars[i].setText(heroe.getNombre() + " [" + heroe.getTipo() + "] " +
                    "HP: " + heroe.getHp() + " MP: " + heroe.getMp() + " - " + estado);
                heroHealthBars[i].setForeground(heroe.esta_vivo() ? Color.GREEN : Color.RED);
            } else {
                heroHealthBars[i].setText("Héroe " + (i + 1) + ": [Vacío]");
                heroHealthBars[i].setForeground(Color.GRAY);
            }
        }
        
        // Actualizar barras de enemigos
        Enemigo[] enemigos = batalla.getEquipoEnemigos();
        for (int i = 0; i < enemyHealthBars.length; i++) {
            if (i < enemigos.length && enemigos[i] != null) {
                Enemigo enemigo = enemigos[i];
                String estado = enemigo.esta_vivo() ? "VIVO" : "MUERTO";
                enemyHealthBars[i].setText(enemigo.getNombre() + " [" + enemigo.getTipo() + "] " +
                    "HP: " + enemigo.getHp() + " MP: " + enemigo.getMp() + " - " + estado);
                enemyHealthBars[i].setForeground(enemigo.esta_vivo() ? Color.GREEN : Color.RED);
            } else {
                enemyHealthBars[i].setText("Enemigo " + (i + 1) + ": [Vacío]");
                enemyHealthBars[i].setForeground(Color.GRAY);
            }
        }
    }
    
    /** Añade un mensaje al área de log de forma segura en el hilo de Swing. */
    private void agregarLogMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(mensaje + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    public JLabel[] getHeroHealthBars() { return heroHealthBars; }
    public JLabel[] getEnemyHealthBars() { return enemyHealthBars; }
    public boolean isBatallaEnCurso() { return batallaEnCurso; }
}