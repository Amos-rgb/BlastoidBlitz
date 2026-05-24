import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player extends Space {
    private int maxHealth;
    private int health;
    private int score;
    private final int START_X;
    private final int START_Y;
    public Player(int x, int y) {
        super(x,y);
        START_X = x;
        START_Y = y;
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

    public void damage() {
        health--;
        if (health == 0) {
            health = maxHealth;
            x = START_X;
            y = START_Y;
        }
    }
}