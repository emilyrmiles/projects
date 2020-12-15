package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class Avatar implements java.io.Serializable {
    int xPos;
    int yPos;
    //public TETile[][] world;
    char lastDirection;
    int health;


    public Avatar(int xPos, int yPos, TETile[][] world) {
        this.xPos = xPos;
        this.yPos = yPos;
        //this.world = world;
        this.lastDirection = ' ';
        world[xPos][yPos] = Tileset.AVATAR;
        health = 5;
        //placeAvatar();
    }


}
