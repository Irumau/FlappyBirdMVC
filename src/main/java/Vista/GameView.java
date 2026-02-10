/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*

THIS CLASS IS NO LONGER USE, BECAUSE IT'S USING A WRONG MVC DESIGN 
CHECK GAMEVIEWNEW
*/


package Vista;

import Modelo.BirdModel;
import Modelo.CloudsManager;
import Modelo.Cloud;
import Modelo.CoinManager;
import Modelo.Coin;
import Utils.GameState;
import Modelo.GameStatus;
import Modelo.HighScoreManager;
import Modelo.PipesManager;
import java.awt.AlphaComposite;
import Modelo.Pipe;
import Modelo.Portal;
import Modelo.PortalManager;
import Utils.Assets;

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
public class GameView extends JPanel {

    private BirdModel birdModel;

    private CloudsManager cloudsManger;

    private PipesManager pipesManager;

    private CoinManager coinManager;
    private HighScoreManager highScoreManager;

    private Portal portal;

    private GameStatus gameStatus;

    public GameView(BirdModel birdModel, PortalManager portalManager, CloudsManager cloudsManger, PipesManager pipesManager, CoinManager coinManager, HighScoreManager highScoreManager, GameStatus gameStatus) {
        this.birdModel = birdModel;
        this.cloudsManger = cloudsManger;
        this.pipesManager = pipesManager;
        this.gameStatus = gameStatus;
        this.coinManager = coinManager;
        this.portal = portalManager.getPortal();
        this.highScoreManager = highScoreManager;

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (Assets.birdSprites[0] == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        // a) Interpolación: Hace que al cambiar el tamaño de la imagen, los bordes no se vean pixelados "feos", sino suavizados.
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //LEER ESTADO DEL JUEGO
        if (gameStatus.getCurrentGameState() == GameState.MENU) {
            drawMenu(g2d);
        } else if (gameStatus.getCurrentGameState() == GameState.PLAYING || gameStatus.getCurrentGameState() == GameState.BONUS) {

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            if (gameStatus.getCurrentGameState() == GameState.BONUS) {
                // Fondo Morado (Bonus)
                g2d.setColor(new Color(50, 0, 100));
            } else {
                // Fondo Azul (Normal)
                g2d.setColor(new Color(135, 206, 235));

            }
            g2d.fillRect(0, 0, getWidth(), getHeight());

            drawClouds(g2d, 0.8f);

            //ZONA | DIBUJAR PORTAL |
            if (portal.isActive()) {
                int currentPortalIndex = portal.getCurrentSpriteIndex();

                g2d.drawImage(Assets.portalSprites[currentPortalIndex], portal.getX(), portal.getY(), 60, 100, null);
            }

            drawPipes(g2d);
            drawCoins(g2d);
            drawBird(g2d, birdModel.getWidth(), birdModel.getHeight());

            drawScoreAndTime(g2d);

        } else if (gameStatus.getCurrentGameState() == GameState.GAME_OVER) {
            drawGameOver(g2d);
        }

    }

    public void drawScoreAndTime(Graphics2D g2d) {
        //ZONA | DIBUJAR SCORE Y TIEMPO|
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        // Sombra del texto (para que se lea bien sobre el cielo azul)
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + gameStatus.getScore(), 622, 52);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + gameStatus.getScore(), 620, 50);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        g2d.drawString("Time: " + gameStatus.getElapsedTime() + "s", 620, 80);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        g2d.drawString("Time: " + gameStatus.getElapsedTime() + "s", 622, 80);
    }

    public void drawMenu(Graphics2D g2d) {
        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int xTitle = (getWidth() - Assets.titleSprite.getWidth()) / 2;
        int yTitle = 100; // Altura fija desde arriba

        drawClouds(g2d, 1.0f);
        drawBird(g2d, 200, 200);
        // ¡Mira qué limpio! Se dibuja normal, porque ya tiene el tamaño correcto.
        g2d.drawImage(Assets.titleSprite, xTitle, yTitle, null);

        g2d.drawImage(Assets.controlSprite, 20, 300, null);

        String startGuide = "Press SPACE/CLICK".toUpperCase();
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        g2d.setColor(Color.WHITE);
        g2d.drawString(startGuide, 430, 550);

        g2d.setColor(new Color(60, 60, 150));
        g2d.drawString(startGuide, 428, 550);

        //Dibujamos el mejor puntaje arriba a la derecha
        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));

        String scoreText = "RECORD: " + highScoreManager.getBestScore();

        // Calculamos el ancho del texto para saber dónde empezar a dibujar
        int textWidth = g2d.getFontMetrics().stringWidth(scoreText);

        int margenDerecho = 30; // 30 píxeles de separación del borde derecho
        int margenSuperior = 50; // 50 píxeles desde el techo

        // X = AnchoPantalla - AnchoTexto - Margen
        int xPos = getWidth() - textWidth - margenDerecho;

        // Dibujamos sombra negra pequeña para que se lea bien
        g2d.setColor(Color.BLACK);
        g2d.drawString(scoreText, xPos + 2, margenSuperior + 2);

        // Dibujamos texto naranja encima
        g2d.setColor(new Color(102, 51, 153));
        g2d.drawString(scoreText, xPos, margenSuperior);
    }

    public void drawClouds(Graphics2D g2d, float opacity) {
        // ZONA | DIBUJAR NUBES | 

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        for (Cloud cloud : cloudsManger.getClouds()) {
            BufferedImage imageCloud = Assets.cloudSprites[cloud.getTipo()];

            if (imageCloud != null) {
                g2d.drawImage(imageCloud, cloud.getX(), cloud.getY(), this);
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public void drawBird(Graphics2D g2d, int birdWith, int birdHeight) {
        //ZONA | DIBUJO DEL PAJARIO | 
        int birdX = birdModel.getX();
        int birdY = birdModel.getY();
        int birdW = birdWith;
        int birdH = birdHeight;
        double angulo = birdModel.getRotationAngle();

        //copia del pincel, por que si no rotariamos todo el mundo
        Graphics2D g2dBird = (Graphics2D) g2d.create();

        //mover el pivote del pajaro
        int centerX = birdX + birdW / 2;
        int centerY = birdY + birdH / 2;
        g2dBird.translate(centerX, centerY);

        g2dBird.rotate(Math.toRadians(angulo));

        BufferedImage currentSprite = Assets.birdSprites[birdModel.getCurrentSpriteIndex()];

        g2dBird.setColor(Color.BLACK);
        g2dBird.drawImage(
                currentSprite,
                -birdW / 2,
                -birdH / 2,
                birdW,
                birdH,
                null
        );

        if (!birdModel.isAlive()) {
            g2dBird.drawImage(Assets.birdSpriteHit, -birdW / 2, -birdH / 2, birdW, birdH, null);
        }

        //Dibujar hitbbox del pajarito
        //g2d.setColor(Color.RED);
        //g2d.draw(birdModel.getBounds());
        //Libera memoria , destruyendo la copia
        g2dBird.dispose();
    }

    public void drawCoins(Graphics2D g2d) {
        // ZONA | DIBUJAR MONEDAS |
        for (Coin coin : coinManager.getCoins()) {
            int index = coin.getCurrentSpriteIndex();

            g2d.drawImage(Assets.coinSprites[index], coin.getX(), coin.getY(), coin.getSIZE(), coin.getSIZE(), null);
        }

    }

    public void drawPipes(Graphics2D g2d) {
        // ZONA | DIBUJAR TUBOS |
        for (Pipe pipe : pipesManager.getPipes()) {

            if (Assets.pipeBelow != null) {
                g2d.drawImage(Assets.pipeBelow, pipe.getX(), pipe.getYspace(), Pipe.getPIPE_WIDTH(), 400, null);
            }

            int imageHeight = 400;
            int yPipeAbove = pipe.getYspace() - pipe.getGapHeight() - imageHeight;
            if (Assets.pipeAbove != null) {
                g2d.drawImage(Assets.pipeAbove, pipe.getX(), yPipeAbove, Pipe.getPIPE_WIDTH(), imageHeight, null);
            }
            //Debug
//            g2d.setColor(Color.BLUE);
//            g2d.draw(pipe.getRectanglePipeAbove());
//            g2d.draw(pipe.getRectanglePipeBelow());

        }
    }

    public void drawGameOver(Graphics2D g2d) {
        //ZONA GAME OVER
        // Configurar fuente bonita y grande
        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));

        String texto = "GAME OVER";

        // Matemáticas para centrar el texto perfectamente
        int anchoTexto = g2d.getFontMetrics().stringWidth(texto);
        int xTexto = (getWidth() - anchoTexto) / 2;
        int yTexto = getHeight() / 2;

        g2d.drawString(texto, xTexto, yTexto);

        // Subtítulo pequeño
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String subtexto = "Presiona 'R' para reiniciar";
        g2d.drawString(subtexto, (getWidth() - g2d.getFontMetrics().stringWidth(subtexto)) / 2, yTexto + 40);

        // Subtitulo de MENU
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String subtextoMenu = "Presiona 'M' para ir al menu";

        g2d.drawString(subtextoMenu, (getWidth() - g2d.getFontMetrics().stringWidth(subtexto)) / 2, yTexto + 60);

        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        String textCurrent = "Tu Puntaje: " + gameStatus.getScore();
        String textBest = "Mejor: " + highScoreManager.getBestScore();
        // Centrar Puntaje Actual
        int wCurrent = g2d.getFontMetrics().stringWidth(textCurrent);
        g2d.drawString(textCurrent, (getWidth() - wCurrent) / 2, yTexto + 100);

        // Centrar Mejor Puntaje (Debajo del actual)
        g2d.setColor(new Color(102, 51, 153)); // Le ponemos color naranja para resaltar
        int wBest = g2d.getFontMetrics().stringWidth(textBest);
        g2d.drawString(textBest, (getWidth() - wBest) / 2, yTexto + 160);

    }
    
    

}
