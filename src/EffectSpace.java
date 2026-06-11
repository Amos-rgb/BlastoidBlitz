import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EffectSpace extends Space {
    int effect;
    public EffectSpace(int x, int y) {
        super(x,y);
        collision = false;
        destroyable = true;
        sprites = new BufferedImage[10];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/effectSpaces/invincibleStar.png"));
            sprites[1] = ImageIO.read(new File("src/sprites/effectSpaces/seaweed.png"));
            sprites[2] = ImageIO.read(new File("src/sprites/effectSpaces/bubble.png"));
            sprites[3] = ImageIO.read(new File("src/sprites/effectSpaces/jellyfish.png"));
            sprites[4] = ImageIO.read(new File("src/sprites/effectSpaces/speedFish.png"));
            sprites[5] = ImageIO.read(new File("src/sprites/effectSpaces/slownessFish.png"));
            sprites[6] = ImageIO.read(new File("src/sprites/effectSpaces/bombUp.png"));
            sprites[7] = ImageIO.read(new File("src/sprites/effectSpaces/bombRangeUp.png"));
            sprites[8] = ImageIO.read(new File("src/sprites/effectSpaces/heart+1.png"));
            sprites[9] = ImageIO.read(new File("src/sprites/effectSpaces/heartFish.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        effect = (int) (Math.random()*Effect.effects.length);
        if (Math.random() > 0.8) effect = (int) (Math.random()*4) + 6; //20% chance it will be forced to be a permanent buff/heart fish

    }

    @Override
    public void drawSpace(Graphics g) {
        g.drawImage(sprites[effect],bounds.x,bounds.y, null);
    }
}
