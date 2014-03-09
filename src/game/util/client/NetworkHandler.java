package game.util.client;

import game.player.Player;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class NetworkHandler {
    
    private MulticastSocket socket;
    private InetAddress group = null;
    private Thread get;
    private Thread send;
    private Player player;
    private volatile boolean running = true;
    private String ip = "";
    private int port = 0;

    public NetworkHandler(String newIp, int newPort, Player localPlayer) {
        this.ip = newIp;
        this.port = newPort;
        this.player = localPlayer;
        
        try {
            socket = new MulticastSocket(port);
        } catch (IOException e) {
            switch (e.getMessage()) {
                case "Connection refused: connect":
                    System.out.println("Fatal: No open socket at " + ip + " port " + port + ".");
                    break;
                default:
                    System.out.println("Fatal Error: " + e);
                    e.printStackTrace();
                    break;
            }
            return;
        }
        
        try {
            group = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.out.println("Error connecting to group: " + e);
        }
        try {
            socket.joinGroup(group);
        } catch (IOException e) {
            System.out.println("Unable to join group: " + e);
        }
        
        get = new Thread(new Runnable() {
            @Override
            @SuppressWarnings("empty-statement")
            public void run() {
                try {
                    while (running) {
                        if (false) throw new IOException("asd");
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (socket != null && !socket.isClosed()) {
                        try {
                            socket.leaveGroup(group);
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Failed to close socket: " + e);
                        }
                    }
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
                        DatagramPacket packet = new DatagramPacket(data,data.length,group,port);
                        socket.send(packet);
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (socket != null && !socket.isClosed()) {
                        try {
                            socket.leaveGroup(group);
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Failed to close socket: " + e);
                        }
                    }
                }
            }
        });
        send.start();
    }
}
