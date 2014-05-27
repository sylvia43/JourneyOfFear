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
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    
    private int port;
    private DatagramSocket socket;
    private List<EnemyPlayerData> players;
    private int clientCounter = 0;
    private Map<Integer,Long> ping;
    private List<Integer> killIds;
    private Queue<Integer> deleteQueue;
    private long currentIteration;
    private boolean running = true;
    
    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }
    
    public Server(int port) {
        players = new CopyOnWriteArrayList<EnemyPlayerData>();
        ping = new ConcurrentHashMap<Integer,Long>();
        deleteQueue = new ConcurrentLinkedQueue<Integer>();
        killIds = new ArrayList<Integer>();
        System.out.println("Set port.");
        this.port = port;
    }
    
    public void terminate(String s) {
        System.out.println("ERROR: " + s);
        running = false;
    }
    
    public boolean isKillId(int id) {
        return killIds.contains(id);
    }
    
    public void killId(int id) {
        killIds.remove(new Integer(id));
        players.remove(new EnemyPlayerData(id));
        deleteQueue.add(id);
    }
    
    public void start() {
        System.out.println("Creating Server.");
        
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            if (e.getMessage().equals("Address already in use: Cannot bind"))
                terminate("Error: There is already a socket on port " + port + "!");
            else
                terminate("Error creating socket: " + e);
        }
        
        System.out.println("Started server.");
        
        Thread handshakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket socket = null;
                try {
                    socket = new ServerSocket(port);
                } catch (IOException e) {
                    terminate("Error creating TCP socket: " + e);
                }
                while (running) {
                    Socket clientSocket;
                    try {
                        clientSocket = socket.accept();
                    } catch (IOException e) {
                        System.out.println("Error accepting socket: " + e);
                        continue;
                    }
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                    } catch (IOException e) {
                        System.out.println("Error getting output stream: " + e);
                        continue;
                    }
                    try {
                        out.write(clientCounter);
                    } catch (IOException e) {
                        System.out.println("Error sending handshake data: " + e);
                        continue;
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error closing handshake socket: " + e);
                        continue;
                    }
                    ping.put(clientCounter,currentIteration);
                    System.out.println("New client! " + clientCounter);
                    clientCounter++;
                }
            }
        });
        handshakeThread.start();
        
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentIteration++;
                for (Map.Entry<Integer,Long> entry : ping.entrySet()) {
                    long oldIteration = entry.getValue();
                    if (currentIteration-oldIteration > 5) {
                        killIds.add(entry.getKey());
                        ping.remove(entry.getKey());
                    }
                }
            }
        },200,200);
        
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[DataPacket.MAX_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);
                DataPacket packet;
                int clientId;
                boolean updated;
                currentIteration = 0;
                
                while (running) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        terminate("Unable to receive data: " + e);
                    }
                    
                    packet = new DataPacket(recvPacket.getData());
                    clientId = packet.getClient();
                    
                    updated = false;
                    for (EnemyPlayerData e : players) {
                        if (e.id == clientId) {
                            ping.put(clientId,currentIteration);
                            packet.update(e);
                            updated = true;
                            break;
                        }
                    }
                    
                    if (updated)
                        continue;
                    
                    players.add(packet.getPlayer());
                    
                    new Thread(new ServerSendThread(players,socket,Server.this,
                            recvPacket.getAddress(),deleteQueue,recvPacket.getPort(),clientId)).start();
                }
            }
        });
        receiveThread.start();
    }
}
