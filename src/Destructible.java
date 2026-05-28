import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Destructible extends Space {
    public Destructible(int x, int y) {
        super(x,y);
        collision = true;
        destroyable = true;
        try {
            sprite = ImageIO.read(new File("src/sprites/wooden plank.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
