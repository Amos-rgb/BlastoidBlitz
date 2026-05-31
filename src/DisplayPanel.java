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
    public Player[] players;
    public ArrayList<Space> spaces;
    private ArrayList<Bomb> bombs;
    private ArrayList<Explosion> explosions;

    public DisplayPanel() throws IOException {
        pressedKeys = new boolean[128];
        players = new Player[2];
        players[0] = new Player(64,64); //Creates player 1 in the upper left corner
        players[1] = new Player(960,960); //Creates player 2 in the lower right corner
        spaces = new ArrayList<>();
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        try {
            background = ImageIO.read(new File("src/realisticPack/sand.png"));
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
        for (Player player : players) player.drawSpace(g); //Draws all players
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
        for (Player player : players) player.reduceImmunity();
        int moveAmount = 16;
        // player 1
        if (pressedKeys[KeyEvent.VK_W]) {movePlayer(players[0],0,-moveAmount);}

        if (pressedKeys[KeyEvent.VK_A]) {movePlayer(players[0],-moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_S]) {movePlayer(players[0],0,moveAmount);}

        if (pressedKeys[KeyEvent.VK_D]) {movePlayer(players[0],moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_Q]) {addBomb(players[0]);}
        // player 2
        if (pressedKeys[KeyEvent.VK_UP]) {movePlayer(players[1],0,-moveAmount);}

        if (pressedKeys[KeyEvent.VK_LEFT]) {movePlayer(players[1],-moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_DOWN]) {movePlayer(players[1],0,moveAmount);}

        if (pressedKeys[KeyEvent.VK_RIGHT]) {movePlayer(players[1],moveAmount,0);}

        if (pressedKeys[KeyEvent.VK_SLASH]) {addBomb(players[1]);}
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
        for (Bomb bomb : bombs) {
            if (bomb.collision && player.bounds.intersects(bomb.bounds)) { //If the player is entering a space with collision, returns to original position
                player.bounds.x -= x;
                player.bounds.y -= y;
                return;
            }
        }
        for (Space space : spaces) {
            if (space.collision && player.bounds.intersects(space.bounds)) { //If the player is entering a space with collision, returns to original position
                player.bounds.x -= x;
                player.bounds.y -= y;
                return;
            }
        }
    }

    public void addBomb(Player player) {
        for (Bomb bomb : bombs) if (bomb.bounds.intersects(player.bounds)) return;
        bombs.add(new Bomb(player));
    }

    public void checkBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.canExplode()) { //Checks all bombs to see if their countdown has finished
                for (int x = bomb.bounds.x-64; x <= bomb.bounds.x+64; x += 64) {
                    for (int y = bomb.bounds.y-64; y <= bomb.bounds.y+64; y += 64) {
                        boolean isSpawnArea = false;
                        for (Space space : spaces) {
                            if (space.bounds.x == x && space.bounds.y == y && space.getClass() == SpawnArea.class) {isSpawnArea = true;}
                        }
                        if (!isSpawnArea) explosions.add(new Explosion(x,y, bomb.getPlayer())); //Creates 9 explosions in a square around the exploded bomb
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
                    if (explosion.bounds.intersects(space.bounds)) //Checks all spaces to see if they are on the same space as the explosion
                        if (space.destroyable) spaces.remove(space); //Otherwise, removes the space
                }
                for (Player player : players) {
                    if (player.bounds.intersects(explosion.bounds))
                        if (player.damage() && player != explosion.getPlayer())
                            explosion.getPlayer().addScore();
                }
            }
            if (explosion.canDisappear()) { //Checks all explosions to see if their countdown has finished
                explosions.remove(explosion); //Removes all finished explosions
            }
        }
    }
}

