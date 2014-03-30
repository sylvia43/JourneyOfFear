package game.util.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSendThread implements Runnable {
    
    private CopyOnWriteArrayList<EnemyPlayerData> players;
    private DatagramSocket socket;    
    private int client;
    private InetAddress destIp;
    private int destPort;
    
    public ServerSendThread(CopyOnWriteArrayList<EnemyPlayerData> players,
            DatagramSocket socket, int client, InetAddress destIp, int destPort) {
        this.players = players;
        this.socket = socket;
        this.client = client;
        this.destIp = destIp;
        this.destPort = destPort;
    }
    
    @Override
    public void run() {
        byte[] data = new byte[DataPacket.MAX_SIZE];
        while(true) {
            for (EnemyPlayerData e : players) {
                data = new DataPacket(e).getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,destIp,destPort);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    ServerLogger.log("Failed to send data: " + ex);
                }
            }
        }
    }
}