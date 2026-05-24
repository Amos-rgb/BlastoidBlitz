import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bomb extends Space {
    private int countdown;
    Player player;
    public Bomb(Player player) {
        super(player.x,player.y);
        collision = false;
        try {
            sprite = ImageIO.read(new File("src/sprites/bomb.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        this.player = player;
        countdown = 125; //3secs
    }

    public boolean canExplode() {
        if (countdown == 0) return true;
        countdown--;
        return false;
    }

    public void detonate() {
        countdown = 0;
    }
}