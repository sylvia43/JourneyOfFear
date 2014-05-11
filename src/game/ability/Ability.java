package game.ability;

import game.util.GameObject;

public abstract class Ability {
    
    public abstract void use(int dx, int dy);
    public abstract void update(int delta, GameObject o, boolean condition);
}
