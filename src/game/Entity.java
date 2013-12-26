package game;

import org.newdawn.slick.geom.Rectangle;

public interface Entity {
    
    public Mask getCollisionMask();
    public Rectangle getAttackMask();
}