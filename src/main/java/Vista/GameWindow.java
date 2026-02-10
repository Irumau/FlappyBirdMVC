/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;


import javax.swing.JFrame;

/**
 *
 * @author Mauricio
 */
public class GameWindow extends JFrame{
    
    public GameWindow(GameViewNew view){
        setTitle("Flappy Bird MVC - Java");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
        add(view);
        
        setVisible(true);
        view.requestFocusInWindow();
        
    }
}
