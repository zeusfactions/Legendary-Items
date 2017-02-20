package Exceptions;

public class DependencyNotFoundException extends Exception{

	public DependencyNotFoundException() {
        super("A required dependency for this plugin");
    }

    public DependencyNotFoundException(Exception e) {
        super("A required dependency for this plugin", e);
    }

    public DependencyNotFoundException(String msg) {
        super(msg);
    }

    public DependencyNotFoundException(String msg, Exception e) {
        super(msg, e);
    }
    
}

