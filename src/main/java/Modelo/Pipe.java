/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class Pipe {
    private int x;
    private int yGap;
    private int currentGapSize;
    
    private boolean passed;
    
    
    //Medidas para el tubo
    private static final int PIPE_WIDTH = 50;
    


    public Pipe(int x, int difficultyGap) {
        this.x = x;
        this.currentGapSize = difficultyGap;
        
        generateRandomHeight();
    }
    
    public void generateRandomHeight(){
        Random random = new Random();
        
        this.yGap = 200 + random.nextInt(150);
    }
    public void updatePipe(int speed){
        this.x -= speed;
    }
    
    public Rectangle getRectanglePipeAbove(){
        return new Rectangle(x, 0, PIPE_WIDTH, yGap - currentGapSize);
    }
    
    
    public Rectangle getRectanglePipeBelow(){
        return new Rectangle(x,yGap,PIPE_WIDTH, 600);
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getYspace(){
        return this.yGap;
    }

    public static int getPIPE_WIDTH() {
        return PIPE_WIDTH;
    }


    public boolean isPassed() {
        return passed;
    }
    
    public int getGapHeight() {
        return currentGapSize;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

            
}
