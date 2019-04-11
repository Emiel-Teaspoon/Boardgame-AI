package boardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class StartScene {

    private BoardGameController controller;
    private StartModel model;

    private BorderPane mainLayout;
    private HBox settingsBox;
    private TextFlow errorPane;
    private GridPane centerGrid;
    private HBox buttonLayout;

    private Text errorText;
    private Label nameLabel;
    private TextField nameField;
    private Button localPlayButton;
    private Button onlinePlayButton;
    private Button settingsButton;

    public StartScene(BoardGameController controller, StartModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        createNodes();
        configureLayoutManagers();
        createActionListeners();

        return new Scene(mainLayout, 500, 500);
    }

    /**
     * Creates the various nodes for the start scene.
     */
    private void createNodes() {
        // Create a Text object, color it crimson and set the font size to 14.
        errorText = new Text("");
        errorText.setFill(Color.CRIMSON);
        errorText.setFont(new Font(14));
        // Create the settings button.
        settingsButton = new Button("Instellingen aanpassen");

        nameField = new TextField(controller.getSettingsName());
        nameLabel = new Label("Naam: ");
        localPlayButton = new Button("Speel lokaal");
        onlinePlayButton = new Button("Speel online");
    }

    /**
     * Create the various grids, modify them where needed and add the child nodes to them.
     */
    private void configureLayoutManagers() {
        mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        settingsBox = new HBox();
        settingsBox.setAlignment(Pos.CENTER_RIGHT);
        settingsBox.getChildren().add(settingsButton);
        mainLayout.setTop(settingsBox);

        centerGrid = new GridPane();
        centerGrid.setAlignment(Pos.CENTER);
        centerGrid.setHgap(10.0);
        centerGrid.setVgap(12.0);

        errorPane = new TextFlow();
        errorPane.setTextAlignment(TextAlignment.LEFT);
        errorPane.getChildren().addAll(errorText);
        centerGrid.add(errorPane, 0, 0, 3, 1);

        centerGrid.add(nameLabel, 1, 1);
        centerGrid.add(nameField, 2, 1);

        buttonLayout = new HBox();
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10.0);

        buttonLayout.getChildren().addAll(localPlayButton, onlinePlayButton);
        centerGrid.add(buttonLayout, 1, 2, 2, 1);

        mainLayout.setCenter(centerGrid);
    }

    /**
     * Function to create the action listeners for the various buttons on the start scene.
     */
    private void createActionListeners() {
        /**
         * Create the settings window and the action listener that takes you there.
         */
        SettingsWindow settingsWindow = new SettingsWindow(controller, new SettingsModel());
        settingsButton.setOnAction(e -> settingsWindow.display());
        /**
         * Create the action listener for the local play button.
         * Before the scene switches and the name is stored in the model, there is a valid name check.
         */
        localPlayButton.setOnAction(e -> {
            if (nameField.getText().length() > 2 && nameField.getText().length() < 13) {
                model.setName(nameField.getText());
                controller.setSettingsName(nameField.getText());
                // TODO add local play scene here.
                // TODO add switch to local play scene here
                errorText.setText("");
                // TODO remove below message once above TODO's have been implemented
                errorText.setText("Deze functionaliteit is nog niet geimplementeerd.");
            } else {
                errorText.setText("Vul een naam in die uit minimaal 3 en maximaal 12 tekens bestaat.");
            }
        });
        /**
         * Create the action listener for the online play button.
         * Before the scene switches and the name is stored in the model, there is a valid name check.
         */
        onlinePlayButton.setOnAction(e -> {
            if (nameField.getText().length() > 2 && nameField.getText().length() < 13) {
                model.setName(nameField.getText());
                controller.setSettingsName(nameField.getText());
                //controller.establishConnection();
                controller.createLobbyScene();
                //controller.updatePlayerList();
                controller.switchScene("LobbyScene", "Lobby");
                errorText.setText("");
            } else {
                errorText.setText("Vul een naam in die uit minimaal 3 en maximaal 12 tekens bestaat.");
            }
        });

    }

}
