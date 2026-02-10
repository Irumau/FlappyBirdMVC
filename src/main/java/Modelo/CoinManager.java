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
public class CoinManager {

    private ArrayList<Coin> coins;
    private Random random;
    private int spawnRate = 0;
    private PipesManager pipesManager;
    private int cooldown = 0;

    private static final int WAVE_PATTERN = 0;
    private static final int ZIGZAG_PATTERN = 1;
    private static final int TUNNEL_PATTERN = 2;
    private static final int RANDOM_PATTERN = 3;
    private int currentPattern = 0;

    public CoinManager(PipesManager pipesManager) {
        this.coins = new ArrayList<>();
        this.pipesManager = pipesManager;
        this.random = new Random();
    }

    public void update(int currentSpeed) {

        moveAndCleanupCoins(currentSpeed);
        spawnRate++;

        if (cooldown > 0) {
            cooldown--;
            return; // Salimos del método aquí
        }

        // 3. Lógica de Spawn Normal
        spawnRate++;
        if (spawnRate > 150) {
            spawnRate = 0;
            spawnCoin();
        }
    }

    public void updateBonusMode(int currentSpeed) {

        moveAndCleanupCoins(currentSpeed);
        spawnRate++;
        if (spawnRate % 10 == 0) {

            switch (this.currentPattern) {
                case WAVE_PATTERN:
                    spawnWaveCoin();
                    break;
                case ZIGZAG_PATTERN:
                    spawnZigZagCoin();
                    break;
                case TUNNEL_PATTERN:
                    spawnTunnelCoin();
                    break;
                case RANDOM_PATTERN:
                    spawnUnverifiedCoin();
                    break;
            }
        }

    }

    public void spawnUnverifiedCoin() {
        //Elegimos una altura aleatoria (entre 50 y 450 aprox)
        
        int y = 50 + random.nextInt(400);

        // 2. Añadimos UNA sola moneda en esa posición
        // 850 es la posición X (fuera de la pantalla a la derecha)
        coins.add(new Coin(850, y));
    }

    private void spawnWaveCoin() {
        int centerX = 300;

        int amplitude = 150;
        double frecuency = 0.1;

        int y = centerX + (int) (Math.sin(spawnRate * frecuency) * amplitude);
        coins.add(new Coin(850, y));
    }

    private void spawnZigZagCoin() {
        int centerX = 150; // Punto base (más arriba)
        int alturaTotal = 300; // Cuánto recorre de arriba a abajo
        int velocidadSubida = 10;

        // FÓRMULA MATEMÁTICA PARA TRIÁNGULO (Ping-Pong)
        // Esto hace que el valor vaya de 0 a 300 y vuelva a 0 linealmente
        // Math.abs(...) crea la V, y el resto ajusta el ciclo.
        // Usamos spawnRate * velocidad para avanzar rápido
        int ciclo = (spawnRate * velocidadSubida) % (alturaTotal * 2);
        int yOffset = (ciclo < alturaTotal) ? ciclo : (alturaTotal * 2) - ciclo;

        int y = centerX + yOffset;
        coins.add(new Coin(850, y));
    }

    private void spawnTunnelCoin() {
        // Simplemente una línea en el medio (Y=300)
        // Es fácil de entender pero difícil de mantener si te mueves mucho
        coins.add(new Coin(850, 300));
    }

    public void spawnCoin() {
        //Generamos fuera de la pantalla
        int xSpawn = 850;
        int attempts = 0;
        boolean validPosition = false;
        int y = 0;

        //Margen de seguridad en para que no spawneen dentro de los bordes de las pipes
        int safetyMargin = 20;

        while (attempts < 5 && !validPosition) {
            y = 50 + random.nextInt(400);

            //Basicamente comprobamos la futura generacion de la monedita pero 
            //exagerando su hitbox para poder gennerarla de manera segura y no colisionar con una pipe
            Rectangle recSafeArea = new Rectangle(xSpawn - safetyMargin, y - safetyMargin,
                    Coin.SIZE + (safetyMargin * 2), Coin.SIZE + (safetyMargin * 2));

            if (!collisionWithPipes(recSafeArea)) {
                validPosition = true;

            } else {
                attempts++;
            }

        }

        if (validPosition) {
            // Si es válido, creamos la moneda NORMAL (tamaño real)
            coins.add(new Coin(xSpawn, y));
        }

    }

    public boolean collisionWithPipes(Rectangle recCoin) {
        if (pipesManager.getPipes().isEmpty()) {
            return false;
        }
        for (Pipe pipe : pipesManager.getPipes()) {
            if (recCoin.intersects(pipe.getRectanglePipeAbove()) || recCoin.intersects(pipe.getRectanglePipeBelow())) {
                return true;
            }
        }
        return false;
    }

    private void moveAndCleanupCoins(int currentSpeed) {
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            coin.update(currentSpeed);
            if (coin.getX() < -50) {
                coins.remove(i);
                i--;
            }
        }
    }

    // Método para borrar una moneda específica (cuando la agarras)
    public void grabCoin(Coin m) {
        coins.remove(m);
    }

    public void reset() {
        coins.clear();
        spawnRate = 0;

        cooldown = 200;

        currentPattern = random.nextInt(4);

        System.out.println("Proximo patron de Bonus: " + currentPattern);
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

}
