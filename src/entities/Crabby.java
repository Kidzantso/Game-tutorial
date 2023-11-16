/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Game;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;

/**
 *
 * @author dodom
 */
public class Crabby extends Enemy{
    
    private int attackBoxOffsetX;
    
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(22,19);
        initAttackbox();
        
    }
    public void updatebehave(int[][] lvlData, Player player){
       if(firstupdate)
        firstUpdateCheck(lvlData);      
       
       if(inAir)
           updateInAir(lvlData);
       else{
        switch(state){
            case IDLE:
                newState(RUNNING);
                break;
            case RUNNING:
                
                if(canSeePlayer(lvlData,player)){
                    turnsTowardsPlayer(player);
                if(isPlayerCloseForAttack(player))
                    newState(ATTACK);
                }
                move(lvlData);
            break;
            case ATTACK:
                if(aniIndex==0)
                    attackChecked=false;
                
                if(aniIndex==3&&!attackChecked)
                    checkPlayerHit(attackBox,player);
                break;
            case HIT:
                break;
        }
       }
    }
    public void update(int[][] lvlData,Player player){
        updatebehave(lvlData,player);
        updateAnimationTick();
        updateAttckBox();
    }
    public int flipX(){
        if(walkDir==RIGHT)
            return width;
        else 
            return 0;
    }
    public int flipW(){
    if(walkDir==RIGHT)
            return -1;
        else 
            return 1;
    }

    private void initAttackbox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(82*Game.SCALE),(int)(19*Game.SCALE));
        attackBoxOffsetX=(int)(Game.SCALE*30);
    }

    protected void updateAttckBox() {
        attackBox.x=hitbox.x - attackBoxOffsetX;
        attackBox.y=hitbox.y;
    }


    

    
}
