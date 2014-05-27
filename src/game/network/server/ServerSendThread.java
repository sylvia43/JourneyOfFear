package game.network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Queue;

public class ServerSendThread implements Runnable {
    
    private Queue<Integer> deleteQueue;
    private List<EnemyPlayerData> players;
    private DatagramSocket socket;
    private Server server;
    private InetAddress destIp;
    private int destPort;
    private int id;
    
    public ServerSendThread(List<EnemyPlayerData> players, DatagramSocket socket,
            Server server, InetAddress destIp, Queue<Integer> deleteQueue,
            int destPort, int id) {
        this.deleteQueue = deleteQueue;
        this.players = players;
        this.socket = socket;
        this.server = server;
        this.destIp = destIp;
        this.destPort = destPort;
        this.id = id;
    }
    
    @Override
    public void run() {
        byte[] data;
        while(true) {
            if (server.isKillId(id)) {
                System.out.println("Client " + id + " disconnected.");
                server.killId(id);
                break;
            }
            
            if (deleteQueue.size() > 0) {
                data = new DataPacket(deleteQueue.poll()).getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,destIp,destPort);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    server.terminate("Failed to send data: " + ex);
                }
                continue;
            }
            
            for (EnemyPlayerData e : players) {
                data = new DataPacket(e).getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,destIp,destPort);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    server.terminate("Failed to send data: " + ex);
                }
            }
        }
    }
}