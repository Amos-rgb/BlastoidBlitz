import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bomb extends Space {
    private int countdown;
    private Player player;
    public Bomb(Player player) {
        super(player.x,player.y);
        collision = false;
        try {
            sprite = ImageIO.read(new File("src/bomb.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        this.player = player;
        countdown = 125;
    }
    public Rectangle explosion() {
        if (countdown == 0) return new Rectangle(x-64,y-64,192,192);
        countdown--;
        return null;
    }
}
