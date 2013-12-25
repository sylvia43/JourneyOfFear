package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Enemy {
    
    private String name;
    private int currentHp;
    
    private double x;
    private double y;
    private int maxHp;
    
    private Animation left;
    private Animation up;
    private Animation down;
    private Animation right;
    
    private String spritePath;
    
    public Enemy() {
        this("enemy_blank.png",1,"Block");
    }
    
    public Enemy(String spritePath, int hp, String name) {
        this.spritePath = spritePath;
        this.maxHp = this.currentHp = hp;
        this.name = name;
    }
    
    public void init(GameContainer container) throws SlickException {
        left = right = up = down = ResourceLoader.initializeAnimation(spritePath);
        x = (int) (Math.random()*container.getWidth());
        y = (int) (Math.random()*container.getHeight());
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        up.draw((float)x,(float)y);
    }
}
