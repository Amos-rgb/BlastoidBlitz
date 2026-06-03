import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(384, 320);
        titleScreen.setLocationRelativeTo(null);
        titleScreen.setVisible(true); //Unfinished attempt at implementing title screen
        JButton button = new JButton("Play");
        titleScreen.add(button);
        button.setBounds(64,192,256,64);
        button.addActionListener(e -> startGame());
        button.addActionListener(e -> titleScreen.dispose());
        JLabel label = new JLabel(new ImageIcon("src/sprites/title.png"));
        label.getIcon().paintIcon(titleScreen, titleScreen.getGraphics(), 64, 64);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/sfx/teto.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }

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