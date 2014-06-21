package game.network.client;

import game.error.NetworkException;
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
    
    public NetworkHandler(String newIp, int newPort, Player localPlayer,
            List<EnemyPlayer> newEnemies) throws UnknownHostException {
        this.enemies = newEnemies;
        this.ip = InetAddress.getByName(newIp);
        this.port = newPort;
        this.player = localPlayer;
    }
    
    public void terminate(String s) {
        System.out.println("ERROR: " + s);
        running = false;
    }
    
    public void start() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new NetworkException("Error creating socket: " + e);
        }
        
        handshake();
        
        get = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] recvData = new byte[DataPacket.MAX_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(recvData,recvData.length);

                while (running) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        terminate("Error receiving packet: " + e);
                    }
                    responseTime = System.currentTimeMillis();
                    DataPacket recvDataPacket = new DataPacket(recvData);
                    
                    int packetId = recvDataPacket.getClient();
                    
                    if (packetId == myClientId)
                        continue;
                    
                    if (recvDataPacket.get(DataPacket.TYPE) == 1) {
                        for (EnemyPlayer e : enemies)
                            if (e.data.id == packetId)
                                enemies.remove(e);
                        continue;
                    }
                    
                    boolean updated = false;
                    
                    for (EnemyPlayer e : enemies) {
                        if (packetId == e.data.id) {
                            recvDataPacket.update(e.data);
                            updated = true;
                            break;
                        }
                    }
                    
                    if (updated)
                        continue;
                    
                    enemies.add(new EnemyPlayer(recvDataPacket.getPlayer()));
                }
            }
        });
        get.start();

        send = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] sendData;
                while (running) {
                    if (responseTime != -1 && System.currentTimeMillis()-responseTime > 1000) {
                        terminate("Disconnecting.");
                    }
                    
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted: " + e);
                    }
                    
                    sendData = player.getBytes(myClientId);
                    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
                    try {
                        socket.send(sendPacket);
                    } catch (IOException e) {
                        terminate("Error sending packet: " + e);
                    }
                }
                if (socket != null && !socket.isClosed()) {
                    System.out.println("Closing socket.");
                    socket.close();
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
            if (e.getMessage().equals("Connection refused: connect"))
                throw new NetworkException("No server at " + ip + ":" + port);
            else
                throw new NetworkException("Error creating TCP socket: " + e);
        }

        InputStream in = null;
        try {
            in = handshakeSocket.getInputStream();
        } catch (IOException e) {
            throw new NetworkException("Error getting input stream: " + e);
        }
        try {
            myClientId = in.read();
        } catch (IOException e) {
            throw new NetworkException("Error recieving handshake data: " + e);
        }
        try {
            handshakeSocket.close();
        } catch (IOException e) {
            throw new NetworkException("Error closing handshake socket: " + e);
        }
    }
}
