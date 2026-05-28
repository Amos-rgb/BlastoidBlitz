import java.util.Random;

public class MazeGenerator {

    // Grid settings
    public static final int ROWS = 16;
    public static final int COLS = 16;

    // Screen settings
    public static final int SCREEN_SIZE = 1024;
    public static final int TILE_SIZE = SCREEN_SIZE / COLS; // 64 px

    // Tile types
    public static final char EMPTY = ' ';
    public static final char WALL = '#';
    public static final char BLOCK = '+';

    private char[][] map = new char[ROWS][COLS];
    private Random random = new Random();

    public void generate() {

        // Initialize empty
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                map[y][x] = EMPTY;
            }
        }

        // Outer walls
        for (int x = 0; x < COLS; x++) {
            map[0][x] = WALL;
            map[ROWS - 1][x] = WALL;
        }

        for (int y = 0; y < ROWS; y++) {
            map[y][0] = WALL;
            map[y][COLS - 1] = WALL;
        }

        // Indestructible walls
        for (int y = 2; y < ROWS - 1; y += 2) {
            for (int x = 2; x < COLS - 1; x += 2) {
                map[y][x] = WALL;
            }
        }

        // Random breakable blocks
        for (int y = 1; y < ROWS - 1; y++) {
            for (int x = 1; x < COLS - 1; x++) {

                if (map[y][x] != EMPTY) {
                    continue;
                }

                // Keep spawn areas clean
                if (isSpawnArea(x, y)) {
                    continue;
                }

                // 65% chance block
                if (random.nextDouble() < 0.65) {
                    map[y][x] = BLOCK;
                }
            }
        }
    }

    private boolean isSpawnArea(int x, int y) {

        // Player 1 spawn (top-left)
        if ((x == 1 && y == 1) ||
                (x == 1 && y == 2) ||
                (x == 2 && y == 1)) {
            return true;
        }

        // Player 2 spawn (bottom-right)
        if ((x == 14 && y == 14) ||
                (x == 13 && y == 14) ||
                (x == 14 && y == 13)) {
            return true;
        }

        return false;
    }

    public void printMap() {

        System.out.println("Tile Size = " + TILE_SIZE + " px");

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                DisplayPanel.GenerateBoard[x][y] = map
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        MazeGenerator generator = new MazeGenerator();

        generator.generate();

        generator.printMap();
    }
}