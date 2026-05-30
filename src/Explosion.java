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
        try {
            sprite = ImageIO.read(new File("src/realisticPack/explosion.png"));
            //sprite = ImageIO.read(new File("src/sprites/wood.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        countdown = 25; //0.6secs
        this.player = player;
    }

    public boolean canDisappear() {
        if (countdown == 0) return true;
        countdown--;
        return false;
    }

    public boolean canDealDamage() {
        return countdown == 25;
    }
}