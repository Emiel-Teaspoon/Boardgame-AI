package boardgame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StartScene {

    private BoardGameController controller;
    private StartModel model;

    private GridPane LoginGridPane;
    private TextField nameField;
    private Button localPlayButton;
    private Button onlinePlayButton;
    private Button settingsButton;

    public StartScene(BoardGameController controller, StartModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        configureGridPane();
        createLayoutControls();
        createActionListeners();

        return new Scene(LoginGridPane, 500, 500);
    }

    private void configureGridPane() {
        LoginGridPane = new GridPane();
        LoginGridPane.setAlignment(Pos.CENTER);

        LoginGridPane.setHgap(5);
        LoginGridPane.setVgap(0);
    }

    private void createLayoutControls() {
        nameField = new TextField();
        Label nameLabel = new Label("Naam: ");

        localPlayButton = new Button("Speel op de computer");
        onlinePlayButton = new Button("Speel online");
        settingsButton = new Button("Instellingen aanpassen");

        LoginGridPane.add(settingsButton, 3, 0);

        LoginGridPane.add(nameLabel, 1, 0);
        LoginGridPane.add(nameField, 1, 1);

        LoginGridPane.add(localPlayButton, 0, 3);
        LoginGridPane.add(onlinePlayButton, 3, 3);
    }

    private void createActionListeners() {
        SettingsWindow settingsWindow = new SettingsWindow(controller);
        localPlayButton.setOnAction(e -> model.setName(nameField.getText()));
        onlinePlayButton.setOnAction(e -> {
            model.setName(nameField.getText());
            controller.createLobbyScene();
            controller.switchScene("LobbyScene", "Lobby");
        });
        settingsButton.setOnAction(e -> settingsWindow.display());
    }

}
