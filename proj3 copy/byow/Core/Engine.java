package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Arrays;


public class Engine implements java.io.Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    String allKeyPresses; //for interactWithKeyboard
    int currInput = 0; //for interactWithString
    int inputLength = 0;
    String inputString;
    char[] inputLeft;
    int maxHealth = 5;
    EmilyWorld wd;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        final int seedOption = 0;
        final int options = 1;
        int seed = 0;
        int pastSeed = 0;
        int inputType = options;
        InputMenu inputMenu = new InputMenu();
        Avatar avatar;
        while (inputMenu.possibleNextInput()) {
            char c = inputMenu.getNextKey();
            c = Character.toUpperCase(c);
            if (inputType == seedOption) {
                if (Character.isDigit(c)) {
                    seed = seed * 10 + Character.getNumericValue(c);
                    inputMenu.showSeedMenu(seed);
                } else if (c == 'S') {
                    pastSeed = seed;
                    inputType = options;
                    ter.initialize(60, 33, 0, 3);
                    wd = new EmilyWorld(seed);
                    ter.renderFrame(wd.returnWorld());
                    playGame(wd, inputMenu);
                } else {
                    inputType = options;
                    seed = 0;
                }
            }
            if (c == 'N') {
                inputType = seedOption;
                seed = 0;
                inputMenu.showSeedMenu(seed);
                //wd = new EmilyWorld((long) pastSeed);
                //saveWorld(wd);
            }
            if (c == 'L') {
                inputType = options;
                ter.initialize(60, 34,0,3);
                wd = (EmilyWorld) loadWorld();
                ter.renderFrame(wd.returnWorld());
                playGame(wd, inputMenu);
            }
            if (c == 'B') {
                inputMenu.showBackground();
                inputMenu.backtoInput();
            }
            if (c == 'Q') {
                break;
            }
        }


    }



    public static class InputMenu {
        InputMenu() {
            StdDraw.clear(new Color(100, 70, 120));
            StdDraw.setPenColor(Color.white);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.7, "Tulip Hunter");
            StdDraw.show();
            font = new Font("Monaco", Font.PLAIN, 16);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.5, "New Game (N)");
            StdDraw.text(0.5, 0.4, "Load Game (L)");
            StdDraw.text(0.5, 0.3, "Quit (Q)");
            StdDraw.text(0.5, 0.2, "Background (B)");
            StdDraw.show();
        }

        public void backtoInput() {
            StdDraw.clear(new Color(100, 70, 120));
            StdDraw.setPenColor(Color.white);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.7, "Nothing to See Here");
            StdDraw.show();
            font = new Font("Monaco", Font.PLAIN, 16);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.5, "New Game (N)");
            StdDraw.text(0.5, 0.4, "Load Game (L)");
            StdDraw.text(0.5, 0.3, "Quit (Q)");
            StdDraw.text(0.5, 0.2, "Background (B)");
            StdDraw.show();
        }
        public void showBackground() {
            StdDraw.clear(new Color(100, 70, 120));
            Font tempFont = new Font("Monaco", Font.ITALIC, 10);
            StdDraw.setFont(tempFont);
            //StdDraw.clear(new Color(100, 70, 120));
            StdDraw.text(0.5, 0.98, "Press any key to go back to menu");
            Font font = new Font("Monaco", Font.PLAIN, 12);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.9, "You're a Dutch merchant in the 1600s, and tulips are IN!");
            StdDraw.text(0.5, 0.8, "Some of them cost as much as a house, so you want to collect ");
            StdDraw.text(0.5, 0.7, "as many of them as possible. Be careful though, tulips can be ");
            StdDraw.text(0.5, 0.6, "finicky flowers. They love water, and hate fire.");
            StdDraw.text(0.5, 0.5, "If you step in fire, you lose 1 health! ");
            StdDraw.text(0.5, 0.4, "But don't worry, if you have less than 5 health, ");
            StdDraw.text(0.5, 0.3, "you can gain 1 health by stepping in water. ");
            StdDraw.text(0.5, 0.2, "Collect all the tulips to win the game and ");
            StdDraw.text(0.5, 0.1, "claim the title of best merchant in the Netherlands! ");
            StdDraw.text(0.5, 0.05, "(At least, until the market bubble crashes)");
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (c != 'f') {
                        return;
                    }
                }
            }
        }

        public char getNextKey() {
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    return c;
                }
            }
        }

        public void showSeedMenu(int seed) {
            StdDraw.clear(new Color(100, 70, 120));
            StdDraw.text(0.5, 0.6, "Please enter A seed, press S when ready");
            StdDraw.text(0.5, 0.5, "Seed so far: " + seed);

        }

        public void headsUp(EmilyWorld wd, double mx, double my) {



            StdDraw.setXscale(0,WIDTH);
            StdDraw.setYscale(0,HEIGHT + 3);
            StdDraw.setPenColor(StdDraw.WHITE);
            Font healthfont = new Font("Monaco", Font.BOLD, 18);
            StdDraw.setFont(healthfont);
            Font font = new Font("Monaco", Font.PLAIN, 12);
            StdDraw.setFont(font);
            StdDraw.text(30+4, 2, "try looking at your feet. (e) ");
            StdDraw.text(5+4, 1, "You have " + Integer.toString(wd.flowersCollected) + " flower out of " + Integer.toString(wd.totalFlowers));
            StdDraw.text(2+4, 2, "Health = " + healthCount(wd.avatar.health));
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.filledRectangle(7+4, 2, wd.avatar.health / 2.0, .4);
            StdDraw.show();
            if (my >= 30 || mx >= 60 || 0 > mx - 3 || 0 > my - 3) {
                return;
            }
            TETile currentTile = wd.world[(int) mx][(int) my - 3];
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(30+4, 1, "you are hovering over: " + currentTile.description());
            StdDraw.show();

        }
        public String healthCount(int health) {
            return Integer.toString(health);
        }
        public int returntotalFlowers(EmilyWorld wd) {
            return wd.totalFlowers;
        }

        public boolean possibleNextInput() {
            return true;
        }
    }
    public void playGame(EmilyWorld wd, InputMenu inputMenu) {
        //char keyPress = StdDraw.nextKeyTyped();
        StdAudio.loop("/byow/Core/Music/Main-Song-Soft.wav");
        moveAvatar(wd, inputMenu);
        drawEnd();
        //saveWorld(wd);
        //System.exit(0);


    }
    public void drawEnd() {
        if (wd.avatar.health == 0) {
            StdDraw.clear(Tileset.BACKGROUND);
            StdDraw.show();
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            //StdDraw.text()
            StdDraw.text(30, 15, "You Lost!");
            StdDraw.text(30,10, "Press any key to exit game");
            StdDraw.show();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (c != 'f') {
                        System.exit(0);
                    }
                }
            }
        }
        if (wd.totalFlowers == wd.flowersCollected) {
            StdDraw.clear(Tileset.BACKGROUND);
            StdDraw.show();
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            //StdDraw.text()
            StdDraw.text(30, 15, "YOU WON!");
            StdDraw.text(30,10, "Press any key to exit game");
            StdDraw.show();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (c != 'f') {
                        System.exit(0);
                    }
                }
            }
        }
    }


    public void moveAvatar(EmilyWorld wd, InputMenu keyPress) {
        //keyPress = Character.toUpperCase(keyPress);
        int avatarCurrX = wd.avatar.xPos;
        int avatarCurrY = wd.avatar.yPos;
        boolean gameRunning = true;
        boolean stepNoise = false;
        allKeyPresses = "";
        boolean flowerPicked = false;

        while (gameRunning) {
            try {
                // thread to pause the game to kill my computer a little less..
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StdDraw.clear();
            ter.renderFrame(wd.world);
            keyPress.headsUp(wd, StdDraw.mouseX(), StdDraw.mouseY());
            while (StdDraw.hasNextKeyTyped()) {
                wd.avatar.yPos = avatarCurrY;
                wd.avatar.xPos = avatarCurrX;
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                allKeyPresses += c;

                if (wd.avatar.health == 0) {
                    wd.avatar.yPos = avatarCurrY;
                    wd.avatar.xPos = avatarCurrX;
                    //gameRunning = false;
                    return;
                }
                if (wd.flowersCollected == wd.totalFlowers) {
                    wd.avatar.yPos = avatarCurrY;
                    wd.avatar.xPos = avatarCurrX;
                    return;
                }
                if (c == 'E') {
                    startInteraction(wd);
                }
                if (c == 'W') { //goes up
                    if (isFireTile(wd.world[avatarCurrX][avatarCurrY + 1])) {
                        wd.avatar.health -= 1;
                        StdAudio.play("/byow/Core/Music/Burn-Sound.wav");
                    }
                    if (isRoomTile(wd.world[avatarCurrX][avatarCurrY + 1])) {
                        if (isWaterTile(wd.world[avatarCurrX][avatarCurrY + 1]) && wd.avatar.health < maxHealth) {
                            wd.avatar.health = maxHealth;
                            StdAudio.play("/byow/Core/Music/Healing-Song.wav");
                        }
                        wd.world[avatarCurrX][avatarCurrY + 1] = Tileset.AVATAR;
                        if (isFlowerTile(wd.pureWorld[avatarCurrX][avatarCurrY])) {
                            wd.pureWorld[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            wd.world[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            flowerPicked = true;
                        } else {
                            wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                        }
                        stepNoise = true;
                        avatarCurrY += 1;

                    }
                }
                if (c == 'S') { //goes down
                    if (isFireTile(wd.world[avatarCurrX][avatarCurrY - 1])) {
                        wd.avatar.health -= 1;
                        StdAudio.play("/byow/Core/Music/Burn-Sound.wav");
                    }
                    if (isRoomTile(wd.world[avatarCurrX][avatarCurrY - 1])) {
                        if (isWaterTile(wd.world[avatarCurrX][avatarCurrY - 1]) && wd.avatar.health < maxHealth) {
                            wd.avatar.health = maxHealth;
                            StdAudio.play("/byow/Core/Music/Healing-Song.wav");
                        }
                        wd.world[avatarCurrX][avatarCurrY - 1] = Tileset.AVATAR;
                        if (isFlowerTile(wd.pureWorld[avatarCurrX][avatarCurrY])) {
                            wd.pureWorld[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            wd.world[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            flowerPicked = true;
                        } else {
                            wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                        }
                        stepNoise = true;
                        avatarCurrY -= 1;
                    }
                }
                if (c == 'D') { //goes right
                    if (isFireTile(wd.world[avatarCurrX + 1][avatarCurrY])) {
                        wd.avatar.health -= 1;
                        StdAudio.play("/byow/Core/Music/Burn-Sound.wav");
                    }
                    if (isRoomTile(wd.world[avatarCurrX + 1][avatarCurrY])) {
                        if (isWaterTile(wd.world[avatarCurrX + 1][avatarCurrY]) && wd.avatar.health < maxHealth) {
                            wd.avatar.health = maxHealth;
                            StdAudio.play("/byow/Core/Music/Healing-Song.wav");
                        }
                        wd.world[avatarCurrX + 1][avatarCurrY] = Tileset.AVATAR;
                        if (isFlowerTile(wd.pureWorld[avatarCurrX][avatarCurrY])) {
                            wd.pureWorld[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            wd.world[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            flowerPicked = true;
                        } else {
                            wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                        }
                        stepNoise = true;
                        avatarCurrX += 1;
                    }
                }
                if (c == 'A') { //goes left
                    if (isFireTile(wd.world[avatarCurrX - 1][avatarCurrY])) {
                        wd.avatar.health -= 1;
                        StdAudio.play("/byow/Core/Music/Burn-Sound.wav");
                    }
                    if (isRoomTile(wd.world[avatarCurrX - 1][avatarCurrY])) {
                        if (isWaterTile(wd.world[avatarCurrX - 1][avatarCurrY]) && wd.avatar.health < maxHealth) {
                            wd.avatar.health = maxHealth;
                            StdAudio.play("/byow/Core/Music/Healing-Song.wav");
                        }
                        wd.world[avatarCurrX - 1][avatarCurrY] = Tileset.AVATAR;
                        if (isFlowerTile(wd.pureWorld[avatarCurrX][avatarCurrY])) {
                            wd.pureWorld[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            wd.world[avatarCurrX][avatarCurrY] = Tileset.FLOOR;
                            flowerPicked = true;
                        } else {
                            wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                        }
                        stepNoise = true;
                        avatarCurrX -= 1;
                    }
                }
                if (allKeyPresses.contains(":Q")) { //revise this, needs to exit via :Q
                    wd.avatar.yPos = avatarCurrY;
                    wd.avatar.xPos = avatarCurrX;
                    gameRunning = false;
                    saveWorld(wd);
                    System.exit(0);
                }
                if (stepNoise) {
                    StdAudio.play("/byow/Core/Music/Step-Noise-V3.wav");
                    stepNoise = false;
                    wd.avatar.yPos = avatarCurrY;
                    wd.avatar.xPos = avatarCurrX;
                }
                if (flowerPicked) {
                    StdAudio.play("/byow/Core/Music/Flower-Picked.wav");
                    wd.flowersCollected++;
                    flowerPicked = false;
                }

            }
        }

    }

    public static boolean isRoomTile(TETile tile) {
        return !tile.description().equals(Tileset.WALL.description())
                && !tile.description().equals(Tileset.NOTHING.description());

    }
    public static boolean isFireTile(TETile tile) {
        return tile.description().equals(Tileset.FIRE.description());
    }
    public static boolean isFlowerTile(TETile tile) {
        return tile.description().equals(Tileset.FLOWER.description());
    }
    public static boolean isWaterTile(TETile tile) {
        return tile.description().equals(Tileset.WATER.description());
    }

    private void drawInteraction(TETile tile, String upperText, String bottomText) {
        StdDraw.clear(Tileset.BACKGROUND);
        StdDraw.setPenColor(tile.textColor());
        StdDraw.text(30, 20, upperText);
        StdDraw.text(30, 10, bottomText);
        StdDraw.show();
        Font font = new Font("Monaco", Font.BOLD, 130);
        StdDraw.setFont(font);
        StdDraw.text(30, 15, String.valueOf(tile.character()));
        StdDraw.show();
        font = new Font("Monaco", Font.BOLD, 12);
        StdDraw.setFont(font);
        StdDraw.text(30, 5, "press any key to look away.");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (c != 'f') {
                    font = new Font("Monaco", Font.BOLD, TERenderer.TILE_SIZE - 2);
                    StdDraw.setFont(font);
                    StdDraw.show();
                    return;
                }

            }
        }

    }

    public void startInteraction(EmilyWorld wd) {
        TETile tile = wd.pureWorld[wd.avatar.xPos][wd.avatar.yPos];
        String tileDes = tile.description();
        switch (tileDes) {
            case "fire":
                //System.out.println("hello");
                drawInteraction(tile, "You are standing on fire!",
                        "You lose one health!");
                return;
            case "flower":
                drawInteraction(tile, "You are standing on a flower.",
                        "I understand why this is worth so much.");
                return;
            case "water":
                drawInteraction(tile, "You are standing on a puddle",
                        "How healing.");
                return;
            case "grass":
                drawInteraction(tile, "You are standing on blades of grass.",
                        "What beautiful fields.");
                return;
            case "floor":
                drawInteraction(tile, "You are standing on the floor.",
                        "");
                return;
            default:
                return;
        }

    }

    public void saveWorld(EmilyWorld wd) {
        try {
            File myWorld = new File("byow/Core/lastGame.txt");
            if (!myWorld.exists()) {
                myWorld.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(myWorld));
            oos.writeObject(wd); //writes this new object in the txt file creates
            oos.flush();
            oos.close(); //ends this action
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Object loadWorld() {
        //checks if there is a previously saved world. if not, return a new world
        File myWorld = new File("byow/Core/lastGame.txt");
        EmilyWorld wd = null;
        try {
            if (myWorld.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(myWorld));
                wd = (EmilyWorld) ois.readObject();
                return wd;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        int seed = 0;
        return new EmilyWorld(seed); //return new World if no saved worlds appear
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {

        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        /**inputLength = input.length();
         inputString = input;
         final int seedOption = 0;
         final int options = 1;
         int seed = 0;
         int pastSeed = 0;
         int inputType = options;
         EmilyWorld wd;
         InputMenu inputMenu = new InputMenu();
         Avatar avatar;
         String substring = new String("");


         while (currInput < inputLength) {
         char c = inputString.charAt(currInput);
         c = Character.toUpperCase(c);
         if (c == 'N') {
         while (inputString.charAt(currInput) != 'S') {
         currInput += 1;
         substring += inputString.charAt(currInput);
         }
         seed = Integer.parseInt(substring);

         }

         }
         return null; **/

        final int seedOption = 0;
        final int options = 1;
        int seed = 0;
        int pastSeed = 0;
        int inputType = options;
        EmilyWorld wd;

        int charSoFar = 0;

        for (char c : input.toCharArray()) {
            c = Character.toUpperCase(c);
            charSoFar++;
            if (inputType == seedOption) {
                if (Character.isDigit(c)) {
                    seed = seed * 10 + Character.getNumericValue(c);
                } else if (c == 'S') {
                    pastSeed = seed;
                    inputType = options;
                    wd = new EmilyWorld(seed);
                    inputLeft = Arrays.copyOfRange(input.toCharArray(),
                            charSoFar - 1, input.length());
                    playGameString(wd, inputLeft);
                    return wd.returnWorld();
                } else {
                    inputType = options;
                    seed = 0;
                }
            }
            if (c == 'N') {
                inputType = seedOption;
                seed = 0;
            }
            if (c == 'L') {
                wd = (EmilyWorld) loadWorld();
                //inputLeft = Arrays.copyOfRange(input.toCharArray(), charSoFar, input.length());
                //playGameString(wd, inputLeft);
                return wd.returnWorld();
            }
        }
        return null;
    }


    public void playGameString(EmilyWorld wd, char[] input) {
        int avatarCurrX = wd.avatar.xPos;
        int avatarCurrY = wd.avatar.yPos;
        boolean stepNoise = false;
        allKeyPresses = "";

        for (int i = 0; i < inputLeft.length; i++) {
            char c = inputLeft[i];

            if (inputLeft[i] == ':' && inputLeft[i + 1] == 'Q'
                    || inputLeft[i] == ':' && inputLeft[i + 1] == 'q') {
                saveWorld(wd);
            }
            if (c == 'W') {
                if (isRoomTile(wd.world[avatarCurrX][avatarCurrY + 1])) { // if tile above is floor
                    wd.world[avatarCurrX][avatarCurrY + 1] = Tileset.AVATAR;
                    wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                    stepNoise = true;
                    avatarCurrY += 1;
                    allKeyPresses += c;
                }
            }
            if (c == 'S') {
                if (isRoomTile(wd.world[avatarCurrX][avatarCurrY - 1])) { // if tile below is floor
                    wd.world[avatarCurrX][avatarCurrY - 1] = Tileset.AVATAR;
                    wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                    stepNoise = true;
                    avatarCurrY -= 1;
                    allKeyPresses += c;
                }
            }
            if (c == 'D') { //goes right
                if (isRoomTile(wd.world[avatarCurrX + 1][avatarCurrY])) {
                    wd.world[avatarCurrX + 1][avatarCurrY] = Tileset.AVATAR;
                    wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                    stepNoise = true;
                    avatarCurrX += 1;
                    allKeyPresses += c;
                }
            }
            if (c == 'A') { //goes left
                if (isRoomTile(wd.world[avatarCurrX - 1][avatarCurrY])) {
                    wd.world[avatarCurrX - 1][avatarCurrY] = Tileset.AVATAR;
                    wd.world[avatarCurrX][avatarCurrY] = wd.pureWorld[avatarCurrX][avatarCurrY];
                    stepNoise = true;
                    avatarCurrX -= 1;
                    allKeyPresses += c;
                }
            }

        }
    }


}


