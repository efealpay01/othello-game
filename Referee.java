package othello;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Referee {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private KeyFrame refFrame;
    private Timeline timeline;
    private Label scoreLabel;
    private Label turnLabel;
    private Board board;

    //Constructor for the referee class
    public Referee(Board board) {
        this.board = board;
        this.scoreLabel = new Label("White: " + 2 + "   " + "Black: " + 2);
        this.turnLabel = new Label("It's the White Player's turn");
        this.setUpTimeline();
    }

    //The void method that changes the current player and edits the labels for score and turn accordingly
    //called when the turn is over for a player
    public void changePlayer() {
        if (!this.board.hasNoMoves(this.getOppositePlayer(currentPlayer)) && !this.board.checkGameOver(player1,player2)) {
            if (this.currentPlayer == player1) {
                this.scoreLabel.setText("White : " + currentPlayer.getScore() + "   " + "Black : " + currentPlayer.getOpponentScore());
                this.turnLabel.setText("It's the White Player's turn");
                this.currentPlayer = this.player2;
            } else if (this.currentPlayer == player2) {
                this.scoreLabel.setText("White : " + currentPlayer.getOpponentScore() + "   " + "Black : " + currentPlayer.getScore());
                this.turnLabel.setText("It's the Black Player's turn");
                this.currentPlayer = this.player1;
            }
        }

    }

    //Setter method for the three players defined in this class: White player, Black player, and currentPLayer
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = this.player1;
    }

    //void method that sets up the timeline
    public void setUpTimeline() {
        this.refFrame = new KeyFrame(Duration.seconds(Constants.DURATION),
                (ActionEvent e) -> this.update());
        this.timeline = new Timeline(this.refFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    //helper method that dictates what the timeline should be doing everytime its called, calls makeMove for the players
    //when its their turn and changes the turn
    public void update() {
        if (this.currentPlayer != null) {
            this.timeline.pause();
            this.currentPlayer.makeMove();
            this.changePlayer();
            this.scoreLabel.setText("White : " + player1.getScore() + "   " + "Black : " + player2.getScore());

        }
    }

    //void method that dictates what should happen when a turn is over
    public void turnOver() {
        this.board.clearOldMoves();
        this.timeline.play();
    }

    //getter method for the turn label
    public Label getTurnLabel() {
        return this.turnLabel;
    }

    //getter method for the score label
    public Label getScoreLabel() {
        return this.scoreLabel;
    }

    //getter method for the opposite player
    public Player getOppositePlayer(Player player) {

        if (player.getPlayerColor() == this.player1.getPlayerColor()) {
            return this.player2;
        }
        else{
            return this.player1;
        }
    }

    //setter method for the score label
    public void setScoreLabel(String label) {
        this.scoreLabel.setText(label);
    }

    //setter method for the turn label
    public void setTurnLabel(String label) {
        this.turnLabel.setText(label);
    }

    //void method that plays the timeline
    public void playTimeline(){
        this.timeline.play();
    }
}


