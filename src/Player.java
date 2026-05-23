import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Space {
    private int maxHealth;
    private int health;
    private int score;
    public Player(int x, int y) {
        super(x,y);
        collision = true;
        try {
            sprite = ImageIO.read(new File("src/sprites/sea cucumber.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        maxHealth = 1;
        health = maxHealth;
        score = 0;
    }
}
