import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(512, 512);
        titleScreen.setLocationRelativeTo(null);
        titleScreen.setVisible(true); //Unfinished attempt at implementing title screen
        JButton button = new JButton("Play");
        titleScreen.add(button);
        button.setBounds(128,256,256,64);
        button.addActionListener(e -> startGame());
        JLabel label = new JLabel("Blastoid Blitz");
        titleScreen.add(label);
        label.setFont(new Font("Comic Sans", Font.BOLD,40));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(64,64);

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
        try {
            JFrame frame = new JFrame("Blastoid Blitz");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1088, 1088);
            frame.setLocationRelativeTo(null);
            DisplayPanel panel = new DisplayPanel();
            MazeGenerator generator = new MazeGenerator(17, 17, 1088);
            generator.generate();
            generator.printMap(panel);
            frame.add(panel);
            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}