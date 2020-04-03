package Chess.Pieces;

import Chess.Board.Move;
import Chess.Pieces.Enums.PieceColor;
import Chess.Pieces.Enums.PieceType;

public abstract class ChessPiece {
    private final PieceType type;
    private final PieceColor color;
    private final Move[] moves;
    private final String name;
    private final char charValue;
    private final boolean repeatableMoves;

    protected ChessPiece(PieceType type, PieceColor color, Move[] moves, boolean repeatableMoves) {
        this.type = type;
        this.color = color;
        this.moves = moves;
        this.repeatableMoves = repeatableMoves;
        name = type.name();
        charValue = type.name().trim().charAt(0);
    }

    public Move[] getMoves() {
        return moves;
    }

    public String getName() {
        return name;
    }

    public PieceColor getColor() {
        return color;
    }

    public char getCharValue() {
        return charValue;
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
