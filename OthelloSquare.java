package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OthelloSquare {

    private Rectangle othelloSquare;
    private Pane gamePane;
    private int col;
    private int row;
    private Piece piece;

    //Constructor for the OthelloSquare class used in the 2D array board
    public OthelloSquare(Pane gamePane, int row, int col, Piece piece){
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.gamePane = gamePane;
        this.othelloSquare = new Rectangle(col* othello.Constants.OTHELLO_SQUARE_WIDTH, row * othello.Constants.OTHELLO_SQUARE_WIDTH
                , othello.Constants.OTHELLO_SQUARE_WIDTH, othello.Constants.OTHELLO_SQUARE_WIDTH);
        this.othelloSquare.setFill(Color.LIGHTYELLOW);
        this.othelloSquare.setStroke(Color.BLACK);
        this.gamePane.getChildren().add(othelloSquare);
    }

    //setter method for the square color
    public void setColor(Color color) {
        this.othelloSquare.setFill(color);
    }

    //getter method for the square color
    public Color getColor(){
        return Color.LIGHTYELLOW;
    }

    //getter method for the x coordinate of the center of the square, returns an int
    public int getXCenter(){
        return this.col * Constants.OTHELLO_SQUARE_WIDTH + 40;
    }

    //getter method for the y coordinate of the center of the square, returns an int
    public int getYCenter(){
        return this.row * Constants.OTHELLO_SQUARE_WIDTH + 40;
    }

    //getter method for the column of the square within the board
    public int getCol(){
        int newCol = (int) (othelloSquare.getX() / Constants.OTHELLO_SQUARE_WIDTH);
        return newCol;
    }

    //getter method for the row of the square within the board
    public int getRow(){
        int newRow = (int) (othelloSquare.getY() / Constants.OTHELLO_SQUARE_WIDTH);
        return newRow;
    }

    //getter method of the piece on top of a given square
    public Piece getPiece(){
        return this.piece;
    }

    //boolean method that checks if a square has no piece on top of it and returns true if empty
    public boolean isEmpty(){
        if (this.getPiece() == null){
            return true;
        }
        else {
            return false;
        }
    }

    //void method that adds a white piece to the square
    public void addWhitePiece(){
        this.piece = new Piece(gamePane, this.getXCenter(), this.getYCenter(), 30, Color.WHITE);
    }
    //void method that adds a black piece to the square
    public void addBlackPiece() {
        this.piece = new Piece(gamePane, this.getXCenter(), this.getYCenter(), 30, Color.BLACK);
    }

    //void method that adds a piece of the given color(via parameters)
    public void addPiece(Color color){
        this.piece = new Piece(gamePane, this.getXCenter(), this.getYCenter(), 30, color);
    }

    //getter method for the piece on top of a square
    public Color getPieceColor(){
        return this.piece.getColor();
    }

    //setter method for the piece color on top of a given square
    public void setPieceColor(Color color){
        this.getPiece().setColor(color);
    }

    //void method that removes a piece from a given square graphically and logically
    public void removePiece(){
        this.piece.remove();
        this.piece = null;
        this.gamePane.getChildren().remove(this.piece);

    }

}

