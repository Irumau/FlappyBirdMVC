/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Utils.GameState;

/**
 *
 * @author Mauricio
 */
public class GameStatus {
    private int score;
    private long startTime;
    private long elapsedTime;
    
    private int currentWorldSpeed;
    private GameState currentGameState;
    
    
    
    private int currentBaseSpeed = 5;
    private final int INITIAL_SPEED = 5; // Velocidad normal 
    private final int MAX_SPEED = 12; // velocidad turbo
    private final int TURBO_ADDITION = 5;
    
    
    private final int INITIAL_SPAWN_INTERVAL = 100; // Cada ~2.5 segundos
    private final int MIN_SPAWN_INTERVAL = 50;
    
    public GameStatus() {
        this.score = 0;
        
        this.startTime = System.currentTimeMillis();
        
        //this.currentGameState = GameState.PLAYING;
        //El juego empieza en el menu
        this.currentGameState = GameState.MENU;
        this.currentWorldSpeed = INITIAL_SPEED;
    }
    
    
    
    public void increaseScore(){
        this.score++;
    }
    
    public void checkDifficulty(){
        int speedBonus = score / 10;
        
        int newSpeed = INITIAL_SPEED + speedBonus;
        
        
        
        if(newSpeed > MAX_SPEED){
            newSpeed = MAX_SPEED;
        }
        
        this.currentBaseSpeed = newSpeed;
        
        
        if(currentGameState != GameState.BONUS){
            this.currentWorldSpeed = this.currentBaseSpeed;
        }
    }
    
    public int getPipeSpawnInterval() {
        // Por cada 5 puntos, reducimos 2 frames el tiempo de espera
        int reduction = (score / 5) * 2; 
        
        int currentInterval = INITIAL_SPAWN_INTERVAL - reduction;
        
        // SEGURIDAD: Nunca bajar del mínimo, o sería imposible pasar
        if (currentInterval < MIN_SPAWN_INTERVAL) {
            return MIN_SPAWN_INTERVAL;
        }
        
        return currentInterval;
    }
    
    public int getPipeGapDifficulty(){
        int reduction = (score/20) *2;
        
        int gap = 120 - reduction;
        
        // Límite mínimo para que sea posible pasar (ej. 90px)
        if (gap < 80) return 80;
        return gap;
    }
    
    
    public void coinScore(){
        int currentScore = this.getScore();
        
        
        this.score = currentScore + 1;
    }
    public void updateTime(){
        long now = System.currentTimeMillis();
        
        this.elapsedTime = (now - startTime) / 1000;
        
    }
    
    public void reset(){
        this.score = 0;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
        
        this.currentBaseSpeed = INITIAL_SPEED;
        this.deactiveTurbo();
        this.setCurrentGameState(GameState.PLAYING);
    }

    public int getScore() {
        return score;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void activeTurbo(){
        this.currentWorldSpeed = this.currentBaseSpeed + TURBO_ADDITION;
    }
    
    public void deactiveTurbo(){
        this.currentWorldSpeed = this.currentBaseSpeed;
    }
    
    public int getWorldSpeed(){
        return this.currentWorldSpeed;
    }
    
    
    
}
