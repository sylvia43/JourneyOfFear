package game.util.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class ServerLogger {
    
    public static final int LINES = 10;
    
    public static String[] toRender = new String[LINES];
    public static ArrayList<EnemyPlayerData> enemies = new ArrayList<EnemyPlayerData>();
    
    public static PrintStream out;

    public static int pos = 0;
    
    public static void init(ArrayList<EnemyPlayerData> otherEnemies) {
        try {
            out = new PrintStream(new FileOutputStream("log.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + e);
        }
        enemies = otherEnemies;
    }
    
    public static void render(Graphics g) {
        g.drawString("Enemies: ",100,0);
        
        for (int i=0;i<enemies.size();i++) {
            EnemyPlayerData e = enemies.get(i);
            g.drawString(e.x + " " + e.y,100,20+20*i);
        }
        
        for (int i=0;i<LINES;i++) {
            if (toRender[i] == null)
                break;
            g.drawString(toRender[i],50,400-20*i);
        }
    }
    
    private ServerLogger() { }
    
    public static void log(String message) {
        System.out.println(message);
        if (out == null)
            return;
        out.println(message);
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