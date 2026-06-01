import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private boolean[] pressedKeys;
    private BufferedImage background;
    private Timer timer;
    public Player[] players;
    public ArrayList<Space> spaces;
    public int spaceCountdown;
    private ArrayList<Bomb> bombs;
    private ArrayList<Explosion> explosions;

    public DisplayPanel() {
        pressedKeys = new boolean[128];
        players = new Player[2];
        players[0] = new Player(64,64); //Creates player 1 in the upper left corner
        players[1] = new Player(960,960); //Creates player 2 in the lower right corner
        spaces = new ArrayList<>();
        spaceCountdown = 0;
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        try {
            background = ImageIO.read(new File("src/sprites/sand.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        timer = new Timer(24,e -> updateGame()); //24ms delay = about 41 fps
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
                g.drawImage(background, i*64, j*64, null); //Draws the background as repeating tiles
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
        int moveAmount = 64/4; //64 over the amount of frames it takes to move one space
        // player 1
        if (players[0].bounds.x % 64 == 0 && players[0].bounds.y % 64 == 0) { //Checks that the player is on the grid
            players[0].setxMoveAmount(0); //If the player is not pressing any movement keys, stops moving
            players[0].setyMoveAmount(0);
            if (pressedKeys[KeyEvent.VK_W]) players[0].setyMoveAmount(-moveAmount); //Up

            if (pressedKeys[KeyEvent.VK_A]) players[0].setxMoveAmount(-moveAmount); //Left

            if (pressedKeys[KeyEvent.VK_S]) players[0].setyMoveAmount(moveAmount); //Down

            if (pressedKeys[KeyEvent.VK_D]) players[0].setxMoveAmount(moveAmount); //Right

            if (pressedKeys[KeyEvent.VK_Q]) {addBomb(players[0]);} //Creates a bomb at the player's current location
        }
        movePlayer(players[0]); //Moves the player according to their moveAmounts
        // player 2
        if (players[1].bounds.x % 64 == 0 && players[1].bounds.y % 64 == 0) {
            players[1].setxMoveAmount(0);
            players[1].setyMoveAmount(0);
            if (pressedKeys[KeyEvent.VK_UP]) players[1].setyMoveAmount(-moveAmount);

            if (pressedKeys[KeyEvent.VK_LEFT]) players[1].setxMoveAmount(-moveAmount);

            if (pressedKeys[KeyEvent.VK_DOWN]) players[1].setyMoveAmount(moveAmount);

            if (pressedKeys[KeyEvent.VK_RIGHT]) players[1].setxMoveAmount(moveAmount);

            if (pressedKeys[KeyEvent.VK_SLASH]) {addBomb(players[1]);}
        }
        movePlayer(players[1]);
    }

    public void updateGame() {
        regenerateSpaces();
        movePlayers();
        checkExplosions();
        checkBombs();
        repaint();
    }

    public void movePlayer(Player player) {
        int x = player.getxMoveAmount();
        int y = player.getyMoveAmount();
        player.bounds.x += x; //Moves the selected player by the desired amount
        player.bounds.y += y;
        for (Bomb bomb : bombs) {
            if (bomb.collision && player.bounds.intersects(bomb.bounds)) { //If the player is entering a space with collision, returns to original position
                player.bounds.x -= x;
                player.bounds.y -= y;
                return;
            }
        }
        for (Player player1 : players) {
            if (player != player1 && player.bounds.intersects(player1.bounds)) {
                if (player.collision) { //Space has collision
                    player.bounds.x -= x;
                    player.bounds.y -= y;
                    return;
                }
            }
        }
        for (Space space : spaces) {
            if (player.bounds.intersects(space.bounds)) { //Returns to original position if:
                if (space.collision) { //Space has collision
                    player.bounds.x -= x;
                    player.bounds.y -= y;
                    return;
                }
                if (space.getClass() == SpawnArea.class && ((SpawnArea) space).getPlayer() != player) { //Space is other player's spawn area
                    player.bounds.x -= x;
                    player.bounds.y -= y;
                    return;
                }
            }
        }
    }

    public void addBomb(Player player) {
        int playerBombs = 0;
        for (Bomb bomb : bombs) {
            if (bomb.bounds.intersects(player.bounds)) return; //Prevents placing multiple bombs on the same spot}
            if (bomb.getPlayer() == player) playerBombs++;
        }
        if (playerBombs < player.getMaxBombs()) bombs.add(new Bomb(player));
    }

    public void checkBombs() {
        int blastRadius = 10; //The amount of blocks around the bomb to destroy
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.bounds.intersects(bomb.getPlayer().bounds) && !bomb.collision) bomb.collision = true;
            if (bomb.canExplode()) { //Checks all bombs to see if their countdown has finished
                for (int x = bomb.bounds.x-blastRadius*64; x <= bomb.bounds.x+blastRadius*64; x += 64) {
                    for (int y = bomb.bounds.y-blastRadius*64; y <= bomb.bounds.y+blastRadius*64; y += 64) {
                        boolean isSpawnArea = false;
                        for (Space space : spaces) {
                            if (space.bounds.x == x && space.bounds.y == y && space.getClass() == SpawnArea.class) {
                                isSpawnArea = true; //If the space is a spawn area, does not create explosion
                                break;
                            }
                        }
                        if (!isSpawnArea && (x == bomb.bounds.x || y == bomb.bounds.y)) explosions.add(new Explosion(x,y, bomb.getPlayer())); //Creates explosions in a square around the exploded bomb
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

    public void regenerateSpaces() {
        if (spaceCountdown == 0) {
            if (spaces.size() < 180) {
                Rectangle bounds = randomSpace();
                for (Space space : spaces) {
                    if (bounds.intersects(space.bounds)) return;
                }
                for (Bomb bomb : bombs) {
                    if (bounds.intersects(bomb.bounds)) return;
                }
                for (Player player : players) {
                    if (bounds.intersects(player.bounds)) return;
                }
                spaces.add(new Destructible(bounds.x, bounds.y));
                spaceCountdown = 75;//
            }
        }
        spaceCountdown--;
    }

    public Rectangle randomSpace() {
        return new Rectangle((int) (Math.random()*17)*64, (int) (Math.random()*17)*64, 64, 64);
    }
}

