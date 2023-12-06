package pieces;

import main.Board;

import java.awt.*;

public class Bishop extends Piece{
    public Bishop(Board board, int file,int rank,  boolean isWhite) {
        super(board);
        this.value = 3;
        this.name = "Bishop";
        this.file = file;
        this.rank = rank;
        this.xPos = file*board.tileSize;
        this.yPos = rank*board.tileSize;
        this.isWhite = isWhite;
        this.sprite = sheet.getSubimage(2*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);
    }
    public boolean isValidMovement(int file, int rank) {
        return Math.abs(file - this.file) == Math.abs(rank - this.rank);
    }
    public boolean moveCollidesWithPiece(int file, int rank) {
        //up left
        if (this.file > file && this.rank > rank)
            for (int f = this.file - 1, r = this.rank - 1; f > file && r > rank; f--, r--)
                if (board.getPiece(f, r) != null)
                    return true;
        //up right
        if (this.file < file && this.rank > rank)
            for (int f = this.file + 1, r = this.rank - 1; f < file && r > rank; f++, r--)
                if (board.getPiece(f, r) != null)
                    return true;
        //down left
        if (this.file > file && this.rank < rank)
            for (int f = this.file - 1, r = this.rank + 1; f > file && r < rank; f--, r++)
                if (board.getPiece(f, r) != null)
                    return true;
        //down right
        if (this.file < file && this.rank < rank)
            for (int f = this.file + 1, r = this.rank + 1; f < file && r < rank; f++, r++)
                if (board.getPiece(f, r) != null)
                    return true;
        return false;


    }

}
