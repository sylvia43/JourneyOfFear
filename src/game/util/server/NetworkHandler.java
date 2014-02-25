package game.util.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkHandler {

    private Socket socket;
    private Thread get;
    private Thread send;
    private volatile boolean running = true;
    private String ip = "";
    private int port = 0;

    public NetworkHandler(String newIp, int newPort) {
        this.ip = newIp;
        this.port = newPort;
        Thread init = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip,port);
                } catch (IOException e) {
                    switch (e.getMessage()) {
                        case "Connection refused: connect":
                            System.out.println("Fatal: No open socket at " + ip + " port " + port + ".");
                            break;
                        default:
                            System.out.println("Fatal Error: " + e);
                            break;
                    }
                    return;
                }

                get = new Thread(new Runnable() {
                    @Override
                    @SuppressWarnings("empty-statement")
                    public void run() {
                        try {
                            byte[] b = new byte[DataPacket.MAX_SIZE];
                            
                            DataInputStream socketIn
                                            = new DataInputStream(socket.getInputStream());
                            while (running) {
                                socketIn.readFully(b,0,DataPacket.MAX_SIZE);
                                new DataPacket(b);
                            }
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Error: " + e);
                        } finally {
                            if (socket != null && !socket.isClosed()) {
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
                            OutputStream socketOut = socket.getOutputStream();
                            while (running) {
                                socketOut.write(DataPacket.player.getPacket().getBytes());
                            }
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Error: " + e);
                        } finally {
                            if (socket != null && !socket.isClosed()) {
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
        });
        init.start();
    }
    
    public void close() {
        running = false;
    }
}
