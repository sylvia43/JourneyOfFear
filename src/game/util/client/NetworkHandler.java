package game.util.client;

import game.enemy.EnemyPlayer;
import game.player.Player;
import game.util.server.ClientID;
import game.util.server.DataPacket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NetworkHandler {
    
    private DatagramSocket socket;
    private Thread get;
    private Thread send;
    private Player player;
    private volatile boolean running = true;
    private InetAddress ip;
    private int port = 0;
    private ArrayList<EnemyPlayer> enemies;
    
    private ClientID myClientID;
    
    public NetworkHandler(String newIp, int newPort, Player localPlayer, ArrayList<EnemyPlayer> newEnemies) {
        this.enemies = newEnemies;
        try {
            this.ip = InetAddress.getByName(newIp);
        } catch (UnknownHostException e) {
            System.out.println("Unable to connect: " + e);
            return;
        }
        this.port = newPort;
        this.player = localPlayer;
        
        try {
            socket = new DatagramSocket(); // Created at any open port.
        } catch (SocketException e) {
            System.out.println("Fatal Error: " + e);
        }
        
        try {
            socket.send(new DatagramPacket(new byte[]{},0,ip,port));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        
        myClientID = new ClientID(socket.getInetAddress(),socket.getLocalPort());
        
        get = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data = new byte[DataPacket.MAX_SIZE];
                    DatagramPacket packet = new DatagramPacket(data,data.length);
                    
                    while (running) {
                        socket.receive(packet);
                        DataPacket p = new DataPacket(data);
                        
                        System.out.println(p.getClient() + " " + myClientID);
                        
                        if (p.getClient().equals(myClientID))
                            continue;
                        
                        boolean updated = false;
                        
                        for (EnemyPlayer e : enemies) {
                            if (p.getClient().equals(e.client)) {
                                e.x = p.get(DataPacket.X);
                                e.y = p.get(DataPacket.Y);
                                updated = true;
                                break;
                            }
                        }
                        
                        if (updated)
                            continue;
                        
                        enemies.add(new EnemyPlayer(p.get(DataPacket.X),p.get(DataPacket.Y),p.getClient()));
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (socket != null && !socket.isClosed())
                        socket.close();
                }
            }
        });
        get.start();

        send = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data = new byte[DataPacket.MAX_SIZE];
                    while (running) {
                        data = player.getBytes(myClientID);
                        DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
                        socket.send(packet);
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (socket != null && !socket.isClosed())
                        socket.close();
                }
            }
        });
        send.start();
    }
}
