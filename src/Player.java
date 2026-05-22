import java.awt.image.BufferedImage;

public class Player {
    public int x;
    public int y;;
    private int maxHealth;
    private int health;
    private int score;
    private BufferedImage sprite;
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        maxHealth = 1;
        health = maxHealth;
        score = 0;
    }

    public void placeBomb() {

    }
}
