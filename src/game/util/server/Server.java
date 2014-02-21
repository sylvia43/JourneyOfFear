package game.util.server;

import game.enemy.EnemyPlayer;
import game.state.StateServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    
    private int port;
    private ServerSocket server;
    private HashMap<Integer,Socket> sockets = new HashMap<Integer,Socket>();
    private int socketCounter = 0;
    
    public Server(int port) {
        ServerLogger.log("Set port.");
        this.port = port;
    }
    
    public void start() {
        ServerLogger.log("Creating Server.");
        try {
            server = new ServerSocket(port,StateServer.MAX_CONNECTIONS);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        
        ServerLogger.log("Started server.");
        
        Thread getClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                try {
                    while (running) {
                        ServerLogger.log("Searching for incoming connections.");
                        sockets.put(socketCounter,server.accept());
                        socketCounter++;
                        ServerLogger.log("Found new incoming connection.");
                        ServerLogger.log("Creating thread for new connection.");
                        
                        Thread clientThread = new Thread(new Runnable() {
                            private volatile boolean running = true;
                            private int localSocketCounter = socketCounter-1;
                            private Socket socket = sockets.get(localSocketCounter);
                            @Override
                            public void run() {
                                ServerLogger.log("Creating thread to get data packets.");
                                Thread getterThread = new Thread(new Runnable() {
                                    private volatile boolean running = true;
                                    private int localSocketCounter = socketCounter-1;
                                    private Socket socket = sockets.get(localSocketCounter);
                                    
                                    @Override
                                    public void run() {
                                        try {
                                            byte[] b = new byte[DataPacket.MAX_SIZE];
                                            System.out.println(sockets);
                                            while (running) {
                                                socket.getInputStream().read(b,0,DataPacket.MAX_SIZE);
                                                //ServerLogger.log("Read data: " + Arrays.toString(b));
                                                new DataPacket(b);
                                            }
                                            ServerLogger.log("Lost client, closing connection.");
                                            sockets.remove(localSocketCounter);
                                            socket = null;
                                        } catch (IOException e) {
                                            System.out.println("Error: " + e);
                                            sockets.remove(localSocketCounter);
                                            socket = null;
                                        }
                                    }
                                    public void kill() {
                                        running = false;
                                    }
                                });
                                getterThread.start();
                                
                                ServerLogger.log("Creating thread to send data packets.");
                                Thread senderThread = new Thread(new Runnable() {
                                    private volatile boolean running = true;
                                    private int localSocketCounter = socketCounter-1;
                                    private Socket socket = sockets.get(localSocketCounter);
                                    @Override
                                    public void run() {
                                        try {
                                            ArrayList<EnemyPlayer> temp = new ArrayList<EnemyPlayer>();
                                            while (running) {
                                                temp.clear();
                                                temp.addAll(DataPacket.enemies);
                                                for (EnemyPlayer e : temp) {
                                                    socket.getOutputStream().write(e.getPacket().getBytes());
                                                }
                                            }
                                        } catch (IOException e) {
                                            System.out.println("Error: " + e);
                                            sockets.remove(localSocketCounter);
                                            socket = null;
                                        } catch (NullPointerException e) {
                                            System.out.println("Client socket closed.");
                                            sockets.remove(localSocketCounter);
                                            socket = null;
                                        }
                                    }
                                    public void kill() {
                                        running = false;
                                    }
                                });
                                senderThread.start();
                            }
                        });
                        clientThread.start();
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            }
        });
        getClients.start();
    }
}
