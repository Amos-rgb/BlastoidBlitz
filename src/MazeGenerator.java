import java.util.Random;

public class MazeGenerator {

    // Grid settings
    public final int ROWS;
    public final int COLS;

    // Screen settings
    public final int SCREEN_SIZE;
    public final int TILE_SIZE; // 64 px

    // Tile types
    public static final char EMPTY = ' ';
    public static final char WALL = '#';
    public static final char BLOCK = '+';

    private char[][] map;
    private Random random;
    public MazeGenerator(int rows, int cols, int screenSize) {
        ROWS = rows;
        COLS = cols;
        SCREEN_SIZE = screenSize;
        TILE_SIZE = SCREEN_SIZE / COLS;
        map = new char[ROWS][COLS];
        random = new Random();
    }

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
        if ((x >= 0 && x <= 3) && (y >= 0 && y <= 3)) {
            return true;
        }

        // Player 2 spawn (bottom-right)
        if ((x <= ROWS-1 && x >= ROWS-4) && (y <= COLS-1 && y >= COLS-4)) {
            return true;
        }

        return false;
    }

    public void printMap(DisplayPanel panel) {

        System.out.println("Tile Size = " + TILE_SIZE + " px");

        boolean breakable = true;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (map[x][y] == WALL){
                    panel.spaces.add(new Indestructible(x*TILE_SIZE,y*TILE_SIZE));
                } else if (map[x][y] == BLOCK) {
                    panel.spaces.add(new Destructible(x*TILE_SIZE,y*TILE_SIZE));
                }
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

}