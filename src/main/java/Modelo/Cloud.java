/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Mauricio
 */
public class Cloud {

    int x, y;
    int baseSpeed;
    int type;

    public Cloud(int x, int y, int baseSpeed, int tipe) {
        this.x = x;
        this.y = y;
        this.baseSpeed = baseSpeed;
        this.type = tipe;
    }
    
    public void updateClouds(int worldSpeed){
        
        int turboFactor = worldSpeed;          
        if(turboFactor < 1) turboFactor = 1;
        x -= (baseSpeed * turboFactor) /2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidad() {
        return baseSpeed;
    }

    public int getTipo() {
        return type;
    }
    
    
}
