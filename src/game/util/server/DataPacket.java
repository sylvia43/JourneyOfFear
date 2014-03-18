package game.util.server;

import java.util.concurrent.CopyOnWriteArrayList;

public class DataPacket {

    public static final int MAX_SIZE = 8;
    
    public static final int X = 0;
    public static final int Y = 4;
    
    private byte[] data;
    
    public static CopyOnWriteArrayList<EnemyPlayerData> players;
    
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    public DataPacket(EnemyPlayerData e) {
        data = new byte[MAX_SIZE];
        add(e.x,X);
        add(e.y,Y);
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

    public void update(EnemyPlayerData e, ClientID client) {
        e.x = this.get(X);
        e.y = this.get(Y);
        e.client = client;
    }
}
