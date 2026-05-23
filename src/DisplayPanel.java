import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private boolean[] pressedKeys;
    private BufferedImage background;
    private Timer timer;
    private Player player1;
    private Player player2;
    private ArrayList<Space> spaces;
    public DisplayPanel() {
        pressedKeys = new boolean[128];
        player1 = new Player(0,0);
        player2 = new Player(960,960);
        spaces = new ArrayList<>();
        spaces.add(player1);
        spaces.add(player2);
        for (int i = 0; i < 64; i++) {
            int x = (int) (Math.random()*16)*64;
            int y = (int) (Math.random()*16)*64;
            boolean validSpace = true;
            for (Space space : spaces) {
                if (space.x == x && space.y == y) {
                    validSpace = false;
                }
            }
            if (validSpace) spaces.add(new Immovable(x,y));
        }
        try {
            background = ImageIO.read(new File("src/sprites/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
        g.drawImage(background, 0, 0, null);
        for (Space space : spaces) space.drawSpace(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void movePlayers() {
        // player1
        if (pressedKeys[KeyEvent.VK_W]) {movePlayer(player1,0,-64);}

        if (pressedKeys[KeyEvent.VK_A]) {movePlayer(player1,-64,0);}

        if (pressedKeys[KeyEvent.VK_S]) {movePlayer(player1,0,64);}

        if (pressedKeys[KeyEvent.VK_D]) {movePlayer(player1,64,0);}

        if (pressedKeys[KeyEvent.VK_Q]) {spaces.add(new Bomb(player1));}
        // player2
        if (pressedKeys[KeyEvent.VK_UP]) {movePlayer(player2,0,-64);}

        if (pressedKeys[KeyEvent.VK_LEFT]) {movePlayer(player2,-64,0);}

        if (pressedKeys[KeyEvent.VK_DOWN]) {movePlayer(player2,0,64);}

        if (pressedKeys[KeyEvent.VK_RIGHT]) {movePlayer(player2,64,0);}

        if (pressedKeys[KeyEvent.VK_SLASH]) {spaces.add(new Bomb(player2));}
    }

    public void updateGame() {
        movePlayers();
        checkForExplosions();
        repaint();
    }

    public void movePlayer(Player player, int x, int y) {
        player.x += x;
        player.y += y;
        if (player.x < 0 || player.x > 960 || player.y < 0 || player.y > 960) {
            player.x -= x;
            player.y -= y;
            return;
        }
        for (Space space : spaces) {
            if (player != space && player.x == space.x && player.y == space.y) {
                if (space.collision) {
                    player.x -= x;
                    player.y -= y;
                    return;
                }
            }
        }
    }

    public void checkForExplosions() {
        for (int i = 0; i < spaces.size(); i++) {
            Space space = spaces.get(i);
            if (space.canExplode()) {
                for (int x = -64; x <= 64; x += 64) {
                    for (int y = -64; y <= 64; y += 64) {
                        spaces.add(new Explosion(space.x+x,space.y+y));
                    }
                }

            }
        }
    }
}
