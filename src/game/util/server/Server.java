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
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    public Server(int port) {
        players = new CopyOnWriteArrayList<EnemyPlayerData>();
        ServerLogger.log("Set port.");
        this.port = port;
    }
    
    public void start() {
        DataPacket.players = players;
        
        ServerLogger.log("Creating Server.");
        
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        ServerLogger.log("Started server.");
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);
                DataPacket packet;
                ClientID client;
                boolean updated = false;
                
                while (true) {
                    
                    // prints data
                    /*
                    for (EnemyPlayerData e : players) {
                        System.out.println(e.client.toString() + ": " + e.x + ", " + e.y);
                    }
                    */
                    
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        ServerLogger.log("Unable to recieve data: " + e);
                    }
                    
                    if (recvPacket.getLength() == 0) {
                        System.out.println("New client!");
                        continue;
                    }
                    
                    client = new ClientID(recvPacket.getAddress(),recvPacket.getPort());
                    packet = new DataPacket(recvPacket.getData());
                    
                    updated = false;
                    for (EnemyPlayerData e : players) {
                        if (e.client.equals(client)) {
                            packet.update(e,client);
                            updated = true;
                            break;
                        }
                    }
                    
                    if (updated)
                        continue;
                    
                    players.add(new EnemyPlayerData(client,packet.get(DataPacket.X),packet.get(DataPacket.Y)));
                    
                    Runnable r = new ServerSendThread(players,socket,client);
                    new Thread(r).start();
                }
            }
        });
        receiveThread.start();
    }
}
