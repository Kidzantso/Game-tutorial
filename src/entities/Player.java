/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Game;
import main.GamePanel;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class Player extends Entity{
  
    private BufferedImage [][] animations;

    private boolean left,right,jump;
    private boolean moving=false,attacking=false,attackChecked=false;
    private int[][] lvlData;
    private float xDrawOffset= 21* Game.SCALE;
    private float yDrawOffset= 4*Game.SCALE;
    private BufferedImage bar;

    private float jumpSpeed = -2.25f*Game.SCALE;
    private float fallSpeedAfterCollision=0.5f*Game.SCALE;
    

    private int healthWidth=healthBarWidth;
    private int PowerWidth=PowerBarWidth;
    private int powerMaxValue=200;
    private int powerValue=powerMaxValue;
    
    private int tileY=0;
    private int flipX=0;
    private int flipW=1;
    private boolean powerAttackactive;
    private int powerAttackTick;
    private int powerGrowSpeed=15;
    private int powerGrowTick;
    
    private Playing playing;
    
    public Player(float x, float y, int width, int height,Playing playing) {
		super(x,y,width, height);
                this.playing=playing;
                this.state=IDLE;
                this.maxHealth=100;
                this.currentHealth=maxHealth;
                this.walkSpeed=Game.SCALE*1.0f;
		loadAnimations();
                initHitbox(20,27);
                initAttackbox();
	}
    public void update(){
        updateHealthBar();
        updatePowerBar();
       if(currentHealth<=0){
           if(state!=DEAD){
           state=DEAD;
           aniTick=0;
           aniIndex=0;
           playing.setPlayerDying(true);
           }else if(aniIndex==GetSpriteAmount(DEAD)-1&&aniTick>=ANI_SPEED-1){
           playing.setGameOver(true);
           }else
           updateAnimationTick();
           
           return;           
        }    
     updateAttckBox();
     updatePos();
     if(moving){
         checkPotionTouched();
         checkTrapTouched();
         tileY=(int)(hitbox.y/Game.TILES_SIZE);
         if(powerAttackactive){
             powerAttackTick++;
         if(powerAttackTick>=35){
             powerAttackTick=0;
         powerAttackactive=false;
           }
        }
     }
     if(attacking || powerAttackactive)
        checkAttack();
     
     updateAnimationTick();
     setAnimation();   
    }

    public int getTileY() {
        return tileY;
    }
    
     public void render(Graphics g,int lvlOffset){
    g.drawImage(animations[state][aniIndex], (int)(hitbox.x -xDrawOffset)-lvlOffset+flipX,(int) (hitbox.y-yDrawOffset),width*flipW,height, null);
    drawUI(g);
    //drawAttackBox(g,lvlOffset);
     }
     public void SetSpwan(Point spawn){
         this.x=spawn.x;
         this.y=spawn.y;
         hitbox.x=x;
         hitbox.y=y;
     }
        private void updateAnimationTick() {
        aniTick++;
        if(aniTick>=ANI_SPEED){
        aniTick=0;
        aniIndex++;
        }
        if(aniIndex>=GetSpriteAmount(state)){
        aniIndex=0;
        attacking=false;
        attackChecked=false;
       }
    }

    private void setAnimation() {
        int startAni=state;
        
        if(moving)
            state=RUNNING;
        else 
            state=IDLE;
        if(inAir){
            if(AirSpeed<0)
                state=JUMP;
            else
                state=FALLING;
        }
        if(powerAttackactive){
        state=ATTACK;
        aniIndex=1;
        aniTick=0;
        return;
        }
        if(attacking){
        state=ATTACK;
        if(startAni!=ATTACK)
        {
        aniIndex=1;
        aniTick=0;
        return;
        }
        }
        if(startAni!=state){
        resetAniTick();
        }
    }

    private void updatePos() {
        moving = false;
        
        if(jump)
            jump();

        if(!inAir)
            if(!powerAttackactive)
                if((!left&&!right)|| (right&&left))
                    return;
        
        float xSpeed=0;
        
        if(left&&!right){
        xSpeed -= walkSpeed;
        flipX=width;
        flipW=-1;
        }
        if(right&&!left){
        xSpeed+=walkSpeed;
        flipX=0;
        flipW=1;
        }
        if(powerAttackactive){
            if((!left&&!right)||(left&&right)){
                if(flipW==-1)
                  xSpeed=-walkSpeed;
                else 
                  xSpeed=walkSpeed;
            }
            xSpeed*=3;
        }
        if(!inAir){
        if(!IsEntityOnFloor(hitbox,lvlData)){
        inAir=true;
        }
        }
        if(inAir&&!powerAttackactive){
        if(CanMoveHere(hitbox.x,hitbox.y+AirSpeed,hitbox.width,hitbox.height,lvlData)){
        hitbox.y+=AirSpeed;
        AirSpeed+=GRAVITY;
        updateXPos(xSpeed);
        }else{
        hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,AirSpeed);
        if(AirSpeed>0){
        resetInAir();
        }else {
        AirSpeed=fallSpeedAfterCollision;
        updateXPos(xSpeed);
        }
        }
        
        }else {
        updateXPos(xSpeed);
        }
      moving=true;
    }
    
    public void resetDirBooleans(){
    left=false;
    right=false;
    }
    
    public void setAttack(boolean attacking){
    this.attacking=attacking;
    }
    
     private void loadAnimations() {
         bar= LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
         BufferedImage img= LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
         animations=new BufferedImage[7][8];
    for (int j=0;j<animations.length;j++)
    for(int i=0;i<animations[j].length;i++){
    animations[j][i]=img.getSubimage(i*64, j*40, 64,40);
    }
    }

     public void loadLvlData(int[][]lvlData){
    this.lvlData=lvlData;
    if(!IsEntityOnFloor(hitbox, lvlData))
        inAir=true;
    }
   
    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }
    
    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
        hitbox.x+=xSpeed;
        }
        else{
        hitbox.x=GetEntityXPostNextToWall(hitbox,xSpeed);
        if(powerAttackactive){
            powerAttackactive=false;
            powerAttackTick=0;
            }
        }
    }
    
    private void resetInAir() {
        inAir=false;
        AirSpeed=0;
    }
    
    private void jump() {
        if(inAir){
        return;
        }
        inAir=true;
        AirSpeed=jumpSpeed;
    }
    
    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    private void drawUI(Graphics g) {
        g.drawImage(bar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        
        
        g.setColor(Color.red);
        g.fillRect(healthBarX+statusBarX, healthBarY+statusBarY, healthWidth, healthBarHeight);
    
        g.setColor(Color.yellow);
        g.fillRect(PowerBarXStart+statusBarX, PowerBarYStart+statusBarY, PowerWidth, PowerBarHeight);
    }
    public void changeHealth(int health){
    currentHealth +=health;
    
    if(currentHealth <=0){
        currentHealth=0;
        //death();
    }else if(currentHealth>=maxHealth){
    currentHealth=maxHealth;
    }
    }
    private void updateHealthBar() {
        healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }

    private void initAttackbox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(20*Game.SCALE),(int)(20*Game.SCALE));
    }

    private void updateAttckBox() {
        if(right&&left){
            if(flipW==1){
            attackBox.x=hitbox.x+hitbox.width+(int)(Game.SCALE*10);
            }
            else{
            attackBox.x=hitbox.x-hitbox.width-(int)(Game.SCALE*10);
            }
        }
        else if(right||(powerAttackactive&&flipW==1)){
            attackBox.x=hitbox.x+hitbox.width+(int)(Game.SCALE*10);
        }else if(left || powerAttackactive&&flipW==-1){
            attackBox.x=hitbox.x-hitbox.width-(int)(Game.SCALE*10);
        }
        attackBox.y=hitbox.y+(Game.SCALE*10);
    }

    public void changePower(int value){
        powerValue+=value;
        if(powerValue>=powerMaxValue)
            powerValue=powerMaxValue;
        else if(powerValue<=0)
            powerValue=0;
    }

    private void checkAttack() {
        if(attackChecked || aniIndex!=1)
            return;
        attackChecked=true;
        
        if(powerAttackactive)
            attackChecked=false;
        
        playing.checkEnemyHit(attackBox);
        playing.checkContinerHit(attackBox);
    }

    public void resetAll() {
      resetDirBooleans();
      inAir=false;
      attacking=false;
      moving=false;
      AirSpeed=0f;
      state=IDLE;
      currentHealth=maxHealth;
      hitbox.x=x;
      hitbox.y=y;
      resetattackBox();
      
      if(!IsEntityOnFloor(hitbox,lvlData))
        inAir=true;
        
      
    }
    
    private void resetattackBox(){
    if(flipW==1){
            attackBox.x=hitbox.x+hitbox.width+(int)(Game.SCALE*10);
            }
            else{
            attackBox.x=hitbox.x-hitbox.width-(int)(Game.SCALE*10);
            }
    
    }
    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkTrapTouched() {
        playing.checkTrapTouched(hitbox);
    }

    private void updatePowerBar() {
        PowerWidth=(int)((powerValue/(float)powerMaxValue)*PowerBarWidth);
        
        powerGrowTick++;
        if(powerGrowTick>=powerGrowSpeed){
            powerGrowTick=0;
            changePower(1);
    }
    }

    public void powerAttack() {
        if(powerAttackactive)
            return;
        if(powerValue>=60){
        powerAttackactive=true;
            changePower(-60);
        }
            
    }
}