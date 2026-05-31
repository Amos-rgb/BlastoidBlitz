import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Indestructible extends Space {
    public Indestructible(int x, int y) {
        super(x,y);
        collision = true;
        destroyable = false;
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/rock.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
    }
}