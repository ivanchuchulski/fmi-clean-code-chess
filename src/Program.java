import Chess.Board.ChessGame;
import Chess.Board.BoardCoordinate;
import Console.InputHandler;
import Console.BoardDisplay;

import java.util.Scanner;

public class Program {
    private static Scanner scanner = new Scanner(System.in);
    private static InputHandler inputHandler = new InputHandler();
    private static BoardDisplay boardDisplay = new BoardDisplay();
    private static ChessGame game = new ChessGame();

    public static void play() {
        boardDisplay.clearConsole();
        boardDisplay.printBoard(game.getBoard());

        do {
            System.out.println("Enter move (eg. A2-A3): ");
            String input = scanner.nextLine();

            if (!inputHandler.isInputValid(input)) {
                System.out.println("Invalid input!");
                System.out.println("Valid input is in form: A2-A3");

                continue;
            }

            BoardCoordinate from = inputHandler.getSource(input);
            BoardCoordinate to = inputHandler.getDestination(input);

            boolean movePlayed = game.playMove(from, to);
            if (!movePlayed) {
                System.out.println("Illegal move!");
                continue;
            }

            boardDisplay.clearConsole();
            boardDisplay.printBoard(game.getBoard());

        } while (!game.isFinished());

        scanner.close();
        System.out.println("Game has finished. Thanks for playing.");
    }
}
