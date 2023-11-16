/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 *
 * @author dodom
 */
public class GamePanel extends JPanel{
    private MouseInputs mouseinputs;
    private Game game;

    
    public GamePanel(Game game){
        mouseinputs=new MouseInputs(this);
        this.game=game;
        SetPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseinputs);
        addMouseMotionListener(mouseinputs);
    }
    
    private void SetPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
public void updateGame(){

}

    public void paintComponent(Graphics g){
    super.paintComponent(g);
    game.render(g);
    }

public Game getGame(){
return game;
}

    
}
