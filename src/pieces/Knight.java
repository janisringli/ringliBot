package pieces;

import main.Board;

import java.awt.*;

public class Knight extends Piece {
    public Knight(Board board,int file, int rank,  boolean isWhite) {
        super(board);
        this.file = file;
        this.rank = rank;
        this.xPos = file*board.tileSize;
        this.yPos = rank*board.tileSize;
        this.isWhite = isWhite;
        this.value = 3;
        this.name = "Knight";
        this.sprite = sheet.getSubimage(3*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);

    }

    public boolean isValidMovement(int file, int rank) {
        return Math.abs(file - this.file) * Math.abs(rank - this.rank) == 2;
    }
}
