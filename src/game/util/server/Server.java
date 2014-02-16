package game.util.server;

import game.enemy.EnemyPlayer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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
                        System.out.println("Searching for incoming connections.");
                        sockets.put(socketCounter,server.accept());
                        socketCounter++;
                        System.out.println("Found new incoming connection.");
                        System.out.println("Creating thread for new connection.");
                        Thread clientThread = new Thread(new Runnable() {
                            private volatile boolean running = true;
                            private int localSocketCounter = socketCounter-1;
                            private Socket socket = sockets.get(localSocketCounter);
                            public void run() {
                                try {
                                    Thread senderThread = new Thread(new Runnable() {
                                        private volatile boolean running = true;
                                        private int localSocketCounter = socketCounter;
                                        private Socket socket = sockets.get(localSocketCounter);
                                        @Override
                                        public void run() {
                                            try {
                                                while (running) {
                                                    for (EnemyPlayer e : DataPacket.enemies)
                                                        socket.getOutputStream().write(e.getPacket().getBytes());
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
                                    senderThread.start();
                                    
                                    byte[] b = new byte[DataPacket.MAX_SIZE];
                                    DataPacket packet = null;
                                    
                                    while (running) {
                                        if (socket.getInputStream().read(b,0,DataPacket.MAX_SIZE)
                                                !=DataPacket.MAX_SIZE)
                                            running = false;
                                        System.out.println("Read data: " + Arrays.toString(b));
                                        packet = new DataPacket(b);
                                        packet.updateEnemy();
                                    }
                                    System.out.println("Lost client, closing connection.");
                                    sockets.remove(localSocketCounter);
                                    socket = null;
                                } catch (IOException e) {
                                    System.out.println("Error: " + e);
                                    System.out.println("Closing Connections.");
                                    sockets.remove(localSocketCounter);
                                    socket = null;
                                }
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
