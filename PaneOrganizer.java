package othello;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {

    private SetupGame game;
    private Pane gamePane;
    private Controls controller;
    private Pane controllerPane;
    private BorderPane root;

    //Constructor for the Pane organizer
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.createGamePane();
        this.createControlPane();
    }

    //getter method for the borderPane root
    public BorderPane getRoot(){
        return this.root;
    }

    //void method that creates the gamePane
    public void createGamePane() {
        this.gamePane = new Pane();
        this.game = new SetupGame(gamePane);
        this.root.setCenter(gamePane);
        this.gamePane.setFocusTraversable(true);
    }
    //void method that creates the controlPane
    public void createControlPane(){
        this.controller = new Controls(this.game);
        this.controllerPane = this.controller.getPane();
        this.root.setRight(this.controllerPane);
        this.controllerPane.setFocusTraversable(false);
    }

}

