import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);

        // create a DisplayPanel object
        DisplayPanel panel = new DisplayPanel();

        // add it to the frame
        frame.add(panel);

        // call setVisible after everything else
        frame.setVisible(true);
    }
}
