package memory.model;

/**
 * Used to keep track of a move in a game of Memory.
 */
public class Move {
    /**
     * The column location of the card that was flipped.
     */
    private final int col;

    /**
     * The row location of the card that was flipped.
     */
    private final int row;

    /**
     * The card that was flipped.
     */
    private final Card card;

    /**
     * Keeps track of a move that was made in a game of memory.
     * 
     * @param col The column location of the card that was flipped.
     * @param row The row location of the card that was flipped.
     * @param card The card that was flipped.
     */
    public Move(int col, int row, Card card) {
        this.col = col;
        this.row = row;
        this.card = card;
    }

    /**
     * Returns the column location of the card that was flipped.
     * 
     * @return The column location of the card that was flipped.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Returns the row location of the card that was flipped.
     * 
     * @return The for location of the card that was flipped.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the card that was flipped.
     * 
     * @return The card that was flipped.
     */
    public Card getCard() {
        return this.card;
    }

    /**
     * Returns true if the cards in both moves match. A move cannot match 
     * itself.
     * 
     * @param move The other move.
     * 
     * @return True if the cards match, and false otherwise.
     */
    public boolean matches(Move move) {
        return this != move && this.card.equals(move.card);
    }

    /**
     * Places the card face down if it is not already.
     */
    public void placeCardFaceDown() throws MemoryException{
        if(this.card.isFaceUp()) {
            this.card.flip();
        }
    }

    @Override
    public String toString() {
        return "Move{"
            + this.card
            + ", col=" + this.col
            + ", row=" + this.row
            + "}";
    }
}
