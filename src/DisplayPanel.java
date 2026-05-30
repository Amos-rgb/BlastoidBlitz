import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private boolean[] pressedKeys;
    private BufferedImage background;
    private Timer timer;
    private Player player1;
    private Player player2;
    public ArrayList<Space> spaces;
    private ArrayList<Bomb> bombs;
    private ArrayList<Explosion> explosions;

    public DisplayPanel() throws IOException {
        pressedKeys = new boolean[128];
        player1 = new Player(64,64); //Creates player1 in the upper left corner
        player2 = new Player(960,960); //Creates player2 in the lower right corner
        spaces = new ArrayList<>();
        spaces.add(player1);
        spaces.add(player2);
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        try {
            background = ImageIO.read(new File("src/realisticPack/sand.png"));
            //background = ImageIO.read(new File("src/sprites/background.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        timer = new Timer(24,e -> updateGame());
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                g.drawImage(background, i*64, j*64, null);
            }
        }
        for (Space space : spaces) space.drawSpace(g); //Draws all spaces
        for (Bomb bomb : bombs) bomb.drawSpace(g); //Draws all bombs
        for (Explosion explosion : explosions) explosion.drawSpace(g); //Draws all explosions
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void movePlayers() {
        int moveAmount = 64;
        // player1
        if (pressedKeys[KeyEvent.VK_W]) {movePlayer(player1,0,-moveAmount);}

        if (pressedKeys[KeyEvent.VK_A]) {movePlayer(player1,-moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_S]) {movePlayer(player1,0,moveAmount);}

        if (pressedKeys[KeyEvent.VK_D]) {movePlayer(player1,moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_Q]) {bombs.add(new Bomb(player1));}
        // player2
        if (pressedKeys[KeyEvent.VK_UP]) {movePlayer(player2,0,-moveAmount);}

        if (pressedKeys[KeyEvent.VK_LEFT]) {movePlayer(player2,-moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_DOWN]) {movePlayer(player2,0,moveAmount);}

        if (pressedKeys[KeyEvent.VK_RIGHT]) {movePlayer(player2,moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_SLASH]) {bombs.add(new Bomb(player2));}
    }

    public void updateGame() {
        movePlayers();
        checkExplosions();
        checkBombs();
        repaint();
    }

    public void movePlayer(Player player, int x, int y) {
        player.bounds.x += x; //Moves the selected player by the desired amount
        player.bounds.y += y;
        try {
            if (x > 0 || y > 0) player.sprite = ImageIO.read(new File("src/realisticPack/johndown.png"));
            if (x < 0 || y < 0) player.sprite = ImageIO.read(new File("src/realisticPack/johnup.png"));
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Bomb bomb : bombs) {
            if (player.bounds.intersects(bomb.bounds)) { //If the player is entering a space with collision, returns to original position
                player.bounds.x -= x;
                player.bounds.y -= y;
                return;
            }
        }
        for (Space space : spaces) {
            if (player != space && player.bounds.intersects(space.bounds)) { //If the player is entering a space with collision, returns to original position
                if (space.collision) {
                    player.bounds.x -= x;
                    player.bounds.y -= y;
                    return;
                }
            }
        }
    }

    public void checkBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.canExplode()) { //Checks all bombs to see if their countdown has finished
                for (int x = -64; x <= 64; x += 64) {
                    for (int y = -64; y <= 64; y += 64) {
                        explosions.add(new Explosion(bomb.bounds.x+x,bomb.bounds.y+y, bomb.player)); //Creates 9 explosions in a square around the exploded bomb
                    }
                }
                bombs.remove(bomb); //Removes the exploded bomb
            }
        }
    }
    public void checkExplosions() {
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            if (explosion.canDealDamage()) { //Checks if an explosion has just been created, and therefore can deal damage
                for (int j = 0; j < bombs.size(); j++) { //Checks all bombs to see if they are on the same space as the explosion
                    Bomb bomb = bombs.get(j);
                    if (explosion.bounds.intersects(bomb.bounds)) bomb.detonate(); //If the bomb is hit by the explosion, it explodes immediately
                }
                for (int j = 0; j < spaces.size(); j++) {
                    Space space = spaces.get(j);
                    if (explosion.bounds.intersects(space.bounds)) { //Checks all spaces to see if they are on the same space as the explosion
                        if (space.getClass() == Player.class) {
                            ((Player) space).damage(); //If the space is a player, damages them
                        } else if (space.destroyable){
                            spaces.remove(space); //Otherwise, removes the space
                        }
                    }
                }
            }
            if (explosion.canDisappear()) { //Checks all explosions to see if their countdown has finished
                explosions.remove(explosion); //Removes all finished explosions
            }
        }
    }
}

