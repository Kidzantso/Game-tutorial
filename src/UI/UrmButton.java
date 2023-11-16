/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;

/**
 *
 * @author dodom
 */
public class UrmButton extends PausingButton{
    private BufferedImage[] UrmImgs;
    private boolean mouseOver,mousePressed;
    private int rowIndex,index;
    public UrmButton(int x, int y, int width, int height,int rowIndex) {
        super(x, y, width, height);
        this.rowIndex=rowIndex;
        loadUrmImgs();
    }
    private void loadUrmImgs() {
         BufferedImage temp=LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
         UrmImgs= new BufferedImage[3];
             for(int i=0;i<UrmImgs.length;i++)
                 UrmImgs[i]=temp.getSubimage(i*URM_SIZE_DEFAULT, rowIndex*URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
    }
    
    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
    }
    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void update(){
        index=0;
        if(mouseOver)
            index=1;
        if(mousePressed)
            index=2;   
    }
    
    public void draw(Graphics g){
        g.drawImage(UrmImgs[index],x, y, URM_SIZE, URM_SIZE, null);
    }
}
