package memory.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MemoryMoveMaker implements EventHandler<ActionEvent> {

    private final MemoryGUI memorygui;
    private final int col;
    private final int row;


    public MemoryMoveMaker(MemoryGUI memorygui, int col, int row){
        this.memorygui = memorygui;
        this.col = col;
        this.row = row;
    }

    @Override
    public void handle(ActionEvent event) {
        memorygui.makeMove(col, row);
        
    }
}