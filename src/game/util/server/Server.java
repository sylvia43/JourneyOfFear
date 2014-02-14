package game.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket server;
    private ArrayList<Socket> sockets = new ArrayList<Socket>();
    private int socketCounter = 0;

    public Server() {
        Thread getClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                try {
                    while (running) {
                        sockets.add(server.accept());
                        socketCounter++;
                        Thread clientThread = new Thread(new Runnable() {
                            private volatile boolean running = true;
                            private Socket socket = sockets.get(socketCounter);
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
