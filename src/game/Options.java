package game;

import org.newdawn.slick.Input;

public class Options {
    
    public int KEY_UP;
    public int KEY_DOWN;
    public int KEY_LEFT;
    public int KEY_RIGHT;
    public boolean MOUSE_ATTACK;
    
    public Options() {
        KEY_UP = Input.KEY_W;
        KEY_DOWN = Input.KEY_S;
        KEY_LEFT = Input.KEY_A;
        KEY_RIGHT = Input.KEY_D;
    }
}
