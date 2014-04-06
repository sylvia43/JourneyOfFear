package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;

public class Hazard extends Obstacle {
    
    protected List<Enemy> enemies;
    protected Player player;
    
    public Hazard(Player player, ArrayList<Enemy> enemies) {
        super();
        this.enemies = enemies;
        this.player = player;
    }
    
    public Hazard(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(x,y);
        this.enemies = enemies;
    }
    
    @Override
    public void update(GameContainer container, int delta, Area currentArea) {
        resolveCollision();
    }
    
    protected void resolveCollision() { }
}