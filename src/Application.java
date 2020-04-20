import Chess.Board.ChessGame;
import Chess.Board.BoardCoordinate;
import Console.InputHandler;
import Console.BoardDisplay;

import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputHandler inputHandler = new InputHandler();
    private static final BoardDisplay boardDisplay = new BoardDisplay();
    private static final ChessGame chessGame = new ChessGame();

    public static void main(String[] args) {

        do {
            String input = getInput();

            BoardCoordinate from = inputHandler.getFrom(input);
            BoardCoordinate to = inputHandler.getTo(input);

            playMove(from, to);
        } while (!chessGame.isFinished());

        ShowBoard();
        chessGame.printFinalResult();

        scanner.close();
        System.out.println("Game has finished. Thanks for playing.");
    }

    private static void ShowBoard() {
        boardDisplay.showBoard(chessGame.getBoard());
    }

    private static String getInput() {
        String input;
        do {
            ShowBoard();
            System.out.println("Enter move (eg. E2-E4): ");

            input = scanner.nextLine();

            if (inputHandler.isInputValid(input)) {
                return input;
            }
            else {
                System.out.println("error : incorrect input format, please try again");
            }
        } while (true);
    }

    private static void playMove(BoardCoordinate from, BoardCoordinate to) {
        boolean movePlayed = chessGame.playMove(from, to);
        if (!movePlayed) {
            System.out.println("Illegal move!");
        }
    }
}