import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bomb extends Space {
    private int countdown;
    private Player player;
    public Bomb(Player player) {
        super(player.bounds.x/64*64,player.bounds.y/64*64);
        collision = false;
        sprites = new BufferedImage[7];
        try {
            for (int i = 0; i < 7; i++) {
                sprites[i] = ImageIO.read(new File("src/sprites/bomb/bomb" + i + ".png"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        frame = 0;
        this.player = player;
        countdown = 126; //Slightly over 3 secs
    }

    public boolean canExplode() {
        if (countdown == 0) return true;
        countdown--;
        return false;
    }

    public void detonate() {
        countdown = 0;
    } //Causes bomb to explode the next time canExplode() is called

    public Player getPlayer() {return player;}
}