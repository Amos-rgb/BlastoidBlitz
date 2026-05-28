import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Space {
    Rectangle bounds;

    public boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    BufferedImage sprite;
    public boolean destroyable; //Whether the space can be destroyed by bombs
    public boolean isBlock;

    public Space (int x, int y){
        bounds = new Rectangle(x,y,64,64);
    }

    public void drawSpace(Graphics g) {
        g.drawImage(sprite,bounds.x,bounds.y, null);
    }
    public boolean isDestroyable(){return destroyable;}
}