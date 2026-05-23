import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Space {
    int x;
    int y;
    public boolean collision;
    BufferedImage sprite;
    public Space(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawSpace(Graphics g) {
        g.drawImage(sprite,x,y, null);
    }

    public boolean canExplode() { return false; }
}
