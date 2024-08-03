package memory.model;

// creates a new memory observer for the game Memroy
public interface MemoryObserver {

    public void memoryChanged(Card card, int col, int row);
    
}
