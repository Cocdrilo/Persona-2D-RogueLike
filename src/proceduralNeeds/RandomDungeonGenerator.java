package proceduralNeeds;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import main.GamePanel;

/**
 * Generates a random dungeon layout using a simple algorithm.
 * The generated dungeon consists of rooms and corridors connecting them.
 */
public class RandomDungeonGenerator {

    public int [][] dungeon;
    GamePanel gp;

    public int specialRoomX;
    public int specialRoomY;

    public RandomDungeonGenerator(GamePanel gp) {
        this.gp = gp;
        int numRooms = 10; // Adjust the number of rooms as needed
        this.dungeon = generateDungeon(gp, numRooms);
        postProcessDungeon();

        // Save the dungeon layout to a text file in the /res/Maps/ folder
    }

    /**
     * Post-processes the dungeon to remove consecutive walls and replace them with black tiles.
     */
    public void postProcessDungeon() {
        Random random = new Random();
        for (int row = 0; row < dungeon.length ; row++) {
            for (int col = 0; col < dungeon[0].length ; col++) {
                if (dungeon[row][col] == 1 && !isInnerWall(row, col)) {
                    dungeon[row][col] = 2; // Cambiar a tile de vacío
                }
            }
        }
        // Ahora realiza la mezcla aleatoria de suelos

        for (int row = 0; row < dungeon.length; row++) {
            for (int col = 0; col < dungeon[0].length; col++) {
                if (dungeon[row][col] == 0) {
                    // Probabilidad de cambiar el tipo de tile
                    if (random.nextDouble() < 0.5) {
                        // Cambiar aleatoriamente a tipo 0, 3, 4 o 5
                        int randomFloorType = random.nextInt(3) + 3;
                        dungeon[row][col] = randomFloorType;
                    }
                }
            }
        }

        //Mezcla de Muros aleatoria
        for (int row = 0; row < dungeon.length; row++) {
            for (int col = 0; col < dungeon[0].length; col++) {
                if (dungeon[row][col] == 1) {
                    // Probabilidad de cambiar el tipo de tile
                    if (random.nextDouble() < 0.5) {
                        // Cambiar aleatoriamente a tipo 0, 3, 4 o 5
                        int randomFloorType = random.nextInt(4) + 6;
                        dungeon[row][col] = randomFloorType;
                    }
                }
            }
        }
        System.out.println("Dungeon post-processing complete.");
    }

    private boolean isInnerWall(int row, int col) {
        // Verificar si el muro tiene un suelo a un tile de distancia
        boolean isTopEdge = row - 1 < 0;
        boolean isBottomEdge = row + 1 >= dungeon.length;
        boolean isLeftEdge = col - 1 < 0;
        boolean isRightEdge = col + 1 >= dungeon[0].length;

        // Comprobaciones válidas dentro de los límites de la matriz
        if (!isTopEdge && dungeon[row - 1][col] == 0) {
            return true;
        }
        if (!isBottomEdge && dungeon[row + 1][col] == 0) {
            return true;
        }
        if (!isLeftEdge && dungeon[row][col - 1] == 0) {
            return true;
        }
        if (!isRightEdge && dungeon[row][col + 1] == 0) {
            return true;
        }

        // Verificar si está en el borde y es una pared interna
        return (isTopEdge || isBottomEdge) && dungeon[row][col] == 0 ||
                (isLeftEdge || isRightEdge) && dungeon[row][col] == 0;
    }


