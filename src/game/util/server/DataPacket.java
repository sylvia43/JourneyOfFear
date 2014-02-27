package game.util.server;

import game.enemy.EnemyPlayer;
import game.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.SlickException;

public class DataPacket {

    private byte[] data;
    private volatile boolean update = false;
    
    private static HashMap<Integer,DataPacket> packets = new HashMap<Integer,DataPacket>();
    
    public static final int MAX_SIZE = 12;
    public static ArrayList<EnemyPlayer> enemies = new ArrayList<EnemyPlayer>();
    public static Player player;
    
    public static final int ID = 0;
    public static final int X = 4;
    public static final int Y = 8;
    
    public static volatile boolean WAIT = false;
    
    private static HashMap<Integer,DataPacket> tempItr = new HashMap<Integer,DataPacket>();
    
    public DataPacket() {
        data = new byte[MAX_SIZE];
    }
    
    @SuppressWarnings("empty-statement")
    public DataPacket(byte[] data) {
        if (data.length != MAX_SIZE)
            return;
        this.data = data;
        update = true;
        while(WAIT); // Wait until tempItr is updated to avoid concurrent modification.
        packets.put(get(ID),this);
    }
    
    public static void updatePlayer(Player newPlayer) {
        player = newPlayer;
    }
    
    public static void update(ArrayList<EnemyPlayer> newEnemies) {
        WAIT = true;
        tempItr.clear();
        tempItr.putAll(packets);
        WAIT = false;
        for (Map.Entry<Integer,DataPacket> entry : tempItr.entrySet()) {
            DataPacket packet = entry.getValue();
            if (packet == null)
                continue;
            packet.update();
        }
        
        newEnemies.clear();
        newEnemies.addAll(enemies);
    }
    
    public static void registerDisconnect(int id) {
        EnemyPlayer enemy = null;
        for (EnemyPlayer e : enemies) {
            if (e.getId() == id) {
                enemy = e;
                break;
            }
        }
        if (enemy == null) {
            ServerLogger.log("No such enemy with id " + id);
            return;
        }
        ServerLogger.log("Removing enemy of id " + id);
        if (!enemies.remove(enemy))
            ServerLogger.log("Failed to remove enemy.");
        enemy = null;
    }
    
    public void update() {
        if (!update)
            return;
        
        update = false;
        
        int id = get(ID);
        
        if (player != null) {
            if (player.getID() == id) {
                player.setX(get(X));
                player.setY(get(Y));
                return;
            }
        }
        
        for (EnemyPlayer e : enemies) {
            if (e.getId() == id) {
                e.setX(get(X));
                e.setY(get(Y));
                return;
            }
        }
        try {
            enemies.add(new EnemyPlayer(get(X),get(Y),id));
            ServerLogger.log("Created new enemy: " + id);
        } catch (SlickException e) {
            System.out.println("Unable to create enemy player: " + e);
        }
        packets.remove(get(ID));
    }
    
    public void add(int i, int pos) {
        data[pos] = (byte) (i >> 24);
        data[pos+1] = (byte) (i >> 16);
        data[pos+2] = (byte) (i >> 8);
        data[pos+3] = (byte) (i);
    }
    
    public int get(int pos) {
        int ret = 0;
        for (int i=0;i<4;i++) {
            ret <<= 8;
            ret |= (int)data[i+pos] & 0xFF;
        }
        return ret;
    }
    
    public static int get(byte[] bytes, int pos) {
        int ret = 0;
        for (int i=0;i<4;i++) {
            ret <<= 8;
            ret |= (int)bytes[i+pos] & 0xFF;
        }
        return ret;
    }
    
    public byte[] getBytes() {
        return data;
    }

    public void updateEnemy() {
        update = true;
    }
}
