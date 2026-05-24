import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Space {
    int x;
    int y;
    public boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    BufferedImage sprite;
    public Space(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawSpace(Graphics g) {
        g.drawImage(sprite,x,y, null);
    }
}