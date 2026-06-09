import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy extends Player {
    int target;
    public Enemy(int x, int y) {
        super(x,y);
        destroyable = true;
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
        target = (int) (Math.random()*2);
    }
    public boolean randomlyMove() {
        if (isOnGrid()) {
            double rand = Math.random();
            if (rand < 0.05) {
                xMoveAmount = 0;
                yMoveAmount = -16;
            } else if (rand < 0.10) {
                xMoveAmount = -16;
                yMoveAmount = 0;
            } else if (rand < 0.15) {
                xMoveAmount = 16;
                yMoveAmount = 0;
            } else if (rand < 0.20) {
                xMoveAmount = 0;
                yMoveAmount = 16;
            } else if (rand < 0.205){ //0.5% chance of placing bomb
                return true;
            } else {
                xMoveAmount = 0;
                yMoveAmount = 0;
            }
        }
        return false;
    }

    public int playerDistance(Player player) {
        return playerXDistance(player) + playerYDistance(player);
    }

    public int playerXDistance(Player player) {
        return Math.abs(bounds.x-player.bounds.x);
    }

    public int playerYDistance(Player player) {
        return Math.abs(bounds.y-player.bounds.y);
    }

    public boolean moveTowardsPlayer(Player player) {
        if (isOnGrid()) {
            if (playerDistance(player) == 64) {
                return true;
            }
            if (Math.random() > 0.5) {
                if (player.bounds.y > bounds.y) {
                    yMoveAmount = moveSpeed;
                } else {
                    yMoveAmount = -moveSpeed;
                }
            } else if (player.bounds.x > bounds.x) {
                xMoveAmount = moveSpeed;
            } else {
                xMoveAmount = -moveSpeed;
            }
        }
        return false;
    }
}
