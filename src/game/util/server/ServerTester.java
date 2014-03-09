package game.util.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ServerTester {
    
    public static int portNumber = 9999;
    public static String ipaddress = "127.0.0.1";
    
    public static MulticastSocket socket;
    public static InetAddress group = null;
    
    public static void main(String[] args) {
        PrintStream out;
        BufferedReader in;
        
        BufferedReader consoleIn;
        
        String consoleInputLine = "";
        String lineFromServer = "";
        
        try {
            System.out.println("Attempting to connect to server.");
            
            socket = new MulticastSocket(portNumber);
            try {
                group = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException e) {
                ServerLogger.log("Error forming group: " + e);
            }
            socket.joinGroup(group);
            
            System.out.println("Connected to Server.");
                        
            consoleIn = new BufferedReader(new InputStreamReader(System.in));
            
            int x = 0;
            int y = 0;
            
            byte[] data = new byte[DataPacket.MAX_SIZE];
            DatagramPacket p = new DatagramPacket(data,data.length);
            while (!consoleInputLine.equals("q")) {
                consoleInputLine = consoleIn.readLine();
                
                if (consoleInputLine.equals("q"))
                    break;
                
                x = Integer.valueOf(consoleInputLine.substring(0,3));
                y = Integer.valueOf(consoleInputLine.substring(3,6));
                
                data = ByteBuffer.allocate(DataPacket.MAX_SIZE).putInt(x).putInt(y).array();
                
                System.out.println("Sending data: " + Arrays.toString(data));
                
                p.setData(data);
                socket.send(p);
            }
            
            System.out.println("Closing Connections.");
            
            socket.close();
            
            System.out.println("Successful Run!");
            
        } catch (IOException e) {
            switch (e.getMessage()) {
                case "Connection refused: connect":
                    System.out.println("Fatal: No open socket at " + ipaddress + " port " + portNumber + ".");
                    break;
                default:
                    System.out.println("Fatal Error: " + e);
                    break;
            }
            e.printStackTrace();
            System.out.println("Fatal: Aborting.");
            
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Enter legit input plz.");
        }
    }
}