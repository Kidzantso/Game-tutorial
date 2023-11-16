/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import gamestates.GameState;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import static main.Game.TILES_SIZE;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex=0;
    
    public LevelManager(Game game) {
        this.game=game;
        importOutsideSprites();
        levels=new ArrayList<>();
        buildAllLevels();
        
    }
    public void draw(Graphics g,int lvloffset){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for(int i=0;i<levels.get(lvlIndex).getLevelData()[0].length;i++){
            int index = levels.get(lvlIndex).getSpriteIndex(i, j);
            g.drawImage(levelSprite[index],TILES_SIZE*i -lvloffset,j*TILES_SIZE,TILES_SIZE,TILES_SIZE,null);
            }

    }
    public void update(){
    }

    public Level getCurrentLevel(){
    return levels.get(lvlIndex);
    }
    public int getAmountoflevels(){
        return levels.size();
    }
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j=0;j<4;j++)
            for(int i=0;i<12;i++){
            int index=j*12+i;
            levelSprite[index]=img.getSubimage(i*32, j*32, 32, 32);
            }
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img:allLevels)
            levels.add(new Level(img));
    }

    public void loadNextLevel() {
        lvlIndex++;
        if(lvlIndex>=levels.size())
        {
        lvlIndex=0;
            System.out.println("No more levels");
            GameState.state=GameState.MENU;
        }
        Level newLevel=levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().LoadEnemies(newLevel);
        game.getPlaying().getObjectmanager().loadObjects(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setmXLvlOffset(newLevel.getmXlvloffsetX());
    }
}
