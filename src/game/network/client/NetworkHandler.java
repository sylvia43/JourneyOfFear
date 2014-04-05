package game.network.client;

import game.enemy.EnemyPlayer;
import game.network.server.DataPacket;
import game.player.Player;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class NetworkHandler {
    
    private DatagramSocket socket;
    private Thread get;
    private Thread send;
    private Player player;
    private volatile boolean running = true;
    private InetAddress ip;
    private int port = 0;
    private List<EnemyPlayer> enemies;
    
    private int myClientId;
    private long responseTime = -1;
    
    public NetworkHandler(String newIp, int newPort, Player localPlayer, List<EnemyPlayer> newEnemies) {
        this.enemies = newEnemies;
        try {
            this.ip = InetAddress.getByName(newIp);
        } catch (UnknownHostException e) {
            System.out.println("Unable to connect: " + e);
            return;
        }
        this.port = newPort;
        this.player = localPlayer;
    }
    
    public void start() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        handshake();
        
        get = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] recvData = new byte[DataPacket.MAX_SIZE];
                    DatagramPacket recvPacket = new DatagramPacket(recvData,recvData.length);
                    
                    while (running) {
                        socket.receive(recvPacket);
                        responseTime = System.currentTimeMillis();
                        DataPacket recvDataPacket = new DataPacket(recvData);
                        
                        if (recvDataPacket.getClient() == myClientId)
                            continue;
                        
                        boolean updated = false;
                        
                        for (EnemyPlayer e : enemies) {
                            if (recvDataPacket.getClient() == e.getId()) {
                                e.setX(recvDataPacket.get(DataPacket.X));
                                e.setY(recvDataPacket.get(DataPacket.Y));
                                updated = true;
                                break;
                            }
                        }
                        
                        if (updated)
                            continue;
                        
                        enemies.add(new EnemyPlayer(recvDataPacket.getClient(),
                                recvDataPacket.get(DataPacket.X),recvDataPacket.get(DataPacket.Y)));
                    }
                } catch (IOException e) {
                    if (e.toString().equals("java.net.SocketException: socket closed"))
                        System.out.println("Reciever thread disconnected.");
                    else
                        System.out.println("Error: " + e);
                }
            }
        });
        get.start();

        send = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] sendData = new byte[DataPacket.MAX_SIZE];
                    while (running) {
                        if (responseTime != -1 && System.currentTimeMillis()-responseTime > 1000) {
                            System.out.println("Disconnecting.");
                            running = false;
                        }
                        sendData = player.getBytes(myClientId);
                        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
                        socket.send(sendPacket);
                    }
                    if (socket != null && !socket.isClosed()) {
                        System.out.println("Closing socket.");
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            }
        });
        send.start();
    }

    private void handshake() {
        Socket handshakeSocket = null;
        try {
            handshakeSocket = new Socket(ip.getHostAddress(),port);
        } catch (IOException e) {
            System.out.println("Error creating TCP socket: " + e);
        }
        
        InputStream in = null;
        try {
            in = handshakeSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e);
        }
        try {
            myClientId = in.read();
        } catch (IOException e) {
            System.out.println("Error recieving handshake data: " + e);
        }
        try {
            handshakeSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing handshake socket: " + e);
        }
    }
}
