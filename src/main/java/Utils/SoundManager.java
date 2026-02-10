/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Mauricio
 */
public class SoundManager {

    public static final int JUMP = 0;
    public static final int COIN = 1;
    public static final int HIT = 2;
    public static final int PORTAL = 3;
    public static final int MBG = 4;
    
    
    private Clip[] clips;

    public SoundManager() {
        clips = new Clip[5];

        clips[JUMP] = loadSound("src/main/java/Assets/sounds/jump.wav");
        clips[COIN] = loadSound("src/main/java/Assets/sounds/pickup.wav");
        clips[HIT] = loadSound("src/main/java/Assets/sounds/hit.wav");

        clips[PORTAL] = loadSound("src/main/java/Assets/sounds/powerup.wav");

        clips[MBG] = loadSound("src/main/java/Assets/sounds/maintheme.wav");

    }

    public Clip loadSound(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {

                System.err.println("Sonido no encontrado! " + path);

                return null;
            }
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            return clip;
        } catch (Exception e) {
            System.err.println("Error cargando sonido :" + path);
            e.printStackTrace();
            return null;
        }
    }

    public void playSound(int soundID) {
        if (soundID < 0 || soundID >= this.clips.length || clips[soundID] == null) {
            return;

        }

        Clip clip = this.clips[soundID];

        //Logica de reproduccion
        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);

        clip.start();
    }

    public void playMusicBg() {
        Clip bgMusic = this.clips[MBG];

        bgMusic.stop();
        bgMusic.setFramePosition(0);

        bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        
        for(int i = 0; i < this.clips.length; i++){
            if(this.clips[i].isRunning()){
                this.clips[i].stop();
            }
        }
    }
}
