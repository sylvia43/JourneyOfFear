package game.util.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class DataPacket {

    public static final int MAX_SIZE = 8;
    
    public static final int X = 0;
    public static final int Y = 4;
    
    private byte[] data;
    private SocketAddress address;
    
    public static List<EnemyPlayerData> players;
    
    public DataPacket(byte[] data, InetSocketAddress address) {
        if (data.length != MAX_SIZE)
            return;
        this.data = data;
        this.address = address;
        this.update(address);
    }

    public DataPacket(EnemyPlayerData e) {
        add(e.x,X);
        add(e.y,Y);
    }
    
    public void update(InetSocketAddress address) {
        System.out.println("Updateing");
        synchronized(players) {
            for (EnemyPlayerData e : players) {
                if (e.address.equals(address)) {
                    e.x = this.get(X);
                    e.y = this.get(Y);
                    break;
                }
            }
            
            EnemyPlayerData e = new EnemyPlayerData();
            e.address = address;
            e.x = this.get(X);
            e.y = this.get(Y);
            players.add(e);
        }
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
}
