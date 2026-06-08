import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Destructible extends Space {
    public Destructible(int x, int y) {
        super(x,y);
        collision = true;
        destroyable = true;
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/barrel.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
    }
}
