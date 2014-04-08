package game.environment;

import game.sprite.ImageMask;

public class SolidObstacle extends Obstacle {
        
    public SolidObstacle() {
        super();
    }

    public SolidObstacle(int x, int y) {
        super(x,y);
    }
    
    public int canMoveSteps(int ox, int oy, ImageMask otherMask, int steps, int dx, int dy) {
        for (int i=0;i<steps;i++) {
            if (mask.intersects(otherMask))
                return i;
        }
        return steps;
    }
}
