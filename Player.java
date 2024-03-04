package othello;

import javafx.scene.paint.Color;

//Interface implemented by both the human player and the computer player
public interface Player {
    public void makeMove();
    public int getScore();
    public int getOpponentScore();
    public Color getOpponentColor();
    public void setColor(Color color);
    public Color getPlayerColor();


}
