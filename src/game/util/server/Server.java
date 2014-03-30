package game.util.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    
    private int port;
    private DatagramSocket socket;
    private CopyOnWriteArrayList<EnemyPlayerData> players;
    private int clientCounter = 0;
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    public Server(int port) {
        players = new CopyOnWriteArrayList<EnemyPlayerData>();
        System.out.println("Set port.");
        this.port = port;
    }
    
    public void start() {        
        System.out.println("Creating Server.");
        
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        System.out.println("Started server.");
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);
                DataPacket packet;
                int clientId;
                boolean updated = false;
                
                while (true) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        System.out.println("Unable to recieve data: " + e);
                    }
                                        
                    if (recvPacket.getLength() == 0) {
                        System.out.println("New client! " + clientCounter);
                        try {
                            socket.send(new DatagramPacket(DataPacket.valueOf(clientCounter),
                                    4,recvPacket.getAddress(),recvPacket.getPort()));
                        } catch (IOException e) {
                            System.out.println("Handshake error: " + e);
                        }
                        clientCounter++;
                        continue;
                    }
                    
                    packet = new DataPacket(recvPacket.getData());
                    clientId = packet.getClient();
                    
                    updated = false;
                    for (EnemyPlayerData e : players) {
                        if (e.id == clientId) {
                            packet.update(e);
                            updated = true;
                            break;
                        }
                    }
                    
                    if (updated)
                        continue;
                    
                    players.add(new EnemyPlayerData(clientId,packet.get(DataPacket.X),packet.get(DataPacket.Y)));
                    
                    Runnable r = new ServerSendThread(players,socket,recvPacket.getAddress(),recvPacket.getPort());
                    new Thread(r).start();
                }
            }
        });
        receiveThread.start();
    }
}
