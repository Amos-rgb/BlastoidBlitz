import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Space {
    public Explosion(int x, int y) {
        super(x,y);
        collision = false;
        try {
            sprite = ImageIO.read(new File("src/sprites/wooden plank.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
