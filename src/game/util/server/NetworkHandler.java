package game.util.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class NetworkHandler {

    private Socket socket;
    private int socketCounter = 0;
    private Thread get;
    private Thread send;
    private volatile boolean running = true;

    public NetworkHandler(String ip, int port) {
        try {
            socket = new Socket(ip,port);
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return;
        }
        
        get = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataPacket packet;
                    byte[] b = new byte[DataPacket.MAX_SIZE];
                    
                    while (running) {
                        if (socket.getInputStream().read(b,0,DataPacket.MAX_SIZE)
                                !=DataPacket.MAX_SIZE)
                            break;
                        System.out.println("Read data: " + Arrays.toString(b));
                        packet = new DataPacket(b);
                        packet.updateEnemy();
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (!socket.isClosed()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Failed to close socket: " + e);
                        }
                    }
                }
            }
        });
        get.start();
        
        send = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (running) {
                        socket.getOutputStream().write(DataPacket.player.getPacket().getBytes());
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } finally {
                    if (!socket.isClosed()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Failed to close socket: " + e);
                        }
                    }
                }
            }
        });
        send.start();
    }
    
    public void close() {
        running = false;
    }
}
