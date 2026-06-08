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
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("src/sprites/bubble.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        effect = (int) (Math.random()*3);
    }

    public int getEffect() {
        return effect;
    }

    @Override
    public void drawSpace(Graphics g) {
        g.drawImage(sprites[0],bounds.x,bounds.y, null);
    }
}
