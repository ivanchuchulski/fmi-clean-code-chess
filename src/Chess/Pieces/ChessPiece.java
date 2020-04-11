package Chess.Pieces;

import Chess.Board.Move;
import Chess.Pieces.PieceEnums.PieceColor;
import Chess.Pieces.PieceEnums.PieceType;

public abstract class ChessPiece {
    private final PieceType type;
    private final PieceColor color;
    private final Move[] moves;
    private final boolean repeatableMoves;

    protected ChessPiece(PieceType type, PieceColor color, Move[] moves, boolean repeatableMoves) {
        this.type = type;
        this.color = color;
        this.moves = moves;
        this.repeatableMoves = repeatableMoves;
    }

    public Move[] getMoves() {
        return moves;
    }
    
    public char getSign() {
        return type.name().charAt(0);
    }

    public char getBoardColor() {
        return color.name().toLowerCase().charAt(0);
    }

    public PieceColor getColor() {
        return color;
    }



    public boolean hasRepeatableMoves() {
        return repeatableMoves;
    }

    public PieceType getPieceType() {
        return type;
    }

    public static PieceColor opponent(PieceColor color) {
        return (color == PieceColor.Black) ? PieceColor.White : PieceColor.Black;
    }

}
