import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Space {
    private final int START_X;
    private final int START_Y;
    private int maxHealth;
    private int health;
    private int score;
    private int maxBombs;
    private int xMoveAmount;
    private int yMoveAmount;
    private int[] effects;
    /* Immunity: 0
    * Ice physics: 1
    * Trapped: 2
    *
    * */
    public Player(int x, int y) {
        super(x,y);
        START_X = x;
        START_Y = y;
        collision = true;
        destroyable = false;
        sprites = new BufferedImage[2];
        try {
            sprites[0] = ImageIO.read(new File("src/realisticPack/johndown.png"));
            sprites[1] = ImageIO.read(new File("src/realisticPack/johnup.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
        maxHealth = 1;
        health = maxHealth;
        score = 0;
        maxBombs = 2;
        effects = new int[10];
        effects[0] = 125; //3secs
    }

    public boolean damage() { //If the player does not have immunity, ecreases the player's health by 1 and sends them back to their initial spawnpoint if health is 0, returns whether they were downed
        if (effects[0] == 0) {
            health--;
            effects[0] = 125;
            if (health == 0) {
                health = maxHealth;
                bounds.x = START_X;
                bounds.y = START_Y;
                return true;
            }
        }
        return false;
    }
    public void addScore() {score++;} //Adds 1 to the player's score

    public int getScore() {return score;}

    public int getHealth() {return health;}

    public int getMaxHealth() {return maxHealth;}

    public int getMaxBombs() {return maxBombs;}

    public void setMoveAmount(int x, int y) {
        if (isOnGrid()) {
            if (effects[1] > 0) {
                if (xMoveAmount == 0 && yMoveAmount == 0) {
                    this.xMoveAmount = x;
                    this.yMoveAmount = y;
                }
            } else {
                this.xMoveAmount = x;
                this.yMoveAmount = y;
            }
        }
    }

    public void setMoveAmount() {
        this.xMoveAmount = 0;
        this.yMoveAmount = 0;
    }

    public int getxMoveAmount() {return xMoveAmount;}

    public int getyMoveAmount() {return yMoveAmount;}

    public boolean isOnGrid() {
        return bounds.x % 64 == 0 && bounds.y % 64 == 0;
    }

    public void movePlayer(int x, int y) {
        if (effects[2] == 0) {
            bounds.x += x;
            bounds.y += y;
        }
    }

    @Override
    public void drawSpace(Graphics g) {
        for (int i = 0; i < effects.length; i++) if (effects[i] > 0) effects[i]--;
        if (frameCountdown == 0) {
            frameCountdown = FRAME_WAIT_TIME;
            frame++;
            frame %= 2;
        }
        g.drawImage(sprites[frame],bounds.x,bounds.y, null);
        frameCountdown--;
    }
}