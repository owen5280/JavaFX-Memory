package memory.view;

import javafx.scene.control.Button;
import memory.model.Card;
import memory.model.MemoryObserver;

public class MemoryChanger implements MemoryObserver {

    private final Button button;
    private final MemoryGUI memorygui;

    public MemoryChanger(Button button, MemoryGUI memorygui){
        this.button = button;
        this.memorygui = memorygui;
    }

    @Override
    public void memoryChanged(Card card, int col, int row) {
        memorygui.updateMemory(button, card, col, row);        
    }
}