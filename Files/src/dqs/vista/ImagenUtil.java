package dqs.vista;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Utilidades para cargar y crear ImageIcons usados en las vistas.
 * Centraliza rutas, redimensionado y creación de iconos por texto como respaldo.
 */
public class ImagenUtil {
    
    private static final String RUTA_IMAGENES = "../Files/src/dqs/utilidades/";
    
    // Mapeo de tipos de heroes con sus imagenes
    /** Devuelve un ImageIcon representando el héroe según su tipo. */
    public static ImageIcon obtenerImagenHeroe(String tipoHeroe) {
        String nombreArchivo = switch (tipoHeroe.toUpperCase()) {
            case "MAGO" -> "Un mago de videojueg.png";
            case "DRUIDA" -> "Un druida teriantrop.png";
            case "GUERRERO" -> "Un guerrero de video.png";
            case "PALADIN" -> "Un paladín de videoj.png";
            default -> "";
        };
        
        if (nombreArchivo.isEmpty()) {
            return crearIconoTexto(tipoHeroe);
        }
        
        return cargarImagenRedimensionada(nombreArchivo, 64, 64);
    }
    
    // Metodo para obtener imagenes de enemigos
    /** Devuelve un ImageIcon del enemigo según su tipo. */
    public static ImageIcon obtenerImagenEnemigo(String tipoEnemigo) {
        String nombreArchivo = "";
        
        switch (tipoEnemigo.toUpperCase()) {
            case "ORCO":
                nombreArchivo = "dibuja un orco.png";
                break;
            case "TROLL":
                nombreArchivo = "dibuja un troll.png";
                break;
            case "DRAGON":
                nombreArchivo = "dragon.png";
                break;
            case "NOMUERTO":
                nombreArchivo = "Dibuja un nomuerto.png";
                break;
            case "GOLEM":
                nombreArchivo = "golem de piedra para.png";
                break;
            default:
                return crearIconoTexto(tipoEnemigo);
        }
        
        return cargarImagenRedimensionada(nombreArchivo, 64, 64);
    }
    
    // Obtener imagen de enemigo más pequeña para botones
    /** Devuelve un ImageIcon de tamaño reducido para botones (enemigos). */
    public static ImageIcon obtenerImagenEnemigoBoton(String tipoEnemigo) {
        String nombreArchivo = "";
        
        switch (tipoEnemigo.toUpperCase()) {
            case "ORCO":
                nombreArchivo = "dibuja un orco.png";
                break;
            case "TROLL":
                nombreArchivo = "dibuja un troll.png";
                break;
            case "DRAGON":
                nombreArchivo = "dragon.png";
                break;
            case "NOMUERTO":
                nombreArchivo = "Dibuja un nomuerto.png";
                break;
            case "GOLEM":
                nombreArchivo = "golem de piedra para.png";
                break;
            default:
                return crearIconoTexto(tipoEnemigo);
        }
        
        return cargarImagenRedimensionada(nombreArchivo, 32, 32);
    }
    
    // Obtener imagen más pequeña para botones
    /** Devuelve un ImageIcon de tamaño reducido para botones (héroes). */
    public static ImageIcon obtenerImagenHeroeBoton(String tipoHeroe) {
        String nombreArchivo = switch (tipoHeroe.toUpperCase()) {
            case "MAGO" -> "Un mago de videojueg.png";
            case "DRUIDA" -> "Un druida teriantrop.png";
            case "GUERRERO" -> "Un guerrero de video.png";
            case "PALADIN" -> "Un paladín de videoj.png";
            default -> "";
        };
        
        if (nombreArchivo.isEmpty()) {
            return crearIconoTexto(tipoHeroe);
        }
        
        return cargarImagenRedimensionada(nombreArchivo, 32, 32);
    }
    
    // Metodo principal para cargar y redimensionar imagenes
    /** Carga una imagen desde disco y la redimensiona, devolviendo un ImageIcon. */
    public static ImageIcon cargarImagenRedimensionada(String nombreArchivo, int ancho, int alto) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(new File(RUTA_IMAGENES + nombreArchivo));
            Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenRedimensionada);
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen: " + nombreArchivo + " - " + e.getMessage());
            return crearIconoTexto(nombreArchivo);
        }
    }
    
    // Crear icono de texto como respaldo
    /** Crea un ImageIcon simple con texto como respaldo cuando falta la imagen. */
    public static ImageIcon crearIconoTexto(String texto) {
        BufferedImage imagen = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();
        
        // Configurar el dibujo
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(70, 70, 90));
        g2d.fillRoundRect(0, 0, 64, 64, 10, 10);
        
        // Dibujar texto
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        
        String textoCorto = texto.length() > 8 ? texto.substring(0, 8) : texto;
        int x = (64 - fm.stringWidth(textoCorto)) / 2;
        int y = (64 + fm.getAscent()) / 2;
        
        g2d.drawString(textoCorto, x, y);
        g2d.dispose();
        
        return new ImageIcon(imagen);
    }
    
    // Iconos especiales para botones del menu
    /** Crea iconos vectoriales pequeños para botones del menú (dibujos simples). */
    public static ImageIcon crearIconoBoton(String tipo, Color color) {
        BufferedImage imagen = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(color);
        
        switch (tipo.toLowerCase()) {
            case "crear" -> {
                // Dibujar un simbolo de mas (+)
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(12, 6, 12, 18);
                g2d.drawLine(6, 12, 18, 12);
            }
            case "ver" -> {
                // Dibujar un ojo
                g2d.fillOval(6, 9, 12, 6);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(10, 11, 4, 2);
            }
            case "batalla" -> {
                // Dibujar espadas cruzadas
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(6, 6, 18, 18);
                g2d.drawLine(18, 6, 6, 18);
            }
            case "mecanicas" -> {
                // Dibujar engranaje
                g2d.fillOval(8, 8, 8, 8);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(10, 10, 4, 4);
            }
            default -> {
                // Icono generico - cuadrado
                g2d.fillRoundRect(4, 4, 16, 16, 4, 4);
            }
        }
        
        g2d.dispose();
        return new ImageIcon(imagen);
    }
}