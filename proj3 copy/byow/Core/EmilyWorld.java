package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EmilyWorld implements java.io.Serializable {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    public TETile[][] pureWorld;
    TETile[][] world;
    long seed = 3;
    Random rd = new Random(seed);
    ArrayList<MyRoom> roomList;
    ArrayList<Point> centerList;
    Avatar avatar;
    public int totalFlowers;
    public int allFlowers;
    public int flowersCollected;

    public EmilyWorld(long seed) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        rd = new Random(seed);
        //ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        roomList = new ArrayList<MyRoom>();
        centerList = new ArrayList<Point>();

        // initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        roomCreator(this.rd.nextInt(15) + 10);
        Hallway test = new EmilyWorld.Hallway();
        pureWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                pureWorld[x][y] = world[x][y];
            }
        }
        flowersCollected = 0;
        totalFlowers = returntotalFlowers();
        allFlowers = returntotalFlowers();
        avatar = new Avatar(centerList.get(0).x , centerList.get(0).y, returnWorld());
        //avatar = new Avatar(0, 0, world);

    }
    public int returntotalFlowers() {
        totalFlowers = 0;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (isFlowerTile(world[x][y])) {
                    totalFlowers += 1;
                }
            }
        }
        return totalFlowers;
    }
    public static boolean isFlowerTile(TETile tile) {
        return tile.description().equals(Tileset.FLOWER.description());
    }



    public TETile[][] returnWorld() {
        //this.roomCreator(this.rd.nextInt(15) + 5);
        return world;
    }
    public static boolean isRoomTile(TETile tile){
        if (tile.description() == Tileset.WALL.description() || tile.description() == Tileset.NOTHING.description()) {
            return false;
        }
        return true;
    }
    public static boolean isFireTile(TETile title) {
        if (title.description() == Tileset.FIRE.description()) {
            return true;
        }
        return false;
    }


    public void roomCreator(int numRooms) {
        for (int i = 0; i < numRooms; i++) {
            //new EmilyWorld.MyRoom();
            MyRoom result = new EmilyWorld.MyRoom();
            roomList.add(i, result);

            //now making a list of centers of all the rooms
            double x1 = roomList.get(i).xStart; //this room's left x value
            double x2 = roomList.get(i).xStart + roomList.get(i).width; //this room's right x value
            double y1 = roomList.get(i).yStart; //this room's bottom y value
            double y2 = roomList.get(i).yStart + roomList.get(i).height; //this room's top y value
            //center = the middle point of the width and middle of height, thus making the center
            Point center;
            center = new Point((int) Math.floor((x1 + x2) / 2), (int) Math.floor((y1 + y2) / 2));
            centerList.add(center); //adds the center point of this room to the array list
        }
        //Hallway test = new EmilyWorld.Hallway();
        //avatar.xPos = centerList.get(0).x;
        //avatar.yPos = centerList.get(0).y;
        //System.out.println(avatar.xPos);
        //System.out.println(centerList.get(3).y);
        //System.out.println(centerList.get(3));
        //Avatar avatar = new Avatar
        //System.out.println(roomList.size());
        //System.out.println(centerList.get(0));
    }


    public ArrayList<Point> returncenterList() {
        return centerList;
    }



    private class MyRoom implements java.io.Serializable {
        int height = rd.nextInt(7) + 2;
        int width = rd.nextInt(7) + 2;
        int xStart = rd.nextInt(WIDTH - width - 2);
        int yStart = rd.nextInt(HEIGHT - height - 2);

        MyRoom() {
            while (this.intersects()) {
                height = rd.nextInt(7) + 2;
                width = rd.nextInt(7) + 2;
                xStart = rd.nextInt(WIDTH - width);
                yStart = rd.nextInt(HEIGHT - height);
            }
            drawRoom();

        }

        private void drawRoom() {
            for (int x = 0; x <= width; x += 1) {
                for (int y = 0; y <= height; y += 1) {
                    if (y == 0 || x == 0 || y == height || x == width) {
                        world[x + xStart][y + yStart] = Tileset.WALL;
                    } else {
                        world[x + xStart][y + yStart] = TETile.colorVariant(randomTile(), 30, 30, 30, rd);
                    }

                }
            }
        }
        private TETile randomTile() {
            int tileNum = rd.nextInt(14);
            switch (tileNum) {
                case 0,6,7,8,9,10,11,12,13: return Tileset.GRASS;
                case 1,4,5: return Tileset.FLOWER;
                case 2: return Tileset.WATER;
                case 3: return Tileset.FLOOR;
                //default: return Tileset.NOTHING;
            }
            return null;
        }


        private boolean intersects() {
            for (int x = 0; x <= width; x += 1) {
                for (int y = 0; y <= height; y += 1) {
                    if (world[x + xStart][y + yStart] != Tileset.NOTHING) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    private class Hallway implements java.io.Serializable {
        //make methods for vertical hallways and horizontal hallways
        private Hallway() {
            for (int i = 0; i < centerList.size() - 1; i++) {
                /**if (i == centerList.size() - 1) {
                    Point last = centerList.get(i);
                    Point first = centerList.get(0);
                    horizontalHallway(last, first);
                    verticalHallway(last, first);
                    return;
                }**/
                Point one = centerList.get(i);
                Point two = centerList.get(i + 1);
                horizontalHallway(one, two);
                verticalHallway(one, two);
                cornerHallway(one, two);
            }
            /**Point one = new Point(59, 29);
            Point two = new Point(13, 13);
            horizontalHallway(one, two);
            verticalHallway(one, two);
             **/
        }

        private TETile hallwayTile(){
            int tileNum = rd.nextInt(7);
            switch (tileNum) {
                case 0: return Tileset.FIRE;
                case 1,2,3,4,5,6,7: return Tileset.FLOOR;
                //default: return Tileset.NOTHING;
            }
            return null;
        }



        private void horizontalHallway(Point room1, Point room2) {

            if (room1.x == room2.x) {
                return;
            }
            int start = Math.min(room1.x, room2.x);

            if (room1.x == start) {
                int fixedY = room1.y;
                for (int i = start; i < room2.x + 1; i++) {
                    Point floorPoint = new Point(i, fixedY);
                    Point upperWallPoint = new Point(i, fixedY + 1);
                    Point lowerWallPoint = new Point(i, fixedY - 1);
                    world[floorPoint.x][floorPoint.y] = TETile.colorVariant(hallwayTile(), 30, 30, 30, rd);

                    if (world[upperWallPoint.x][upperWallPoint.y] == Tileset.NOTHING) {
                        world[upperWallPoint.x][upperWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                    if (world[lowerWallPoint.x][lowerWallPoint.y] == Tileset.NOTHING) {
                        world[lowerWallPoint.x][lowerWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }

                }
            }
            if (room2.x == start) {
                int fixedY2 = room2.y;
                for (int i = start; i < room1.x + 1; i++) {
                    Point floorPoint = new Point(i, fixedY2);
                    Point upperWallPoint = new Point(i, fixedY2 + 1);
                    Point lowerWallPoint = new Point(i, fixedY2 - 1);
                    world[floorPoint.x][floorPoint.y] = TETile.colorVariant(hallwayTile(), 30, 30, 30, rd);

                    if (world[upperWallPoint.x][upperWallPoint.y] == Tileset.NOTHING) {
                        world[upperWallPoint.x][upperWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                    if (world[lowerWallPoint.x][lowerWallPoint.y] == Tileset.NOTHING) {
                        world[lowerWallPoint.x][lowerWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                }
            }

        }

        private void verticalHallway(Point room1, Point room2) {

            if (room1.y == room2.y) {
                return;
            }
            int start = Math.min(room1.y, room2.y);

            if (room1.y == start) {
                int fixedX = Math.max(room1.x, room2.x);
                for (int i = start; i < room2.y + 1; i++) {
                    Point floorPoint = new Point(fixedX, i);
                    Point leftWallPoint = new Point(fixedX - 1, i);
                    Point rightWallPoint = new Point(fixedX + 1, i);
                    world[floorPoint.x][floorPoint.y] = TETile.colorVariant(hallwayTile(), 30, 30, 30, rd);

                    if (world[leftWallPoint.x][leftWallPoint.y] == Tileset.NOTHING) {
                        world[leftWallPoint.x][leftWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                    if (world[rightWallPoint.x][rightWallPoint.y] == Tileset.NOTHING) {
                        world[rightWallPoint.x][rightWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                }
            }
            if (room2.y == start) {
                int fixedX2 = Math.max(room1.x, room2.x);
                for (int i = start; i < room1.y + 1; i++) {
                    Point floorPoint = new Point(fixedX2, i);
                    Point leftWallPoint = new Point(fixedX2 - 1, i);
                    Point rightWallPoint = new Point(fixedX2 + 1, i);
                    world[floorPoint.x][floorPoint.y] = TETile.colorVariant(hallwayTile(), 30, 30, 30, rd);

                    if (world[leftWallPoint.x][leftWallPoint.y] == Tileset.NOTHING) {
                        world[leftWallPoint.x][leftWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                    if (world[rightWallPoint.x][rightWallPoint.y] == Tileset.NOTHING) {
                        world[rightWallPoint.x][rightWallPoint.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                    }
                }
            }

        }
        private void cornerHallway(Point room1, Point room2) {
            int minXroom = Math.min(room1.x, room2.x);
            int minYroom = Math.min(room1.y, room2.y);


            if (room1.x < room2.x && room1.y < room2.y) {
                //corner is on bottom right
                Point corner = new Point(room1.x + (room2.x - room1.x) + 1, room1.y - 1);
                if (world[corner.x][corner.y] == Tileset.NOTHING) {
                    world[corner.x][corner.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                }
            }
            if (room1.x > room2.x && room1.y > room2.y) {
                //corner is on bottom right
                Point corner = new Point(room2.x + (room1.x - room2.x) + 1, room2.y - 1);
                if (world[corner.x][corner.y] == Tileset.NOTHING) {
                    world[corner.x][corner.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                }
            }
            if (room1.x > room2.x && room1.y < room2.y) {
                //corner is on bottom left
                Point corner = new Point(room1.x + 1, room2.y + 1);
                if (world[corner.x][corner.y] == Tileset.NOTHING) {
                    world[corner.x][corner.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                }
            }
            if (room1.x < room2.x && room1.y > room2.y) {
                //corner is on bottom left
                Point corner = new Point(room2.x + 1, room1.y + 1);
                if (world[corner.x][corner.y] == Tileset.NOTHING) {
                    world[corner.x][corner.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, rd);
                }
            }


        }









    }

}
