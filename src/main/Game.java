/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import UI.AudioOptions;
import gamestates.GameOptions;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import java.awt.Graphics;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class Game implements Runnable{
    
    private GameWindow gamewindow;
    private GamePanel gamepanel;
    private Thread gameThread;
    private final int FPS_SET=120;
    private final int UPS_SET=200;

    private Playing playing;
    private Menu menu;
    private GameOptions gameOptions;
    private AudioOptions AudioOptions;
    
    public final static int TILES_DEFAULT_SIZE=32;
    public final static float SCALE=1.5f;
    public final static int TILES_IN_WIDTH=26;
    public final static int TILES_IN_HEIGHT=14;
    public final static int TILES_SIZE=(int)(TILES_DEFAULT_SIZE*SCALE);
    public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT=TILES_SIZE*TILES_IN_HEIGHT;
    
    public Game() {  
        initClasses();
        
        gamepanel=new GamePanel(this);
        gamewindow=new GameWindow(gamepanel);
        gamepanel.setFocusable(true);
        gamepanel.requestFocus();
        
        startGameLoop();
    }
    private void initClasses() {
       AudioOptions=new AudioOptions();
       menu=new Menu(this);
       playing=new Playing(this);
       gameOptions=new GameOptions(this);
    }
    private void startGameLoop(){
    gameThread=new Thread(this);
    gameThread.start();
    }


    public void update(){   
        switch(GameState.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }
    public void render(Graphics g){
        switch(GameState.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                gameOptions.draw(g);
                break;
            default:
                break;
        }

    }
    @Override
    public void run() {
        
        double timePerFrame= 1000000000.0/FPS_SET;
        double timePerUpdate= 1000000000.0/UPS_SET;        
        long previousTime=System.nanoTime();
        
        int frames=0;
        int updates=0;
        long lastcheck=System.currentTimeMillis();
        
        double deltaU=0;
        double deltaF=0;
        
        while(true){

        long currentTime = System.nanoTime();
        
        deltaU+=(currentTime-previousTime)/timePerUpdate;
        deltaF+=(currentTime-previousTime)/timePerFrame;
        previousTime=currentTime;
        
        if(deltaU>=1){
        update();
        updates++;
        deltaU--;
        }
        if(deltaF>=1){
            //update();
        gamepanel.repaint();
           deltaF--;
           frames++;
        }
        
        if(System.currentTimeMillis()-lastcheck>=1000){
            lastcheck=System.currentTimeMillis();
            System.out.println("Frames:"+ frames + " | UPS "+updates);
            frames=0;
            updates=0;
        }
      }
       
    }
    public void windowFocusLost(){
   if(GameState.state==GameState.PLAYING)
       playing.getPlayer().resetDirBooleans();
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }

    public AudioOptions getAudioOptions() {
        return AudioOptions;
    }
        public GameOptions getGameOptions() {
        return gameOptions;
    }
    
}
