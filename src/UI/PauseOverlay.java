/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import gamestates.GameState;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class PauseOverlay {
    
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    private AudioOptions audioOptions;

    private UrmButton menuB,replayB,unpauseB;

    
    public PauseOverlay(Playing playing){
    this.playing=playing;
    loadBackground();
    audioOptions=playing.getGame().getAudioOptions();
    createUrmButtons();

    }
    
    public void draw(Graphics g){
    g.drawImage(backgroundImg, bgX, bgY,bgW,bgH,null);
    
    menuB.draw(g);
    replayB.draw(g);
    unpauseB.draw(g);
    audioOptions.draw(g);
    }
    public void update(){
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
    }
    
    
    public void mousePressed(MouseEvent e) {
           if(isIn(e,menuB))
               menuB.setMousePressed(true);
           else if(isIn(e,replayB))
               replayB.setMousePressed(true);
           else if(isIn(e,unpauseB))
               unpauseB.setMousePressed(true);
            else 
               audioOptions.mousePressed(e);
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
               if(menuB.isMousePressed()){
               playing.resetAll();
               GameState.state=GameState.MENU;
               playing.unpauseGame();
               }
        }else if(isIn(e,replayB)){
               if(replayB.isMousePressed()){
               playing.resetAll();
               playing.unpauseGame();
               }
        }else if(isIn(e,unpauseB)){
            if(unpauseB.isMousePressed())
               playing.unpauseGame();
        }else 
                audioOptions.mouseReleased(e);
        
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseDragged(MouseEvent e) {
            audioOptions.mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
           menuB.setMouseOver(false);
           replayB.setMouseOver(false);
           unpauseB.setMouseOver(false);
           
           
           if(isIn(e,menuB))
               menuB.setMouseOver(true);
           else if(isIn(e,replayB))
               replayB.setMouseOver(true);
           else if(isIn(e,unpauseB))
               unpauseB.setMouseOver(true);
           else 
               audioOptions.mouseMoved(e);
    
    }
    
    private boolean isIn(MouseEvent e,PausingButton b){
        return b.getBounds().contains(e.getX(), e.getY());
    }
    
    private void loadBackground() {
       backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
       bgW=(int)(backgroundImg.getWidth()*Game.SCALE);
       bgH=(int)(backgroundImg.getHeight()*Game.SCALE);
       bgX=Game.GAME_WIDTH/2-bgW/2;
       bgY=(int)(25 * Game.SCALE);
               }

   

    private void createUrmButtons() {
        int menuX=(int)(313*Game.SCALE);
        int replayX=(int)(387*Game.SCALE);
        int unpauseX=(int)(462*Game.SCALE);
        int bY=(int)(325*Game.SCALE);
        menuB = new UrmButton(menuX,bY,URM_SIZE,URM_SIZE,2);
        replayB = new UrmButton(replayX,bY,URM_SIZE,URM_SIZE,1);
        unpauseB = new UrmButton(unpauseX,bY,URM_SIZE,URM_SIZE,0);
    }

}
