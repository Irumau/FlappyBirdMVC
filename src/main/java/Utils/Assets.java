/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Modelo.BirdModel;
import Modelo.Coin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mauricio
 */
public class Assets {

    public static BufferedImage[] birdSprites;
    public static BufferedImage birdSpriteHit;

    public static BufferedImage[] cloudSprites;

    public static BufferedImage pipeBelow, pipeAbove;

    public static BufferedImage[] coinSprites;
    public static BufferedImage[] portalSprites;

    public static BufferedImage titleSprite, controlSprite;

    public static void init() {

        loadBirdSprites();
        loadCloudsSprites();
        loadCoinSprites();
        loadPipeSprites();
        loadPortalSprites();
        loadUISprites();
        
        System.out.println("Assets cargados globalmente.");
    }

    public static void loadBirdSprites() {

        birdSprites = new BufferedImage[BirdModel.totalSprites];

        for (int i = 0; i < BirdModel.totalSprites; i++) {
            birdSprites[i] = ImageLoader.load("src/main/java/Assets/flying/frame-" + (i + 1) + ".png");
        }

        birdSpriteHit = ImageLoader.load("src/main/java/Assets/Hit/frame-2.png");
    }

    public static void loadCloudsSprites() {
        cloudSprites = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            cloudSprites[i] = ImageLoader.load("src/main/java/Assets/clouds/cloud-" + (i + 1) + ".png");
        }
    }

    public static void loadPipeSprites() {
        pipeAbove = ImageLoader.load("src/main/java/Assets/pipe/PipeAbove.png");
        pipeBelow = ImageLoader.load("src/main/java/Assets/pipe/PipeBelow.png");
    }

    public static void loadCoinSprites() {
        coinSprites = new BufferedImage[9];

        BufferedImage imageCoinComplete = ImageLoader.load("src/main/java/Assets/coin/coin2_20x20.png");

        for (int i = 0; i < coinSprites.length; i++) {
            coinSprites[i] = imageCoinComplete.getSubimage(i * Coin.COIN_WIDTH_ORIGINAL, 0, Coin.COIN_WIDTH_ORIGINAL, Coin.COIN_HEIGTH_ORIGINAL);
        }

    }

    public static void loadPortalSprites() {
        portalSprites = new BufferedImage[4];

        BufferedImage imagePortalComplete = ImageLoader.load("src/main/java/Assets/Portal/portalsSpriteSheet.png");
        int cellWidth = imagePortalComplete.getWidth() / 4;  // 4 columnas
        int cellHeight = imagePortalComplete.getHeight() / 4;
        int yellowRow = 2;

        for (int col = 0; col < 4; col++) {
            portalSprites[col] = imagePortalComplete.getSubimage(col * cellWidth, yellowRow * cellHeight, cellWidth, cellHeight);
        }

    }

    public static void loadUISprites() {
        BufferedImage titleOriginal = ImageLoader.load("src/main/java/Assets/UI/Title_logo.png");

        titleSprite = ImageLoader.resizeImage(titleOriginal, 400, 150);

        BufferedImage controlOriginal = ImageLoader.load("src/main/java/Assets/UI/SpriteControls.png");
        controlSprite = ImageLoader.resizeImage(controlOriginal, 300, 250);
    }
}
