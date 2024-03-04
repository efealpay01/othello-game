package othello;

import javafx.scene.paint.Color;

public class ComputerPlayer implements Player{
    private Board board;
    private Color color;
    private Referee ref;
    private int intelligence;

    //Constructor for the computer player class
    public ComputerPlayer(Color color, Board board, Referee referee, int intelligence){
        this.intelligence = intelligence;
        this.board = board;
        this.color = color;
        this.ref = referee;
    }

    //the make move method that checks the game is still continuing and uses the getBestMove algorithm to make
    //the best move given the board state
    @Override
    public void makeMove(){
        if (board.checkGameOver(this,ref.getOppositePlayer(this))){
            board.editGOLabel(this,ref.getOppositePlayer(this));
            return;
        }
        Move m = getBestMove(this.board, this.intelligence,this, ref);
        this.board.squareAt(m.getMoveRow(),m.getMoveCol()).addPiece(this.color);
        board.checkSandwich(m.getMoveRow(), m.getMoveCol(), this,true);
        ref.turnOver();
    }

    //The method that uses the minimax algorithm to recursively call itself to return the bestMove possible
    //given the player, board, and intelligence level
    public Move getBestMove(Board board, int intelligence, Player player, Referee ref){
        if (board.checkGameOver(player, ref.getOppositePlayer(player))){
            if (board.getPlayerScore(player) > board.getOpponentScore(player)){
                return new Move(0,0,100000);
            }
            if (board.getPlayerScore(player) < board.getOpponentScore(player)){
                return new Move(0,0,-100000);
            }
            if (board.getPlayerScore(player) == board.getOpponentScore(player)){
                return new Move(0,0,0);
            }
        }
        board.getValidMoves(player);
        if (board.getValidMoves(player).isEmpty()){
            if (intelligence == 1){
                return new Move(0,0,-100000);
            }
            else{
                Move oppMove = getBestMove(board, intelligence-1, ref.getOppositePlayer(player), ref);
                return  new Move(oppMove.getMoveRow(),oppMove.getMoveCol(),-1 * oppMove.getMoveValue());
            }
        }
        else {
            Move bestMove = new Move(1,1, -100000);
            for (Move move: board.getValidMoves(player)){
                Board testBoard = new Board(board);
                int currMoveVal = 0;
                testBoard.squareAt(move.getMoveRow(), move.getMoveCol()).addPiece(player.getPlayerColor());
                if (intelligence == 1){
                     currMoveVal = testBoard.boardEval(player);
                }
                else {
                     currMoveVal = (-1*(getBestMove(testBoard, intelligence-1, ref.getOppositePlayer(player), ref).getMoveValue()));
                }
                if (bestMove.getMoveValue() <= currMoveVal){
                    bestMove.setMoveValue(currMoveVal);
                    bestMove = new Move(move.getMoveRow(), move.getMoveCol(), currMoveVal);
                }

            }
            return bestMove;
        }
    }
    //int method getter for the score of a given player
    @Override
    public int getScore(){
        return this.board.getPlayerScore(this);
    }

    //int method getter for the opponent's score
    @Override
    public int getOpponentScore() {
        return this.board.getOpponentScore(this);
    }

    //Color type getter method for the player color
    @Override
    public Color getPlayerColor(){
        return this.color;
    }

    //setter method for the player color
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    //Color type method that returns the opposite players color
    @Override
    public Color getOpponentColor(){
        if (this.getPlayerColor() == Color.WHITE){
            return Color.BLACK;
        }
        if (this.getPlayerColor() == Color.BLACK){
            return Color.WHITE;
        }
        return null;
    }
}
