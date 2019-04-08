package boardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameScene {

    private BoardGameController controller;
    private GameModel model;

    public GameScene(BoardGameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        BorderPane layout = new BorderPane();
        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setPadding(new Insets(20, 0, 20, 20));

        Pane gameBoardLayout = new Pane();

        Game currentGame = model.getCurrentGame();
        gameBoardLayout.getChildren().add(currentGame.startGame());

        Label playerOneName = new Label(model.getPlayerOne());
        Label playerTwoName = new Label(model.getPlayerTwo());
        Button backButton = new Button("Terug");

        playerOneName.setMaxWidth(Double.MAX_VALUE);
        playerTwoName.setMaxWidth(Double.MAX_VALUE);
        backButton.setMaxWidth(Double.MAX_VALUE);

        backButton.setOnAction(e -> controller.switchScene("LobbyScene", "Lobby"));

        VBox sideBar = new VBox();
        sideBar.setSpacing(10);
        sideBar.setPadding(new Insets(0, 20, 10, 20));
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        sideBar.getChildren().addAll(playerOneName, playerTwoName, backButton);

        layout.setLeft(gameBoardLayout);
        layout.setRight(sideBar);

        return new Scene(layout, 800, 800);
    }



}
