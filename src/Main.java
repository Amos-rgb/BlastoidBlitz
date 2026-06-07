import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/sprites/fishFont.otf")));
        titleScreen(null);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(""));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void titleScreen(Component location) {
        JFrame titleScreen = new JFrame("Title Screen");
        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleScreen.setSize(256, 384);
        titleScreen.setLocationRelativeTo(location);
        titleScreen.setLayout(null);
        titleScreen.add(new JButton());
        JButton titleCard = new JButton(new ImageIcon("./src/sprites/titleScreen/title.png")); //Title card
        titleCard.addActionListener(e -> titleScreen(titleScreen));
        titleCard.addActionListener(e -> titleScreen.dispose());
        titleCard.setBorderPainted(false);
        titleCard.setContentAreaFilled(false);
        titleCard.setBounds(0,0,256,128);
        titleScreen.add(titleCard);

        JButton playButton = fishButton("Play",64, 144); //Play button
        titleScreen.add(playButton);
        playButton.addActionListener(e -> gameSelect(titleScreen));
        playButton.addActionListener(e -> titleScreen.dispose());

        JButton settingButton = fishButton("Setting", 64,208); //Setting button
        titleScreen.add(settingButton);
        settingButton.addActionListener(e -> setting(titleScreen));
        settingButton.addActionListener(e -> titleScreen.dispose());

        JButton exitButton = fishButton("Exit",64,272); //Exit button
        titleScreen.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        JLabel background = new JLabel(new ImageIcon("./src/sprites/titleScreen/titleBackground.png")); //Background
        background.setBounds(0,0,256,384);
        titleScreen.add(background);

        titleScreen.setVisible(true);
    }

    public static void setting(Component location) {
        JFrame settingScreen = new JFrame("Setting");
        settingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingScreen.setSize(256,384);
        settingScreen.setLocationRelativeTo(location);
        settingScreen.setLayout(null);

        JLabel settingText = new JLabel("<html> This game's setting is in the ocean. </html>"); //Text
        settingText.setFont(new Font("Little Fish", Font.PLAIN, 18));
        settingText.setForeground(Color.white);
        settingText.setBounds(16,128,224,64);
        settingScreen.add(settingText);

        JButton back = fishButton("Back",64,208); //Back button
        back.addActionListener(e -> titleScreen(settingScreen));
        back.addActionListener(e -> settingScreen.dispose());
        settingScreen.add(back);

        JLabel background = new JLabel(new ImageIcon("./src/sprites/titleScreen/titleBackground.png")); //Background
        background.setBounds(0,0,256,384);
        settingScreen.add(background);

        settingScreen.setVisible(true);
    }

    public static void gameSelect(Component location) {
        AtomicInteger gamemode = new AtomicInteger(1);
        JFrame gameSelectScreen = new JFrame("Game Select");
        gameSelectScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSelectScreen.setSize(256, 384);
        gameSelectScreen.setLocationRelativeTo(location);
        gameSelectScreen.setLayout(null);

        JLabel gamemodeText = new JLabel("<html> Select a gamemode! </html>"); //Text
        gamemodeText.setFont(new Font("Little Fish", Font.PLAIN, 24));
        gamemodeText.setForeground(Color.white);
        gamemodeText.setBounds(16,0,224,64);
        gameSelectScreen.add(gamemodeText);

        JButton timerMode = new JButton(new ImageIcon("src/sprites/titleScreen/gamemodes/timerModeSelected.png")); //Timer mode
        timerMode.setBorderPainted(false);
        timerMode.setContentAreaFilled(false);
        timerMode.setBounds(16, 64, 64, 64);
        gameSelectScreen.add(timerMode);

        JButton livesMode = new JButton(new ImageIcon("src/sprites/titleScreen/gamemodes/livesMode.png")); //Lives mode
        livesMode.setBorderPainted(false);
        livesMode.setContentAreaFilled(false);
        livesMode.setBounds(96, 64, 64, 64);
        gameSelectScreen.add(livesMode);

        JButton scoreMode = new JButton(new ImageIcon("src/sprites/titleScreen/gamemodes/scoreMode.png")); //Score mode
        scoreMode.setBorderPainted(false);
        scoreMode.setContentAreaFilled(false);
        scoreMode.setBounds(172, 64, 64, 64);
        gameSelectScreen.add(scoreMode);

        JLabel gamemodeDesc = new JLabel("<html> Players will play until the timer runs out! </html>"); //Description
        gamemodeDesc.setFont(new Font("Little Fish", Font.PLAIN, 18));
        gamemodeDesc.setForeground(Color.white);
        gamemodeDesc.setBounds(16,136,224,64);
        gamemodeDesc.setVerticalAlignment(SwingConstants.TOP);
        gameSelectScreen.add(gamemodeDesc);


        timerMode.addActionListener(e -> {
            gamemode.set(1);
            timerMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/timerModeSelected.png"));
            livesMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/livesMode.png"));
            scoreMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/scoreMode.png"));
            gamemodeDesc.setText("<html> Players will play until the timer runs out! </html>");
        });
        livesMode.addActionListener(e -> {
            gamemode.set(2);
            timerMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/timerMode.png"));
            livesMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/livesModeSelected.png"));
            scoreMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/scoreMode.png"));
            gamemodeDesc.setText("<html> Players will play until one has run out of lives! </html>");
        });
        scoreMode.addActionListener(e -> {
            gamemode.set(3);
            timerMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/timerMode.png"));
            livesMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/livesMode.png"));
            scoreMode.setIcon(new ImageIcon("src/sprites/titleScreen/gamemodes/scoreModeSelected.png"));
            gamemodeDesc.setText("<html> Players will play until one has reached a certain score! </html>");
        });

        JButton button = fishButton("Start!",64,208); //Start button
        gameSelectScreen.add(button);
        button.addActionListener(e -> startGame(gamemode.get()));
        button.addActionListener(e -> gameSelectScreen.dispose());

        JButton back = fishButton("Back",64,272); //Back button
        back.addActionListener(e -> titleScreen(gameSelectScreen));
        back.addActionListener(e -> gameSelectScreen.dispose());
        gameSelectScreen.add(back);

        JLabel background = new JLabel(new ImageIcon("./src/sprites/titleScreen/titleBackground.png")); //Background
        background.setBounds(0,0,256,384);
        gameSelectScreen.add(background);

        gameSelectScreen.setVisible(true);
    }

    public static void startGame(int gamemode) {
        JFrame frame = new JFrame("Blastoid Blitz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1344, 1088);
        frame.setLocationRelativeTo(null);
        DisplayPanel panel = new DisplayPanel(gamemode);
        MazeGenerator generator = new MazeGenerator(17, 17, 1088);
        generator.generate();
        generator.printMap(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static JButton fishButton(String text, int x, int y) {
        int fish = (int) (Math.random()*14);
        JButton button = new JButton(new ImageIcon("src/sprites/titleScreen/fish/fish" + fish + ".png"));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setLayout(null);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Little Fish", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        if (fish <= 6) {
            button.setBounds(x+12, y, 128, 64);
            label.setBounds(36, 0, 64, 64);
        } else {
            button.setBounds(x, y, 128, 64);
            label.setBounds(48,0,64,64);
        }
        button.add(label);
        return button;
    }
}