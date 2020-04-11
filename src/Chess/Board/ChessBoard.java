package Chess.Board;

import Chess.Board.BoardEnums.TileColor;
import Chess.Pieces.ChessPiece;
import Chess.Pieces.*;
import Chess.Pieces.PieceEnums.PieceColor;

import java.util.ArrayList;

public class ChessBoard {
    private final int ROWS_NUM = 8;
    private final int COLUMNS_NUM = 8;
    private final Tile[][] board;

    public ChessBoard() {
        board = new Tile[ROWS_NUM][COLUMNS_NUM];

        initializeBoard();

        fillBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS_NUM; i++) {
            for (int j = 0; j < COLUMNS_NUM; j++) {
                if (j % 2 + i == 0) {
                    board[i][j] = new Tile(TileColor.Black);
                }
                else  {
                    board[i][j] = new Tile(TileColor.White);
                }
            }
        }
    }

    /**
        Initial filler of board
     */
    private void fillBoard() {
        fillPawns();

        fillRooks();

        fillKnights();

        fillBishops();

        fillQueens();

        fillKings();
    }

    private void fillKings() {
        board[0][4].setPiece(new King(PieceColor.Black));
        board[7][4].setPiece(new King(PieceColor.White));
    }

    private void fillQueens() {
        board[0][3].setPiece(new Queen(PieceColor.Black));
        board[7][3].setPiece(new Queen(PieceColor.White));
    }

    private void fillBishops() {
        board[0][2].setPiece(new Bishop(PieceColor.Black));
        board[0][5].setPiece(new Bishop(PieceColor.Black));
        board[7][2].setPiece(new Bishop(PieceColor.White));
        board[7][5].setPiece(new Bishop(PieceColor.White));
    }

    private void fillKnights() {
        board[0][1].setPiece(new Knight(PieceColor.Black));
        board[0][6].setPiece(new Knight(PieceColor.Black));
        board[7][1].setPiece(new Knight(PieceColor.White));
        board[7][6].setPiece(new Knight(PieceColor.White));
    }

    private void fillRooks() {
        board[0][0].setPiece(new Rook(PieceColor.Black));
        board[0][7].setPiece(new Rook(PieceColor.Black));
        board[7][0].setPiece(new Rook(PieceColor.White));
        board[7][7].setPiece(new Rook(PieceColor.White));
    }

    private void fillPawns() {
        for (int i = 0; i < COLUMNS_NUM; i++) {
            board[1][i].setPiece(new Pawn(PieceColor.Black));
            board[6][i].setPiece(new Pawn(PieceColor.White));
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    // Will break on boards with no Kings of 'color'. Should never happen.
    public BoardCoordinate getKingLocation(PieceColor color) {
        BoardCoordinate location = new BoardCoordinate(-1, -1);
        for (int x = 0; x <= 7; x++) {
            for (int y = 0; y <= 7; y++) {
                if (!board[y][x].isEmpty()) {
                    ChessPiece piece = board[y][x].getPiece();
                    if (piece.getColor() == color && piece instanceof King) {
                        location = new BoardCoordinate(x, y);
                    }
                }
            }
        }
        return location;
    }

    public BoardCoordinate[] getAllPiecesLocationForColor(PieceColor color) {
        ArrayList<BoardCoordinate> locations = new ArrayList<>();
        for (int x = 0; x <= 7; x++) {
            for (int y = 0; y <= 7; y++) {
                if (!board[y][x].isEmpty() && board[y][x].getPiece().getColor() == color)
                    locations.add(new BoardCoordinate(x, y));
            }
        }
        return locations.toArray(new BoardCoordinate[0]);//allocate new array automatically.
    }

    public Tile getTileFromTuple(BoardCoordinate boardCoordinate) {
        return board[boardCoordinate.Y()][boardCoordinate.X()];
    }


}
