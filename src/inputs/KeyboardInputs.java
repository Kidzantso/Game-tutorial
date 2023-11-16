/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import gamestates.GameState;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import static utilz.Constants.Directions.*;
/**
 *
 * @author dodom
 */
public class KeyboardInputs implements KeyListener{
    private GamePanel gamepanel;
    public KeyboardInputs(GamePanel gamepanel){
    this.gamepanel=gamepanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
         switch(GameState.state){
            case MENU:
                gamepanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch(GameState.state){
            case MENU:
                gamepanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamepanel.getGame().getPlaying().keyPressed(e);
                break;
            case OPTIONS:
                gamepanel.getGame().getGameOptions().keyPressed(e);
            default:
                break;
        }
    }

        
}
