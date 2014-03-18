package game.util.server;

import java.net.InetAddress;
import java.util.Objects;

public class ClientID {
    
    public InetAddress ip;
    public int port;
    
    public ClientID(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClientID))
            return false;
        ClientID other = (ClientID) object;
        return ip.equals(other.ip) && port==other.port;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19*hash+Objects.hashCode(this.ip);
        hash = 19*hash+this.port;
        return hash;
    }
    
    @Override
    public String toString() {
        return ip.toString() + ":" + port;
    }
}