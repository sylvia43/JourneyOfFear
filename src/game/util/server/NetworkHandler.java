package game.util.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class NetworkHandler {

    private Socket socket;
    private int socketCounter = 0;

    public NetworkHandler(String ip, int port) {
        try {
            socket = new Socket(ip,port);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        
        Thread get = new Thread(new Runnable() {
            private volatile boolean running = true;
            @Override
            public void run() {
                try {
                    DataPacket packet;
                    byte[] b = new byte[DataPacket.MAX_SIZE];
                    
                    while (running) {
                        if (socket.getInputStream().read(b,0,DataPacket.MAX_SIZE)
                            !=DataPacket.MAX_SIZE)
                            running = false;
                        System.out.println("Read data: " + Arrays.toString(b));
                        packet = new DataPacket(b);
                        packet.updateEnemy();
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            }
        });
        get.start();
        
        Thread send = new Thread(new Runnable() {
            private volatile boolean running = true;
            @Override
            public void run() {
                try {
                    while (true) {
                        socket.getOutputStream().write(DataPacket.player.getPacket().getBytes());
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            }
        });
        send.start();
    }
}
