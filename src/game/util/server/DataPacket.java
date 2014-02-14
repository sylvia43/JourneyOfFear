package game.util.server;

public class DataPacket {

    private byte[] data;
    public static final int MAX_SIZE = 64;
    
    public DataPacket() {
        data = new byte[MAX_SIZE];
    }
    
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    public void add(int i, int pos) {
        data[pos] = (byte) (i >> 24);
        data[pos+1] = (byte) (i >> 16);
        data[pos+2] = (byte) (i >> 8);
        data[pos+3] = (byte) (i);
    }

    public void updateEnemy() {
        // Update enemy here.
    }
}
