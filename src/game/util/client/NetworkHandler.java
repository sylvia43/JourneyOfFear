package game.util.client;

import game.enemy.EnemyPlayer;
import game.player.Player;
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
        
        get = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data = new byte[8];
                    DatagramPacket p = new DatagramPacket(data,data.length);
                    enemies.add(new EnemyPlayer());
                    while (running) {
                        socket.receive(p);
                        enemies.get(0).x = DataPacket.get(data,DataPacket.X);
                        enemies.get(0).y = DataPacket.get(data,DataPacket.Y);
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
                    byte[] data = new byte[8];
                    while (running) {
                        data = player.getBytes();
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
