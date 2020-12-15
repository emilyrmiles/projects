package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset implements java.io.Serializable {
    public static final Color BACKGROUND = new Color(190, 200, 130);
    public static final TETile AVATAR = new TETile('@', Color.BLACK, BACKGROUND, "you");
    public static final TETile WALL = new TETile('#', new Color(130, 200, 100),
            new Color(50, 50, 80),
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(50, 150, 150), BACKGROUND,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black,
            new Color(80, 150, 220), "nothing");
    public static final TETile GRASS = new TETile('"', new Color(21, 100, 50), BACKGROUND, "grass");
    public static final TETile WATER = new TETile('≈', new Color(40, 50, 250), BACKGROUND, "water");
    public static final TETile FLOWER = new TETile('❀',
            new Color(222, 60, 30), BACKGROUND, "tulip");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile FIRE = new TETile('₪',new Color(200, 70, 0) , new Color(235, 210, 140),"fire");
}


