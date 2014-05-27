package game.network.server;

public class EnemyPlayerData {
    
    public int x;
    public int y;
    public int id;
    public int dir;
    
    public EnemyPlayerData(int id) {
        this.id = id;
    } 
    
    public EnemyPlayerData(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof EnemyPlayerData))
            return false;
        
        return ((EnemyPlayerData) o).id == id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }
}