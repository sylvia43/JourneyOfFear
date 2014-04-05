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
    
    public static int get(byte[] bytes, int pos) {
        int ret = 0;
        for (int i=0;i<4;i++) {
            ret <<= 8;
            ret |= (int)bytes[i+pos] & 0xFF;
        }
        return ret;
    }
    
    public static void add(byte[] arr, int i, int pos) {
        arr[pos] = (byte) (i >> 24);
        arr[pos+1] = (byte) (i >> 16);
        arr[pos+2] = (byte) (i >> 8);
        arr[pos+3] = (byte) (i);
    }
    
    public static byte[] valueOf(int i) {
        byte[] arr = new byte[4];
        arr[0] = (byte) (i >> 24);
        arr[1] = (byte) (i >> 16);
        arr[2] = (byte) (i >> 8);
        arr[3] = (byte) (i);
        return arr;
    }
    
    public byte[] getBytes() {
        return data;
    }
    
    public int getClient() {
        return get(ID);
    }
    
    public void update(EnemyPlayerData e) {
        if (e.getId() != this.get(ID))
            throw new RuntimeException("Buggy code @ DataPacket.update(EnemyPlayerData e)");
        e.setX(this.get(X));
        e.setY(this.get(Y));
    }
}
