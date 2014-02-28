package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Obstacle {
    
    protected Animation sprite;
    public Animation getSprite() { return sprite; }
    protected AnimationMask mask;
    
    protected int x;
    protected int y;  
    protected int miniWidth;
    protected int miniHeight;
    
    protected Color minimapColor;
    
    protected static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
         
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public int getMiniWidth() { return miniWidth; }
     public int getMiniHeight() { return miniHeight; }
    public Color getColor() { return minimapColor; }
    
    public ImageMask getCollisionMask() {
        return mask.getImageMask(sprite.getFrame());
    }
    
    public Obstacle() {
        minimapColor = Color.red;
        this.x = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_X-100)) + 50;
        this.y = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y-100)) + 50;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public Obstacle(int x, int y) {
        minimapColor = Color.red;
        this.x = x;
        this.y = y;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta, Area currentArea) {
        resolveCollision();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }
     
    public static void updateEnemies(ArrayList<Enemy> newEnemies) {
        enemies = newEnemies;
    }
    
    //Empty methods. These methods should be overriden
    protected void initializeSprite() throws SlickException { }
    protected void resolveCollision() { }
    
    protected AnimationMask createMask() {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getImage(i));
        }
        return new AnimationMask(masks);
    }
    
    public byte isInObstacle(Player player) { return -1; }
}