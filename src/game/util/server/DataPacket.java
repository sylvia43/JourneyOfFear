package game.util.server;

import java.util.concurrent.CopyOnWriteArrayList;

public class DataPacket {

    public static final int MAX_SIZE = 16;
    
    public static final int CLIENT_ID = 0;
    public static final int PORT = 4;
    public static final int X = 8;
    public static final int Y = 12;
    
    private byte[] data;
    
    public static CopyOnWriteArrayList<EnemyPlayerData> players;
    
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    // Called by server send thread.
    public DataPacket(EnemyPlayerData e) {
        data = new byte[MAX_SIZE];
        byte[] ip = e.client.ip.getAddress();
        add(ip[0],CLIENT_ID);
        add(ip[1],CLIENT_ID+1);
        add(ip[2],CLIENT_ID+2);
        add(ip[3],CLIENT_ID+3);
        add(e.client.port,PORT);
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
    
    public static void add(byte[] arr, int i, int pos) {
        arr[pos] = (byte) (i >> 24);
        arr[pos+1] = (byte) (i >> 16);
        arr[pos+2] = (byte) (i >> 8);
        arr[pos+3] = (byte) (i);
    }
    
    public byte[] getBytes() {
        return data;
    }
    
    public ClientID getClient() {
        return new ClientID(
                get(CLIENT_ID) + "." +
                get(CLIENT_ID+1) + "." +
                get(CLIENT_ID+2) + "." +
                get(CLIENT_ID+3),get(PORT));
    }
    
    public void update(EnemyPlayerData e, ClientID client) {
        e.x = this.get(X);
        e.y = this.get(Y);
        e.client = client;
    }
}
