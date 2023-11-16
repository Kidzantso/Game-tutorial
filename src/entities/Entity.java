/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import main.Game;

/**
 *
 * @author dodom
 */
public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick,aniIndex;
    protected int state;
    protected float AirSpeed;
    protected boolean inAir=false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;
        
    public Entity(float x,float y,int width,int height){
    this.x=x;
    this.y=y;
    this.width=width;
    this.height=height;     
    }

    protected void drawHitbox(Graphics g){
    g.setColor(Color.pink);
    g.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }
    protected void initHitbox(int width,int height) {
        hitbox=new Rectangle2D.Float(x,y,(int)(width * Game.SCALE),(int)(height * Game.SCALE));
    }
//    protected void updateHitbox(){
//    hitbox.x=(int)x;
//    hitbox.y=(int)y;
//    }
    public Rectangle2D.Float getHitbox(){
    return hitbox;
    }
    public int getState() {
        return state;
    }
    protected void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x-lvlOffset, (int)attackBox.y, (int)attackBox.width,(int) attackBox.height);
    }
        public int getAniIndex() {
        return aniIndex;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
     
}
