package game;

import org.newdawn.slick.Input;

public class Options {
    
    public static int M_UP;
    public static int M_DOWN;
    public static int M_LEFT;
    public static int M_RIGHT;
    
    public static int A_UP;
    public static int A_DOWN;
    public static int A_LEFT;
    public static int A_RIGHT;
    
    static {
        M_UP = Input.KEY_W;
        M_DOWN = Input.KEY_S;
        M_LEFT = Input.KEY_A;
        M_RIGHT = Input.KEY_D;
        A_UP = Input.KEY_UP;
        A_DOWN = Input.KEY_DOWN;
        A_LEFT = Input.KEY_LEFT;
        A_RIGHT = Input.KEY_RIGHT;
    }
}
