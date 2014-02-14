package game.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket server;
    private HashMap<Integer,Socket> sockets = new HashMap<Integer,Socket>();
    private int socketCounter = 0;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        Thread getClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                try {
                    while (running) {
                        sockets.put(socketCounter,server.accept());
                        socketCounter++;
                        Thread clientThread = new Thread(new Runnable() {
                            private volatile boolean running = true;
                            private int localSocketCounter = socketCounter;
                            private Socket socket = sockets.get(localSocketCounter);
                            public void run() {
                                try {
                                    while (running) {
                                        byte[] b = new byte[DataPacket.MAX_SIZE];
                                        socket.getInputStream().read(b,0,DataPacket.MAX_SIZE);
                                        DataPacket packet = new DataPacket(b);
                                        packet.updateEnemy();
                                    }
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
                        clientThread.start();
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            }
            public void kill() {
                running = false;
            }
        });
        getClients.start();
    }
}
