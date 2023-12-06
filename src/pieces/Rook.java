package pieces;

import main.Board;

import java.awt.*;

public class Rook extends Piece {
    public Rook(Board board, int file, int rank, boolean isWhite) {
        super(board);
        this.file = file;
        this.rank = rank;
        this.xPos = file * board.tileSize;
        this.yPos = rank * board.tileSize;
        this.isWhite = isWhite;
        this.value = 5;
        this.name = "Rook";
        this.sprite = sheet.getSubimage(4 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);

    }

    public boolean isValidMovement(int file, int rank) {
        return (file == this.file || rank == this.rank);
    }

    public boolean moveCollidesWithPiece(int file, int rank) {
        //left
        if (this.file > file)
            for (int f = this.file - 1; f > file; f--)
                if (board.getPiece(f, this.rank) != null)
                    return true;
        //right
        if (this.file < file)
            for (int f = this.file + 1; f < file; f++)
                if (board.getPiece(f, this.rank) != null)
                    return true;
        //up
        if (this.rank > rank)
            for (int r = this.rank - 1; r > rank; r--)
                if (board.getPiece(this.file, r) != null)
                    return true;
        //down
        if (this.rank < rank)
            for (int r = this.rank + 1; r < rank; r++)
                if (board.getPiece(this.file, r) != null)
                    return true;
        return false;
    }
}