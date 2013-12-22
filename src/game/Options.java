package game;

import org.newdawn.slick.Input;

public class Options {
    
    public int M_UP;
    public int M_DOWN;
    public int M_LEFT;
    public int M_RIGHT;
    
    public int A_UP;
    public int A_DOWN;
    public int A_LEFT;
    public int A_RIGHT;
    
    public boolean MOUSE_ATTACK;
    
    public Options() {
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
