import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Space {
    Rectangle bounds;

    boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    boolean destroyable; //Whether the space can be destroyed by bombs
    boolean isTrap;
    BufferedImage[] sprites;
    int frame;
    final int FRAME_WAIT_TIME = 18; //Frames before a sprite's frame is updated (18 = 0.432 secs)
    int frameCountdown;

    public Space (int x, int y){
        bounds = new Rectangle(x,y,64,64);
        frameCountdown = FRAME_WAIT_TIME;
    }

    public void drawSpace(Graphics g) {
        if (frameCountdown == 0) {
            frameCountdown = FRAME_WAIT_TIME;
            frame++;
            frame %= sprites.length;
        }
        g.drawImage(sprites[frame],bounds.x,bounds.y, null);
        frameCountdown--;
    }

}