    /**
     * Generates a random dungeon layout.
     *
     * @param gp       The GamePanel instance.
     * @param numRooms The number of rooms to generate in the dungeon.
     * @return A 2D array representing the generated dungeon layout.
     */
    public int[][] generateDungeon(GamePanel gp, int numRooms) {
        int[][] world = new int[gp.maxWorldRow][gp.maxWorldCol];

        // Generate random rooms
        Random random = new Random();

        List<Room> rooms = new ArrayList<>();

        // Initialize the world grid with walls (1)
        for (int row = 0; row < gp.maxWorldRow; row++) {
            Arrays.fill(world[row], 1);
        }

        // Generate boss room randomly
        int bossRoomSize = 5;

        int corner = random.nextInt(4); // 0, 1, 2, or 3

        int bossRoomX, bossRoomY;

        bossRoomY = switch (corner) {
            case 0 -> { // Top-left corner
                bossRoomX = 1;
                yield 1;
            }

            case 2 -> { // Bottom-right corner
                bossRoomX = gp.maxWorldCol - bossRoomSize - 1;
                yield gp.maxWorldRow - bossRoomSize - 1;
            }
            default -> {
                // This should never happen
                bossRoomX = 1;
                yield 1;
            }
        };

        // Calcular el centro de la sala especial
        specialRoomX = bossRoomX + bossRoomSize / 2;
        specialRoomY = bossRoomY + bossRoomSize / 2;

        for (int y = bossRoomY; y < bossRoomY + bossRoomSize; y++) {
            for (int x = bossRoomX; x < bossRoomX + bossRoomSize; x++) {
                world[y][x] = 0; // Boss room floor is represented by '0'
                rooms.add(new Room(specialRoomX - bossRoomSize / 2, specialRoomY - bossRoomSize / 2, bossRoomSize, bossRoomSize, true));
                System.out.println("Boss room set at: " + bossRoomX + " " + bossRoomY);
            }
        }


        for (int i = 0; i < numRooms; i++) {
            int roomWidth = random.nextInt(10) + 3; // Width is always 2 tiles
            int roomHeight = random.nextInt(10) + 3; // Random height between 3 and 7 tiles

            int roomX = random.nextInt(gp.maxWorldCol - roomWidth - 2) + 1; // Random x position with 1-unit buffer
            int roomY = random.nextInt(gp.maxWorldRow - roomHeight - 2) + 1; // Random y position with 1-unit buffer

            // Check if the room overlaps with existing rooms or boss room
            boolean overlaps = false;
            for (int y = roomY - 1; y < roomY + roomHeight + 1; y++) {
                for (int x = roomX - 1; x < roomX + roomWidth + 1; x++) {
                    if (world[y][x] == 0) {
                        overlaps = true;
                        break;
                    }
                }
                if (overlaps) {
                    break;
                }
            }

            // If there's no overlap, add the room to the world
            if (!overlaps) {
                for (int y = roomY; y < roomY + roomHeight; y++) {
                    for (int x = roomX; x < roomX + roomWidth; x++) {
                        world[y][x] = 0; // Use '0' to represent floors
                    }
                }

                // Store the room's position and dimensions
                rooms.add(new Room(roomX, roomY, roomWidth, roomHeight,false));
            }
        }

        // Connect rooms with corridors
        connectRooms(world, rooms);

        return world;
    }

    /**
     * Getter para obtener las coordenadas de la sala especial.
     *
     * @return Un array de dos elementos donde [0] es la coordenada X y [1] es la coordenada Y.
     */
    public int[] getSpecialRoomCoordinates() {
        return new int[]{specialRoomX, specialRoomY};
    }


    private static void saveDungeonToFile(int[][] dungeon, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int row = 0; row < dungeon.length; row++) {
                for (int col = 0; col < dungeon[0].length; col++) {
                    writer.print(dungeon[row][col] + " ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }


    /**
     * Represents a room in the dungeon with its position and dimensions.
     */
    static class Room {
        int x, y, width, height;
        boolean special;

        /**
         * Constructs a new Room with the specified position and dimensions.
         *
         * @param x      The x-coordinate of the top-left corner of the room.
         * @param y      The y-coordinate of the top-left corner of the room.
         * @param width  The width of the room.
         * @param height The height of the room.
         */
        public Room(int x, int y, int width, int height,boolean special) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.special = special;
        }
    }

    /**
     * Connects rooms in the dungeon layout with corridors.
     *
     * @param world The 2D array representing the dungeon layout.
     * @param rooms The list of rooms in the dungeon.
     */
    static void connectRooms(int[][] world, List<Room> rooms) {
        Random random = new Random();
        int minCorridorLength = 2;
        int maxCorridorLength = 3;

        for (int i = 0; i < rooms.size() - 1; i++) {
            Room room1 = rooms.get(i);
            Room room2 = rooms.get(i + 1);

            // Connect room centers with corridors
            int x1 = room1.x + room1.width / 2;
            int y1 = room1.y + room1.height / 2;
            int x2 = room2.x + room2.width / 2;
            int y2 = room2.y + room2.height / 2;

            int corridorLength = random.nextInt(maxCorridorLength - minCorridorLength + 1) + minCorridorLength;

            // Generate horizontal corridor
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int y = y1 - corridorLength / 2; y <= y1 + corridorLength / 2; y++) {
                    if (y >= 0 && y < world.length && x >= 0 && x < world[0].length) {
                        world[y][x] = 0; // Corridor is represented by '0'
                    }
                }
            }

            // Generate vertical corridor
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                for (int x = x2 - corridorLength / 2; x <= x2 + corridorLength / 2; x++) {
                    if (x >= 0 && x < world[0].length && y >= 0 && y < world.length) {
                        world[y][x] = 0; // Corridor is represented by '0'
                    }
                }
            }

            // Ensure the entrance to the special room is always 1x1
            if (room2.special) {
                int entranceX, entranceY;

                if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
                    int directionX = (x2 - x1) / Math.max(1, Math.abs(x2 - x1));
                    entranceX = x2 - directionX;
                    entranceY = y2;
                } else {
                    int directionY = (y2 - y1) / Math.max(1, Math.abs(y2 - y1));
                    entranceX = x2;
                    entranceY = y2 - directionY;
                }

                world[entranceY][entranceX] = 0; // Corridor entrance to special room is 1x1
            }
        }
    }
}


