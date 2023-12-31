/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import gamestates.GameState;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.URMButtons.URM_SIZE;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class GameOverlay {
    
    private Playing playing;
    private BufferedImage img;
    private int imgX,imgY,imgW,imgH;
    private UrmButton menu,play;
    public GameOverlay(Playing playing){
        this.playing=playing;
        createImg();
        createButtons();
    }
    public void draw (Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        
        g.drawImage(img, imgX, imgY, imgH, imgH,null);
        menu.draw(g);
        play.draw(g);
//        g.setColor(Color.white);
//        g.drawString("Game Over", Game.GAME_WIDTH/2, 150);
//        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH/2, 300);
        
    }
        public void update(){
        play.update();
        menu.update();
    }
    public void keyPressed(KeyEvent e){
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
            playing.resetAll();
            GameState.state=GameState.MENU;
            }
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_OVERLAY);
        imgW=(int)(img.getWidth()*Game.SCALE);
        imgH=(int)(img.getHeight()*Game.SCALE);
        imgX=Game.GAME_WIDTH/2-imgW/2;
        imgY=(int)(100*Game.SCALE);
    }

    private void createButtons() {
        int menuX = (int) (335*Game.SCALE);
        int playX = (int) (440*Game.SCALE);
        int y = (int) (195*Game.SCALE);
        play=new UrmButton(playX,y,URM_SIZE,URM_SIZE,0);
        menu=new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
    }
     private boolean isIn(UrmButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }
    public void mouseMoved(MouseEvent e){
           menu.setMouseOver(false);
           play.setMouseOver(false);
           
           if(isIn(menu,e))
               menu.setMouseOver(true);
           else if(isIn(play,e))
               play.setMouseOver(true);
     }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e))
               menu.setMousePressed(true);
           else if(isIn(play,e))
               play.setMousePressed(true);
    }
    public void mouseReleased(MouseEvent e){
    if(isIn(menu,e)){
            if(menu.isMousePressed()){
                playing.resetAll();
                GameState.state=GameState.MENU;
            }
    }
        else if(isIn(play,e)){
            if(play.isMousePressed()){
                playing.resetAll();
        }
        }
        
        menu.resetBools();
        play.resetBools();
    }
}
