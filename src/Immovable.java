import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Immovable extends Space {
    public Immovable(int x, int y) {
        super(x,y);
        collision = true;
        try {
            sprite = ImageIO.read(new File("src/wooden plank.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
