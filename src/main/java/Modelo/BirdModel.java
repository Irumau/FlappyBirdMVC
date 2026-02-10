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
public class BirdModel {

    private int x, y;
    private int velocityY;
    private boolean isAlive = true;

    private int currentSpriteIndex = 0;
    //permite actualizar los 
    private int animationTickCounter = 0;
    //Velocidad de animacion, mientras mayor sea mas lento va a ser el cambio entre sprites para lograr la animacion
    private final int ANIMATION_SPEED = 7;

    public static final int totalSprites = 8;

    //tamano del pajarito (Para que el sprite no sea tan grande lo modificamos con estas dos variables.
    private int width, height;
    // Angulo de rotacion del pajarito, si salta va a mirar hacia arriba, y si no hacia abajo
    private double rotationAngle = 0;

    private double hoverTime = 0;
    
    public BirdModel(int x, int y) {
        this.x = x;
        this.y = y;

        this.width = 60;
        this.height = 50;
    }

    public void update() {
        velocityY += 1;

        y += velocityY;

        if (y > 500) {
            y = 500;
            velocityY = 0;
        }
        if (y < 0) {
            y = 0;
            velocityY = 0;
        }

        animationTickCounter++;

        //logica de rotacion
        if (velocityY < 0) {
            rotationAngle = -25;
        } else if (velocityY > 0) {
            rotationAngle += 2;
            if (rotationAngle > 90) {
                rotationAngle = 90;
            }
        }

        if (animationTickCounter >= ANIMATION_SPEED) {
            animationTickCounter = 0;
            currentSpriteIndex++;
            if (currentSpriteIndex >= totalSprites) {
                currentSpriteIndex = 0;
            }
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(this.x + 5, this.y + 5, this.width - 15, this.height - 15);
    }

    public void jump() {
        velocityY -= 12;

    }

    //Reinicia el estado del pajarito en caso de que reiniciemos
    public void reset() {
        this.x = 100;
        this.y = 300;
        this.velocityY = 0;
        this.rotationAngle = 0;
        
        this.isAlive = true;
    }
    
    
    public void hover(){
        animationTickCounter++;
        if(animationTickCounter >= ANIMATION_SPEED){
            animationTickCounter =0;
            currentSpriteIndex++;
            if(currentSpriteIndex >= totalSprites){
                currentSpriteIndex = 0;
            }
        }
        
        hoverTime += 0.1;
        this.y = 340 + (int) (Math.sin(hoverTime)*10);
        
        this.x = 450;
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

    public int getTotalSprites() {
        return this.totalSprites;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
