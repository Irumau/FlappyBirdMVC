/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class CloudsManager {

    private ArrayList<Cloud> clouds;
    private Random random;
    private int screenWidth = 800;

    public CloudsManager() {
        this.clouds = new ArrayList<>();
        this.random = new Random();

        spawnClouds(100);
        spawnClouds(400);
        spawnClouds(700);
    }

    public void spawnClouds(int xPosition) {
        int y = 50 + random.nextInt(600);
        int tipe = random.nextInt(3);

        int baseSpeed = 1 + random.nextInt(3);

        clouds.add(new Cloud(xPosition, y, baseSpeed, tipe));
    }

    
    public void update(int worldSpeed){
        for(Cloud cloud : this.clouds){
            cloud.updateClouds(worldSpeed);
        }
        
        if (!this.clouds.isEmpty() && this.clouds.get(0).getX() < -150) {
            clouds.remove(0); // Borramos la vieja
            spawnClouds(screenWidth + 50); // Creamos una nueva a la derecha
        }
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
    
    
    
}
