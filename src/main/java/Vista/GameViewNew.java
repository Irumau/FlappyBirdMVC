/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Utils.GameState; // Solo importamos el Enum, es inofensivo
import Utils.Assets;
import Utils.GameFrameDTO; // Importamos nuestra "Caja de Datos"

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Mauricio
 */




public class GameViewNew extends JPanel{

private GameFrameDTO currentFrame;

    public GameViewNew() {
        setFocusable(true);
        // El constructor ya no recibe modelos, está vacío y listo para trabajar
    }

    /**
     * Este es el método CLAVE. El controlador llamará a esto pasándole los datos.
     */
    public void render(GameFrameDTO frameData) {
        this.currentFrame = frameData;
        this.repaint(); // Llama indirectamente a paintComponent
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Si aún no hemos recibido el primer frame de datos, no dibujamos nada
        if (currentFrame == null || Assets.birdSprites[0] == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // USAMOS currentFrame EN LUGAR DE gameStatus
        if (currentFrame.currentState == GameState.MENU) {
            drawMenu(g2d);
        } else if (currentFrame.currentState == GameState.PLAYING || currentFrame.currentState == GameState.BONUS) {

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            
            if (currentFrame.currentState == GameState.BONUS) {
                g2d.setColor(new Color(50, 0, 100)); // Fondo Morado
            } else {
                g2d.setColor(new Color(135, 206, 235)); // Fondo Azul
            }
            g2d.fillRect(0, 0, getWidth(), getHeight());

            drawClouds(g2d, 0.8f);

            // ZONA | DIBUJAR PORTAL |
            if (currentFrame.isPortalActive) {
                g2d.drawImage(Assets.portalSprites[currentFrame.portalSpriteIndex], 
                              currentFrame.portalX, currentFrame.portalY, 60, 100, null);
            }

            drawPipes(g2d);
            drawCoins(g2d);
            
            // Pasamos los datos primitivos que vienen en el DTO
            drawBird(g2d, currentFrame.birdWidth, currentFrame.birdHeight);

            drawScoreAndTime(g2d);

        } else if (currentFrame.currentState == GameState.GAME_OVER) {
            drawGameOver(g2d);
        }
    }
    
    public void drawScoreAndTime(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        // Usamos currentFrame.score y currentFrame.elapsedTime
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + currentFrame.score, 622, 52);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + currentFrame.score, 620, 50);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        g2d.drawString("Time: " + currentFrame.elapsedTime + "s", 620, 80);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        g2d.drawString("Time: " + currentFrame.elapsedTime + "s", 622, 80);
    }

    public void drawMenu(Graphics2D g2d) {
        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int xTitle = (getWidth() - Assets.titleSprite.getWidth()) / 2;
        int yTitle = 100;

        drawClouds(g2d, 1.0f);
        
        // Dibujamos un pajaro estático o del DTO para el menu
        // Como drawBird usa currentFrame internamente, funcionará si el controlador manda datos en el menu
        drawBird(g2d, 200, 200); 
        
        g2d.drawImage(Assets.titleSprite, xTitle, yTitle, null);
        g2d.drawImage(Assets.controlSprite, 20, 300, null);

        String startGuide = "Press SPACE/CLICK".toUpperCase();
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        g2d.setColor(Color.WHITE);
        g2d.drawString(startGuide, 430, 550);

        g2d.setColor(new Color(60, 60, 150));
        g2d.drawString(startGuide, 428, 550);

        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));

        String scoreText = "RECORD: " + currentFrame.bestScore;

        int textWidth = g2d.getFontMetrics().stringWidth(scoreText);
        int margenDerecho = 30;
        int margenSuperior = 50;
        int xPos = getWidth() - textWidth - margenDerecho;

        g2d.setColor(Color.BLACK);
        g2d.drawString(scoreText, xPos + 2, margenSuperior + 2);

        g2d.setColor(new Color(102, 51, 153));
        g2d.drawString(scoreText, xPos, margenSuperior);
    }

    public void drawClouds(Graphics2D g2d, float opacity) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        
        // Iteramos sobre la lista de DATOS, no sobre objetos nube del modelo
        for (GameFrameDTO.CloudData c : currentFrame.clouds) {
            BufferedImage imageCloud = Assets.cloudSprites[c.type];
            if (imageCloud != null) {
                g2d.drawImage(imageCloud, c.x, c.y, this);
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public void drawBird(Graphics2D g2d, int birdW, int birdH) {
        // Obtenemos datos primitivos del DTO
        int birdX = currentFrame.birdX;
        int birdY = currentFrame.birdY;
        double angulo = currentFrame.birdRotation;

        Graphics2D g2dBird = (Graphics2D) g2d.create();

        int centerX = birdX + birdW / 2;
        int centerY = birdY + birdH / 2;
        g2dBird.translate(centerX, centerY);

        g2dBird.rotate(Math.toRadians(angulo));

        BufferedImage currentSprite = Assets.birdSprites[currentFrame.birdSpriteIndex];

        g2dBird.setColor(Color.BLACK);
        g2dBird.drawImage(currentSprite, -birdW / 2, -birdH / 2, birdW, birdH, null);

        if (!currentFrame.isBirdAlive) {
            g2dBird.drawImage(Assets.birdSpriteHit, -birdW / 2, -birdH / 2, birdW, birdH, null);
        }

        g2dBird.dispose();
    }

    public void drawCoins(Graphics2D g2d) {
        for (GameFrameDTO.CoinData c : currentFrame.coins) {
            g2d.drawImage(Assets.coinSprites[c.spriteIndex], c.x, c.y, c.size, c.size, null);
        }
    }

    public void drawPipes(Graphics2D g2d) {
        
        int pipeW = currentFrame.pipeWidth; 

        for (GameFrameDTO.PipeData p : currentFrame.pipes) {
            if (Assets.pipeBelow != null) {
                g2d.drawImage(Assets.pipeBelow, p.x, p.ySpace, pipeW, 400, null);
            }

            int imageHeight = 400;
            int yPipeAbove = p.ySpace - p.gapHeight - imageHeight;
            
            if (Assets.pipeAbove != null) {
                g2d.drawImage(Assets.pipeAbove, p.x, yPipeAbove, pipeW, imageHeight, null);
            }
        }
    }

    public void drawGameOver(Graphics2D g2d) {
        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));

        String texto = "GAME OVER";
        int anchoTexto = g2d.getFontMetrics().stringWidth(texto);
        int xTexto = (getWidth() - anchoTexto) / 2;
        int yTexto = getHeight() / 2;

        g2d.drawString(texto, xTexto, yTexto);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String subtexto = "Presiona 'R' para reiniciar";
        g2d.drawString(subtexto, (getWidth() - g2d.getFontMetrics().stringWidth(subtexto)) / 2, yTexto + 40);

        String subtextoMenu = "Presiona 'M' para ir al menu";
        g2d.drawString(subtextoMenu, (getWidth() - g2d.getFontMetrics().stringWidth(subtexto)) / 2, yTexto + 60);

        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        String textCurrent = "Tu Puntaje: " + currentFrame.score;
        String textBest = "Mejor: " + currentFrame.bestScore;
        
        int wCurrent = g2d.getFontMetrics().stringWidth(textCurrent);
        g2d.drawString(textCurrent, (getWidth() - wCurrent) / 2, yTexto + 100);

        g2d.setColor(new Color(102, 51, 153));
        int wBest = g2d.getFontMetrics().stringWidth(textBest);
        g2d.drawString(textBest, (getWidth() - wBest) / 2, yTexto + 160);
    }
    
}
