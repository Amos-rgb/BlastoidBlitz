import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player extends Space {
    private final int START_X;
    private final int START_Y;
    private int maxHealth;
    private int health;
    private int score;
    private int immunity;
    public Player(int x, int y) {
        super(x,y);
        START_X = x;
        START_Y = y;
        collision = true;
        try {
            sprite = ImageIO.read(new File("src/realisticPack/johndown.png"));
            //sprite = ImageIO.read(new File("src/sprites/kelp.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        maxHealth = 1;
        health = maxHealth;
        score = 0;
        immunity = 125; //3secs
    }

    public boolean damage() { //If the player does not have immunity, ecreases the player's health by 1 and sends them back to their initial spawnpoint if health is 0, returns whether they were downed
        if (immunity == 0) {
            health--;
            immunity = 125;
            if (health == 0) {
                health = maxHealth;
                bounds.x = START_X;
                bounds.y = START_Y;
                return true;
            }
        }
        return false;
    }
    public void reduceImmunity() {if (immunity > 0) immunity--;}

    public void addScore() {score++;} //Adds 1 to the player's score

    public int getScore() {return score;}

    public int getHealth() {return health;}

    public int getMaxHealth() {return maxHealth;}
}