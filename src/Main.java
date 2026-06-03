import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/sprites/pirateFont.otf")));
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(512, 512);
        titleScreen.setLocationRelativeTo(null);
        titleScreen.setLayout(null);
        ImageIcon title = new ImageIcon("./src/sprites/title.png");
        JLabel label = new JLabel(title);
        label.setBounds(128, 32, 256, 128);
        titleScreen.add(label);
        titleScreen.setVisible(true);
        JButton gameButton = new JButton("Play");
        titleScreen.add(gameButton);
        for (Font font : graphicsEnvironment.getAllFonts()) System.out.println(font.getFontName());
        gameButton.setFont(new Font("Pirate Treasure Demo", Font.PLAIN, 14));
        gameButton.setBounds(128,192,256,64);
        gameButton.addActionListener(e -> gameSelect());
        gameButton.addActionListener(e -> titleScreen.dispose());
        JButton settingsButton = new JButton("Controls");
        titleScreen.add(settingsButton);
        settingsButton.setBounds(128,288,256,64);
        settingsButton.addActionListener(e -> gameSelect());
        settingsButton.addActionListener(e -> titleScreen.dispose());
        JButton exitButton = new JButton("Exit");
        titleScreen.add(exitButton);
        exitButton.setBounds(128,384,256,64);
        exitButton.addActionListener(e -> titleScreen.dispose());
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/sfx/teto.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void gameSelect() {
        JFrame gameSelectScreen = new JFrame("Game Select");
        gameSelectScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSelectScreen.setSize(384, 640);
        gameSelectScreen.setLocationRelativeTo(null);
        gameSelectScreen.setVisible(true);
        JButton button = new JButton("Start!");
        gameSelectScreen.add(button);
        button.setBounds(64,192,256,64);
        button.addActionListener(e -> startGame());
        button.addActionListener(e -> gameSelectScreen.dispose());
    }
    public static void startGame() {
        try { //Plays audio
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/soundtrack/test.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1344, 1088);
        frame.setLocationRelativeTo(null);
        DisplayPanel panel = new DisplayPanel();
        MazeGenerator generator = new MazeGenerator(17, 17, 1088);
        generator.generate();
        generator.printMap(panel);
        frame.add(panel);
        frame.setVisible(true);
    }
}