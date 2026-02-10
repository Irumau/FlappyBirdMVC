/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mauricio
 */
public class ImageLoader {

    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Error faltal cargando imagen: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // 1. Crear una nueva imagen vacía del tamaño objetivo que soporte transparencia (ARGB)
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        // 2. Obtener el "pincel" (Graphics2D) para dibujar en esa nueva imagen vacía
        Graphics2D g2d = resizedImage.createGraphics();

        // 3. Configurar la calidad (IMPORTANTE para que el texto no se vea borroso o pixelado)
        // Usamos interpolación BILINEAR o BICUBIC para suavizar al cambiar tamaño
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 4. Dibujar la imagen original estirándola para que quepa en el nuevo lienzo
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

        // 5. Liberar el pincel
        g2d.dispose();

        // 6. Devolver la nueva imagen pequeñita
        return resizedImage;
    }

}
