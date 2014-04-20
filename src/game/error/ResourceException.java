package game.error;

public class ResourceException extends RuntimeException {
    public ResourceException() { super(); }
    public ResourceException(String s) { super(s); }
    public ResourceException(String s, Throwable throwable) { super(s, throwable); }
    public ResourceException(Throwable throwable) { super(throwable); }
}
