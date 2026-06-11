import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Player extends Space {
    private final int START_X;
    private final int START_Y;
    int maxHealth;
    int health;
    int score;
    public int lives;
    public int moveSpeed;
    int maxBombs;
    int bombsPlaced;
    int bombRadius;
    int xMoveAmount;
    int yMoveAmount;
    public int[] effects;
    int totalBombsPlaced;
    int playersDefeated;
    int enemiesDefeated;
    int spacesDestroyed;
    int effectSpacesLandedOn;
    int selfKills;
    int spacesWalked;
    int playerNum;
    public Player(int x, int y, int playerNum) {
        super(x,y);
        START_X = x;
        START_Y = y;
        this.playerNum = playerNum;
        collision = true;
        destroyable = false;
        sprites = new BufferedImage[20];
        try {

            for (int i = 0; i < 4; i++) {
                sprites[i] = ImageIO.read(new File("src/sprites/player" + playerNum + "/player" + playerNum + "Neutral" + i + ".png"));
            }
            for (int i = 0; i < 4; i++) {
                sprites[i+4] = ImageIO.read(new File("src/sprites/player" + playerNum + "/player" + playerNum + "Up" + i + ".png"));
            }
            for (int i = 0; i < 4; i++) {
                sprites[i+8] = ImageIO.read(new File("src/sprites/player" + playerNum + "/player" + playerNum + "Left" + i + ".png"));
            }
            for (int i = 0; i < 4; i++) {
                sprites[i+12] = ImageIO.read(new File("src/sprites/player" + playerNum + "/player" + playerNum + "Right" + i + ".png"));
            }
            for (int i = 0; i < 4; i++) {
                sprites[i+16] = ImageIO.read(new File("src/sprites/player" + playerNum + "/player" + playerNum + "Down" + i + ".png"));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        frame = 0;
        maxHealth = 1;
        health = maxHealth;
        score = 0;
        lives = 10;
        moveSpeed = 8;
        maxBombs = 2;
        bombRadius = 2;
        effects = new int[10];
        this.inflict(0);
    }

    public boolean damage() { //If the player does not have immunity, decreases the player's health by 1 and sends them back to their initial spawnpoint if health is 0, returns whether they were downed
        if (effects[0] == 0) {
            health--;
            effects[0] = Effect.second*3;
            if (health == 0) {
                lives--;
                health = maxHealth;
                bounds.x = START_X;
                bounds.y = START_Y;
                return true;
            }
        }
        return false;
    }

    public void addScore() {score++;} //Adds 1 to the player's score

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

    public void inflict(int effect) {
        if (effect == 9) {
            for (int i = 0; i < effects.length; i++) if (effects[i] > 1) effects[i] = 1;
            health = maxHealth;
        }
        if (effects[effect] == 0) {
            if (effect == 3) moveSpeed *= 2;
            if (effect == 4) moveSpeed *= 4;
            if (effect == 5) moveSpeed /= 2;
        }
        if (Effect.effects[effect].stackable) {
            if (effect == 6) maxBombs++;
            if (effect == 7) bombRadius++;
            if (effect == 8) maxHealth++;
        } else effects[effect] += Effect.effects[effect].frames;
    }

    @Override
    public void drawSpace(Graphics g) {
        if (effects[3] == 1) moveSpeed /= 2;
        if (effects[4] == 1) moveSpeed /= 4;
        if (effects[5] == 1) moveSpeed *= 2;
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