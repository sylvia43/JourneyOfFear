package game.util.server;

import game.enemy.EnemyPlayer;
import game.state.StateServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            ServerLogger.log("Error: " + e);
        }
        
        ServerLogger.log("Started server.");
        
        Thread getClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            @Override
            public void run() {
                try {
                    while (running) {
                        ServerLogger.log("Searching for incoming connections.");
                        sockets.put(socketCounter,server.accept());
                        socketCounter++;
                        ServerLogger.log("Found new incoming connection.");
                        ServerLogger.log("Creating thread for new connection.");
                        
                        ServerLogger.log("Creating thread to get data packets.");
                        
                        // Getter thread.
                        new Thread(new Runnable() {
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
                                    InputStream socketIn = socket.getInputStream();
                                    int bytesRead;
                                    while (running) {
                                        if((bytesRead=socketIn.read(b,0,DataPacket.MAX_SIZE))
                                                !=DataPacket.MAX_SIZE) {
                                            ServerLogger.log("Reading error: " + bytesRead);
                                            continue;
                                        }
                                        int newId = DataPacket.get(b,DataPacket.ID);
                                        if (id==0) {
                                            id = newId;
                                            ServerLogger.log("Setting id: " + id);
                                        } else if (id!=newId) {
                                            ServerLogger.log("Id mismatch: " + newId);
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
                                ServerLogger.log("Stopping Getter Thread.");
                            }
                        }).start();
                        
                        ServerLogger.log("Creating thread to send data packets.");
                        
                        // Sender Thread.
                        new Thread(new Runnable() {
                            private volatile boolean running = true;
                            private int localSocketCounter = socketCounter-1;
                            private Socket socket = sockets.get(localSocketCounter);
                            @Override
                            public void run() {
                                try {
                                    ArrayList<EnemyPlayer> temp = new ArrayList<EnemyPlayer>();
                                    OutputStream out = socket.getOutputStream();
                                    while (running) {
                                        temp.clear();
                                        temp.addAll(DataPacket.enemies);
                                        for (EnemyPlayer e : temp) {
                                            out.write(e.getPacket().getBytes());
                                        }
                                    }
                                } catch (IOException e) {
                                    ServerLogger.log("Error: " + e);
                                } catch (NullPointerException e) {
                                    ServerLogger.log("Client socket closed.");
                                //} catch (InterruptedException e) {
                                //    ServerLogger.log("Server Interrupted: " + e);
                                } finally {
                                    sockets.remove(localSocketCounter);
                                    try {
                                        socket.close();
                                    } catch (IOException e) {
                                        ServerLogger.log("Failed to close socket: " + e);
                                    }
                                    socket = null;
                                }
                                ServerLogger.log("Stopping Sender Thread.");
                            }
                        }).start();
                    }
                } catch (IOException e) {
                    ServerLogger.log("Error: " + e);
                }
            }
        });
        getClients.start();
    }
}