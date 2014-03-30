package game.util.server;

public class EnemyPlayerData {
    
    public int id;
    public int x;
    public int y;
    
    public EnemyPlayerData(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return id + " - x:" + x + " y:" + y;
    }
}