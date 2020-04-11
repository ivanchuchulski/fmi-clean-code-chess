import Chess.Board.BoardCoordinate;
import Chess.Board.ChessGame;
import Chess.Pieces.PieceEnums.PieceColor;
import Console.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoPlayerChessGameTest {
    private static InputHandler inputHandler = new InputHandler();
    private static BoardDisplay boardDisplay = new BoardDisplay();
    private static ChessGame game = new ChessGame();

    @Before
    public void setup() {
        inputHandler = new InputHandler();
        boardDisplay = new BoardDisplay();
        game = new ChessGame();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testNewGameIsNotFinished() {
        assertTrue("Game shouldn't start finished", !game.isFinished());
    }

    @Test
    public void testFoolsMate() {
        String[] foolsMateMoves = new String[]{"F2-F3", "E7-E6", "G2-G4", "D8-H4"};

        for (String move : foolsMateMoves) {
            BoardCoordinate from = inputHandler.getSource(move);
            BoardCoordinate to = inputHandler.getDestination(move);

            boolean movePlayed = game.playMove(from, to);
            if (!movePlayed) {
                fail("Should be legal move");
            }
        }

        boardDisplay.printBoard(game.getBoard());

        assert (game.isFinished() && game.getLastPlayedMovePieceColor().equals(PieceColor.Black));
    }

    @Test
    public void testScholarsMate() {
        String[] scholarsMateMoves = new String[]{"E2-E4", "E7-E5", "F1-C4", "B8-C6", "D1-H5", "G8-F6", "H5-F7"};

        for (String move : scholarsMateMoves) {
            BoardCoordinate from = inputHandler.getSource(move);
            BoardCoordinate to = inputHandler.getDestination(move);

            boolean movePlayed = game.playMove(from, to);
            if (!movePlayed) {
                fail("Should be legal move");
            }
        }

        boardDisplay.printBoard(game.getBoard());

        assert (game.isFinished() && game.getLastPlayedMovePieceColor().equals(PieceColor.White));
    }

    @Test
    public void testFirstMovePawn() {
        BoardCoordinate location = inputHandler.parse("A2");

        assert (game.isFirstMoveForPawn(location, game.getBoard()));
    }

    @Test
    public void testNotFirstMovePawn() {
        String move = "A2-A3";
        BoardCoordinate source = inputHandler.getSource(move);
        BoardCoordinate destination = inputHandler.getDestination(move);

        game.playMove(source, destination);

        assert (!game.isFirstMoveForPawn(destination, game.getBoard()));
    }
}