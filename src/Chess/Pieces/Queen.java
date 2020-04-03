package Chess.Pieces;

import Chess.Board.Move;
import Chess.Pieces.Enums.PieceColor;
import Chess.Pieces.Enums.PieceType;

public class Queen extends ChessPiece {

    public Queen(PieceColor color) {
        super(PieceType.Queen, color, validMoves(), true);
    }


    private static Move[] validMoves() {
        return new Move[]{new Move(1, 0, false, false), new Move(0, 1, false, false),
                new Move(-1, 0, false, false), new Move(0, -1, false, false),
                new Move(1, 1, false, false), new Move(1, -1, false, false),
                new Move(-1, 1, false, false), new Move(-1, -1, false, false)};
    }
}
