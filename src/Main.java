import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/sprites/pirateFont.otf")));
        titleScreen();
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/sfx/teto.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void titleScreen() {
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(256, 384);
        titleScreen.setLocationRelativeTo(null);
        titleScreen.setLayout(null);
        titleScreen.add(new JButton());

        JButton titleCard = new JButton(new ImageIcon("./src/sprites/title.png")); //Title card
        titleCard.addActionListener(e -> titleScreen.dispose());
        titleCard.addActionListener(e -> titleScreen());
        titleCard.setBorderPainted(false);
        titleCard.setBounds(0, 16, 256, 128);
        titleScreen.add(titleCard);

        JButton playButton = titleButton("Play",144); //Play button
        titleScreen.add(playButton);
        playButton.addActionListener(e -> gameSelect());
        playButton.addActionListener(e -> titleScreen.dispose());

        JButton settingButton = titleButton("Setting",208); //Setting button
        titleScreen.add(settingButton);
        settingButton.addActionListener(e -> setting());
        settingButton.addActionListener(e -> titleScreen.dispose());

        JButton exitButton = titleButton("Exit",272); //Exit button
        titleScreen.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        JLabel background = new JLabel(new ImageIcon("./src/sprites/titleBackground.png")); //Background
        background.setBounds(0,0,256,384);
        titleScreen.add(background);

        titleScreen.setVisible(true);
    }

    public static void setting() {
        JFrame settingScreen = new JFrame("Setting");
        settingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingScreen.setSize(256,384);
        settingScreen.setLocationRelativeTo(null);
        settingScreen.setLayout(null);

        JLabel settingText = new JLabel("<html> This game's setting is in the ocean. </html>"); //Text
        settingText.setFont(new Font("Pirate Treasure Demo", Font.PLAIN, 18));
        settingText.setForeground(Color.white);
        settingText.setBounds(16,128,224,64);
        settingScreen.add(settingText);

        int fish = (int) (Math.random()*7); //Back button
        JButton back = new JButton(new ImageIcon("src/sprites/fish/fish" + fish + ".png"));
        back.setBorderPainted(false);
        back.setLayout(null);
        JLabel label = new JLabel("Back");
        label.setFont(new Font("Pirate Treasure Demo", Font.PLAIN, 10));
        back.add(label);
        back.setBounds(16,272,128,64);
        label.setBounds(40,0,128,64);
        back.addActionListener(e -> titleScreen());
        back.addActionListener(e -> settingScreen.dispose());
        settingScreen.add(back);

        JLabel background = new JLabel(new ImageIcon("./src/sprites/titleBackground.png")); //Background
        background.setBounds(0,0,256,384);
        settingScreen.add(background);

        settingScreen.setVisible(true);
    }

    public static void gameSelect() {
        JFrame gameSelectScreen = new JFrame("Game Select");
        gameSelectScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSelectScreen.setSize(512, 512);
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

    public static JButton titleButton(String text, int y) {
        int fish = (int) (Math.random()*14);
        JButton button = new JButton(new ImageIcon("src/sprites/fish/fish" + fish + ".png"));
        button.setBorderPainted(false);
        button.setLayout(null);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Pirate Treasure Demo", Font.PLAIN, 10));
        if (fish <= 6) {
            button.setBounds(76, y, 128, 64);
            label.setBounds(36, 0, 64, 64);
        } else {
            button.setBounds(64, y, 128, 64);
            label.setBounds(48,0,64,64);
        }
        button.add(label);
        return button;
    }
}