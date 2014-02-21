package game.util.server;

import game.enemy.EnemyPlayer;
import game.state.StateServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
            ServerLogger.log("Error: " + e);
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
                                Thread getterThread;
                                Thread senderThread;
                                
                                getterThread = new Thread(new Runnable() {
                                    private volatile boolean running = true;
                                    private int localSocketCounter = socketCounter-1;
                                    private Socket socket = sockets.get(localSocketCounter);
                                    
                                    @Override
                                    @SuppressWarnings("empty-statement")
                                    public void run() {
                                        int id = 0;
                                        try {
                                            byte[] b = new byte[DataPacket.MAX_SIZE];
                                            ServerLogger.log(sockets.toString());
                                            while (running) {
                                                if(socket.getInputStream().read(b,0,DataPacket.MAX_SIZE)!=DataPacket.MAX_SIZE) {
                                                    ServerLogger.log("Reading error.");
                                                    ServerLogger.log(Arrays.toString(b));
                                                    continue;
                                                }
                                                int newId = DataPacket.get(b,8);
                                                if (id==0) {
                                                    id = newId;
                                                    ServerLogger.log("Setting id: " + id);
                                                } else if (id!=newId) {
                                                    ServerLogger.log("Id mismatch: " + id);
                                                    continue;
                                                }
                                                new DataPacket(b);
                                            }
                                        } catch (IOException e) {
                                            ServerLogger.log("Error: " + e);
                                        } finally {
                                            sockets.remove(localSocketCounter);
                                            try {
                                                socket.close();
                                            } catch (IOException e) {
                                                ServerLogger.log("Failed to close socket: " + e);
                                            }
                                            socket = null;
                                            ServerLogger.log("Closing connection");
                                            DataPacket.registerDisconnect(id);
                                        }
                                    }
                                });
                                getterThread.start();
                                
                                ServerLogger.log("Creating thread to send data packets.");
                                senderThread = new Thread(new Runnable() {
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
                                                Thread.sleep(16);
                                            }
                                        } catch (IOException e) {
                                            ServerLogger.log("Error: " + e);
                                        } catch (NullPointerException e) {
                                            ServerLogger.log("Client socket closed.");
                                        } catch (InterruptedException e) {
                                            ServerLogger.log("Server Interrupted: " + e);
                                        } finally {
                                            sockets.remove(localSocketCounter);
                                            try {
                                                socket.close();
                                            } catch (IOException e) {
                                                ServerLogger.log("Failed to close socket: " + e);
                                            }
                                            socket = null;
                                        }
                                    }
                                });
                                senderThread.start();
                            }
                        });
                        clientThread.start();
                    }
                } catch (IOException e) {
                    ServerLogger.log("Error: " + e);
                }
            }
        });
        getClients.start();
    }
}
