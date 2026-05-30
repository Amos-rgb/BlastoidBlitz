import java.awt.*;
import java.awt.image.BufferedImage;

public class Space {
    Rectangle bounds;

    boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    boolean destroyable; //Whether the space can be destroyed by bombs
    BufferedImage sprite;
    public boolean isBlock;

    public Space (int x, int y){
        bounds = new Rectangle(x,y,64,64);
    }

    public void drawSpace(Graphics g) {
        g.drawImage(sprite,bounds.x,bounds.y, null);
    }

}