package game.environment.obstacle;

import game.sprite.Rectangle;

public abstract class SolidObstacle extends Obstacle {
        
    public SolidObstacle() {
        super();
    }

    public SolidObstacle(int x, int y) {
        super(x,y);
    }
    
    public abstract int canMoveSteps(Rectangle otherMask, int steps, int dx, int dy);
}
