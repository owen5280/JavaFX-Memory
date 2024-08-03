package memory.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import memory.model.Card;
import memory.model.Memory;
import memory.model.MemoryException;

public class MemoryGUI extends Application{
    private static final String IMAGE_PATH = "../media/images/memory/";   // CHANGE SOURCE FILE AS NEEDED
    private static final int memory_cols = 4;
    private static final int memory_rows = 5;
    private Button[][] buttons = new Button[memory_cols][memory_rows];
    private Card[] cards = {
        new Card('A', IMAGE_PATH + "goat00.png"),
        new Card('B', IMAGE_PATH + "goat01.png"),
        new Card('C', IMAGE_PATH + "goat02.png"),
        new Card('D', IMAGE_PATH + "goat03.png"),
        new Card('E', IMAGE_PATH + "goat04.png"),
        new Card('F', IMAGE_PATH + "goat05.png"),
        new Card('G', IMAGE_PATH + "goat06.png"),
        new Card('H', IMAGE_PATH + "goat07.png"),
        new Card('I', IMAGE_PATH + "goat08.png"),
        new Card('J', IMAGE_PATH + "goat09.png"),
        new Card('K', IMAGE_PATH + "troll01.png"),
        new Card('L', IMAGE_PATH + "troll02.png"),
        new Card('M', IMAGE_PATH + "troll03.png"),
        new Card('N', IMAGE_PATH + "troll04.png"),
    };
    private char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'};
    private String BACK = IMAGE_PATH + "back.png";
    private String WOOD = IMAGE_PATH + "wood.png";
    private Memory memory;
    private Label statusLable;
    private Label scoreVal;
    private Label movesVal;

    @Override
    public void start(Stage stage) throws Exception, MemoryException{
        memory = new Memory(memory_cols, memory_rows, chars);
        BorderPane pane = new BorderPane();

        // center pane
        GridPane center = new GridPane();
        for(int col = 0; col < memory.getCols(); col++){
            for(int row = 0; row < memory.getRows(); row++){
                Button button = makeMemoryButton(col, row);
                center.add(button, col, row);
            }
        }
        pane.setCenter(center);

        // left pane
        GridPane left = new GridPane();
        scoreVal = makeLabel(String.valueOf(memory.getScore()), 50, Color.BLACK, Color.PINK);
        Label score = makeLabel("Score", 27, Color.BLACK, Color.PINK);
        movesVal = makeLabel(String.valueOf(memory.getMoves()),50,  Color.BLACK, Color.LIGHTGREEN);
        Label moves = makeLabel("Moves", 27, Color.BLACK, Color.LIGHTGREEN);
        left.add(score, 0, 1);
        left.add(scoreVal, 0, 2, 5, 1);
        left.add(moves, 0, 3);
        left.add(movesVal, 0, 4, 5, 1);
        pane.setLeft(left);
        
        // botton pane
        GridPane bottom = new GridPane();
        statusLable = makeLabel("Status: None!", 11, Color.BLACK, Color.LIGHTBLUE);
        statusLable.setAlignment(Pos.CENTER_LEFT);
        statusLable.setPadding(new Insets(5));
        statusLable.setMinSize(473, 40);
        statusLable.setMaxSize(473, 40);

        Button restart = makeButton("Restart");
        Button quit = makeButton("Quit");
        restart.setOnAction(e -> {try {start(stage);} catch (MemoryException me) {} catch (Exception e1) {}});
        quit.setOnAction(e -> stage.close());

        bottom.add(statusLable, 1, 0, 10, 1);
        bottom.add(restart, 11, 0);
        bottom.add(quit, 12, 0);
        pane.setBottom(bottom);

        // initialization
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Memory!");
        stage.show();
    }

    // makes a button for the memory grid
    private Button makeMemoryButton(int col, int row) throws MemoryException{
        Button button = new Button();
        button.setBackground(new Background(new BackgroundImage(
            new Image(BACK),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT
        )));
        button.setMinSize(108, 108);
        button.setPadding(Insets.EMPTY);
        buttons[col][row] = button;

        Card card = memory.getCard(col, row);
        updateMemory(button, card, col, row);

        MemoryMoveMaker moveMaker = new MemoryMoveMaker(this, col, row);
        button.setOnAction(moveMaker);

        MemoryChanger changer = new MemoryChanger(button, this);
        memory.setOnChange(changer);

        return button;
    }

    // makes a regular button for restart and quit
    private static Button makeButton(String text){
        Button button = new Button(text);
        button.setMinSize(70, 40);
        button.setPadding(Insets.EMPTY);
        button.setStyle("-fx-background-color: #ff7070; -fx-text-fill: black; -fx-border-color: #d14f4f;");
        return button;
    }

    // makes a lable for score, moves, and status
    public Label makeLabel(String text, int font_size, Color textColor, Color backgroundColor){
        Label label = new Label(text);
        label.setTextFill(textColor);
        label.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(0), Insets.EMPTY)));
        label.setFont(new Font("Courier New", font_size));
        label.setMaxSize(180, 135);
        label.setMinSize(180, 135);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    // update memory method. changes pictures as called for by main memory method
    public void updateMemory(Button button, Card card, int col, int row){
        if(card == Card.NULL_CARD){
            buttons[col][row].setGraphic(new ImageView(WOOD));
        }else if(card.isFaceUp() == true){
            for(int i = 0; i < this.cards.length; i++){
                if(this.cards[i].getSymbol() == card.getSymbol()){
                    buttons[col][row].setGraphic(new ImageView(this.cards[i].getPath()));
                    break;
                }
            }
        }else if(card.isFaceUp() == false){
            buttons[col][row].setGraphic(new ImageView(BACK));
        } 
    }

    // attempts to makes a move everytime a memory button is pressed 
    public void makeMove(int col, int row){ 
        try{
            memory.flip(col, row);
            scoreVal.setText(String.valueOf(memory.getScore()));
            movesVal.setText(String.valueOf(memory.getMoves()));
            if(memory.isGameOver() == true){
                memory.flip(col, row);
                statusLable.setText("Status: You Won! Press \"Restart\" to restart or \"Quit\" to quit.");
            }
        }catch(MemoryException me){
            statusLable.setText("Status: " + me.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }  
}