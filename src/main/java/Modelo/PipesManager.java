/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class PipesManager {
    private ArrayList<Pipe> pipes;
    
    private Random random;
    private int spawnRate = 0;
    
    
    private GameStatus gameStatus;

    public PipesManager(GameStatus gameStatus) {
        this.pipes = new ArrayList<Pipe>();
        
        this.random = new Random();
        
        this.gameStatus = gameStatus;
        
        spawnPipes(600);
    }
    
    public void spawnPipes(int xPosition){
        //int gapHeight = 200 + random.nextInt(150);
        int currentGapDifficulty = gameStatus.getPipeGapDifficulty();
        
        
        pipes.add(new Pipe(xPosition, currentGapDifficulty));
    }
    
    public void update(int currentSpeed){
        spawnRate++;
        
        
        int currentSpawnInterval = gameStatus.getPipeSpawnInterval();
        
        if(spawnRate >= currentSpawnInterval){
            spawnRate = 0;
            spawnPipes(850);
        }
        
        
        for(int i=0; i< pipes.size();i++){
            
            Pipe p = pipes.get(i);
            p.updatePipe(currentSpeed);
            if(p.getX() + Pipe.getPIPE_WIDTH() < -50){
                pipes.remove(p);
                i--;
            }
        }
    }
    
    
    public boolean checkCollision(BirdModel birdModel){
               //verificar colision pajarito --> tubos
        Rectangle rBird = birdModel.getBounds();

        for (Pipe pipe : getPipes()) {
            if (rBird.intersects(pipe.getRectanglePipeAbove()) || rBird.intersects(pipe.getRectanglePipeBelow())) {
                return true;
            }
        }
        return false;
    }
    
    public void reset(){
        pipes.clear();
        spawnRate = 0;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    
    
    
    
}
