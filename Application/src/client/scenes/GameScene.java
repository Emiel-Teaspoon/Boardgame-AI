package client.scenes;

import client.GameModel;
import game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import client.ClientModel;
import javafx.scene.paint.Color;

public class GameScene extends ClientScene {

    private Button btBack;
    VBox sideBar;
    Pane gameBoardLayout;

    private GameModel gameModel;
    private String playerOne;
    private String playerTwo;

    public GameScene(ClientModel model) {
        super(model);
        buildScene();
    }

    public void startGame(GameModel gameModel) {
        this.gameModel = gameModel;
        Game currentGame = gameModel.getCurrentGame();
        gameBoardLayout.getChildren().add(currentGame.startGame());

        Label playerOneName = new Label(gameModel.getPlayerOne());
        Label playerTwoName = new Label(gameModel.getPlayerTwo());

        playerOneName.setMaxWidth(Double.MAX_VALUE);
        playerTwoName.setMaxWidth(Double.MAX_VALUE);

        sideBar.getChildren().addAll(playerOneName, playerTwoName, btBack);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }

    @Override
    public String getName() {
        return "Game Client - Game";
    }

    @Override
    public void buildScene() {
        BorderPane layout = new BorderPane();
        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setPadding(new Insets(20, 0, 20, 20));

        gameBoardLayout = new Pane();

        Button backButton = new Button("Terug");


        backButton.setMaxWidth(Double.MAX_VALUE);

        backButton.setOnAction(e -> model.switchScene("lobby"));

        sideBar = new VBox();
        sideBar.setSpacing(10);
        sideBar.setPadding(new Insets(0, 20, 10, 20));
        sideBar.setAlignment(Pos.CENTER_RIGHT);

        layout.setCenter(gameBoardLayout);

        scene = new Scene(layout, 1200, 800);

        buildButtons();
        Pane pane = new Pane(btBack);
        scene = new Scene(pane, 1080, 720);
    }

    private void buildButtons() {
        btBack = new Button("Terug naar Lobby");
        btBack.setOnAction(e-> {
            model.switchScene("lobby");
        });
    }
}
