package main;

import pieces.Piece;

public class CheckScanner {
    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    public boolean isKingChecked(Move move) {

        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingFile = king.file;
        int kingRank = king.rank;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingFile = move.newFile;
            kingRank = move.newRank;
        }


        return hitByRook(move.newFile, move.newRank, king, kingFile, kingRank, 0, 1) || //up
                hitByRook(move.newFile, move.newRank, king, kingFile, kingRank, 1, 0) || //right
                hitByRook(move.newFile, move.newRank, king, kingFile, kingRank, 0, -1) || //down
                hitByRook(move.newFile, move.newRank, king, kingFile, kingRank, -1, 0) || //left

                hitByBishop(move.newFile, move.newRank, king, kingFile, kingRank, -1, -1) || //up left
                hitByBishop(move.newFile, move.newRank, king, kingFile, kingRank, 1, -1) || //up right
                hitByBishop(move.newFile, move.newRank, king, kingFile, kingRank, 1, 1) || //down right
                hitByBishop(move.newFile, move.newRank, king, kingFile, kingRank, -1, 1) || //down left

                hitByKnight(move.newFile, move.newRank, king, kingFile, kingRank) ||
                hitByPawn(move.newFile, move.newRank, king, kingFile, kingRank) ||
                hitByKing(king, kingFile, kingRank);

    }

    private boolean hitByRook(int file, int rank, Piece king, int kingFile, int kingRank, int fileValue, int rankValue) {
        for (int i = 1; i < 8; i++) {
            if (kingFile + (i * fileValue) == file && kingRank + (i * rankValue) == rank) {
                break;
            }
            Piece piece = board.getPiece(kingFile + (i * fileValue), kingRank + (i * rankValue));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int file, int rank, Piece king, int kingFile, int kingRank, int fileValue, int rankValue) {
        for (int i = 1; i < 8; i++) {
            if (kingFile - (i * fileValue) == file && kingRank - (i * rankValue) == rank) {
                break;
            }
            Piece piece = board.getPiece(kingFile - (i * fileValue), kingRank - (i * rankValue));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int file, int rank, Piece king, int kingFile, int kingRank) {

        return checkKnight(board.getPiece(kingFile - 1, kingRank - 2), king, file, rank) ||
                checkKnight(board.getPiece(kingFile + 1, kingRank - 2), king, file, rank) ||
                checkKnight(board.getPiece(kingFile + 2, kingRank - 1), king, file, rank) ||
                checkKnight(board.getPiece(kingFile + 2, kingRank + 1), king, file, rank) ||
                checkKnight(board.getPiece(kingFile + 1, kingRank + 2), king, file, rank) ||
                checkKnight(board.getPiece(kingFile - 1, kingRank + 2), king, file, rank) ||
                checkKnight(board.getPiece(kingFile - 2, kingRank + 1), king, file, rank) ||
                checkKnight(board.getPiece(kingFile - 2, kingRank - 1), king, file, rank);
    }

    private boolean checkKnight(Piece p, Piece k, int file, int rank) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.file == file && p.rank == rank);
    }

    private boolean hitByKing(Piece king, int kingFile, int kingRank) {
        return checkKing(board.getPiece(kingFile - 1, kingRank - 1), king) ||
                checkKing(board.getPiece(kingFile + 1, kingRank - 1), king) ||
                checkKing(board.getPiece(kingFile, kingRank - 1), king) ||
                checkKing(board.getPiece(kingFile - 1, kingRank), king) ||
                checkKing(board.getPiece(kingFile + 1, kingRank), king) ||
                checkKing(board.getPiece(kingFile - 1, kingRank + 1), king) ||
                checkKing(board.getPiece(kingFile + 1, kingRank + 1), king) ||
                checkKing(board.getPiece(kingFile, kingRank + 1), king);
    }

    private boolean checkKing(Piece p, Piece k) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int file, int rank, Piece king, int kingFile, int kingRank) {
        int colorVal = king.isWhite ? -1 : +1;
        return checkPawn(board.getPiece(kingFile + 1, kingRank + colorVal), king, file, rank) ||
                checkPawn(board.getPiece(kingFile - 1, kingRank + colorVal), king, file, rank);
    }

    private boolean checkPawn(Piece p, Piece k, int file, int rank) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && p.file == file && p.rank == rank;
    }
    //checkmate
    public boolean isCheckMate(boolean isWhite){
        Piece king = board.findKing(isWhite);
        assert king != null;
        int kingFile = king.file;
        int kingRank = king.rank;
        if (king.isFirstMove){
            return false;
        }
        if (board.checkScanner.isKingChecked(new Move(board,king, kingFile + 1, kingRank)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile + 1, kingRank + 1)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile, kingRank + 1)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile - 1, kingRank + 1)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile - 1, kingRank)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile - 1, kingRank - 1)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile, kingRank - 1)) &&
                board.checkScanner.isKingChecked(new Move(board,king, kingFile + 1, kingRank - 1))){
            System.out.println("Stalemate");
            return true;
        }
        for (Piece piece : board.pieceList){
            if (piece.isWhite == isWhite){
                for (int i = 0; i < board.files; i++){
                    for (int j = 0; j < board.ranks; j++){
                        if (piece.isValidMovement(i,j) && !board.checkScanner.isKingChecked(new Move(board,piece, i, j))){
                            System.out.println("Not Checkmate");
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("Checkmate");
        return true;

    }

}
