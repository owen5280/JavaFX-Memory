package memory.model;

import java.util.Random;

/**
 * Implements a simple game of Memory.
 */
public class Memory {

    // creates a memory observer for the main memory class
    private MemoryObserver observer;

    // a setOnChange method for the memory buttons in the MemoryGUI
    public void setOnChange(MemoryObserver observer){
        this.observer = observer;
    }

    // a notifyObserver function for any changes that are made to the main memory game
    private void notifyObserver(Card card, int col, int row) throws MemoryException{
        if(observer != null){
            observer.memoryChanged(card, col, row);
        }
    }

    /**
     * Used to shuffle the cards in the deck before playing them on the board.
     */
    private static final Random RNG = new Random();

    /**
     * The default symbols used for the cards in the game if no other symbols
     * are provided.
     */
    private static final char[] DEFAULT_SYMBOLS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * The deck used to play the game. It will contain two cards for each 
     * unique symbol.
     */
    private final Card[] board;

    /**
     * The number of columns on the board.
     */
    private final int cols;

    /**
     * The number of rows on the board.
     */
    private final int rows;

    /**
     * The number of unique cards on the board.
     */
    private final int pairs;

    /**
     * Used to keep track of the number of matches.
     */
    private int score;
    
    /**
     * Used to keep track of the number of moves that have been made.
     */
    private int moves;

    /**
     * The first move made this round.
     */
    private Move move1;


    /**
     * The second move made this round.
     */
    public Move move2;

    /**
     * Creates a new Memory game using a deck played into the specified number
     * of rows and columns. The cards will use the provided symbols.
     * 
     * @param cols The number of columns on the board.
     * @param rows The number of rows on the board.
     * @param symbols The symbols used on the faces of the cards.
     * 
     * @throws MemoryException If the provided configuration is invalid for a
     * game of memory.
     */
    public Memory(int cols, int rows, char[] symbols) throws MemoryException {
        this.cols = cols;
        this.rows = rows;

        int cardCount = rows * cols;
        this.pairs = cardCount / 2;

        if(cardCount % 2 != 0) {
            throw new MemoryException("Can't play memory with an odd number of cards.");
        } else if(symbols.length < pairs) {
            throw new MemoryException(cardCount + " symbols needed for a "
                + cols + "x" + rows + " board, but only " 
                + symbols.length + " provided.");
        }

        board = new Card[cardCount];
        for(int i=0; i<pairs; i++) {
            board[i] = new Card(symbols[i]);
            board[i + pairs] = new Card(symbols[i]);
        }
        shuffle(board);

        this.score = 0;
        this.moves = 0;
    }

    /**
     * Creates a new Memory game using a deck played into the specified number
     * of rows and columns. The cards will use the default symbols.
     * 
     * @param cols The number of columns on the board.
     * @param rows The number of rows on the board.
     * 
     * @throws MemoryException If the provided configuration is invalid for a
     * game of memory.
     */
    public Memory(int cols, int rows) throws MemoryException {
        this(cols, rows, DEFAULT_SYMBOLS);
    }
    
    /**
     * Returns the number of columns on the board.
     * 
     * @return The number of columns on the board.
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Returns the number of rows on the board.
     * 
     * @return The number of rows on the board.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Returns the number of moves made in this game - this is the number of
     * times any single card has been flipped.
     * 
     * @return The number of moves made in this game.
     */
    public int getMoves() {
        return this.moves;
    }

    /**
     * Returns the player's score. This is the number of matches that the
     * player has made so far.
     * 
     * @return The player's score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns true if the game is over - that is to say that all possible
     * matches have been made and the board is cleared.
     * 
     * @return True if the game is over, and false otherwise.
     */
    public boolean isGameOver() {
        return this.score == this.pairs;
    }

    /**
     * Gets the card at the specified column and row.
     * 
     * @param col The column of the desired card.
     * @param row The row of the desired card.
     * 
     * @return The card at the specified column and row. This may be null!
     * 
     * @exception MemoryException If the column or row is invalid.
     */
    public Card getCard(int col, int row) throws MemoryException {
        if(col < 0 || col >= this.cols) {
            throw new MemoryException("Invalid column: " + col);
        } else if(row < 0 || row >= this.rows) {
            throw new MemoryException("Invalid row: " + row);
        }

        int index = col * this.rows + row;
        return board[index];
    }

    /**
     * Flips the card at the specified location.
     * 
     * @param col The column of the card to flip.
     * @param row The row of the card to flip.
     * 
     * @throws MemoryException If the column or row are invalid or if the card
     * is already face up.
     */
    public void flip(int col, int row) throws MemoryException {
        Card card = getCard(col, row);

        // gids rid of any end game MemoryExceptions and prints options to retarts a new game or quit
        if(this.isGameOver() == true){
            throw new MemoryException("You Won! Press \"Restart\" to restart or \"Quit\" to quit.");
        }else if(card == Card.NULL_CARD) {
            throw new MemoryException("Can't play a card that has been removed.");
        } else if(card.isFaceUp()) {
            throw new MemoryException("Can't flip a card that is already face up!");
        } 

        card.flip();
        moves++;
        Move move = new Move(col, row, card);
        
        if(move1 == null) {
            move1 = move;
        } else if(move2 == null) {
            move2 = move;
            if(move1.matches(move2)) {
                score++;
            }
        } else {
            if(move1.matches(move2)) {
                removeCard(move1);
                removeCard(move2);
            } else {
                placeCardFaceDown(move1);
                placeCardFaceDown(move2);
            }
            move1 = move;
            move2 = null;
        }
        // noitifies the observer after a flip
        notifyObserver(card, col, row);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int row=0; row<this.rows; row++) {
            for(int col=0; col<this.cols; col++) {
                int index = col * this.rows + row;
                Card card = this.board[index];
                // this uses the ternary operator - look it up!
                builder.append(card != Card.NULL_CARD ? card : "   ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Helper function that shuffles a board.
     * 
     * @param board The board to shuffle.
     */
    private static void shuffle(Card[] board) {
        for(int i=0; i<board.length; i++) {
            int j = RNG.nextInt(board.length);
            Card temp = board[i];
            board[i] = board[j];
            board[j] = temp;
        }
    }

    /**
     * Replaces a card on the board with the null card.
     * 
     * @param move The move that represents the card that should be removed.
     */
    private void removeCard(Move move) throws MemoryException{
        if(move != null) {
            int col = move.getCol();
            int row = move.getRow();
            int index = col * this.rows + row;
            this.board[index] = Card.NULL_CARD;
            // notifies the observer after a card is removed
            notifyObserver(this.board[index], col, row);
        }
    }

    /**
     * Caled when a card is placed face down after a match fails.
     * 
     * @param move The move that contains the location of the card.
     */
    private void placeCardFaceDown(Move move) throws MemoryException{
        move.placeCardFaceDown();
        // notifies an observer after a card is put face down
        notifyObserver(move.getCard(), move.getCol(), move.getRow());
    }
}
