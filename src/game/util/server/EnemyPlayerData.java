package game.util.server;

import java.net.InetSocketAddress;

public class EnemyPlayerData {
    
    public InetSocketAddress address;
    public int x;
    public int y;
    
    public String toString() {
        return "x:" + x + " y:" + y;
    }
}