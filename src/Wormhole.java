import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wormhole extends Space {

    public Wormhole(int x, int y) {
        super(x, y);
        collision = false;
        destroyable = false;
        sprites = new BufferedImage[10];
        try {
            for (int i = 0; i < 10; i++) {
                sprites[i] = ImageIO.read(new File("src/sprites/coral/coral" + i + ".png"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
