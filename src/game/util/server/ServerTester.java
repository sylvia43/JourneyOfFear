package game.util.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ServerTester {
    
    public static int portNumber = 9999;
    public static String ipaddress = "0.0.0.0";
    
    public static Socket socket;
    
    public static void main(String[] args) {
        PrintStream out;
        BufferedReader in;
        
        BufferedReader consoleIn;
        
        String consoleInputLine = "";
        String lineFromServer = "";
        
        try {
            System.out.println("Attempting to connect to server.");
            
            socket = new Socket(ipaddress,portNumber);
            
            System.out.println("Connected to Server.");
                        
            consoleIn = new BufferedReader(new InputStreamReader(System.in));
            
            int x = 0;
            int y = 0;
            int id = 0;
            
            byte[] data = new byte[12];
            
            while (!consoleInputLine.equals("q")) {
                consoleInputLine = consoleIn.readLine();
                
                if (consoleInputLine.equals("q"))
                    break;
                
                x = Integer.valueOf(consoleInputLine.substring(0,3));
                y = Integer.valueOf(consoleInputLine.substring(3,6));
                id = Integer.valueOf(consoleInputLine.substring(6,9));
                
                data = ByteBuffer.allocate(12).putInt(x).putInt(y).putInt(id).array();
                
                System.out.println("Sending data: " + Arrays.toString(data));
                
                socket.getOutputStream().write(data);
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
            
            System.out.println("Fatal: Aborting.");
            
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Enter legit input plz.");
        }
    }
}