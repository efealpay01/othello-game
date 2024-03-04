package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {
    private Circle pieceCircle;
    private Pane gamePane;
    private Color color;

    //Constructor for the piece a wrapper class for a circle
    public Piece(Pane gamePane, int xCord, int yCord, int rad, Color color){
        this.color = color;
        this.gamePane = gamePane;
        this.pieceCircle = new Circle();
        this.pieceCircle.setFill(color);
        this.pieceCircle.setCenterX(xCord);
        this.pieceCircle.setCenterY(yCord);
        this.pieceCircle.setRadius(rad);
        this.pieceCircle.setStroke(Color.BLACK);
        this.gamePane.getChildren().add(pieceCircle);
    }

    //getter method for the piece color
    public Color getColor(){
        return this.color;
    }

    //setter method for the color of the piece
    public void setColor(Color color){
        this.color = color;
        this.pieceCircle.setFill(color);
    }
    //Method that removes the piece logically and graphicallt
    public void remove(){
        this.gamePane.getChildren().remove(pieceCircle);
        this.pieceCircle = null;
    }
}

