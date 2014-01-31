package game;

import game.util.resource.ResourceLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Health {
    private int health;
    private int maxHealth;
    
    private static Image emptyHeart = null;
    private static Image halfHeart = null;
    private static Image fullHeart = null;
    
    static {
        try {
            emptyHeart = ResourceLoader.initializeImage("player/health/health_empty.png");
            halfHeart = ResourceLoader.initializeImage("player/health/health_half.png");
            fullHeart = ResourceLoader.initializeImage("player/health/health_full.png");
        } catch (SlickException e) {
            System.out.println("Exception initializing hearts: " + e);
        }
    }
    
    public int getHealth() { return health; }
    public void damage(int health) { this.health -= health; }
    
    public Health(int health) {
        this.health = health; 
        this.maxHealth = health; 
    }
    
    public void render(int camX, int camY) {
        int fullNum = this.health / 2;
        int halfNum = this.health % 2;
        int emptyNum = 5 - (fullNum + halfNum);
        for(int i = 0; i < maxHealth; i++) {
            if(i < fullNum)
                fullHeart.draw(camX + (600 - i * 30), camY + 10, 4);
            else if(halfNum > 0) {
                halfHeart.draw(camX + (600 - i * 30), camY + 10, 4);
                halfNum--;
            }
            else if(i - (fullNum + this.health % 2) < emptyNum)
                emptyHeart.draw(camX + (600 - i * 30), camY + 10, 4);
        }
    }
}
