import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpawnArea extends Space {
    private Player player;
    public SpawnArea(int x, int y, Player player) {
        super(x,y);
        collision = false;
        destroyable = false;
        sprites = new BufferedImage[2];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/kelp/kelp1.png"));
            sprites[1] = ImageIO.read(new File("src/sprites/kelp/kelp2.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
        this.player = player;
    }
}
