package memory.model;

/**
 * A checked exception that may be thrown by a Memory game.
 */
public class MemoryException extends Exception {
    /**
     * Creates a new MemoryException with the specified message.
     * 
     * @param message The message describing the error that caused the 
     * exception.
     */
    public MemoryException(String message) {
        super(message);
    }
}
