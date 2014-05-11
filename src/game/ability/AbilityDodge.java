package game.ability;

import game.util.GameObject;

public class AbilityDodge extends Ability {
    
    protected int dodgeTimer;
    protected boolean dodging;
    protected int dodgeDX;
    protected int dodgeDY;
    
    protected static final int DODGE_MULTIPLIER = 30;
    protected static final int DODGE_DISTANCE = 200;
    protected static final int DODGE_DURATION = 400;

    @Override
    public void use(int dx, int dy) {
        if (dodgeTimer>0)
            return;
        
        dodgeTimer = DODGE_DURATION;
        dodgeDX=(int)(DODGE_DISTANCE*Math.cos(Math.atan2(dy,dx)));
        dodgeDY=(int)(DODGE_DISTANCE*Math.sin(Math.atan2(dy,dx)));
    }

    @Override
    public void update(int delta, GameObject o, boolean hit) {
        if (dodgeTimer>0)
            dodgeTimer -= delta;
        
        if (dodgeTimer>0 && !hit) {
            o.setX(o.getX()+(dodgeDX*dodgeTimer)/(DODGE_DISTANCE*DODGE_MULTIPLIER));
            o.setY(o.getY()+(dodgeDY*dodgeTimer)/(DODGE_DISTANCE*DODGE_MULTIPLIER));
        }
    }
}
