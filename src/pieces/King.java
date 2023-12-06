package pieces;

import main.Board;
import main.Move;

public class King extends Piece{
    public King(Board board, int file,int rank,  boolean isWhite) {
        super(board);
        this.value = 1000;
        this.name = "King";
        this.file = file;
        this.rank = rank;
        this.xPos = file*board.tileSize;
        this.yPos = rank*board.tileSize;
        this.isWhite = isWhite;
        this.sprite = sheet.getSubimage(0*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, java.awt.Image.SCALE_SMOOTH);
    }
    public boolean isValidMovement(int file, int rank) {
        return Math.abs((file - this.file) * (rank - this.rank)) == 1 || Math.abs(file - this.file) + Math.abs(rank - this.rank) == 1 || canCastle(file, rank);
    }
    public boolean moveCollidesWithPiece(int file, int rank) {
        return board.getPiece(file, rank) != null;
    }
    public boolean canCastle(int file, int rank){
        if (this.rank == rank){
            if (file == 6) {
                Piece rook = board.getPiece(7, rank);
                if (rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPiece(5, rank) == null &&
                            board.getPiece(6, rank) == null && !board.checkScanner.isKingChecked(new Move(board,this, 5, rank));
                }
            }else if (file == 2){
                Piece rook = board.getPiece(0, rank);
                if (rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPiece(1, rank) == null &&
                            board.getPiece(2, rank) == null &&
                            board.getPiece(3, rank) == null && !board.checkScanner.isKingChecked(new Move(board,this, 3, rank));
                }
            }
        }
        return false;
    }

}
