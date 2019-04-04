package boardgame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LobbyScene {

    private BoardGameController controller;
    private LobbyModel model;

    public LobbyScene(BoardGameController controller, LobbyModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        HBox mainLayout = new HBox();

        Pane playerOverviewLayout = new Pane();
        ListView<String> playerOverview = new ListView<>(model.getPlayerList());
        playerOverviewLayout.getChildren().add(playerOverview);

        Pane gameSelectionLayout = new Pane();
        ComboBox<String> gameListing = new ComboBox<>();
        gameListing.getSelectionModel().selectFirst();

        gameListing.setItems(model.getGameList());
        gameListing.getSelectionModel().selectFirst();
        gameSelectionLayout.getChildren().add(gameListing);

        GridPane buttonLayout = new GridPane();
        Button backToStartButton = new Button("Terug");
        Button startGameButton = new Button("Start game");
        buttonLayout.add(backToStartButton, 0, 0);
        buttonLayout.add(startGameButton, 0, 1);
        backToStartButton.setOnAction(e -> controller.switchScene("StartScene", "Startscherm"));
        startGameButton.setOnAction(e -> {
            model.setSelectedGame(gameListing.getValue());
            model.setChosenOpponent(playerOverview.getSelectionModel().getSelectedItem());
            controller.prepareGameScene();
            controller.switchScene("GameScene", model.getSelectedGame());
        });

        mainLayout.getChildren().addAll(playerOverviewLayout, gameSelectionLayout, buttonLayout);

        return new Scene(mainLayout, 500, 500);
    }
}
