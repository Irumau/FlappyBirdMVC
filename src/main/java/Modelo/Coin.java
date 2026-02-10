/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.Rectangle;

/**
 *
 * @author Mauricio
 */
public class Coin {

    private int x, y;
    private int currentSpriteIndex = 0;
    private int animationTick = 0;

    public static final int SIZE = 40;

    
    public static final int COIN_WIDTH_ORIGINAL = 20;
    public static final int COIN_HEIGTH_ORIGINAL = 20;
    
    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int speed) {

        x -= speed;
        animationTick++;

        if (animationTick >= 4) {
            animationTick = 0;
            currentSpriteIndex++;
        }

        if (currentSpriteIndex >= 9) {
            currentSpriteIndex = 0;
        }
    }

    public Rectangle getCoinBounds() {
        return new Rectangle(x + 5, y + 5, SIZE - 10, SIZE - 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public static int getSIZE() {
        return SIZE;
    }
    
    
    

}
