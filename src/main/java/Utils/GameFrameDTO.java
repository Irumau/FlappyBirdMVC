/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import java.util.ArrayList;

/**
 *
 * @author Mauricio
 */
public class GameFrameDTO {
    
    
    public GameState currentState;
    public int score;
    public long elapsedTime;
    public int bestScore;
    
    public int pipeWidth;
    
    
    
    
    //Datos del pajaro
    public int birdX, birdY, birdWidth, birdHeight;
    public double birdRotation;
    public int birdSpriteIndex;
    public boolean isBirdAlive;
    
    //Listas de objetos
    public ArrayList<CloudData> clouds = new ArrayList<>();
    public ArrayList<PipeData> pipes = new ArrayList<>();
    public ArrayList<CoinData> coins = new ArrayList<>();
    
    //Portal 
    public boolean isPortalActive;
    public int portalX, portalY, portalSpriteIndex;
    
    
    
//    En Java, cuando pones una clase dentro de otra y le pones static,
//    se llama Clase Anidada Estática (Static Nested Class).
    public static class CloudData {
        public int x, y, type;
        public CloudData(int x, int y, int type) { this.x = x; this.y = y; this.type = type; }
    }
    
    public static class PipeData {
        public int x, ySpace, gapHeight;
        public PipeData(int x, int ySpace, int gapHeight) { this.x = x; this.ySpace = ySpace; this.gapHeight = gapHeight; }
    }
    
    public static class CoinData {
        public int x, y, spriteIndex, size;
        public CoinData(int x, int y, int spriteIndex, int size) { this.x = x; this.y = y; this.spriteIndex = spriteIndex; this.size = size; }
    }
}
