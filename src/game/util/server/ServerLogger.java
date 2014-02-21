package game.util.server;

import game.enemy.EnemyPlayer;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class ServerLogger {
    
    public static final int LINES = 10;
    
    public static String[] toRender = new String[LINES];
    public static ArrayList<EnemyPlayer> enemies = new ArrayList<EnemyPlayer>();
    
    public static int pos = 0;
    
    public static void update() {
        enemies.clear();
        enemies.addAll(DataPacket.enemies);
    }
    
    public static void render(Graphics g) {
        
        g.drawString("Enemies: ",100,0);
        
        for (int i=0;i<enemies.size();i++) {
            EnemyPlayer e = enemies.get(i);
            g.drawString(e.getId() + " " + e.getX() + " " + e.getY(),100,20+20*i);
        }
        
        for (int i=0;i<LINES;i++) {
            if (toRender[i] == null)
                break;
            g.drawString(toRender[i],50,400-20*i);
        }
    }
    
    private ServerLogger() { }
    
    public static void log(String message) {
        toRender[9] = toRender[8];
        toRender[8] = toRender[7];
        toRender[7] = toRender[6];
        toRender[6] = toRender[5];
        toRender[5] = toRender[4];
        toRender[4] = toRender[3];
        toRender[3] = toRender[2];
        toRender[2] = toRender[1];
        toRender[1] = toRender[0];
        toRender[0] = message;
    }
}