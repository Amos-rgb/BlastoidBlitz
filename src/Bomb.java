import java.awt.*;

public class Bomb {
    public int x;
    public int y;
    private int countdown;
    private Player player;
    public Bomb(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
        countdown = 125;
    }
    public Rectangle explosion() {
        if (countdown == 0) return new Rectangle(x-64,y-64,192,192);
        countdown--;
        return new Rectangle(-1,-1,0,0);
    }
}
