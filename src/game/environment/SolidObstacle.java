package game.environment;

import game.sprite.Rectangle;

public class SolidObstacle extends Obstacle {
        
    public SolidObstacle() {
        super();
    }

    public SolidObstacle(int x, int y) {
        super(x,y);
    }
    
    public int canMoveSteps(Rectangle otherMask, int steps, int dx, int dy) { return 0; }
}
