/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import gamestates.GameState;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButton menu,next;
    private BufferedImage img;
    private int bgx,bgy,bgw,bgh;
    
    
    public LevelCompletedOverlay(Playing playing){
        this.playing=playing;
        initimg();
        initButtons();
    }

    private void initimg() {
            img=LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMAGE);
            bgw=(int)(img.getWidth()*Game.SCALE);
            bgh=(int)(img.getHeight()*Game.SCALE);
            bgx=Game.GAME_WIDTH/2-bgw/2;
            bgy=(int)(75*Game.SCALE);
    }

    private void initButtons() {
        int menuX = (int) (330*Game.SCALE);
        int nextX = (int) (445*Game.SCALE);
        int y = (int) (195*Game.SCALE);
        next=new UrmButton(nextX,y,URM_SIZE,URM_SIZE,0);
        menu=new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
        
    }
    
    public void update(){
        next.update();
        menu.update();
    }
    public void draw(Graphics g){    
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
    g.drawImage(img, bgx, bgy, bgw, bgh, null);
    next.draw(g);
    menu.draw(g);
    }
     
    private boolean isIn(UrmButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }
    public void mouseMoved(MouseEvent e){
           menu.setMouseOver(false);
           next.setMouseOver(false);
           
           if(isIn(menu,e))
               menu.setMouseOver(true);
           else if(isIn(next,e))
               next.setMouseOver(true);
     }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e))
               menu.setMousePressed(true);
           else if(isIn(next,e))
               next.setMousePressed(true);
    }
    public void mouseReleased(MouseEvent e){
    if(isIn(menu,e)){
            if(menu.isMousePressed()){
                playing.resetAll();
                GameState.state=GameState.MENU;
            }
    }
        else if(isIn(next,e)){
            if(next.isMousePressed()){
                playing.loadNextLevel();
        }
        }
        
        menu.resetBools();
        next.resetBools();
    }
}
