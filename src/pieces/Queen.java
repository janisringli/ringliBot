package pieces;

import main.Board;

public class Queen extends Piece{
    public Queen(Board board, int file,int rank,  boolean isWhite) {
        super(board);
        this.value = 9;
        this.name = "Queen";
        this.file = file;
        this.rank = rank;
        this.xPos = file*board.tileSize;
        this.yPos = rank*board.tileSize;
        this.isWhite = isWhite;
        this.sprite = sheet.getSubimage(1*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, java.awt.Image.SCALE_SMOOTH);
    }
    public boolean isValidMovement(int file, int rank) {
        return this.file == file || this.rank == rank || Math.abs(file - this.file) == Math.abs(rank - this.rank);
    }
    public boolean moveCollidesWithPiece(int file, int rank) {
        if(this.file == file || this.rank == rank){
            int min = Math.min(this.file, file);
            int max = Math.max(this.file, file);
            for(int i = min+1; i < max; i++){
                if(board.getPiece(i, this.rank) != null){
                    return true;
                }
            }
            min = Math.min(this.rank, rank);
            max = Math.max(this.rank, rank);
            for(int i = min+1; i < max; i++){
                if(board.getPiece(this.file, i) != null){
                    return true;
                }
            }
        }else if(Math.abs(file - this.file) == Math.abs(rank - this.rank)){
            int minFile = Math.min(this.file, file);
            int maxFile = Math.max(this.file, file);
            int minRank = Math.min(this.rank, rank);
            int maxRank = Math.max(this.rank, rank);
            for(int i = 1; i < maxFile - minFile; i++){
                if(board.getPiece(minFile + i, minRank + i) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
