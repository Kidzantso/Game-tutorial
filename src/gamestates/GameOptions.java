/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import UI.AudioOptions;
import UI.PausingButton;
import UI.UrmButton;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class GameOptions extends State implements Statemethods{
    
    private AudioOptions audioOptions;
    private BufferedImage bckimg,optionsbackgroundimg;
    private int bgX,bgY,bgW,bgH;
    private UrmButton menuB;
    
    public GameOptions(Game game){
        super(game);
        loadImgs();
        loadButtons();
        audioOptions=game.getAudioOptions();
    }
    
    private void loadButtons() {
        int menuX=(int)(387*Game.SCALE);
        int menuY=(int)(325*Game.SCALE);
        
        menuB=new UrmButton(menuX,menuY,URM_SIZE,URM_SIZE,2);
    }
    
    private void loadImgs() {
        bckimg=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU_IMAGE);
        optionsbackgroundimg=LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);
        
        bgW=(int)(optionsbackgroundimg.getWidth()*Game.SCALE);
        bgH=(int)(optionsbackgroundimg.getHeight()*Game.SCALE);
        bgX=Game.GAME_WIDTH/2 - bgW/2;
        bgY=(int)(33*Game.SCALE);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(bckimg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsbackgroundimg, bgX, bgY, bgW, bgH, null);
        
        menuB.draw(g);
        audioOptions.draw(g);
    }
    
@Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(menuB, e)){
            menuB.setMousePressed(true);}
        else 
            audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(menuB, e)){
            if(menuB.isMousePressed())
                GameState.state=GameState.MENU;
            }else 
                audioOptions.mouseReleased(e);
        
        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        
        if(isIn(menuB, e))
             menuB.setMouseOver(true);
        else 
            audioOptions.mouseMoved(e);
        
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            GameState.state=GameState.MENU;
    }
     private boolean isIn(UrmButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }
   
    @Override
    public void keyReleased(KeyEvent e) { 
    }
        @Override
    public void mouseClicked(MouseEvent e) {      
    }    
}
