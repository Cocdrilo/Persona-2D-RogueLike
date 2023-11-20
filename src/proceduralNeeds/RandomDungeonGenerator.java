package proceduralNeeds;

import java.util.*;

import main.GamePanel;

/**
 * Generates a random dungeon layout using a simple algorithm.
 * The generated dungeon consists of rooms and corridors connecting them.
 */
public class RandomDungeonGenerator {

    /**
     * Generates a random dungeon layout.
     *
     * @param gp       The GamePanel instance.
     * @param numRooms The number of rooms to generate in the dungeon.
     * @return A 2D array representing the generated dungeon layout.
     */
    public static int[][] generateDungeon(GamePanel gp, int numRooms) {
        int[][] world = new int[gp.maxWorldRow][gp.maxWorldCol];

        // Initialize the world grid with walls (1)
        for (int row = 0; row < gp.maxWorldRow; row++) {
            Arrays.fill(world[row], 1);
        }

        // Generate random rooms
        Random random = new Random();

        List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < numRooms; i++) {
            int roomWidth = random.nextInt(10) + 3; // Width is always 2 tiles
            int roomHeight = random.nextInt(10) + 3; // Random height between 3 and 7 tiles

            int roomX = random.nextInt(gp.maxWorldCol - roomWidth - 2) + 1; // Random x position with 1-unit buffer
            int roomY = random.nextInt(gp.maxWorldRow - roomHeight - 2) + 1; // Random y position with 1-unit buffer

            // Check if the room overlaps with existing rooms
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
                rooms.add(new Room(roomX, roomY, roomWidth, roomHeight));
            }
        }

        // Connect rooms with corridors
        connectRooms(world, rooms);

        return world;
    }

    /**
     * Represents a room in the dungeon with its position and dimensions.
     */
    static class Room {
        int x, y, width, height;

        /**
         * Constructs a new Room with the specified position and dimensions.
         *
         * @param x      The x-coordinate of the top-left corner of the room.
         * @param y      The y-coordinate of the top-left corner of the room.
         * @param width  The width of the room.
         * @param height The height of the room.
         */
        public Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
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
            if (x1 < x2) {
                for (int x = x1; x <= x2; x++) {
                    for (int y = y1 - corridorLength / 2; y <= y1 + corridorLength / 2; y++) {
                        if (x >= 0 && x < world[0].length && y >= 0 && y < world.length) {
                            world[y][x] = 0; // Corridor is represented by '0'
                        }
                    }
                }
            } else {
                for (int x = x2; x <= x1; x++) {
                    for (int y = y1 - corridorLength / 2; y <= y1 + corridorLength / 2; y++) {
                        if (x >= 0 && x < world[0].length && y >= 0 && y < world.length) {
                            world[y][x] = 0; // Corridor is represented by '0'
                        }
                    }
                }
            }

            // Generate vertical corridor
            if (y1 < y2) {
                for (int y = y1; y <= y2; y++) {
                    for (int x = x2 - corridorLength / 2; x <= x2 + corridorLength / 2; x++) {
                        if (x >= 0 && x < world[0].length && y >= 0 && y < world.length) {
                            world[y][x] = 0; // Corridor is represented by '0'
                        }
                    }
                }
            } else {
                for (int y = y2; y <= y1; y++) {
                    for (int x = x2 - corridorLength / 2; x <= x2 + corridorLength / 2; x++) {
                        if (x >= 0 && x < world[0].length && y >= 0 && y < world.length) {
                            world[y][x] = 0; // Corridor is represented by '0'
                        }
                    }
                }
            }
        }
    }
}
