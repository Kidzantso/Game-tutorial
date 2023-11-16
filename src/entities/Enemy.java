/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.geom.Rectangle2D;
import main.Game;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.*;
/**
 *
 * @author dodom
 */
public abstract class Enemy extends Entity{
    protected int enemyType,walkDir=LEFT;
    protected boolean firstupdate=true;
    
    protected int tileY;
    protected float attackDistace = Game.TILES_SIZE;
    protected boolean active=true,attackChecked;
    
    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType=enemyType;
        this.state=IDLE;
        maxHealth=GetMaxHealth(enemyType);
        currentHealth=maxHealth;
        walkSpeed=0.35f*Game.SCALE;
    }
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>=ANI_SPEED){
        aniTick=0;
        aniIndex++;
        if(aniIndex>=GetSpriteAmount(enemyType, state)){
        aniIndex=0;
        
        
        switch(state){
            case ATTACK,HIT -> state=IDLE;
            case DEAD -> active=false;
        }               
       }
     }
    }
    
    public boolean isActive() {
        return active;
    }

    protected void firstUpdateCheck(int[][] lvlData){
          if(!IsEntityOnFloor(hitbox,lvlData))
           inAir=true;
           firstupdate=false;
        }
    
    protected void updateInAir(int[][] lvlData){

       if(CanMoveHere(hitbox.x,hitbox.y+AirSpeed,hitbox.width,hitbox.height,lvlData)){
       hitbox.y+=AirSpeed;
       AirSpeed+=GRAVITY;
       }else{
           inAir=false;
           hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox, AirSpeed);
           tileY=(int)(hitbox.y/Game.TILES_SIZE);
       }
    }
    protected void move(int[][]lvlData){
        float xSpeed=0;
                
                if(walkDir ==LEFT)
                    xSpeed = -walkSpeed;
                else 
                    xSpeed = walkSpeed;
                
                if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                    if(IsFloor(hitbox,xSpeed,lvlData)){
                        hitbox.x+=xSpeed;
                        return;
                    }
                ChangeWalkDir();
    }
    
    protected void newState(int enemyState){
    this.state=enemyState;
    aniTick=0;
    aniIndex=0;
    }
    
    protected void ChangeWalkDir() {
        if(walkDir==LEFT)
            walkDir=RIGHT;
        else 
            walkDir=LEFT;
    }
    protected boolean canSeePlayer(int[][]lvlData,Player player){
        int playerTileY=(int)(player.getHitbox().y/Game.TILES_SIZE);
        if(playerTileY==tileY)
            if(isPlayerInRange(player)){
            if(IsSightClear(lvlData,hitbox,player.hitbox,tileY))
                return true;
            }
        return false;
    }
    protected void turnsTowardsPlayer(Player player){
        if(player.hitbox.x>hitbox.x)
        walkDir=RIGHT;
        else
        walkDir=LEFT;
    }
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x-hitbox.x);
        return absValue <=attackDistace *5;
    }
    protected boolean isPlayerCloseForAttack(Player player){
    int absValue = (int) Math.abs(player.hitbox.x-hitbox.x);
        return absValue <=attackDistace;
    }
    void hurt(int i) {
       currentHealth-=i;
       if(currentHealth<=0)
           newState(DEAD);
       else
           newState(HIT);
    }
    protected void checkPlayerHit(Rectangle2D.Float attackBox,Player player){
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDMG(enemyType));
        attackChecked=true;
    }
    void resetEnemy() {
        hitbox.x=x;
        hitbox.y=y;
        firstupdate=true;
        active=true;
        currentHealth=maxHealth;
        newState(IDLE);
        AirSpeed=0;
    }
}
