package game.util.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    private static final String ip = "224.0.0.1";
    
    private int port;
    private MulticastSocket server;
    private InetAddress group = null;
    
    private List<EnemyPlayerData> players;
    public List<EnemyPlayerData> synchronizedPlayers;

    public Server(int port) {
        players = new ArrayList<EnemyPlayerData>();
        synchronizedPlayers = (List<EnemyPlayerData>) Collections.synchronizedList(players);
        ServerLogger.log("Set port.");
        this.port = port;
    }
    
    public void start() {
        DataPacket.players = synchronizedPlayers;
        
        ServerLogger.log("Creating Server.");
        
        try {
            server = new MulticastSocket(port);
        } catch (IOException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        try {
            group = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            ServerLogger.log("Error forming group: " + e);
        }
        server.connect(group,port);
        ServerLogger.log("Started server.");
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket p = new DatagramPacket(bytes,bytes.length);
                while (true) {
                    try {
                        server.receive(p);
                        System.out.println("Recieved.");
                        new DataPacket(p.getData(),(InetSocketAddress)p.getSocketAddress());
                    } catch (IOException e) {
                        ServerLogger.log("Unable to recieve data: " + e);
                    }
                }
            }
        });
        receiveThread.start();
        
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] data = new byte[DataPacket.MAX_SIZE];
                while(true) {
                    synchronized(players) {
                        for (EnemyPlayerData e : players) {
                            System.out.println(e);
                            data = new DataPacket(e).getBytes();
                            DatagramPacket packet = new DatagramPacket(data,data.length,group,port);
                            try {
                                server.send(packet);
                            } catch (IOException ex) {
                                ServerLogger.log("Failed to send data: " + ex);
                            }
                        }
                    }
                }
            }
        });
        sendThread.start();
    }
}
