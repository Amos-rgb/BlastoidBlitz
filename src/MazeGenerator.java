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
    public static final char SPAWN_AREA_1 = '1';
    public static final char SPAWN_AREA_2 = '2';
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

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 4; x++) {
                map[y][x] = SPAWN_AREA_1;
            }
        }
        for (int y = ROWS-2; y > ROWS-5; y--) {
            for (int x = COLS-2; x > ROWS-5; x--) {
                map[y][x] = SPAWN_AREA_2;
            }
        }

        // Indestructible walls
        for (int y = 2; y < ROWS - 1; y += 2) {
            for (int x = 2; x < COLS - 1; x += 2) {
                if (map[y][x] == EMPTY) map[y][x] = WALL;
            }
        }

        // Random breakable blocks
        for (int y = 1; y < ROWS - 1; y++) {
            for (int x = 1; x < COLS - 1; x++) {
                // 65% chance block
                if (map[y][x] == EMPTY) {
                    if (random.nextDouble() < 0.65) {
                        map[y][x] = BLOCK;
                    }
                }
            }
        }
    }

    public void printMap(DisplayPanel panel) {

        System.out.println("Tile Size = " + TILE_SIZE + " px");

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (map[x][y] == WALL){
                    panel.spaces.add(new Indestructible(x*TILE_SIZE,y*TILE_SIZE));
                } else if (map[x][y] == BLOCK) {
                    panel.spaces.add(new Destructible(x*TILE_SIZE,y*TILE_SIZE));
                } else if (map[x][y] == SPAWN_AREA_1 || map[x][y] == SPAWN_AREA_2) {
                    panel.spaces.add(new SpawnArea(x*TILE_SIZE,y*TILE_SIZE,null));
                }
                System.out.print(map[y][x] + " ");
            }
            System.out.println();
        }
    }

}