package game.util;

import org.newdawn.slick.Input;

public class Options {
    
    public static int MOVE_UP;
    public static int MOVE_DOWN;
    public static int MOVE_LEFT;
    public static int MOVE_RIGHT;
    
    public static int ATTACK_UP;
    public static int ATTACK_DOWN;
    public static int ATTACK_LEFT;
    public static int ATTACK_RIGHT;
    
    static {
        MOVE_UP = Input.KEY_W;
        MOVE_DOWN = Input.KEY_S;
        MOVE_LEFT = Input.KEY_A;
        MOVE_RIGHT = Input.KEY_D;
        ATTACK_UP = Input.KEY_UP;
        ATTACK_DOWN = Input.KEY_DOWN;
        ATTACK_LEFT = Input.KEY_LEFT;
        ATTACK_RIGHT = Input.KEY_RIGHT;
    }
}
