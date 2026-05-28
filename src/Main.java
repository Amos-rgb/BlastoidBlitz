import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(512, 512);
        titleScreen.setLocationRelativeTo(null);
        JButton button = new JButton("Play");
        button.setLocation(256,256);
        button.setBounds(256,256,256,64);
        JLabel label = new JLabel("Blastoid Blitz");
        titleScreen.add(button);
        titleScreen.add(label);
        titleScreen.setVisible(true); */ //Unfinished attempt at implementing title screen
        try { //Plays audio
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/soundtrack/test.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);
        frame.add(new DisplayPanel());
        frame.setVisible(true);
    }
}