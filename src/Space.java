import java.awt.*;
import java.awt.image.BufferedImage;

public class Space {
    Rectangle bounds;

    boolean collision; //Whether the space should prevent a player from moving on to it (true for Players and Immovables, false for Bombs and Explosions)
    boolean destroyable; //Whether the space can be destroyed by bombs
    BufferedImage[] sprites;
    int frame;
    int frameWaitTime; //Frames before a sprite's frame is updated
    int frameCountdown;

    public Space (int x, int y){
        bounds = new Rectangle(x,y,64,64);
        frameWaitTime = 400/DisplayPanel.FRAME_LENGTH;
        frameCountdown = frameWaitTime;
    }

    public void drawSpace(Graphics g) {
        if (DisplayPanel.imminentVictory && frameWaitTime == 400/DisplayPanel.FRAME_LENGTH && this.getClass() != Bomb.class) frameWaitTime = 200/DisplayPanel.FRAME_LENGTH;
        if (frameCountdown == 0) {
            frameCountdown = frameWaitTime;
            frame++;
            frame %= sprites.length;
        }
        g.drawImage(sprites[frame],bounds.x,bounds.y, null);
        frameCountdown--;
    }

}