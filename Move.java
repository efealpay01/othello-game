package othello;

public class Move {

    private int row;
    private int col;
    private int value;

    //Constructor for the move class
    public Move(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    //setter for the moveValue of the move
    public void setMoveValue(int value){
        this.value = value;
    }

    //getter for the row of the move
    public int getMoveRow(){
        return this.row;
    }
    //getter for the column of the move
    public int getMoveCol(){
        return this.col;
    }
    //getter for the moveValue of the move
    public int getMoveValue(){
        return this.value;
    }

}
