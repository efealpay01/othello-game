package othello;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
 * Controls sets up the GUI for the game menu, allowing the user to pick the
 * game modes and to start and track games. Controls holds a one-way reference
 * to the Game, so it can control the Game's player settings.
 */
public class Controls {

  private SetupGame game;

  private VBox controlsPane;

  // Arrays for player buttons. Each button is checked to see if it is
  // selected when the user starts each game.
  private RadioButton[][] playerButtons;

  public Controls(SetupGame othello) {
    this.game = othello;
    this.controlsPane = new VBox();
    this.controlsPane.setPadding(new Insets(10));
    this.controlsPane.setSpacing(30);
    this.controlsPane.setStyle("-fx-background-color: grey;");
    this.controlsPane.setAlignment(Pos.CENTER);

    this.setupInstructions();
    /* TODO: Add score & turn labels. These should be contained in whatever class is controlling turn-taking, but
        be sure to add them to the this.controlsPane here!  */
    this.setupGameLabels();
    this.setupMenu();
    this.setupGameButtons();
  }

  // TODO: Be sure to use this method to add the control pane to the this.root pane in Pane Organizer
  public Pane getPane() {
    return this.controlsPane;
  }

  // how do I change these labels from the referee
  private void setupInstructions() {
    Label instructionsLabel = new Label(
        "Select options, then press Apply Settings");

    this.controlsPane.getChildren().addAll(instructionsLabel);
  }

  /*
   * Sets up the two halves of the player mode menu.
   */
  private void setupMenu() {
    this.playerButtons = new RadioButton[2][4];

    HBox playersMenu = new HBox();
    playersMenu.setSpacing(10);
    playersMenu.setAlignment(Pos.CENTER);
    playersMenu.getChildren().addAll(this.playerMenu(Constants.WHITE),
        this.playerMenu(Constants.BLACK));

    this.controlsPane.getChildren().add(playersMenu);
  }

  /*
   * Provides the menu for each player mode.
   */
  private VBox playerMenu(int player) {
    VBox playerMenu = new VBox();
    playerMenu.setPrefWidth(Constants.CONTROLS_PANE_WIDTH / 2);
    playerMenu.setSpacing(10);
    playerMenu.setAlignment(Pos.CENTER);

    // Player color.
    String playerColor = "White";
    if (player == Constants.BLACK) {
      playerColor = "Black";
    }
    Label playerName = new Label(playerColor);

    // Radio button group for player mode.
    ToggleGroup toggleGroup = new ToggleGroup();

    // Human player.
    RadioButton humanButton = new RadioButton("Human         ");
    humanButton.setToggleGroup(toggleGroup);
    humanButton.setSelected(true);
    this.playerButtons[player][0] = humanButton;

    // Computer Players.
    for (int i = 1; i < 4; i++) {
      RadioButton computerButton = new RadioButton("Computer " + i + "  ");
      computerButton.setToggleGroup(toggleGroup);
      this.playerButtons[player][i] = computerButton;

      // Enables deterministic button when Computer player selected.

    }

    // Checkbox for deterministic play. Only enabled when computer player
    // selected. This is ONLY for Bells&Whistles

    // Visually add the player mode menu.
    playerMenu.getChildren().add(playerName);
    for (RadioButton rb : this.playerButtons[player]) {
      playerMenu.getChildren().add(rb);
    }

    return playerMenu;
  }

  private void setupGameButtons() {
    Button applySettingsButton = new Button("Apply Settings");
    applySettingsButton.setOnAction((ActionEvent e)->this.applySettings(e));
    applySettingsButton.setFocusTraversable(false);

    Button resetButton = new Button("Reset");
    resetButton.setOnAction((ActionEvent e)-> this.resetHandler(e));
    resetButton.setFocusTraversable(false);

    Button quitButton = new Button("Quit");
    quitButton.setOnAction((ActionEvent e)->Platform.exit());
    quitButton.setFocusTraversable(false);

    this.controlsPane.getChildren().addAll(applySettingsButton, resetButton,
        quitButton);
  }

  /*
   * Handler for Apply Settings button.
   */

    public void applySettings(ActionEvent e) {

      // Determine game play mode for each player.
      int whitePlayerMode = 0;
      int blackPlayerMode = 0;
      for (int player = 0; player < 2; player++) {
        for (int mode = 0; mode < 4; mode++) {
          if (this.playerButtons[player][mode].isSelected()) {
            if (player == Constants.WHITE) {
              whitePlayerMode = mode;
            } else {
              blackPlayerMode = mode;
            }
          }
        }




      /* TODO: Set the Game's players, which starts the game. whitePlayerMode and blackPlayerMode store the modes of
          the players, where 0 is HumanPlayer, and 1-3 are levels 1-3 of ComputerPlayer respectively. We also provide
          whitePlayerDeterministic and blackPlayerDeterministic which stores whether the players should be
          deterministic or not, but feel free to ignore these unless you are doing the deterministic Bells&Whistles.
          You should have a method in the game class that sets the players of the game, and then call this method here
          with the information we provided! */

    }
      this.game.setPlayers(whitePlayerMode,blackPlayerMode);
  }

  /* TODO: Fill out this handle method once you have figured out how to reset the game.
      This will most likely not be done until after you have implemented turn-taking and score-keeping */

    //The void method that assigns the reset button to the resetGame method in the setupGame class
    public void resetHandler(ActionEvent e){
      this.game.resetGame();
    }

    //The void method that assigns the labels and add them to the controlPane
    public void setupGameLabels(){
      Label scoreLabel = game.getScoreLabel();
      Label turnLabel = game.getTurnLabel();
      Label GOLabel = game.getGOLabel();
      this.controlsPane.getChildren().addAll(scoreLabel,turnLabel, GOLabel);
  }


}
