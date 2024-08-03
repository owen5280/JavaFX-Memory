package memory.view;

import java.util.Scanner;

import memory.model.Memory;
import memory.model.MemoryException;

public class MemoryCLI {
    private static final String NL = System.getProperty("line.separator");

    public static void main(String[] args) throws MemoryException {
        int cols = 4;
        int rows = 5;
        if(args.length == 2) {
            cols = Integer.parseInt(args[0]);
            rows = Integer.parseInt(args[1]);
        }
        Memory memory = new Memory(cols, rows);

        try(Scanner scanner = new Scanner(System.in)) {
            boolean sentinel = true;
            while(sentinel) {
                System.out.println(memoryToString(memory));
                System.out.print(">> ");
                String command = scanner.nextLine();
                String[] tokens = command.split(" ");
                if(tokens.length == 0) {
                    System.out.println("Please enter a command.");
                    help();
                } else {
                    switch(tokens[0]) {
                        case "quit":
                            sentinel = !areYouSure(scanner);
                            break;
                        case "move":
                            move(tokens, memory);
                            sentinel = !memory.isGameOver();
                            break;
                        case "help":
                            help();
                            break;
                        default:
                            invalid(command);
                    }
                }
            }
            if(memory.isGameOver()) {
                System.out.println(memoryToString(memory));
                System.out.println("You won!");
            } else {
                System.out.println("Better luck next time, champ.");
            }
        }
    }

    /**
     * Prints a help message with the available commands.
     */
    private static void help() {
        System.out.println("Available commands: ");
        System.out.println("  help - displays this message");
        System.out.println("  move C R - makes a move in the specified row and column.");
        System.out.println("  quit - quits the game");
        System.out.println();
    }

    /**
     * Prompts the user to ask if they are sure.
     * @param scanner The scanner used to read the user response.
     * @return True if the user indicated that they are sure.
     */
    private static boolean areYouSure(Scanner scanner) {
        System.out.print("Are you sure? (y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    private static void move(String[] tokens, Memory memory) {
        if(tokens.length != 3) {
            System.err.println("Invalid move! Please try again.");
        } else {
            try {
                int col = Integer.parseInt(tokens[1]);
                int row = Integer.parseInt(tokens[2]);
                memory.flip(col, row);
            } catch(MemoryException me) {
                System.err.println(me.getMessage());
            } catch(NumberFormatException nfe) {
                System.err.println("Column and row must be integers.");
            }
        }
    }

    /**
     * Displays an invalid command message.
     *
     * @param command The invalid command.
     */
    private static void invalid(String command) {
        System.out.println("Invalid command: " + command);
        help();
    }

    private static String memoryToString(Memory memory) {
        StringBuilder builder = new StringBuilder();

        builder.append("Moves: " + memory.getMoves() + NL);
        builder.append("Score: " + memory.getScore() + NL);
        builder.append(NL);
        builder.append(memory);
        builder.append(NL);

        return builder.toString();
    }
}
