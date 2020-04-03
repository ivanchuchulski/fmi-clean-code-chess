package Chess.Pieces;

import Chess.Board.Move;

public class Rook extends ChessPiece {

    public Rook(PieceColor color) {
        super(PieceType.Rook, color, validMoves(), true);
    }


    private static Move[] validMoves() {
        return new Move[]{new Move(1, 0, false, false), new Move(0, 1, false, false),
                new Move(-1, 0, false, false), new Move(0, -1, false, false)};
    }
}
