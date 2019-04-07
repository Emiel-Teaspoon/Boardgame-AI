package boardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LobbyScene {

    private BoardGameController controller;
    private LobbyModel model;

    private BorderPane mainLayout;
    private Pane gameSelectionLayout;
    private HBox buttonLayoutRight;
    private HBox buttonLayoutLeft;

    private ListView<String> playerOverview;
    private ComboBox<String> gameListing;

    private Button backButton;
    private Button playButton;

    public LobbyScene(BoardGameController controller, LobbyModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        createNodes();
        configureLayoutManagers();
        createActionListeners();

        return new Scene(mainLayout, 500, 500);
    }

    private void createNodes() {
        playerOverview = new ListView<>(model.getPlayerList());
        playerOverview.setMaxWidth(150);
        playerOverview.setMaxHeight(250);

        gameListing = new ComboBox<>();
        gameListing.setItems(model.getGameList());
        gameListing.getSelectionModel().selectFirst();

        backButton = new Button("Terug");
        playButton = new Button("Start game");
    }

    private void configureLayoutManagers() {
        mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        HBox centerGrid = new HBox();
        centerGrid.setAlignment(Pos.TOP_CENTER);
        centerGrid.setSpacing(10);
        centerGrid.getChildren().addAll(playerOverview, gameListing);
        mainLayout.setCenter(centerGrid);

        buttonLayoutRight = new HBox();
        buttonLayoutRight.setAlignment(Pos.CENTER_RIGHT);
        buttonLayoutRight.getChildren().addAll(playButton);
        mainLayout.setRight(buttonLayoutRight);

        buttonLayoutLeft = new HBox();
        buttonLayoutLeft.setAlignment(Pos.CENTER_LEFT);
        buttonLayoutLeft.getChildren().addAll(backButton);
        mainLayout.setLeft(buttonLayoutLeft);
    }

    private void createActionListeners() {
        backButton.setOnAction(e -> controller.switchScene("StartScene", "Startscherm"));
        playButton.setOnAction(e -> {
            model.setSelectedGame(gameListing.getValue());
            model.setChosenOpponent(playerOverview.getSelectionModel().getSelectedItem());
            controller.loadAvailableGames();
            controller.prepareGameScene();
            controller.switchScene("GameScene", model.getSelectedGame());
        });
    }
}
