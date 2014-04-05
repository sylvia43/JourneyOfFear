package game.network.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    
    private int port;
    private DatagramSocket socket;
    private List<EnemyPlayerData> players;
    private int clientCounter = 0;
    private Map<Integer,Long> ping;
    private List<Integer> killIds;
    private long currentIteration;
    private boolean running = true;
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    public Server(int port) {
        players = new CopyOnWriteArrayList<EnemyPlayerData>();
        ping = new ConcurrentHashMap<Integer,Long>();
        killIds = new ArrayList<Integer>();
        System.out.println("Set port.");
        this.port = port;
    }
    
    public boolean isKillId(int id) {
        return killIds.contains(id);
    }
    
    public void killId(int id) {
        killIds.remove(new Integer(id));
    }
    
    public void start() {
        System.out.println("Creating Server.");
        
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Error creating socket: " + e);
        }
        
        System.out.println("Started server.");
        
        Thread handshakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket socket = null;
                try {
                    socket = new ServerSocket(port);
                } catch (IOException e) {
                    System.out.println("Error creating TCP socket: " + e);
                }
                while (running) {
                    Socket clientSocket = null;
                    try {
                        clientSocket = socket.accept();
                    } catch (IOException e) {
                        System.out.println("Error accepting socket: " + e);
                    }
                    OutputStream out = null;
                    try {
                        out = clientSocket.getOutputStream();
                    } catch (IOException e) {
                        System.out.println("Error getting output stream: " + e);
                    }
                    try {
                        out.write(clientCounter);
                    } catch (IOException e) {
                        System.out.println("Error sending handshake data: " + e);
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error closing handshake socket: " + e);
                    }
                    ping.put(clientCounter,currentIteration);
                    System.out.println("New client! " + clientCounter);
                    clientCounter++;
                }
            }
        });
        handshakeThread.start();
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);
                DataPacket packet;
                int clientId;
                boolean updated = false;
                currentIteration = 0;
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(running) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                            currentIteration++;
                            for (Map.Entry<Integer,Long> entry : ping.entrySet()) {
                                long oldIteration = entry.getValue();
                                if (currentIteration-oldIteration > 5) {
                                    killIds.add(entry.getKey());
                                    ping.remove(entry.getKey());
                                }
                            }
                        }
                    }
                }).start();
                
                while (running) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        System.out.println("Unable to recieve data: " + e);
                    }
                    
                    packet = new DataPacket(recvPacket.getData());
                    clientId = packet.getClient();
                    
                    updated = false;
                    for (EnemyPlayerData e : players) {
                        if (e.getId() == clientId) {
                            ping.put(clientId,currentIteration);
                            packet.update(e);
                            updated = true;
                            break;
                        }
                    }
                    
                    if (updated)
                        continue;
                    
                    players.add(new EnemyPlayerData(clientId,packet.get(DataPacket.X),packet.get(DataPacket.Y)));
                    
                    Runnable r = new ServerSendThread(players,socket,Server.this,
                            recvPacket.getAddress(),recvPacket.getPort(),clientId);
                    new Thread(r).start();
                }
            }
        });
        receiveThread.start();
    }
}
