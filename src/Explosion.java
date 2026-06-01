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
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/bubble.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
        this.player = player;
        countdown = 25; //0.6secs
    }

    public boolean canDisappear() {
        if (countdown == 0) return true;
        countdown--;
        return false;
    }

    public boolean canDealDamage() {
        return countdown == 24;
    }

    public Player getPlayer() {return player;}
}