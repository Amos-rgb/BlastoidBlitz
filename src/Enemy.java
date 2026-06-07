import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy extends Player {
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
    }
    public boolean randomlyMove() {
        if (isOnGrid()) {
            double rand = Math.random();
            if (rand < 0.1) {
                xMoveAmount = 0;
                yMoveAmount = -16;
            } else if (rand < 0.2) {
                xMoveAmount = -16;
                yMoveAmount = 0;
            } else if (rand < 0.3) {
                xMoveAmount = 16;
                yMoveAmount = 0;
            } else if (rand < 0.4) {
                xMoveAmount = 0;
                yMoveAmount = 16;
            } else if (rand < 0.41){
                return true;
            } else {
                xMoveAmount = 0;
                yMoveAmount = 0;
            }
        }
        return false;
    }
}
