package game.environment;

import org.newdawn.slick.Color;

public class Hazard extends Obstacle {
    
    public Hazard() {
        minimapColor = Color.blue;
    }
    
    public Hazard(int x, int y) {
        super(x,y);
        minimapColor = Color.blue;
    }
}