package game.util;

import org.newdawn.slick.Input;

public enum Options {
    
    MOVE_UP(Input.KEY_W),
    MOVE_DOWN(Input.KEY_S),
    MOVE_LEFT(Input.KEY_A),
    MOVE_RIGHT(Input.KEY_D),
    
    ATTACK_UP(Input.KEY_UP),
    ATTACK_DOWN(Input.KEY_DOWN),
    ATTACK_LEFT(Input.KEY_LEFT),
    ATTACK_RIGHT(Input.KEY_RIGHT),
    
    SWITCH_WEAPON(Input.KEY_SPACE),
    
    OPEN_MINIMAP(Input.KEY_M);
    
    private int key;
    public int key() { return key; }
    
    Options(int key) {
        this.key = key;
    }
}
