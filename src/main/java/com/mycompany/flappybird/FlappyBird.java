/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.flappybird;

import Controlador.GameController;
import Modelo.BirdModel;
import Modelo.CloudsManager;
import Modelo.CoinManager;
import Modelo.GameStatus;
import Modelo.HighScoreManager;
import Modelo.PipesManager;
import Modelo.Portal;
import Modelo.PortalManager;
import Utils.SoundManager;
import Utils.Assets;
import Vista.GameView;
import Vista.GameViewNew;
import Vista.GameWindow;
import javax.swing.SwingUtilities;



/**
 *
 * @author Mauri cio
 */


public class FlappyBird {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(()->{
            
            Assets.init();
            
            GameStatus gameStatus = new GameStatus();
            SoundManager soundManager = new SoundManager();
            BirdModel model = new BirdModel(100, 300);
            CloudsManager cloudsManager = new CloudsManager();
            PipesManager pipesManager = new PipesManager(gameStatus);
            CoinManager coinManager = new CoinManager(pipesManager);
            PortalManager portalManager = new PortalManager(pipesManager);
            
            GameViewNew view = new GameViewNew();
            
            
            HighScoreManager highScoreManager = new HighScoreManager();
            
            //GameView view = new GameView(model,portalManager,cloudsManager,pipesManager,coinManager,highScoreManager,gameStatus);
            
            GameController controller = new GameController(model,portalManager, view,cloudsManager,pipesManager,coinManager,gameStatus,soundManager, highScoreManager);
            
            new GameWindow(view);
            
            controller.startGame();
        });
    }
}
