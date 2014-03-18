package game.util.server;

public class EnemyPlayerData {
    
    public ClientID client;
    
    public int x;
    public int y;
    
    public EnemyPlayerData(ClientID client, int x, int y) {
        this.client = client;
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return "x:" + x + " y:" + y;
    }
}