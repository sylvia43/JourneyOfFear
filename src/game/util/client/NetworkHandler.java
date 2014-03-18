package game.util.client;

import game.player.Player;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.newdawn.slick.Graphics;

public class NetworkHandler {
    
    private DatagramSocket socket;
    private Thread get;
    private Thread send;
    private Player player;
    private volatile boolean running = true;
    private InetAddress ip;
    private int port = 0;
    public Graphics g;

    public NetworkHandler(String newIp, int newPort, Player localPlayer) {
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
                    while (running) {
                        socket.receive(p);
                        
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
