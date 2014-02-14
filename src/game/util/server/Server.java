package game.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ServerSocket server;
    private volatile Socket currentSocket;
    private ArrayList<Socket> sockets = new ArrayList<Socket>();
       
    public Server() {
        Thread getClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                try {
                    while (running) {
                        if (currentSocket == null)
                            currentSocket = server.accept();
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
        Thread getFromClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                while (running) {
                    if (currentSocket != null) {
                        sockets.add(currentSocket);
                        currentSocket = null;
                    }
                    for (Socket s : sockets) {
                        try {
                            byte[] b = new byte[DataPacket.MAX_SIZE];
                            s.getInputStream().read(b,0,DataPacket.MAX_SIZE);
                            DataPacket packet = new DataPacket(b);
                            packet.updateEnemy();
                        } catch (IOException e) {
                            System.out.println("Error: " + e);
                        }
                    }
                }
            }
            public void kill() {
                running = false;
            }
        });
        getFromClients.start();
        Thread sendToClients = new Thread(new Runnable() {
            private volatile boolean running = true;
            public void run() {
                while (running) {
                    for (Socket s : sockets) {
                        // Send data here.
                    }
                }
            }
            public void kill() {
                running = false;
            }
        });
        sendToClients.start();
    }
    
    @Override
    public void run() {
        while(true) {
            
        }
    }
}
