package othello;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Board {

    private OthelloSquare[][] board;
    private Pane gamePane;
    private Label label;
    private int wCounter;
    private int bCounter;


    //This is the constructor for the board that is actually displayed within the game, It is a 2D array
    //of OthelloSquares
    public Board(Pane gamePane) {
        this.gamePane = gamePane;
        this.label = new Label("");
        this.gamePane.getChildren().add(label);
        this.board = new OthelloSquare[10][10];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                this.board[row][col] = new OthelloSquare(gamePane, row, col, null);
            }
        }
        this.formBorder();
        this.setStartingPieces();
    }

    //This is the constructor for the copy constructor for the computer player to make moves on that doesn't show
    //on the actual game board.
    public Board(Board board) {
        OthelloSquare[][] oldSquares = board.getSquares();
        this.board = new OthelloSquare[10][10];
        this.gamePane = new Pane();
        for (int row = 0; row < oldSquares.length; row++) {
            for (int col = 0; col < oldSquares[row].length; col++) {
                this.board[row][col] = new OthelloSquare(this.gamePane, row, col, null);
                if (!oldSquares[row][col].isEmpty()){
                    this.board[row][col].addPiece(oldSquares[row][col].getPiece().getColor());
                }

            }
        }
    }

    //this method is used to form the border of the othello board by changing the squares colors,
    //used in the constructor
    private void formBorder() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (row == 0 || col == 0 || col == board[row].length - 1 || row == board.length - 1) {
                    this.board[row][col].setColor(Color.BROWN);
                }

            }
        }
    }

    //This is a method that sets the starting four pieces on the board.
    private void setStartingPieces() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((row == 4 && col == 4) || (row == 5 && col == 5)) {
                    this.board[row][col].addWhitePiece();
                }
                if ((row == 4 && col == 5) || (row == 5 && col == 4)) {
                    this.board[row][col].addBlackPiece();
                }
            }
        }
    }

    //A method that return the square for a given row and column, return type is OthelloSquare
    public OthelloSquare squareAt(int i, int j) {
        return this.board[i][j];
    }


    //A boolean method that checks if a given OthelloSquare is in the board and not on the boarder
    public boolean inBoard(OthelloSquare square) {
        if (square.getColor() == Color.BROWN) {
            return false;
        }
        return true;
    }

    //This is the recursive method that checks to see if there's a sandwich on the board for a given direction
    public boolean checkOneDirection(int row, int col, int rowDirection, int colDirection,
                                     Player player, boolean seenOpponent, boolean flipMode) {
        if (this.board[row+rowDirection][col+colDirection].isEmpty() || !(inBoard(board[row+rowDirection][col+colDirection]))) {
            return false;
        }
        if (this.board[row+rowDirection][col+colDirection].getPieceColor() == player.getOpponentColor()) {
            return checkOneDirection(row + rowDirection, col + colDirection, rowDirection,
                    colDirection, player, true, flipMode);
        }

        if (this.board[row+rowDirection][col+colDirection].getPieceColor() == player.getPlayerColor() && seenOpponent) {
            if (flipMode){
                this.flipPieces(row,col,rowDirection,colDirection,player);
            }
            return true;
        }

        return false;
    }

    //This is a boolean method that calls the recursive method checkOneDirection for all directions given an Othello
    //square
    public boolean checkSandwich(int row , int col, Player player, boolean flipMode) {
        boolean i = false;
        for (int rowDir = -1; rowDir < 2; rowDir++) {
            for (int colDir = -1; colDir < 2; colDir++) {
                if (rowDir == 0 && colDir == 0){
                    continue;
                }
                if (checkOneDirection(row, col, rowDir, colDir, player, false, flipMode)){
                    i = true;
                }
            }
        }
        return i;
    }

    //Boolean method that returns true if a move is valid to make
    public boolean moveValid(int row, int col, Player player) {
         return ((this.board[row][col].getPiece() == null) && checkSandwich(row, col, player, false) &&
                 this.board[row][col].getColor() != Color.BROWN);
    }

    //A void method that displays the moves a human player can make when its his/her turn.
    public void displayValidMove(Player player) {
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (moveValid(row, col, player)) {
                    this.board[row][col].setColor(Color.GREY);
                }
            }
        }
    }
    //The void method that flips the pieces when a sandwich is made on the board.
    public void flipPieces(int row, int col, int rowDir, int colDir, Player player){
        while (this.board[row][col].getPieceColor() == player.getOpponentColor()){
                this.squareAt(row, col).setPieceColor(player.getPlayerColor());
            row = row - rowDir;
            col = col - colDir;
        }
    }

    //A void method to activate the ClickHandler as the human player makes a move
    public void activateClickHandler(EventHandler <MouseEvent> clickHandler){
        this.gamePane.setOnMouseClicked(clickHandler);
    }

    //This is a method that clears the old available moves for the human player that just ended
    //their turn.
    public void clearOldMoves(){
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                this.squareAt(row,col).setColor(Color.LIGHTYELLOW);
            }
        }
    }

    //An int method that gets the score of a player(takes in a player)
    public int getPlayerScore(Player player){
        int counter = 0;
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!board[row][col].isEmpty()){
                    if (board[row][col].getPieceColor() == player.getPlayerColor()){
                        counter++;
                    }
                }

            }
        }
        return counter;
    }
    //An integer method that gets the opponents score given a player
    public int getOpponentScore(Player player){
        int counter = 0;
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!board[row][col].isEmpty()){
                    if (board[row][col].getPieceColor() == player.getOpponentColor()){
                        counter++;
                    }
                }

            }
        }
        return counter;
    }
    //Boolean method that checks the game is over by seeing if either player has a valid move to make
    public boolean checkGameOver(Player p1, Player p2) {
        if (this.hasNoMoves(p1) && this.hasNoMoves(p2)) {
            return true;
        }
        return false;
    }

    //A int method that return the number of white pieces on the board
    public int getWhites(){
        this.wCounter = 0;
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!board[row][col].isEmpty()){
                    if (board[row][col].getPieceColor() == Color.WHITE){
                        wCounter++;
                    }
                }
            }
        }
        return wCounter;
    }
    //A int method that return the number of black pieces on the board
    public int getBlacks(){
        this.bCounter = 0;
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!board[row][col].isEmpty()){
                    if (board[row][col].getPieceColor() == Color.BLACK){
                        bCounter++;
                    }
                }
            }
        }
        return bCounter;
    }
    //A void method that updates the Game Over label once the game is over
    public void editGOLabel(Player p1, Player p2){
        if (this.checkGameOver(p1,p2) && this.getBlacks() > this.getWhites()){
            this.label.setText("Game Over, Black Wins!");
        }
        else if (this.checkGameOver(p1,p2) && this.getWhites() > this.getBlacks()){
            this.label.setText("Game Over, White Wins!");
        }
        else if (this.checkGameOver(p1,p2) && this.getWhites() == this.getBlacks()){
            this.label.setText("It's a draw!");
        }
    }

    //Getter method for the game over label
    public Label getGOLabel(){
        return this.label;
    }

    //void method that removes every old piece on the board and resets the starting pieces
    public void resetBoard(){
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!this.board[row][col].isEmpty()){
                    this.board[row][col].removePiece();
                }

            }
        }
        setStartingPieces();
        this.clearOldMoves();
    }

    //boolean method that checks if a given player(as a parameter) has any moves available.
    //returns true if the player has no moves
    public boolean hasNoMoves(Player player){
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (moveValid(row, col, player)) {
                    return false;
                }
            }
        }
        return true;
    }

    //An integer method that helps the computer player evaluate the state of the board for a given player
    public int boardEval(Player player){
        int boardValue = 0;
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (!board[row][col].isEmpty()){
                    if (board[row][col].getPieceColor() == player.getPlayerColor()){
                        boardValue = boardValue + Constants.SCORE_MATRIX[row-1][col-1];
                    }
                    if (board[row][col].getPieceColor() == player.getOpponentColor()){
                        boardValue = boardValue - Constants.SCORE_MATRIX[row-1][col-1];
                    }
                }
            }
        }
        return boardValue;
    }

    //A method that return an arraylist of all available and valid moves for a computer player
    public ArrayList<Move> getValidMoves(Player player){
        ArrayList<Move> validMoves = new ArrayList<>();
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (this.moveValid(row,col,player) && this.board[row][col].getColor() != Color.BROWN){
                    validMoves.add(new Move(row,col,0));
                }
            }
        }
        return validMoves;
    }

    //getter method for 2D array that represents the board
    public OthelloSquare[][] getSquares() {
        return this.board;
    }

    //setter method for the game over label
    public void setGOLabel(String label){
        this.label.setText(label);
    }


}


