package game.util.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSendThread implements Runnable {
    
    private CopyOnWriteArrayList<EnemyPlayerData> players;
    private DatagramSocket socket;    
    private ClientID client;
    
    public ServerSendThread(CopyOnWriteArrayList<EnemyPlayerData> players,
            DatagramSocket socket, ClientID client) {
        this.players = players;
        this.socket = socket;
        this.client = client;
    }
    
    @Override
    public void run() {
        byte[] data = new byte[DataPacket.MAX_SIZE];
        while(true) {
            for (EnemyPlayerData e : players) {
                data = new DataPacket(e).getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,client.ip,client.port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    ServerLogger.log("Failed to send data: " + ex);
                }
            }
        }
    }
}