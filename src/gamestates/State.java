/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import UI.MenuButton;
import java.awt.event.MouseEvent;
import main.Game;

/**
 *
 * @author dodom
 */
public class State {
    protected Game game;
    public State(Game game){
    this.game=game;
    }
    public boolean isIn(MouseEvent e,MenuButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }
    public Game getGame(){
    return game;
    }
}
