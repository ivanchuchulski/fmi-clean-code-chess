package Chess.Board;

import Chess.Pieces.ChessPiece;
import Chess.Pieces.PieceEnums.PieceColor;
import Chess.Pieces.PieceEnums.PieceType;

import java.util.ArrayList;

public class ChessGame {
    private final PieceColor FIRST_MOVE_PIECE_COLOR = PieceColor.White;

    private boolean isFinished;
    private PieceColor currentMovePieceColor;
    private final ChessBoard board;

    public ChessGame() {
        isFinished = false;
        currentMovePieceColor = FIRST_MOVE_PIECE_COLOR;
        board = new ChessBoard();
    }

    public void printWinner() {
        if (currentMovePieceColor.equals(PieceColor.White)) {
            System.out.printf("White won this game!%n");
        }
        else {
            System.out.printf("Black won this game!%n");
        }
    }

    public PieceColor getLastPlayedMovePieceColor() {
        return currentMovePieceColor.equals(PieceColor.Black) ? PieceColor.White : PieceColor.Black;
    }

    /**
     * @return returns the current ChessBoard associated with the game.
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * @return returns whether the given ChessGame is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * @return returns true if move was played, false if move was illegal
     */
    public boolean playMove(BoardCoordinate from, BoardCoordinate to) {
        if (isValidMove(from, to, false)) {
            Tile fromTile = board.getBoard()[from.Y()][from.X()];
            ChessPiece pieceToMove = fromTile.getPiece();

            Tile toTile = board.getBoard()[to.Y()][to.X()];
            toTile.setPiece(pieceToMove);

            fromTile.empty();
            setNextMovePieceColor();

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param from         the position from which the player tries to move from
     * @param to           the position the player tries to move to
     * @param hypothetical if the move is hypothetical, we disregard if it sets the from player in check
     * @return a boolean indicating whether the move is valid or not
     */
    private boolean isValidMove(BoardCoordinate from, BoardCoordinate to, boolean hypothetical) {
        Tile fromTile = board.getTileFromTuple(from);
        Tile toTile = board.getTileFromTuple(to);
        ChessPiece fromPiece = fromTile.getPiece();
        ChessPiece toPiece = toTile.getPiece();

        if (isFromPieceNull(fromPiece)) {
            return false;
        }
        else if (!isPieceTheSameColorAsCurrentMovePieceColor(fromPiece)) {
            return false;
        }
        else if (isDestinationSquareOccupiedByPieceOfTheSameColor(fromPiece, toPiece)) {
            return false;
        }
        else if (isValidMoveForPiece(from, to)) {
            //if hypothetical and valid, return true
            if (hypothetical) {
                return true;
            }

            //temporarily play the move to see if it makes us check
            toTile.setPiece(fromPiece);
            fromTile.empty();

            //check that move doesn't put oneself in check
            if (isKingCheck(currentMovePieceColor)) {
                toTile.setPiece(toPiece);
                fromTile.setPiece(fromPiece);
                return false;
            }

            //if mate, finish game
            if (isColorCheckMate(ChessPiece.opponent(currentMovePieceColor))) {
                isFinished = true;
            }

            //revert temporary move
            toTile.setPiece(toPiece);
            fromTile.setPiece(fromPiece);

            return true;
        }
        return false;
    }

     private boolean isFromPieceNull (ChessPiece fromPiece) {
        return fromPiece == null;
    }

    private boolean isPieceTheSameColorAsCurrentMovePieceColor(ChessPiece piece) {
        return piece.getColor() == currentMovePieceColor;
    }

    private boolean isDestinationSquareOccupiedByPieceOfTheSameColor(ChessPiece fromPiece, ChessPiece toPiece) {
        return toPiece != null && isPieceTheSameColorAsCurrentMovePieceColor(toPiece);
    }

    private void setNextMovePieceColor() {
        currentMovePieceColor = (currentMovePieceColor == PieceColor.White) ? PieceColor.Black : PieceColor.White;
    }

    // Checks whether a given move from from one tuple to another is valid.
    private boolean isValidMoveForPiece(BoardCoordinate from, BoardCoordinate to) {
        ChessPiece fromPiece = board.getTileFromTuple(from).getPiece();
        boolean repeatableMoves = fromPiece.hasRepeatableMoves();

        return repeatableMoves
                ? isValidMoveForPieceRepeatable(from, to)
                : isValidMoveForPieceNonRepeatable(from, to);
    }

    // Check whether a given move is valid for a piece without repeatable moves.
    private boolean isValidMoveForPieceRepeatable(BoardCoordinate from, BoardCoordinate to) {
        ChessPiece fromPiece = board.getTileFromTuple(from).getPiece();
        Move[] validMoves = fromPiece.getMoves();

        int xMove = to.X() - from.X();
        int yMove = to.Y() - from.Y();

        for (int i = 1; i <= 7; i++) {
            for (Move move : validMoves) {
                //generally check for possible move
                if (move.x * i == xMove && move.y * i == yMove) {
                    //if move is generally valid - check if path is valid up till i
                    for (int j = 1; j <= i; j++) {
                        Tile tile = board.getTileFromTuple(new BoardCoordinate(from.X() + move.x * j, from.Y() + move.y * j));
                        //if passing through non empty tile return false
                        if (j != i && !tile.isEmpty()) {
                            return false;
                        }

                        //if last move and toTile is empty or holds opponents piece, return true
                        if (j == i && (tile.isEmpty() || tile.getPiece().getColor() != currentMovePieceColor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Check whether a given move is valid for a piece with repeatable moves.
    private boolean isValidMoveForPieceNonRepeatable(BoardCoordinate from, BoardCoordinate to) {
        ChessPiece fromPiece = board.getTileFromTuple(from).getPiece();
        Move[] validMoves = fromPiece.getMoves();
        Tile toTile = board.getTileFromTuple(to);

        int xMove = to.X() - from.X();
        int yMove = to.Y() - from.Y();

        for (Move move : validMoves) {
            if (move.x == xMove && move.y == yMove) {
                if (move.onTakeOnly) {
                    //if move is only legal on take (pawns)
                    if (toTile.isEmpty()) {
                        return false;
                    }
                    else {
                        ChessPiece toPiece = toTile.getPiece();
                        return fromPiece.getColor() != toPiece.getColor();//if different color, valid move
                    }
                    //handling first move only for pawns - they should not have moved yet
                }
                else if (move.firstMoveOnly) {
                    return toTile.isEmpty() && isFirstMoveForPawn(from, board);
                }
                else {
                    return toTile.isEmpty();
                }
            }
        }
        return false;
    }

    // Function that checks if any piece can prevent check for the given color
    // This includes whether the King can move out of check himself.
    private boolean isCheckPreventable(PieceColor color) {
        boolean canPreventCheck = false;
        BoardCoordinate[] locations = board.getAllPiecesLocationForColor(color);

        for (BoardCoordinate location : locations) {
            Tile fromTile = board.getTileFromTuple(location);
            ChessPiece piece = fromTile.getPiece();
            BoardCoordinate[] possibleMoves = validMovesForPiece(piece, location);

            for (BoardCoordinate newLocation : possibleMoves) {
                Tile toTile = board.getTileFromTuple(newLocation);
                ChessPiece toPiece = toTile.getPiece();

                //temporarily play the move to see if it makes us check
                toTile.setPiece(piece);
                fromTile.empty();

                //if we're no longer check
                if (!isKingCheck(color)) {
                    canPreventCheck = true;
                }

                //revert temporary move
                toTile.setPiece(toPiece);
                fromTile.setPiece(piece);
                if (canPreventCheck) { // early out
                    System.out.printf("Prevented with from:" + fromTile + ", to: " + toTile);
                    return canPreventCheck;
                }
            }
        }
        return canPreventCheck;
    }

    private boolean isColorCheckMate(PieceColor color) {
        //if not check, then we're not mate
        if (!isKingCheck(color)) {
            return false;
        }
        else {
            return !isCheckPreventable(color);
        }
    }

    private boolean isKingCheck(PieceColor kingColor) {
        BoardCoordinate kingLocation = board.getKingLocation(kingColor);

        return canOpponentTakeLocation(kingLocation, kingColor);
    }

    private boolean canOpponentTakeLocation(BoardCoordinate location, PieceColor color) {
        PieceColor opponentColor = ChessPiece.opponent(color);
        BoardCoordinate[] piecesLocation = board.getAllPiecesLocationForColor(opponentColor);

        for (BoardCoordinate fromBoardCoordinate : piecesLocation) {
            if (isValidMove(fromBoardCoordinate, location, true)) {
                return true;
            }
        }
        return false;
    }



    // A utility function that gets all the possible moves for a piece, with illegal ones removed.
    // NOTICE: Does not check for counter-check when evaluating legality.
    //         This means it mostly checks if it is a legal move for the piece in terms
    //         of ensuring its not taking one of its own, and within its 'possibleMoves'.
    // Returns the Tuples representing the Tiles to which the given piece
    // can legally move.
    private BoardCoordinate[] validMovesForPiece(ChessPiece piece, BoardCoordinate currentLocation) {
        return piece.hasRepeatableMoves()
                ? validMovesRepeatable(piece, currentLocation)
                : validMovesNonRepeatable(piece, currentLocation);
    }

    // Returns the Tuples representing the Tiles to which the given piece
    // can legally move.
    private BoardCoordinate[] validMovesRepeatable(ChessPiece piece, BoardCoordinate currentLocation) {
        Move[] moves = piece.getMoves();
        ArrayList<BoardCoordinate> possibleMoves = new ArrayList<>();

        for (Move move : moves) {
            for (int i = 1; i < 7; i++) {
                int newX = currentLocation.X() + move.x * i;
                int newY = currentLocation.Y() + move.y * i;

                if (newX < 0 || newX > 7 || newY < 0 || newY > 7)  {
                    break;
                }

                BoardCoordinate toLocation = new BoardCoordinate(newX, newY);
                Tile tile = board.getTileFromTuple(toLocation);
                if (tile.isEmpty()) {
                    possibleMoves.add(toLocation);
                }
                else {
                    if (tile.getPiece().getColor() != piece.getColor()) {
                        possibleMoves.add(toLocation);
                    }
                    break;
                }
            }
        }
        return possibleMoves.toArray(new BoardCoordinate[0]);
    }

    private BoardCoordinate[] validMovesNonRepeatable(ChessPiece piece, BoardCoordinate currentLocation) {
        Move[] moves = piece.getMoves();
        ArrayList<BoardCoordinate> possibleMoves = new ArrayList<>();

        for (Move move : moves) {
            int currentX = currentLocation.X();
            int currentY = currentLocation.Y();
            int newX = currentX + move.x;
            int newY = currentY + move.y;

            if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
                continue;
            }

            BoardCoordinate newLocation = new BoardCoordinate(newX, newY);

            if (isValidMoveForPiece(currentLocation, newLocation))  {
                possibleMoves.add(newLocation);
            }
        }
        return possibleMoves.toArray(new BoardCoordinate[0]);
    }

    // Determine whether the Pawn at 'from' on 'board' has moved yet.
    public boolean isFirstMoveForPawn(BoardCoordinate from, ChessBoard board) {
        Tile tile = board.getTileFromTuple(from);
        if (tile.isEmpty() || tile.getPiece().getPieceType() != PieceType.Pawn) {
            return false;
        }
        else {
            PieceColor color = tile.getPiece().getColor();
            return (color == PieceColor.White)
                    ? from.Y() == 6
                    : from.Y() == 1;
        }
    }
}
