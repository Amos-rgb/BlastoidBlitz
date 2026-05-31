import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpawnArea extends Space { //Spawn area: space that prevents explosion and does not have collision for its assigned player
    private Player player;
    public SpawnArea(int x, int y, Player player) {
        super(x,y);
        collision = false;
        destroyable = false;
        sprites = new BufferedImage[4];
        try {
            for (int i = 0; i < 4; i++) {
                sprites[i] = ImageIO.read(new File("src/sprites/kelp/kelp" + i + ".png"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
