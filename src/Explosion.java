import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Space {
    private int countdown;
    private Player player;
    public Explosion(int x, int y, Player player) {
        super(x,y);
        collision = false;
        destroyable = false;
        sprites = new BufferedImage[4];
        try {
            for (int i = 0; i < 4; i++) {
                sprites[i] = ImageIO.read(new File("src/sprites/explosion/explosion" + i + ".png"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frameWaitTime = 125/DisplayPanel.FRAME_LENGTH;
        frame = 0;
        this.player = player;
        countdown = 500/DisplayPanel.FRAME_LENGTH; //0.5secs
    }

    public boolean canDisappear() {
        if (countdown == 0) return true;
        countdown--;
        return false;
    }

    public boolean canDealDamage() {
        return countdown == (500/DisplayPanel.FRAME_LENGTH)-1;
    }

    public Player getPlayer() {return player;}
}