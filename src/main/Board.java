package main;

import org.w3c.dom.ls.LSOutput;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int tileSize = 85;
    // access static variable
    int moveCount = Main.moveCount;
    int files = 8;
    int ranks = 8;
    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);
    public boolean isWhiteTurn;
    public boolean isBlackTurn;
    public int enPassantTile = -1;

    public Board() {
        System.out.println("moveCount: " + moveCount);

        this.setPreferredSize(new Dimension(ranks * tileSize, files * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
    }


    public Piece getPiece( int file,int rank) {
        for (Piece piece : pieceList) {
            if (piece.file == file && piece.rank == rank) {
                return piece;
            }
        }
        return null;
    }
    public void nextTurn(){

    }


    public void makeMove(Move move){
        if(move.piece.name.equals("Pawn")){
            movePawn(move);
            moveCount++;
            System.out.println("moveCount: " + moveCount);
        }
        else if (move.piece.name.equals("King")) {
            moveKing(move);
            moveCount++;
        }
        moveCount++;
        System.out.println("moveCount: " + moveCount);
            move.piece.file = move.newFile;
            move.piece.rank = move.newRank;
            move.piece.xPos = move.newFile * tileSize;
            move.piece.yPos = move.newRank * tileSize;

            move.piece.isFirstMove = false;
            capture(move.capturedPiece);
    }

    private void moveKing(Move move) {
        if (Math.abs(move.piece.file - move.newFile) == 2) {
            Piece rook;
            if (move.newFile == 6) {
                rook = getPiece(7, move.piece.rank);
                rook.file = 5;
                rook.xPos = 5 * tileSize;
            } else {
                rook = getPiece(0, move.piece.rank);
                rook.file = 3;
                rook.xPos = 3 * tileSize;
            }
            rook.xPos = rook.file * tileSize;
        }
    }
        private void movePawn (Move move){

            //en passant
            int colorIndex = move.piece.isWhite ? 1 : -1;
            if (getTileNum(move.newFile, move.newRank) == enPassantTile) {
                move.capturedPiece = getPiece(move.newFile, move.newRank + colorIndex);
            }
            if (Math.abs(move.piece.rank - move.newRank) == 2) {
                enPassantTile = getTileNum(move.newFile, move.newRank - colorIndex);
            } else {
                enPassantTile = -1;
            }

            //promotions
            colorIndex = move.piece.isWhite ? 0 : 7;
            if (move.newRank == colorIndex) {
                promotePawn(move);
            }
        }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newFile, move.newRank, move.piece.isWhite));
        capture(move.capturedPiece);
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }
//alternating turns



    public boolean isValidMove(Move move){
        if (sameTeam(move.piece, move.capturedPiece)){
            return false;
        }


        if (checkScanner.isKingChecked(move)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newFile, move.newRank)){
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newFile, move.newRank)){
            return false;
        }
        return true;
    }


   public boolean sameTeam(Piece p1, Piece p2) {
       if (p1 == null || p2 == null) {
           return false;
       }

       return p1.isWhite == p2.isWhite;
   }

   public int getTileNum(int file, int rank){
       return rank * ranks + file;
   }

   Piece findKing(boolean isWhite) {
       for (Piece piece : pieceList) {
           if (isWhite == piece.isWhite && piece.name.equals("King")) {
               return piece;
           }
       }
       return null;
   }

    public void addPieces() {
        //---------------White Pieces----------------
        pieceList.add(new Rook(this, 0, 0, true));
        pieceList.add(new Knight(this, 1, 0, true));
        pieceList.add(new Bishop(this, 2, 0, true));
        pieceList.add(new Queen(this, 3, 0, true));
        pieceList.add(new King(this, 4, 0, true));
        pieceList.add(new Bishop(this, 5, 0, true));
        pieceList.add(new Knight(this, 6, 0, true));
        pieceList.add(new Rook(this, 7, 0, true));
        //---------------White Pawns----------------
        pieceList.add(new Pawn(this, 0, 1, true));
        pieceList.add(new Pawn(this, 1, 1, true));
        pieceList.add(new Pawn(this, 2, 1, true));
        pieceList.add(new Pawn(this, 3, 1, true));
        pieceList.add(new Pawn(this, 4, 1, true));
        pieceList.add(new Pawn(this, 5, 1, true));
        pieceList.add(new Pawn(this, 6, 1, true));
        pieceList.add(new Pawn(this, 7, 1, true));


        //---------------Black Pieces----------------
        pieceList.add(new Rook(this, 0, 7, false));
        pieceList.add(new Knight(this, 1, 7, false));
        pieceList.add(new Bishop(this, 2, 7, false));
        pieceList.add(new Queen(this, 3, 7, false));
        pieceList.add(new King(this, 4, 7, false));
        pieceList.add(new Bishop(this, 5, 7, false));
        pieceList.add(new Knight(this, 6, 7, false));
        pieceList.add(new Rook(this, 7, 7, false));
        //---------------Black Pawns----------------
        pieceList.add(new Pawn(this, 0, 6, false));
        pieceList.add(new Pawn(this, 1, 6, false));
        pieceList.add(new Pawn(this, 2, 6, false));
        pieceList.add(new Pawn(this, 3, 6, false));
        pieceList.add(new Pawn(this, 4, 6, false));
        pieceList.add(new Pawn(this, 5, 6, false));
        pieceList.add(new Pawn(this, 6, 6, false));
        pieceList.add(new Pawn(this, 7, 6, false));


    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Draws the board
        for (int i = 0; i < ranks; i++) {
            for (int j = 0; j < files; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(191, 164, 112));
                } else {
                    g.setColor(new Color(122, 85, 53));
                }
                g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }



        }
        //Draws the selected piece's possible moves
        if(selectedPiece != null)
            for (int r = 0; r < ranks; r++) {
                for (int f = 0; f < files; f++) {
                    if (isValidMove(new Move(this, selectedPiece,f,r))){

                        g2d.setColor(new Color(68, 180, 57, 128));
                        g2d.fillRect(f * tileSize, r * tileSize, tileSize, tileSize);

                    }
                }
            }
        //Draws the pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
