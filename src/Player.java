import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Space {
    private final int START_X;
    private final int START_Y;
    int maxHealth;
    int health;
    private int score;
    public int lives;
    private int maxBombs;
    int xMoveAmount;
    int yMoveAmount;
    public int[] effects;
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
        sprites = new BufferedImage[20];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/starfish/starfishUp1.png"));
            sprites[1] = ImageIO.read(new File("src/sprites/starfish/starfishRight1.png"));
            sprites[2] = ImageIO.read(new File("src/sprites/starfish/starfishDown1.png"));
            sprites[3] = ImageIO.read(new File("src/sprites/starfish/starfishLeft1.png"));
            for (int i = 0; i < 3; i++) {
                sprites[i+4] = ImageIO.read(new File("src/sprites/starfish/starfishUp" + i + ".png"));
            }
            sprites[7] = ImageIO.read(new File("src/sprites/starfish/starfishUp1.png"));
            for (int i = 0; i < 3; i++) {
                sprites[i+8] = ImageIO.read(new File("src/sprites/starfish/starfishLeft" + i + ".png"));
            }
            sprites[11] = ImageIO.read(new File("src/sprites/starfish/starfishLeft1.png"));
            for (int i = 0; i < 3; i++) {
                sprites[i+12] = ImageIO.read(new File("src/sprites/starfish/starfishRight" + i + ".png"));
            }
            sprites[15] = ImageIO.read(new File("src/sprites/starfish/starfishRight1.png"));
            for (int i = 0; i < 3; i++) {
                sprites[i+16] = ImageIO.read(new File("src/sprites/starfish/starfishDown" + i + ".png"));
            }
            sprites[19] = ImageIO.read(new File("src/sprites/starfish/starfishDown1.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        frame = 0;
        maxHealth = 1;
        health = maxHealth;
        score = 0;
        lives = 10;
        maxBombs = 2;
        effects = new int[10];
        effects[0] = 125; //3secs
    }

    public boolean damage() { //If the player does not have immunity, decreases the player's health by 1 and sends them back to their initial spawnpoint if health is 0, returns whether they were downed
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

    public int getLives() {return lives;}

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

    public int getDirection() {
        if (yMoveAmount < 0) return 1;
        if (xMoveAmount < 0) return 2;
        if (xMoveAmount > 0) return 3;
        if (yMoveAmount > 0) return 4;
        return 0;
    }

    public boolean isOnGrid() {
        return bounds.x % 64 == 0 && bounds.y % 64 == 0;
    }

    public void movePlayer(int x, int y) {
        if (effects[2] == 0) {
            bounds.x += x;
            bounds.y += y;
        }
         if (!isOnGrid() && xMoveAmount == 0 && yMoveAmount == 0) {
             bounds.x = (bounds.x/64)*64;
             bounds.y = (bounds.y/64)*64;
         }
    }

    public void inflict(int effect, int frames) {
        effects[effect] += frames;
    }

    @Override
    public void drawSpace(Graphics g) {
        for (int i = 0; i < effects.length; i++) if (effects[i] > 0) effects[i]--;
        if (DisplayPanel.imminentVictory && frameWaitTime == 400/DisplayPanel.FRAME_LENGTH) frameWaitTime = 200/DisplayPanel.FRAME_LENGTH;
        if (frameCountdown == 0) {
            frameCountdown = frameWaitTime;
            frame++;
            frame %= 4;
        }
        if (effects[2] == 0) {
            g.drawImage(sprites[frame+(4*getDirection())],bounds.x,bounds.y, null);
        } else {
            try {
                BufferedImage b = ImageIO.read(new File("src/sprites/trappedPlayerInBubble.png"));
                g.drawImage(b,bounds.x,bounds.y, null);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        frameCountdown--;
    }
}