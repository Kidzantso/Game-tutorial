/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import entities.Player;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import main.Game;
import static utilz.Constants.ObjectConstants.*;
import utilz.Constants.Projectiles;
import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethods.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class ObjectManager {
    
    private Playing playing;
    private BufferedImage[][]potionImgs,containerImgs;
    private BufferedImage[] cannonsimg;
    private BufferedImage trapimg,cannonballimg;
    private ArrayList<Potion>potions;
    private ArrayList<Traps>traps;
    private ArrayList<Cannon>cannons;
    private ArrayList<GameContainer>containers;
    private ArrayList<Projectile>projectiles= new ArrayList<>();
    
    public ObjectManager(Playing playing){
    this.playing=playing;
    loadImages();

    }
    public void chechPotionTouched(Rectangle2D.Float hitbox){
        for(Potion p:potions)
            if(p.isActive())
                if(hitbox.intersects(p.getHitbox())){
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
    }
    public void checkTrapTouched(Rectangle2D.Float hitbox){
        for(Traps t:traps)           
                if(hitbox.intersects(t.getHitbox())){
                    playing.getPlayer().changeHealth(-100);
                }
    }
    
    public void applyEffectToPlayer(Potion p){
        if(p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }
    
    public void checkContainerHit(Rectangle2D.Float attackbox){
        for(GameContainer c:containers)
            if(c.isActive()&&!c.doAnimation){
                if(attackbox.intersects(c.getHitbox())){
                    c.setAnimation(true);
                    
                    int type=0;
                    if(c.getObjType()==BARREL)
                        type=1;
                    potions.add(new Potion((int)(c.getHitbox().x + c.getHitbox().width/2),(int)(c.getHitbox().y-c.getHitbox().height/2),type));
                        
                    return;
                }
                    }
    
    }
    private void loadImages() {
       BufferedImage potionSprite=LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
       potionImgs=new BufferedImage[2][7];
       
       for(int j=0;j<potionImgs.length;j++)
           for(int i=0;i<potionImgs[j].length;i++)
               potionImgs[j][i]=potionSprite.getSubimage(12*i, 16*j, 12, 16);
           
       BufferedImage containerSprite=LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
       containerImgs=new BufferedImage[2][8];
       
       for(int j=0;j<containerImgs.length;j++)
           for(int i=0;i<containerImgs[j].length;i++)
               containerImgs[j][i]=containerSprite.getSubimage(40*i, 30*j, 40, 30);
    
     trapimg=LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
     
     cannonsimg=new BufferedImage[7];
     BufferedImage cannonsSprite=LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);
     for(int j=0;j<cannonsimg.length;j++)
         cannonsimg[j]=cannonsSprite.getSubimage(40*j, 0, 40, 26);
     
     cannonballimg=LoadSave.GetSpriteAtlas(LoadSave.BALL);
    }
    
    public void update(int[][] lvlData,Player player){
        for(Potion p:potions)
            if(p.isActive())
                p.update();
        
        for(GameContainer c:containers)
            if(c.isActive())
                c.update();
        
        updateCannons(lvlData,player);
        updateprojectiles(lvlData,player);
    
    }
    public void draw(Graphics g,int xLvlOffset){
        drawPotions(g,xLvlOffset);
        drawContainers(g,xLvlOffset);
        drawTraps(g,xLvlOffset);
        drawCannons(g,xLvlOffset);
        drawProjectiles(g,xLvlOffset);
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
         for(Potion p:potions)
            if(p.isActive()){
                int type =0;
                if(p.getObjType()==RED_POTION)
                    type=1;
                
                g.drawImage(potionImgs[type][p.getAniIndex()],(int) (p.getHitbox().x-xLvlOffset),(int) p.getHitbox().y,POTION_WIDTH,POTION_HEIGHT, null);

                
    }}

    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer c:containers)
            if(c.isActive()){
                int type =0;
                if(c.getObjType()==BARREL)
                    type=1;
                
                g.drawImage(containerImgs[type][c.getAniIndex()],(int) (c.getHitbox().x-xLvlOffset),(int) (c.getHitbox().y-c.getyDrawOffset()),CONTAINER_WIDTH,CONTAINER_HEIGHT, null);
    }
    
    }

    public void loadObjects(Level newLevel) {
        containers= new ArrayList<>(newLevel.getContainers());
        potions=new ArrayList<>(newLevel.getPotions());
        traps=newLevel.getTraps();
        cannons=newLevel.getCannons();
        projectiles.clear();
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        
        for(GameContainer c:containers)
            c.reset();
        for(Potion p:potions)
            p.reset();
        for(Cannon cn:cannons)
            cn.reset();
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for(Traps t:traps)
           g.drawImage(trapimg,(int) (t.getHitbox().x-xLvlOffset),(int) (t.getHitbox().y-t.getyDrawOffset()),TRAP_WIDTH,TRAP_HEIGHT, null); 
    }

   

    private void drawCannons(Graphics g, int xLvlOffset) {
            for(Cannon c:cannons){
                int x = (int)(c.getHitbox().x-xLvlOffset);
                int width=CANNON_WIDTH;
            if(c.getObjType()== CANNON_RIGHT){
             x+=width;
             width*=-1;
            } 
                 g.drawImage(cannonsimg[c.getAniIndex()],x,(int) c.getHitbox().y,width,CANNON_HEIGHT, null);
            }
            
}
 private void updateCannons(int[][] lvlData,Player player) {
        for(Cannon c:cannons){
            if(!c.doAnimation)
                if(c.getTileY()==player.getTileY())
                    if(isPlayerInRange(c,player))
                        if(isPlayerInfrontOfCannon(c,player))
                            if(CanCannonSeePlayer(lvlData,player.getHitbox(),c.getHitbox(),c.getTileY()))
                            c.setAnimation(true);
                            
            
            c.update();
            if(c.getAniIndex()==4&&c.getAniTick()==0)
                shootCanon(c);
    }
}

    private boolean isPlayerInRange(Cannon c, Player player) {
         int absValue = (int) Math.abs(player.getHitbox().x-c.getHitbox().x);
        return absValue <= Game.TILES_SIZE *5;
    }

    private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
       if(c.getObjType()==CANNON_LEFT){
           if(c.getHitbox().x>player.getHitbox().x)
               return true;
    }else if(c.getHitbox().x<player.getHitbox().x)
        return true;
    
    return false;
    }

    private void shootCanon(Cannon c) {
        c.setAnimation(true);
        
        int dir=1;
        if(c.objType==CANNON_LEFT)
            dir=-1;
        
        projectiles.add(new Projectile((int)c.getHitbox().x,(int)c.getHitbox().y,dir));
    }

    private void updateprojectiles(int[][] lvlData, Player player) {
        for(Projectile p:projectiles)
            if(p.isActive()){
                p.updatepos();
                if(p.getHitbox().intersects(player.getHitbox()))
                {
                player.changeHealth(-25);
                p.setActive(false);
                }else if(IsProjectileHittingLevel(p,lvlData))
                 p.setActive(false);
                
            }
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for(Projectile p:projectiles)
            if(p.isActive())
                g.drawImage(cannonballimg,(int) p.getHitbox().x-xLvlOffset,(int) p.getHitbox().y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
    }


}
