package game.environment.obstacle;

import game.sprite.Rectangle;
import game.state.StateSingleplayer;

public abstract class SolidObstacle extends Obstacle {
        
    public SolidObstacle() {
        this((int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)));
    }

    public SolidObstacle(int x, int y) {
        super(x,y);
    }
    
    public abstract int canMoveSteps(Rectangle otherMask, int steps, int dx, int dy);
}
