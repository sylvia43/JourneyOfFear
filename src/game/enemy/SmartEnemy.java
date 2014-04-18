package game.enemy;

import game.player.Player;
import org.newdawn.slick.GameContainer;

public abstract class SmartEnemy extends AttackingEnemy {
    
    protected int dodgeTimer;
    protected boolean dodging;
    protected int dodgeDX;
    protected int dodgeDY;
    
    protected static final int DODGE_MULTIPLIER = 30;
    protected static final int DODGE_DISTANCE = 200;
    protected static final int DODGE_DURATION = 400;
    
    public SmartEnemy(Player player) {
        super(player);
    }
    
    @Override
    public void update(GameContainer container, int delta) {
        super.update(container,delta);
        avoidAttacks(delta);
                
        if (dodgeTimer>0)
            dodgeTimer -= delta;
        
        if (dodgeTimer>0 && stunTimer<=0) {
            x+=(dodgeDX*dodgeTimer)/(DODGE_DISTANCE*DODGE_MULTIPLIER);
            y+=(dodgeDY*dodgeTimer)/(DODGE_DISTANCE*DODGE_MULTIPLIER);
        }
    }
    
    protected void avoidAttacks(int delta) {
        if(player.getAttack().isAttacking() &&
                Math.sqrt((x-player.getX())*(x-player.getX())+(y-player.getY())*(y-player.getY())) <= 192)
            initializeDodge(x-player.getX(),y-player.getY(),1);
    }
    
    protected void initializeDodge(int dx, int dy, double mult) {
        if (dodgeTimer>0)
            return;
        
        dodgeTimer = DODGE_DURATION;
        dodgeDX=(int)(mult*DODGE_DISTANCE*Math.cos(Math.atan2(dy,dx)));
        dodgeDY=(int)(mult*DODGE_DISTANCE*Math.sin(Math.atan2(dy,dx)));
    }
}
