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
public class Portal {

    private int x, y;
    private boolean active = false;
    private int currentSpriteIndex = 0;
    private int animationTick = 0;

    //Medidas para cargar las imagenes de manera correcta. Y no de un tamaño indimensionable
//    public static final int PORTAL_WIDTH_ORIGINAL = 32;
//    public static final int PORTAL_HEIGTH_ORIGINAL = 48;

    public Portal() {
        this.x = -100; // Empieza escondido
        this.active = false;
    }

    public void spawn(int width, int y) {
        this.x = width;
        this.active = true;
        this.y = y;
    }

    public void update(int currentSpeed) {
        if (active) {
            x -= currentSpeed;

            animationTick++;
            if (animationTick >= 10) {
                animationTick = 0;
                currentSpriteIndex++;
                if (currentSpriteIndex >= 4) {
                    currentSpriteIndex = 0;
                }
            }
        }
    }

    public Rectangle getPortalBounds() {
        return new Rectangle(x, y, 32, 48);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean isActive) {
        this.active = isActive;
    }

    public int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

}
