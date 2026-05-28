import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Space {
    int x;
    int y;
    public boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    BufferedImage sprite;
    private int UpBound;
    private int bottomBound;
    private int leftBound;
    private int rightBound;
    boolean destroyable;
    boolean isBlock;

    public Space (int X1, int X2, int Y1, int Y2){
        this.isBlock = true;
        if (isBlock) {
            if (X1 > X2) {
                leftBound = X2;
                rightBound = X1;
            } else {
                leftBound = X1;
                rightBound = X2;
            }
            if (Y1 > Y2) {
                UpBound = Y2;
                bottomBound = Y1;
            } else {
                UpBound = Y1;
                bottomBound = Y2;
            }
            this.x = (X1 + X2) / 2;
            this.y = (Y1 + Y2) / 2;
        }else{
            System.out.println("error, bad code");
        }
    }
    public Space (int x, int y){
        isBlock = false;

    }



    public int getUpBound (){
        return UpBound;
    }
    public int getBottomBound(){
        return bottomBound;
    }
    public int getLeftBound(){
        return leftBound;
    }
    public int getRightBound(){
        return rightBound;
    }

    public void update(int X1, int X2, int Y1, int Y2){
        if (X1 > X2){
            leftBound = X2;
            rightBound=X1;
        }else{
            leftBound = X1;
            rightBound=X2;
        }
        if (Y1 > Y2){
            UpBound = Y2;
            bottomBound=Y1;
        }else{
            UpBound = Y1;
            bottomBound=Y2;
        }
    }


    public void drawSpace(Graphics g) {
        g.drawImage(sprite,x,y, null);
    }
    public boolean isDestroyable(){return destroyable;}
}