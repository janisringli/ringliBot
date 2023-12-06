package main;

import pieces.Piece;

public class Move {

    int oldRank;
    int oldFile;
    int newRank;
    int newFile;
    Piece piece;
    Piece capturedPiece;

    public Move(Board board, Piece piece,  int newFile, int newRank) {
        this.piece = piece;
        this.oldFile = piece.file;
        this.oldRank = piece.rank;
        this.newFile = newFile;
        this.newRank = newRank;
        this.capturedPiece = board.getPiece(newFile,newRank);
    }
}
