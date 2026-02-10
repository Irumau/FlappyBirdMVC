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
public class PortalManager {

    private Portal portal;
    private PipesManager pipesManager;
    private Random random;

    public PortalManager(PipesManager pipesManager) {
        this.pipesManager = pipesManager;
        this.portal = new Portal();
        this.random = new Random();
    }

    public void update(int currentSpeed) {
        portal.update(currentSpeed);
    }

    public void spawnPortalInPipeGap() {
        Pipe targetPipe = null;
        int maxX = -1000;

        
        for(Pipe pipe : pipesManager.getPipes()){
            if(pipe.getX() > maxX){
                maxX = pipe.getX();
                targetPipe = pipe;
            }
        }
        
        if(targetPipe != null){
            
            
            int xSpawn = targetPipe.getX() + (Pipe.getPIPE_WIDTH() / 2 ) - (32/2);
            int gapSize = targetPipe.getGapHeight();
            // Calculamos el centro vertical del huecos
            // Recordando que ySpace es el borde SUPERIOR del tubo de ABAJO
            int yCenterGap = targetPipe.getYspace() - (gapSize / 2);
            
            // Calculamos la esquina superior izquierda del portal para que quede centrado
            // Restamos la mitad de la altura del portal (48 / 2 = 24)
            int ySpawn = yCenterGap - (48 / 2) ;
            
            portal.spawn(xSpawn, ySpawn - 30);
            
            System.out.println("Portal generado en el hueco del tubo en X: " + xSpawn);
        }

    }

    public boolean collisionWithPortal(Rectangle recPortal) {
       
        for(Pipe pipe : pipesManager.getPipes()){
            if(recPortal.intersects(pipe.getRectanglePipeAbove()) || recPortal.intersects(pipe.getRectanglePipeBelow())){
                return true;
            }
        }
        return false;
    }

    public Portal getPortal() {
        return portal;
    }
    public boolean isPortalActive(){ 
        return portal.isActive();
        
    }
    public void deactivePortal(){
        portal.setIsActive(false);
    }

}
