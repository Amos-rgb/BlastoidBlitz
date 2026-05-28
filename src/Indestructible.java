import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Indestructible extends Space {
    public Indestructible(int x, int y) {
        super(x,y);
        collision = true;
        destroyable = false;
        try {
            sprite = ImageIO.read(new File("src/sprites/obstacle-Rock.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}