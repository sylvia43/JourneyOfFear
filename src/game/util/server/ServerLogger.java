package game.util.server;

import org.newdawn.slick.Graphics;

public class ServerLogger {
    
    public static final int LINES = 20;
    
    public static String[] toRender = new String[LINES];
    public static int pos = 0;
        
    public static void render(Graphics g) {
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