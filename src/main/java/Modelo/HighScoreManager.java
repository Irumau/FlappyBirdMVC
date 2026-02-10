/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author Mauricio
 */
public class HighScoreManager {

    private int BestScore = 0;

    private final String FILE_NAME = "highscore.txt";
    private final String FOLDER_NAME = "Data";
    
    private final File scoreFile;
    
    public HighScoreManager() {
        
        String filePath = FOLDER_NAME + File.separator + FILE_NAME;
        this.scoreFile = new File(filePath);
        loadHighScore();
    }

    
    
    // Método auxiliar para crear carpeta y archivo si no existen
    private void createFile() throws IOException {
        // 1. Crear la carpeta "Data" si no existe
        File folder = new File(FOLDER_NAME);
        if (!folder.exists()) {
            folder.mkdir(); // mkdir = make directory
        }
        
        // 2. Crear el archivo con un 0 inicial
        if (scoreFile.createNewFile()) {
            saveHighScore(0);
        }
    }
    
    public void loadHighScore() {


        try {
            if (scoreFile.exists()) {
                Scanner sc = new Scanner(scoreFile);
                if (sc.hasNextInt()) {
                    this.BestScore = sc.nextInt();
                }
                sc.close();
            } else {
                createFile();
            }

        } catch (IOException e) {
            System.out.println("No se encontro dicho archivo");
            System.out.println("ERROR CARGANDO SCORE");
            e.printStackTrace();
        }

    }

    public void saveHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(scoreFile);
            writer.write(Integer.toString(score));
            writer.close();
            this.BestScore = score;
        }catch(IOException e){
            System.err.println("ERROR GUARDANDO EL SCORE");
        }
    }
    
    
    public void checkAndSaveScore(int currentScore){
        if(currentScore > this.BestScore){
            
            
            saveHighScore(currentScore);
        }
    }

    public int getBestScore() {
        return BestScore;
    }
    
    
}
