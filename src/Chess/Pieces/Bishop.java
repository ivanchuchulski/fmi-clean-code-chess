package Chess.Pieces;

import Chess.Board.Move;
import Chess.Pieces.Enums.PieceColor;
import Chess.Pieces.Enums.PieceType;

public class Bishop extends ChessPiece {

    public Bishop(PieceColor color) {
        super(PieceType.Bishop, color, validMoves(), true);
    }


    private static Move[] validMoves() {
        return new Move[]{new Move(1, 1, false, false), new Move(1, -1, false, false),
                new Move(-1, 1, false, false), new Move(-1, -1, false, false)};
    }
}
