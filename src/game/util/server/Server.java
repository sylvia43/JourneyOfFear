package game.util.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    private static final String ip = "230.0.0.1";
    
    private int port;
    private DatagramSocket server;
    private InetAddress group = null;
    
    private CopyOnWriteArrayList<EnemyPlayerData> players;
    
    public Server(int port) {
        players = new CopyOnWriteArrayList<EnemyPlayerData>();
        ServerLogger.log("Set port.");
        this.port = port;
    }
    
    public void start() {
        DataPacket.players = players;
        
        ServerLogger.log("Creating Server.");
        
        try {
            server = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        try {
            group = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            ServerLogger.log("Error forming group: " + e);
        }
        
        ServerLogger.log("Started server.");
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket p = new DatagramPacket(bytes,bytes.length);
                while (true) {
                    try {
                        System.out.println("Ready to receive.");
                        server.receive(p);
                        System.out.println("Got data.");
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
                    for (EnemyPlayerData e : players) {
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
        });
        sendThread.start();
    }
}
