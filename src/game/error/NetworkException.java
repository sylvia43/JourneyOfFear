package game.error;

/** Represents a networking exception, will halt the program. */
public class NetworkException extends RuntimeException {
    public NetworkException() { super(); }
    public NetworkException(String s) { super(s); }
    public NetworkException(String s, Throwable throwable) { super(s, throwable); }
    public NetworkException(Throwable throwable) { super(throwable); }
}
