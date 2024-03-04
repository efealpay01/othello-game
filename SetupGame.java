package othello;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SetupGame {

    private Pane gamePane;
    private Board board;
    private Player player1;
    private Player player2;
    private Referee ref;

    //Constructor for the setupGame class
    public SetupGame(Pane gamePane){
        this.gamePane = gamePane;
        this.board = new Board(this.gamePane);
        this.ref = new Referee(this.board);
    }

    //Method that sets up the players according to the mode of the game and sets them up for the referee
    public void setPlayers(int whitePlayerMode, int blackPlayerMode){
        if (whitePlayerMode == 0) {
            this.player1 = new HumanPlayer(Color.WHITE, this.board, this.ref);
        }
        else if (whitePlayerMode == 1){
            this.player1 = new ComputerPlayer(Color.WHITE, this.board, this.ref,1);
        }
        else if (whitePlayerMode == 2) {
            this.player1 = new ComputerPlayer(Color.WHITE, this.board, this.ref,2);
        }
        else if (whitePlayerMode == 3) {
            this.player1 = new ComputerPlayer(Color.WHITE, this.board, this.ref,3);
        }
        if (blackPlayerMode == 0){
            this.player2 = new HumanPlayer(Color.BLACK, this.board, this.ref);
        }
        else if (blackPlayerMode == 1){
            this.player2 = new ComputerPlayer(Color.BLACK, this.board, this.ref,1);
        }
        else if (blackPlayerMode == 2){
            this.player2 = new ComputerPlayer(Color.BLACK, this.board, this.ref,2);
        }
        else if (blackPlayerMode == 3){
            this.player2 = new ComputerPlayer(Color.BLACK, this.board, this.ref,3);
        }
        this.ref.setPlayers(player1, player2);
        }


        //Void method that resets the game
    public void resetGame(){
        this.board.resetBoard();
        this.board.setGOLabel(" ");
        this.ref.setPlayers(null,null);
        this.ref.setScoreLabel("White: " + 2 + "   " + "Black: " + 2);
        this.ref.setTurnLabel("It's white players turn");
        this.ref.playTimeline();
    }

    //getter method for turn label
    public Label getTurnLabel(){return this.ref.getTurnLabel();
    }
    //getter method for score label
    public Label getScoreLabel(){
        return this.ref.getScoreLabel();
    }
    //getter for game over label
    public Label getGOLabel(){return this.board.getGOLabel();}

}
