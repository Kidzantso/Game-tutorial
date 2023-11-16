/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import entities.Crabby;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Traps;
import utilz.HelpMethods;
import static utilz.HelpMethods.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class Level {
    private BufferedImage img;
    private int[][]lvlData;
    private ArrayList<Crabby>crabs;
    private ArrayList<Potion>potions;
    private ArrayList<Traps>traps;
    private ArrayList<GameContainer>containers;
    private ArrayList<Cannon>cannons;
    private int lvlTilesWide;
    private int mXTilesOffset;
    private int mXlvloffsetX;


    private Point playerSpawn;
    
    public Level(BufferedImage img){
    this.img=img;
    createLevelData();
    creatEnemies();
    createPotions();
    createContainers();
    createTraps();
    createcannons();
    calculateLevelOffsets();
    calcPlayerSpawn();
    }
    public int getSpriteIndex(int x,int y){
    return lvlData[y][x];
    }
    public int[][] getLevelData(){
    return lvlData;
    }

    private void createLevelData() {
        lvlData=GetLevelData(img);
    }

    private void creatEnemies() {
        crabs=GetCrabs(img);
    }
 
    public int[][] getLvlData() {
        return lvlData;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public int getmXlvloffsetX() {
        return mXlvloffsetX;
    }

    private void calculateLevelOffsets() {
        lvlTilesWide = img.getWidth();
        mXTilesOffset = lvlTilesWide-Game.TILES_IN_WIDTH;
        mXlvloffsetX=Game.TILES_SIZE*mXTilesOffset;
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }
    public Point getplayerSpawn(){
    return playerSpawn;
    }

    private void createPotions() {
        potions=HelpMethods.GetPotions(img);
    }
    
    private void createContainers() {
        containers=HelpMethods.GetContainers(img);
    }
    
    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    private void createTraps() {
        traps=HelpMethods.GetTraps(img);
    }
    
    private void createcannons() {
        cannons=HelpMethods.GetCannons(img);
    }
    public ArrayList<Cannon> getCannons() {
        return cannons;
    }
    public ArrayList<Traps> getTraps() {
        return traps;
    }
    
}
