package game.network.server;

public class DataPacket {

    public static final int MAX_SIZE = 16;
    
    public static final int TYPE = 0;
    public static final int ID = 4;
    public static final int X = 8;
    public static final int Y = 12;
    
    private byte[] data;
        
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    // Called by server send thread.
    public DataPacket(EnemyPlayerData e) {
        data = new byte[MAX_SIZE];
        add(0,TYPE);
        add(e.getId(),ID);
        add(e.getX(),X);
        add(e.getY(),Y);
    }
    
    public DataPacket(int id) {
        data = new byte[MAX_SIZE];
        add(1,TYPE);
        add(id,ID);
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
        e.setX(get(X));
        e.setY(get(Y));
    }
}
