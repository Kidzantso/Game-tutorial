/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import UI.GameOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverlay;
import entities.EnemyManager;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import levels.LevelManager;
import main.Game;
import static main.Game.SCALE;
import objects.ObjectManager;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

/**
 *
 * @author dodom
 */
public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectmanager;
    private PauseOverlay pauseOverlay;
    private GameOverlay gameoverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused =false,lvlCompleted=false;
    
    private int xLvlOffset;
    private int leftBorder=(int) (0.2*Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8*Game.GAME_WIDTH);
    private int mXlvloffsetX;
    
    private BufferedImage backgroundImg;
    private BufferedImage bigCloud,smallCloud;
    private int[] SmallCloudsPos;
    private Random rnd=new Random();
    
    private boolean gameOver=false;
    private boolean playerDying=false;
    
    
    public Playing(Game game) {
        super(game);
        initClasses();
        
        backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND);
        bigCloud=LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud=LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        SmallCloudsPos=new int[8];
        for(int i=0;i<SmallCloudsPos.length;i++)
            SmallCloudsPos[i]=(int)(90*Game.SCALE)+rnd.nextInt((int)(100*Game.SCALE));
    
        calculatelvlOffset();
        loadStartLevel();
    
    
    }
  private void initClasses() { 
        
        levelManager=new LevelManager(game);
        enemyManager=new EnemyManager(this);
        objectmanager=new ObjectManager(this);
        player = new Player(200,200,(int)(64*Game.SCALE),(int)(40*Game.SCALE),this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.SetSpwan(levelManager.getCurrentLevel().getplayerSpawn());
        pauseOverlay=new PauseOverlay(this);
        gameoverlay=new GameOverlay(this);
        levelCompletedOverlay=new LevelCompletedOverlay(this);
        
    } 
  
    @Override
    public void update() {
        if(paused){
        pauseOverlay.update();
        }
        else if(lvlCompleted){
            levelCompletedOverlay.update();
        }else if(gameOver){
        gameoverlay.update();
        }else if(playerDying){
            player.update();
        }else{
        levelManager.update(); 
        objectmanager.update(levelManager.getCurrentLevel().getLevelData(),player);
        player.update();
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
        checkCloseToBorder();
        }
    }
    
private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX-xLvlOffset;
        
        if(diff>rightBorder)
            xLvlOffset+=diff-rightBorder;
        else if(diff<leftBorder)
            xLvlOffset+=diff-leftBorder;
        
        if(xLvlOffset >mXlvloffsetX)
            xLvlOffset=mXlvloffsetX;
        else if(xLvlOffset<0)
            xLvlOffset=0;
    }

    @Override
    public void draw(Graphics g) {        
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);        
        levelManager.draw(g,xLvlOffset);       
        player.render(g,xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
        objectmanager.draw(g,xLvlOffset);
         if(paused){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
         }else if(gameOver){
         gameoverlay.draw(g);         
         }
         else if(lvlCompleted)
           levelCompletedOverlay.draw(g);
         
    }
    private void drawClouds(Graphics g) {
        for(int i=0;i<3;i++)
            g.drawImage(bigCloud, i*BIG_CLOUD_WIDTH -(int) (xLvlOffset*0.3), (int)(204*Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        for(int i=0;i<SmallCloudsPos.length;i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH*4*i -(int) (xLvlOffset*0.7), SmallCloudsPos[i], SMALL_CLOUD_WIDTH,SMALL_CLOUD_HEIGHT,null);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
        if(e.getButton()==MouseEvent.BUTTON1){
        player.setAttack(true);
        }else if(e.getButton()==MouseEvent.BUTTON3){
        player.powerAttack();
        }
    }}
    @Override
    public void keyPressed(KeyEvent e) {
            if(gameOver)
                gameoverlay.keyPressed(e);
            else
            switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
                case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
                case KeyEvent.VK_ESCAPE:
                    paused=!paused;
                    break;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if(!gameOver)
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }
    public void mouseDragged(MouseEvent e) {
        if(!gameOver)
        if(paused){
        pauseOverlay.mouseDragged(e);
        }
    
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
        if(paused)
        pauseOverlay.mousePressed(e);
        else if(lvlCompleted)
         levelCompletedOverlay.mousePressed(e);
        
        }else{
        gameoverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
        if(paused)
            pauseOverlay.mouseReleased(e);
        else if(lvlCompleted)
         levelCompletedOverlay.mouseReleased(e);
    }else{
        gameoverlay.mouseReleased(e);
        }
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver)  {  
        if(paused)
        pauseOverlay.mouseMoved(e);
        else if(lvlCompleted)
        levelCompletedOverlay.mouseMoved(e);
    }else{
        gameoverlay.mouseMoved(e);
        }
    
    }

    public ObjectManager getObjectmanager() {
        return objectmanager;
    }

    public void unpauseGame(){
       paused=false;
   }
    
    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    
    public Player  getPlayer(){
        return player;
    }

    public void resetAll(){
        gameOver=false;
        paused=false;
        lvlCompleted=false;
        playerDying=false;
        player.resetAll();
        enemyManager.resetAllEnemeies();
        objectmanager.resetAllObjects();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
      enemyManager.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean gameOver) {
         this.gameOver=gameOver;
    }

    private void calculatelvlOffset() {
        mXlvloffsetX=levelManager.getCurrentLevel().getmXlvloffsetX();
    }

    private void loadStartLevel() {
          objectmanager.loadObjects(levelManager.getCurrentLevel());
          enemyManager.LoadEnemies(levelManager.getCurrentLevel());         
    }

    public void loadNextLevel(){
        levelManager.loadNextLevel();
        player.SetSpwan(levelManager.getCurrentLevel().getplayerSpawn());
        resetAll();
    }

    public void setmXLvlOffset(int xLvlOffset) {
        this.mXlvloffsetX = xLvlOffset;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setLevelCompleted(boolean levelCompleted) {
      this.lvlCompleted  =levelCompleted;
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectmanager.chechPotionTouched(hitbox);
    }
    public void checkTrapTouched(Rectangle2D.Float hitbox) {
        objectmanager.checkTrapTouched(hitbox);
    }
    public void checkContinerHit(Rectangle2D.Float attackBox) {
       objectmanager.checkContainerHit(attackBox);
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying=playerDying;
    }
    
}
