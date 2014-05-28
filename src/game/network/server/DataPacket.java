package game.network.server;

import game.player.Player;

public class DataPacket {

    public static final int MAX_SIZE = 32;
    
    public static final int TYPE = 0;
    public static final int ID = 4;
    public static final int X = 8;
    public static final int Y = 12;
    public static final int DIR = 16;
    public static final int FRAME = 20;
    public static final int W_TYPE = 24;
    public static final int W_FRAME = 28;
    
    private byte[] data;
        
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    private DataPacket() {
        data = new byte[MAX_SIZE];
    }
    
    private DataPacket(int type, int id, int x, int y, int dir, int animFrame,
            int weaponType, int weaponFrame) {
        this();
        add(type,TYPE);
        add(id,ID);
        add(x,X);
        add(y,Y);
        add(dir,DIR);
        add(animFrame,FRAME);
        add(weaponType,W_TYPE);
        add(weaponFrame,W_FRAME);
    }
    
    // Called by server send thread.
    public DataPacket(EnemyPlayerData e) {
        this(0,e.id,e.x,e.y,e.dir,e.frame,e.weapType,e.weapFrame);
    }
    
    public DataPacket(Player p, int id) {
        this(0,id,p.getX(),p.getY(),p.getDir(),p.getFrame(),p.getAttackIndex(),p.getAttackFrame());
    }
    
    public void update(EnemyPlayerData e) {
        e.x = get(X);
        e.y = get(Y);
        e.dir = get(DIR);
        e.frame = get(FRAME);
        e.weapType = get(W_TYPE);
        e.weapFrame = get(W_FRAME);
    }

    public EnemyPlayerData getPlayer() {
        return new EnemyPlayerData(get(ID),get(X),get(Y),get(DIR),get(FRAME),get(W_TYPE),get(W_FRAME));
    }
    
    /** Packet to send disconnect to client. */
    public DataPacket(int id) {
        this(1,id,0,0,0,0,0,0);
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
}
