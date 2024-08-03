package memory.model;

/**
 * A class that represents a Card in a game of Memory.
 */
public class Card {
    /**
     * The blank character (a space). Not allowed to be used in a game of
     * Memory.
     */
    public static final char NULL_SYMBOL = '\0';

    /**
     * An implementation of the null object pattern - used to represent a
     * card that has been removed from the board.
     */
    public static Card NULL_CARD = new Card();

    /**
     * The string used for a card that is face down.
     */
    private static final String FACE_DOWN = "[ ]";

    /**
     * The symbol on the card.
     */
    private final char symbol;

    /**
     * Indicates whether the card is face up or not.
     */
    private boolean faceUp;

    /**
     * Creates a new Card with the specified symbol that is face down.
     * 
     * @param symbol The symbol on the face of the card.
     * 
     * @throws MemoryException If the symbol is invalid.
     */
    public Card(char symbol) throws MemoryException {
        if(symbol == NULL_SYMBOL) {
            throw new MemoryException("Blank cards are not allowed.");
        }
        this.symbol = symbol;
        this.faceUp = false;
    }

    // holds the path for an image for a card
    public String path;

    // new constructor for a card with an image
    public Card(char symbol, String path) {
        this.symbol = symbol;
        this.faceUp = false;
        this.path = path;
    }  

    // returns the image path of a card
    public String getPath(){
        return this.path;
    }

    /**
     * Not visible outside of the class. Used to create the null card.
     * 
     */
    private Card() {
        this.symbol = NULL_SYMBOL;
        this.faceUp = true;
    }

    /**
     * Returns the symbol on the card.
     * 
     * @return The symbol on this card.
     */
    public char getSymbol() {
        return this.symbol;
    }

    /**
     * Flips the card.
     */
    public void flip() {
        this.faceUp = !this.faceUp;
    }

    /**
     * Returns true if the card is face up, false otherwise.
     * 
     * @return True if the card is face up, false otherwise.
     */
    public boolean isFaceUp() {
        return this.faceUp;
    }


    /**
     * If the card is currently face up, returns a string with the card's
     * symbol in it, e.g. "[A]", otherwise, returns the face down string, 
     * i.e. "[ ]".
     */
    @Override
    public String toString() {
        if(this.faceUp) {
            return "[" + this.symbol + "]";
        } else {
            return FACE_DOWN;
        }
    }

    /**
     * Returns true of the parameter is another Card and has the same symbol.
     * Otherwise returns false.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Card) {
            Card other = (Card)o;
            return this.symbol == other.symbol;
        } else {
            return false;
        }
    }
}
