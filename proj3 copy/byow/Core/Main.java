package byow.Core;


import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
//import edu.princeton.cs.introcs.StdAudio;
//import edu.princeton.cs.introcs.StdDraw;


/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    private static final int SEED = 0;
    private static final int OPTIONS = 1;
    private static int seed = 0;
    private static int digits = 0;
    private static int pastSeed = 0;

    public static void main(String[] args) {

        Engine eb = new Engine();
        //eb.interactWithKeyboard();
        TERenderer ter = new TERenderer();
        //ter.initialize(60, 33, 0, 3);
        eb.interactWithKeyboard();





    }


}

