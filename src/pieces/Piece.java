package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public int file,rank;
    public int xPos, yPos;
    public int value;
    public String name;
    public boolean isWhite;
    public boolean isWhiteTurn;
    public boolean isBlackTurn;

    public boolean isFirstMove = true;
    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces.png" ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth()/6;
    Image sprite;

    Board board;
    public Piece(Board board){
        this.board = board;
    }

    public boolean isValidMovement(int file, int rank ){
        return true;
    }
    public boolean moveCollidesWithPiece(int file, int rank ){
        return false;
    }

   public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
   }

}
