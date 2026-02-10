/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.BirdModel;
import Modelo.CloudsManager;
import Modelo.CoinManager;
import Modelo.Coin;
import Utils.GameState;
import Modelo.GameStatus;
import Modelo.HighScoreManager;
import Modelo.PipesManager;
import Modelo.Pipe;
import Modelo.Cloud;

import Modelo.Portal;

import Modelo.PortalManager;
import Utils.GameFrameDTO;
import Utils.SoundManager;

import Vista.GameView;
import Vista.GameViewNew;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

/**
 *
 * @author Mauricio
 */
public class GameController implements ActionListener {

    private BirdModel model;
    private CloudsManager cloudsManager;
    private PipesManager pipesManager;
    private CoinManager coinManager;
    private PortalManager portalManager;
    private SoundManager soundManager;
    private HighScoreManager highScoreManager;

    private GameStatus gameStatus;
    private GameState gameState = GameState.PLAYING;

    private int countForPortal = 0;
    private int bonusTime = 0;

    //private GameView view;
    private Timer gameLoopTimer;
    private GameViewNew view;

    public GameController(BirdModel model, PortalManager portalManager, GameViewNew view, CloudsManager cloudsManager, PipesManager pipeManager, CoinManager coinManager, GameStatus gameStatus, SoundManager soundManager, HighScoreManager highScoreManager) {
        this.model = model;
        this.cloudsManager = cloudsManager;
        this.pipesManager = pipeManager;
        this.coinManager = coinManager;
        this.soundManager = soundManager;

        this.portalManager = portalManager;
        this.gameStatus = gameStatus;
        this.highScoreManager = highScoreManager;
        this.view = view;

        setupInput();
        gameLoopTimer = new Timer(25, this);
    }

    public void startGame() {

        gameLoopTimer.start();
        soundManager.playMusicBg();
    }

