/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import static utilz.Constants.EnemyConstants.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */


public class EnemyManager {
    private Playing playing;
    private BufferedImage[][]crabbyArr;
    private ArrayList<Crabby> crabbies=new ArrayList<>();
   public EnemyManager(Playing playing){
   
       this.playing=playing;
       loadEnemyImgs();

   } 

    private void loadEnemyImgs() {
    crabbyArr= new BufferedImage[5][9];
    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
    for (int j=0;j<crabbyArr.length;j++)
    for(int i=0;i<crabbyArr[j].length;i++){
    crabbyArr[j][i]=temp.getSubimage(i*CRABBY_WIDTH_DEFAULT, j*CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT,CRABBY_HEIGHT_DEFAULT);
    }
    }
    public void update(int [][] lvlData,Player player){
        boolean isAnyActive=false;
        for(Crabby c:crabbies)
            if(c.isActive()){
            c.update(lvlData,player);
            isAnyActive=true;
        }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }
    public void draw(Graphics g,int xLvlOffset){
        drawCrabs(g,xLvlOffset);
    }

    private void drawCrabs(Graphics g,int xLvlOffset) {
       for(Crabby c:crabbies)
       if(c.isActive())
       {
           g.drawImage(crabbyArr[c.getState()][c.getAniIndex()],(int) c.getHitbox().x-xLvlOffset- CRABBY_DRAWOFFSET_X+c.flipX(),(int) c.getHitbox().y- CRABBY_DRAWOFFSET_Y , CRABBY_WIDTH*c.flipW(), CRABBY_HEIGHT, null);
           //c.drawAttackBox(g,xLvlOffset);
       }
    
    }

    public void LoadEnemies(Level level) {
        crabbies =level.getCrabs();
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Crabby c:crabbies)
           if(c.getCurrentHealth()>0)
            if(c.isActive())
                if(attackBox.intersects(c.getHitbox())){
                c.hurt(10);
                return;
            }
    }

    public void resetAllEnemeies() {
        for(Crabby c:crabbies)
            c.resetEnemy();
    }
}
