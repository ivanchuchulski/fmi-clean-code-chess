package Chess.Board;


import Chess.Board.Enums.TileColor;
import Chess.Pieces.ChessPiece;

public class Tile {
    private ChessPiece piece;
    private final TileColor color;

    public Tile(TileColor color) {
        this.color = color;
    }

    public Tile(TileColor color, ChessPiece piece) {
        this.color = color;
        this.piece = piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public String getValue() {
        if (piece != null) {
            return "[" + piece.getCharValue() + "]";
        } else {
            return "[ ]";
        }
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public void empty() {
        piece = null;
    }
}
