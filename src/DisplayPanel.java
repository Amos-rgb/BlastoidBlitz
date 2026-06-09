import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    public static final int FRAME_LENGTH = 24;
    private Clip clip;
    private int gamemode;
    public static boolean imminentVictory;
    private boolean IVMusicStarted;
    private double clock;
    private static boolean[] pressedKeys;
    private BufferedImage background;
    private BufferedImage GUIBackground;
    private BufferedImage water;
    private int waterPos;
    private Timer timer;
    public Player[] players;
    public ArrayList<Space> spaces;
    public int spaceCountdown;
    private ArrayList<Bomb> bombs;
    private ArrayList<Explosion> explosions;
    private ArrayList<Enemy> enemies;
    private int tikTok;//a variable to exam if a timer is triggered

    public DisplayPanel(int gamemode) {
        this.gamemode = gamemode;
        imminentVictory = false;
        IVMusicStarted = imminentVictory;
        if (gamemode == 1) clock = 300;
        else clock = 0;
        pressedKeys = new boolean[128];
        players = new Player[2];
        players[0] = new Player(64,64); //Creates player 1 in the upper left corner
        players[1] = new Player(960,960); //Creates player 2 in the lower right corner
        spaces = new ArrayList<>();
        spaceCountdown = 0;
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        enemies = new ArrayList<>();
        try {
            background = ImageIO.read(new File("src/sprites/sand.png"));
            GUIBackground = ImageIO.read(new File("src/sprites/guiBackground.png"));
            water = ImageIO.read(new File("src/sprites/water.png"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        waterPos = 0;
        timer = new Timer(FRAME_LENGTH,e -> updateGame()); //24ms delay = about 41 fps
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        try { //Plays audio
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/soundtrack/battleTheme.wav"));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.setLoopPoints(clip.getFrameLength()/9,clip.getFrameLength()-50000);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                g.drawImage(background, i * 64, j * 64, null); //Draws the background as repeating tiles
            }
        }
        for (int i = 17; i < 21; i++) {
            for (int j = 0; j < 17; j++) {
                g.drawImage(GUIBackground, i * 64, j * 64, null); //Draws the background as repeating tiles
            }
        }
        for (Space space : spaces) space.drawSpace(g); //Draws all spaces
        for (Bomb bomb : bombs) bomb.drawSpace(g); //Draws all bombs
        for (Enemy enemy : enemies) enemy.drawSpace(g); //Draws all players
        for (Player player : players) player.drawSpace(g); //Draws all players
        for (Explosion explosion : explosions) explosion.drawSpace(g); //Draws all explosions

        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f ));
        g2d.drawRect(0,0,1344,1088);
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                g2d.drawImage(water, (i * 64) + waterPos-64, (j * 64) + waterPos-64, null); //Draws the water filter as repeating tiles
            }
        }
        if (imminentVictory) waterPos += 2;
        else waterPos++;
        waterPos%=64;

        g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f ));
        g.setFont(new Font("Little Fish", Font.PLAIN, 64));
        if (imminentVictory && clock%2 < 1) g.setColor(Color.red);
        else g.setColor(Color.white);
        g.drawString((int) clock/60 + ":" + new DecimalFormat("00").format((int) clock%60),512,52);
        g.setFont(new Font("Little Fish", Font.PLAIN, 36));
        for (int i = 0; i < players.length; i++) { //Draws player info
            g.drawString("Player " + (i+1) + ":", 1096, 40+(i*512));
            g.drawString("Health: " + players[i].getHealth() + "/" + players[i].getMaxHealth(), 1096, 120+(i*512));
            if (gamemode == 2) g.drawString("Lives: " + players[i].getLives(), 1096, 160+(i*512));
            else g.drawString("Score: " + players[i].getScore(), 1096, 160+(i*512));
            g.drawString("Bombs placed: " + players[i].bombsPlaced + "/" + players[i].maxBombs, 1096, 200+(i*512));
            g.drawString("Bomb size: " + players[i].bombRadius, 1096, 240+(i*512));
            g.drawString("Effects:", 1096, 280+(i*512));
            int k = 320;
            for (int j = 0; j < Effect.effects.length; j++) {
                if (players[i].effects[j] > 0) {
                    if (!Effect.effects[i].stackable) g.drawString(Effect.effects[j].toString() + ": " + players[i].effects[j]*FRAME_LENGTH/1000 + "secs",1096,k+(i*512));
                    k += 40;
                }
            }
        }
        g.drawString("W, A, S, D, Q", 1096, 80);
        g.drawString("Arrow Keys, /", 1096, 592);
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

    public void updateGame() {
        checkWinCondition();
        regenerateSpaces();
        movePlayers();
        moveEnemies();
        checkExplosions();
        checkBombs();
        repaint();
        if (gamemode == 1) clock -= FRAME_LENGTH*0.001;
        else clock += FRAME_LENGTH*0.001;
        tikTok++;
    }

    private void movePlayers() {
        // player 1
        players[0].setMoveAmount(0, 0);
        if (pressedKeys[KeyEvent.VK_W]) players[0].setMoveAmount(0, -players[0].moveSpeed); //Up

        if (pressedKeys[KeyEvent.VK_A]) players[0].setMoveAmount(-players[0].moveSpeed, 0); //Left

        if (pressedKeys[KeyEvent.VK_S]) players[0].setMoveAmount(0, players[0].moveSpeed); //Down

        if (pressedKeys[KeyEvent.VK_D]) players[0].setMoveAmount(players[0].moveSpeed, 0); //Right

        if (pressedKeys[KeyEvent.VK_Q]) addBomb(players[0]); //Creates a bomb at the player's current location
        movePlayer(players[0]); //Moves the player according to their moveAmounts
        // player 2
        players[1].setMoveAmount(0,0);
        if (pressedKeys[KeyEvent.VK_UP]) players[1].setMoveAmount(0, -players[1].moveSpeed);

        if (pressedKeys[KeyEvent.VK_LEFT]) players[1].setMoveAmount(-players[1].moveSpeed, 0);

        if (pressedKeys[KeyEvent.VK_DOWN]) players[1].setMoveAmount(0, players[1].moveSpeed);

        if (pressedKeys[KeyEvent.VK_RIGHT]) players[1].setMoveAmount(players[1].moveSpeed, 0);

        if (pressedKeys[KeyEvent.VK_SLASH]) addBomb(players[1]);
        movePlayer(players[1]);
    }

    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.moveTowardsPlayer(players[enemy.target])) addBomb(enemy);
            movePlayer(enemy);
        }
    }

    public void movePlayer(Player player) {
        int x = player.getxMoveAmount();
        int y = player.getyMoveAmount();
        player.movePlayer(x,y); //Moves the selected player by the desired amount
        for (Bomb bomb : bombs) {
            if ((bomb.collision || bomb.getPlayer() != player) && player.bounds.intersects(bomb.bounds)) { //Returns to original position when colliding with bomb
                player.movePlayer(-x,-y);
                player.setMoveAmount();
                return;
            }
        }
        for (Player player1 : players) {
            if (player != player1 && player.bounds.intersects(player1.bounds)) { //Returns to original position when colliding with player
                if (player1.collision) {
                    player.movePlayer(-x,-y);
                    player.setMoveAmount();
                    return;
                }
            }
        }
        for (Enemy enemy : enemies) {
            if (player != enemy && player.bounds.intersects(enemy.bounds)) { //Returns to original position when colliding with player
                if (enemy.collision) {
                    player.setMoveAmount();
                    player.movePlayer(-x,-y);
                    return;
                }
            }
        }
        for (int i = 0; i < spaces.size(); i++) {
            Space space = spaces.get(i);
            if (player.bounds.intersects(space.bounds)) {
                if (space.collision) { //Returns to original position when colliding with miscellaneous space
                    player.setMoveAmount();
                    player.movePlayer(-x,-y);
                    return;
                }
                if (space.getClass() == EffectSpace.class && player.isOnGrid()) {
                    player.inflict(((EffectSpace) space).effect);
                    spaces.remove(space);
                }
                if (space.getClass() == SpawnArea.class && ((SpawnArea) space).getPlayer() != player) { //Returns to original position when colliding with spawn area
                    player.setMoveAmount();
                    player.movePlayer(-x,-y);
                    return;
                }
            }
        }
    }

    public void addBomb(Player player) {
        for (Space space : spaces) {
            if (player.bounds.intersects(space.bounds) && space.getClass() != SpawnArea.class) return;
        }
        for (Bomb bomb : bombs) {
            if (player.bounds.intersects(bomb.bounds)) return;
        }
        if (player.bombsPlaced < player.maxBombs) {
            bombs.add(new Bomb(player));
            player.bombsPlaced++;
        }
    }

    public void checkBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.bounds.intersects(bomb.getPlayer().bounds) && !bomb.collision) bomb.collision = true;
            if (bomb.canExplode()) { //Checks all bombs to see if their countdown has finished
                int bombX = bomb.bounds.x;
                int bombY = bomb.bounds.y;
                int blastRadius = bomb.getPlayer().bombRadius; //The amount of blocks around the bomb to destroy
                for (int x = -blastRadius*64; x <= blastRadius*64; x += 64) {
                    for (int y = -blastRadius*64; y <= blastRadius*64; y += 64) {
                        boolean isSpawnArea = false;
                        for (Space space : spaces) {
                            if (space.bounds.x == bombX + x && space.bounds.y == bombY + y && space.getClass() == SpawnArea.class) {
                                isSpawnArea = true; //If the space is a spawn area, does not create explosion
                                break;
                            }
                        }
                        if (!isSpawnArea && (x == 0 || y == 0)) explosions.add(new Explosion(bombX+x,bombY+y, bomb.getPlayer())); //Creates explosions in a diamond around the exploded bomb
                    }
                }
                bomb.getPlayer().bombsPlaced--;
                bombs.remove(bomb); //Removes the exploded bomb
            }
        }
    }

    public void checkExplosions() {
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            if (explosion.canDealDamage()) { //Checks if an explosion has just been created, and therefore can deal damage
                for (Bomb bomb : bombs) { //Checks all bombs to see if they are on the same space as the explosion
                    if (explosion.bounds.intersects(bomb.bounds))
                        bomb.detonate(); //If the bomb is hit by the explosion, it explodes immediately
                }
                for (int j = 0; j < spaces.size(); j++) {
                    Space space = spaces.get(j);
                    if (explosion.bounds.intersects(space.bounds)) //Checks all spaces to see if they are on the same space as the explosion
                        if (space.destroyable) {
                            spaces.remove(space); //Otherwise, removes the space
                            j--;
                        }
                }
                for (Player player : players) {
                    if (player.bounds.intersects(explosion.bounds))
                        if (player.damage() && player != explosion.getPlayer())
                            explosion.getPlayer().addScore();
                }
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    if (enemy.bounds.intersects(explosion.bounds))
                        if (enemy.destroyable) {
                            for (Player player : players) {
                                if (player == explosion.getPlayer()) player.addScore();
                            }
                            enemies.remove(enemy);
                            j--;
                        }
                }
            }
            if (explosion.canDisappear()) { //Checks all explosions to see if their countdown has finished
                explosions.remove(explosion); //Removes all finished explosions
            }
        }
    }

    public void regenerateSpaces() {
        if (spaceCountdown <= 0) {
            if (spaces.size() < 2000) {
                spaceCountdown = 1000/FRAME_LENGTH;//1 sec
                Rectangle bounds = randomSpace();
                if (overlaps(bounds)) return;
                addRandomObstacle(bounds.x,bounds.y);
            }
        }
        spaceCountdown--;
    }

    public void checkWinCondition() {
        if (gamemode == 1) {
             if (clock <= 0)
                System.exit(0);
             else if (clock <= 60)
                 imminentVictory = true;
        }
        for (Player player : players) {
            if (gamemode == 2) {
                 if (player.getLives() == 0)
                    System.exit(0);
                 else if (player.getLives() == 1)
                     imminentVictory = true;
            }
            if (gamemode == 3) {
                if (player.getScore() >= 10)
                    System.exit(0);
                else if (player.getScore() == 9)
                    imminentVictory = true;
            }
        }
        if (imminentVictory && !IVMusicStarted) {
            clip.stop();
            try { //Plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/soundtrack/imminentVictory.wav"));
                clip = AudioSystem.getClip();
                clip.open(audio);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                System.out.println(e.getMessage());
            }
            IVMusicStarted = true;
        }
    }

    public Rectangle randomSpace() {
        return new Rectangle((int) (Math.random()*17)*64, (int) (Math.random()*17)*64, 64, 64);
    }

    public void addRandomObstacle(int x, int y) {
        double rand = Math.random();
        if (rand > 0.1)
            spaces.add(new Destructible(x, y));
        else if (rand>0.09)
            enemies.add(new Enemy(x,y));
        else
            spaces.add(new EffectSpace(x,y));
    }

    public boolean overlaps(Rectangle bounds) {
        for (Space space : spaces) {
            if (bounds.intersects(space.bounds)) return true;
        }
        for (Bomb bomb : bombs) {
            if (bounds.intersects(bomb.bounds)) return true;
        }
        for (Player player : players) {
            if (bounds.intersects(player.bounds)) return true;
        }
        for (Enemy enemy : enemies) {
            if (bounds.intersects(enemy.bounds)) return true;
        }
        return false;
    }
}