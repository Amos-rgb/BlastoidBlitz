import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);

        DisplayPanel panel = new DisplayPanel();

        frame.add(panel);

        frame.setVisible(true);
    }
}