    public void setupInput() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (gameStatus.getCurrentGameState() == GameState.MENU) {
                        startGameLogic();

                    }
                    if (model.isAlive() && gameStatus.getCurrentGameState() == GameState.PLAYING || gameStatus.getCurrentGameState() == GameState.BONUS) {
                        model.jump();
                        soundManager.playSound(SoundManager.JUMP);
                    }

                }

                if (e.getKeyCode() == KeyEvent.VK_R) {

                    if (gameStatus.getCurrentGameState() == GameState.GAME_OVER) {
                        resetGame();
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (gameStatus.getCurrentGameState() == GameState.GAME_OVER) {
                        resetGame();
                        gameStatus.setCurrentGameState(GameState.MENU);
                    }
                }

            }
        });

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent m) {
                if (m.getButton() == MouseEvent.BUTTON1) {
                    if (gameStatus.getCurrentGameState() == GameState.MENU) {
                        startGameLogic();
                    }
                    if (model.isAlive() && gameStatus.getCurrentGameState() == GameState.PLAYING || gameStatus.getCurrentGameState() == GameState.BONUS) {
                        model.jump();
                        soundManager.playSound(SoundManager.JUMP);
                    }
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameState state = gameStatus.getCurrentGameState();
        if (state == GameState.GAME_OVER) {
            return;
        }

        switch (state) {
            case MENU:
                logicMenu();
                break;
            case PLAYING:
                playingNormalLogic();
                model.update();
                gameStatus.updateTime();

                break;
            case BONUS:
                logicBonus();
                model.update();
                gameStatus.updateTime();

                break;
            default:
                throw new AssertionError();
        }
        
        enviarDatosALaVista();
    }

    private void playingNormalLogic() {

        int currentSpeed = gameStatus.getWorldSpeed();

        cloudsManager.update(currentSpeed);
        pipesManager.update(currentSpeed);
        coinManager.update(currentSpeed);
        portalManager.update(currentSpeed);
        checkScore();

        if (!pipesManager.getPipes().isEmpty()) {
            countForPortal++;
        }
        if (!portalManager.isPortalActive() && countForPortal > 500) {
            countForPortal = 0;
            portalManager.spawnPortalInPipeGap();
        }

        if (portalManager.isPortalActive() && model.getBounds().intersects(portalManager.getPortal().getPortalBounds())) {
            enterBonus();
        }

        checkCoinCollisions();
        checkPipesCollisions();
    }

    private void logicBonus() {

        int velocidadActual = gameStatus.getWorldSpeed();
        cloudsManager.update(velocidadActual);

        coinManager.updateBonusMode(velocidadActual);

        bonusTime--;
        if (bonusTime <= 0) {
            leaveBonus();
        }
        checkCoinCollisions();
    }

    private void startGameLogic() {
        gameStatus.setCurrentGameState(GameState.PLAYING);
        gameStatus.reset();
        model.reset();

        soundManager.playSound(SoundManager.JUMP);
    }

    private void enterBonus() {
        soundManager.playSound(SoundManager.PORTAL);
        gameStatus.setCurrentGameState(GameState.BONUS);
        portalManager.deactivePortal();
        gameStatus.activeTurbo();

        bonusTime = 500;
        pipesManager.reset();
    }

    private void leaveBonus() {
        gameStatus.setCurrentGameState(GameState.PLAYING);
        gameStatus.deactiveTurbo();
        coinManager.reset();
        countForPortal = 0;
    }

    public void checkPipesCollisions() {


//      Checkea en caso de que el usuario logre "BUGEAR" los limites superiores e inferiores y sobrepase los 600 como maximo en Y y 0 como minimo
//      terminando el juego en consecuencia
        if (model.getY() > 600 || model.getY() < 0) {
            gameOver();
            return;
        }


        if(pipesManager.checkCollision(model)){
            gameOver();
            return;
        }

    }

    public void checkCoinCollisions() {
        //verificar colision pajarito--> monedas
        Rectangle rBird = model.getBounds();
        for (int i = 0; i < coinManager.getCoins().size(); i++) {

            Coin currentCoin = coinManager.getCoins().get(i);

            if (rBird.intersects(currentCoin.getCoinBounds())) {
                gameStatus.coinScore();

                coinManager.grabCoin(currentCoin);

                soundManager.playSound(SoundManager.COIN);

                gameStatus.checkDifficulty();
                break;
            }
        }
    }

    public void gameOver() {

        if (model.isAlive()) {
            soundManager.playSound(SoundManager.HIT);
        }

        highScoreManager.checkAndSaveScore(gameStatus.getScore());
        soundManager.stopMusic();
        gameStatus.setCurrentGameState(GameState.GAME_OVER);
        model.setIsAlive(false);
        gameLoopTimer.stop();
        enviarDatosALaVista();

    }

    public void checkScore() {
        for (Pipe pipe : pipesManager.getPipes()) {
            if (!pipe.isPassed() && model.getX() > pipe.getX() + Pipe.getPIPE_WIDTH()) {
                gameStatus.increaseScore();

                gameStatus.checkDifficulty();
                pipe.setPassed(true);

            }
        }
    }

    public void resetGame() {
        gameStatus.reset();
        model.setIsAlive(true);
        coinManager.reset();
        pipesManager.reset();

        soundManager.playMusicBg();

        portalManager.deactivePortal();
        countForPortal = 0;

        gameLoopTimer.start();
        view.repaint();

    }

    public void logicMenu() {
        int currentSpeed = gameStatus.getWorldSpeed();

        gameStatus.setCurrentGameState(GameState.MENU);
        cloudsManager.update(currentSpeed);
        model.hover();
    }

    private void enviarDatosALaVista() {
        GameFrameDTO dto = new GameFrameDTO();

        // --- Mismos datos que ya tenías ---
        dto.currentState = gameStatus.getCurrentGameState(); // OJO: Usar el estado actual real
        dto.score = gameStatus.getScore();
        dto.elapsedTime = gameStatus.getElapsedTime();
        dto.bestScore = highScoreManager.getBestScore();

        dto.birdX = model.getX();
        dto.birdY = model.getY();
        dto.birdWidth = model.getWidth();
        dto.birdHeight = model.getHeight();
        dto.birdRotation = model.getRotationAngle();
        dto.birdSpriteIndex = model.getCurrentSpriteIndex();
        dto.isBirdAlive = model.isAlive();

        dto.pipeWidth = Pipe.getPIPE_WIDTH();
        
        for (Cloud c : cloudsManager.getClouds()) {
            dto.clouds.add(new GameFrameDTO.CloudData(c.getX(), c.getY(), c.getTipo()));
        }

        for (Pipe p : pipesManager.getPipes()) {
            dto.pipes.add(new GameFrameDTO.PipeData(p.getX(), p.getYspace(), p.getGapHeight()));
        }

        for (Coin c : coinManager.getCoins()) {
            dto.coins.add(new GameFrameDTO.CoinData(c.getX(), c.getY(), c.getCurrentSpriteIndex(), c.getSIZE()));
        }

        dto.isPortalActive = portalManager.isPortalActive();
        if (dto.isPortalActive) {
            Portal p = portalManager.getPortal();
            dto.portalX = p.getX();
            dto.portalY = p.getY();
            dto.portalSpriteIndex = p.getCurrentSpriteIndex();
        }

        // ENVIAR
        view.render(dto);
    }
}
