package othello;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HumanPlayer implements Player{
    private Color color;
    private Board board;
    private Referee ref;
    private int mouseRow;
    private int mouseCol;

    //Constructor for the HumanPlayer class
    public HumanPlayer(Color color, Board board, Referee referee){
        this.color = color;
        this.board = board;
        this.ref = referee;
    }
    //getter method for the player color
    public Color getPlayerColor(){
        return this.color;
    }

    //setter method for the player color
    public void setColor(Color color) {
        this.color = color;
    }

    //getter method for the opponents color
    public Color getOpponentColor(){
        if (this.getPlayerColor() == Color.WHITE){
            return Color.BLACK;
        }
        if (this.getPlayerColor() == Color.BLACK){
            return Color.WHITE;
        }
        return null;
    }

    //void method that dictates what should happen when the mouse is clicked
    public void onMouseClick(MouseEvent e) {
        double xMouse = e.getSceneX();
        double yMouse = e.getSceneY();
        this.mouseRow = (int) (yMouse / Constants.OTHELLO_SQUARE_WIDTH);
        this.mouseCol = (int) (xMouse / Constants.OTHELLO_SQUARE_WIDTH);
        if (board.squareAt(mouseRow,mouseCol).isEmpty() &&
                (mouseRow>0 && mouseCol>0) && (mouseRow<9 && mouseCol<9) &&
                (board.moveValid(mouseRow,mouseCol,this)))
            {
            board.squareAt(mouseRow,mouseCol).addPiece(this.color);
            board.checkSandwich(mouseRow,mouseCol,this,true);
                board.displayValidMove(this);
                ref.turnOver();
                this.board.boardEval(this);
            if (board.checkGameOver(this,ref.getOppositePlayer(this))){
                board.editGOLabel(this,ref.getOppositePlayer(this));
            }
        }
    }

    //makeMove method for the HumanPlayer that displays the valid moves and activates the clickHandler
    @Override
    public void makeMove(){
        board.displayValidMove(this);
        this.board.activateClickHandler((MouseEvent e)-> this.onMouseClick(e));
    }

    //integer method getter for the player score
    public int getScore(){
        return this.board.getPlayerScore(this);
    }

    //integer method getter method for the opponent score
    public int getOpponentScore(){
        return this.board.getOpponentScore(this);
    }

}
