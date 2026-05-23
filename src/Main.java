import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
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
        titleScreen.setVisible(true); */
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);
        frame.add(new DisplayPanel());
        frame.setVisible(true);
    }
}
