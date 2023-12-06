package pieces;

import main.Board;

import java.awt.*;

public class Pawn extends Piece {
    public Pawn(Board board, int file, int rank, boolean isWhite) {
        super(board);
        this.file = file;
        this.rank = rank;
        this.xPos = file * board.tileSize;
        this.yPos = rank * board.tileSize;
        this.isWhite = isWhite;
        this.value = 1;
        this.name = "Pawn";
        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);

    }

    public boolean isValidMovement(int file, int rank) {

        int colorIndex = isWhite ? 1 : -1;

        // push forward 1 Tile
        if (this.file == file && this.rank == rank - colorIndex && board.getPiece(file, rank) == null) {
            return true;
        }
        //move forward 2 Tiles
        if(isFirstMove && this.file == file && this.rank == rank - 2*colorIndex && board.getPiece(file, rank - colorIndex) == null && board.getPiece(file, rank) == null){
            return true;
        }
        //capture right
        if (this.file == file - 1 && this.rank == rank - colorIndex && board.getPiece(file, rank) != null) {
            return true;
        }
        //capture left
        if (this.file == file + 1 && this.rank == rank - colorIndex && board.getPiece(file, rank) != null) {
            return true;
        }

        //en passant left
        if (this.file == file - 1 && this.rank == rank - colorIndex && board.getPiece(file, rank) == null && board.getPiece(file, rank + colorIndex) != null && board.getPiece(file, rank + colorIndex).name.equals("Pawn") && board.getPiece(file, rank + colorIndex).isWhite != this.isWhite && board.getPiece(file, rank + colorIndex).isFirstMove) {
            return true;
        }
        //en passant right
        if (this.file == file + 1 && this.rank == rank - colorIndex && board.getPiece(file, rank) == null && board.getPiece(file, rank + colorIndex) != null && board.getPiece(file, rank + colorIndex).name.equals("Pawn") && board.getPiece(file, rank + colorIndex).isWhite != this.isWhite && board.getPiece(file, rank + colorIndex).isFirstMove) {
            return true;
        }

        return false;
    }

    public boolean moveCollidesWithPiece(int file, int rank) {
        if (this.isWhite) {
            if (this.rank == 1) {
                if (this.file == file && (rank - this.rank == 1 || rank - this.rank == 2)) {
                    return board.getPiece(file, rank) != null;
                } else if (Math.abs(file - this.file) == 1 && rank - this.rank == 1) {
                    return board.getPiece(file, rank) == null;
                }
            } else {
                if (this.file == file && rank - this.rank == 1) {
                    return board.getPiece(file, rank) != null;
                } else if (Math.abs(file - this.file) == 1 && rank - this.rank == 1) {
                    return board.getPiece(file, rank) == null;
                }
            }
        } else {
            if (this.rank == 6) {
                if (this.file == file && (rank - this.rank == -1 || rank - this.rank == -2)) {
                    return board.getPiece(file, rank) != null;
                } else if (Math.abs(file - this.file) == 1 && rank - this.rank == -1) {
                    return board.getPiece(file, rank) == null;
                }
            } else {
                if (this.file == file && rank - this.rank == -1) {
                    return board.getPiece(file, rank) != null;
                } else if (Math.abs(file - this.file) == 1 && rank - this.rank == -1) {
                    return board.getPiece(file, rank) == null;
                }
            }
        }
        return false;
    }
}
