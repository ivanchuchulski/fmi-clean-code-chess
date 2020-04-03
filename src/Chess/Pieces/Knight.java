package Chess.Pieces;

import Chess.Board.Move;
import Chess.Pieces.Enums.PieceColor;
import Chess.Pieces.Enums.PieceType;

public class Knight extends ChessPiece {

    public Knight(PieceColor color) {
        super(PieceType.Knight, color, validMoves(), false);
    }


    private static Move[] validMoves() {
        return new Move[]{new Move(2, 1, false, false), new Move(1, 2, false, false),
                new Move(2, -1, false, false), new Move(-1, 2, false, false),
                new Move(2, -1, false, false), new Move(-1, 2, false, false),
                new Move(-2, 1, false, false), new Move(1, -2, false, false),
                new Move(-2, -1, false, false), new Move(-1, -2, false, false),
                new Move(-2, -1, false, false), new Move(-1, -2, false, false)};
    }
}
