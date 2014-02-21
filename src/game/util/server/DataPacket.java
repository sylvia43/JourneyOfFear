package game.util.server;

import game.enemy.EnemyPlayer;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;

public class DataPacket {

    private byte[] data;
    private volatile boolean update = false;
    
    private static ArrayList<DataPacket> packets = new ArrayList<DataPacket>();
    
    public static final int MAX_SIZE = 12;
    public static ArrayList<EnemyPlayer> enemies = new ArrayList<EnemyPlayer>();
    public static Player player;
    
    public DataPacket() {
        data = new byte[MAX_SIZE];
    }
    
    public DataPacket(byte[] data) {
        if (data.length != MAX_SIZE)
            return;
        this.data = data;
        update = true;
        packets.add(this);
    }
    
    public static void updatePlayer(Player newPlayer) {
        player = newPlayer;
    }
    
    public static void update(ArrayList<EnemyPlayer> newEnemies) {
        ArrayList<DataPacket> temp = (ArrayList<DataPacket>) packets.clone();
        for (DataPacket packet : temp) {
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
        enemies.remove(enemy);
    }
    
    public void update() {
        if (!update)
            return;
        update = false;
        
        if (player != null)
            if (player.getID() == get(8))
                return;
        
        for (EnemyPlayer e : enemies) {
            if (e.getId() == get(8)) {
                e.setX(get(0));
                e.setY(get(4));
                return;
            }
        }
        try {
            enemies.add(new EnemyPlayer(get(0),get(4),get(8)));
            ServerLogger.log("Created new enemy.");
        } catch (SlickException e) {
            System.out.println("Unable to create enemy player: " + e);
        }
        packets.remove(this);
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
