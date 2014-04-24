package game.network.server;

public class DataPacket {

    public static final int MAX_SIZE = 12;
    
    public static final int ID = 0;
    public static final int X = 4;
    public static final int Y = 8;
    
    private byte[] data;
        
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    // Called by server send thread.
    public DataPacket(EnemyPlayerData e) {
        data = new byte[MAX_SIZE];
        add(e.getId(),ID);
        add(e.getX(),X);
        add(e.getY(),Y);
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
    
    public byte[] getBytes() {
        return data;
    }
    
    public int getClient() {
        return get(ID);
    }
    
    public void update(EnemyPlayerData e) {
        e.setX(this.get(X));
        e.setY(this.get(Y));
    }
}
