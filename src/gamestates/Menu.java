/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import UI.MenuButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class Menu extends State implements Statemethods{
    
    private MenuButton[] buttons =new MenuButton[3];
    private BufferedImage backgroundImg,backgroundimagepink;
    private int menuX,menuY,menuWidth,menuHeight;
    
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundimagepink=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU_IMAGE);
    }
    private void loadBackground() {
        backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
        menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
        menuX=Game.GAME_WIDTH/2-menuWidth/2;
        menuY=(int)(45*Game.SCALE);
    }
    
private void loadButtons() {
        buttons[0]=new MenuButton(Game.GAME_WIDTH/2,(int)(Game.SCALE*150),0,GameState.PLAYING);
        buttons[1]=new MenuButton(Game.GAME_WIDTH/2,(int)(Game.SCALE*220),1,GameState.OPTIONS);
        buttons[2]=new MenuButton(Game.GAME_WIDTH/2,(int)(Game.SCALE*290),2,GameState.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb:buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
       g.drawImage(backgroundimagepink, 0, 0,Game.GAME_WIDTH,Game.GAME_HEIGHT, null);
       g.drawImage(backgroundImg, menuX, menuY,menuWidth,menuHeight, null);
       for(MenuButton mb:buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb:buttons){
        if(isIn(e,mb)){
        mb.setMousePressed(true);
        }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb:buttons){
        if(isIn(e,mb)){
        if(mb.isMousePressed())
          mb.applyGamestate();
        break;
        }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb:buttons)
            mb.setMouseOver(false);
        
        for(MenuButton mb:buttons)
            if(isIn(e,mb)){
            mb.setMouseOver(true);
            break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            GameState.state=GameState.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void resetButtons() {
        for(MenuButton mb:buttons){
        mb.resetBools();
        }
    }
 
}
