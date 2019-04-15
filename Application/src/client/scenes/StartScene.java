package client.scenes;

//import com.sun.org.apache.xpath.internal.operations.String;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import client.ClientModel;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.HashMap;

public class StartScene extends ClientScene {

    private Button btSettings;
    private Button btPlay;

    private BorderPane mainLayout;
    private HBox settingsBox;
    private TextFlow errorPane;
    private GridPane centerGrid;
    private HBox buttonLayout;

    private Label nameLabel;
    private TextField nameField;

    public StartScene(ClientModel model) {
        super(model);
        buildScene();
    }

    @Override
    public void onEnter() {
        if (model.getConnection().isConnected()) {
            model.getConnection().close();
        }
        System.out.println("disconnected");
        nameField.setText(model.getSettings().get("name"));
    }

    @Override
    public void onLeave() {

    }

    private void play() {
        if (!model.getSettings().get("name").equals(nameField.getText())) {
            HashMap<String, String> newSetting = new HashMap<>();
            newSetting.put("name", nameField.getText());
            model.updateSettings(newSetting);
        }

        model.switchScene("lobby");
    }

    @Override
    public String getName() {
        return "Game Client - Start";
    }

    @Override
    public void buildScene() {
        buildButtons();
        buildNodes();
        buildLayoutManagers();

        scene = new Scene(mainLayout, 640, 480);
    }

    private void buildLayoutManagers() {
        mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        settingsBox = new HBox();
        settingsBox.setAlignment(Pos.CENTER_RIGHT);
        settingsBox.getChildren().add(btSettings);
        mainLayout.setTop(settingsBox);

        centerGrid = new GridPane();
        centerGrid.setAlignment(Pos.CENTER);
        centerGrid.setHgap(10.0);
        centerGrid.setVgap(12.0);

        errorPane = new TextFlow();
        errorPane.setTextAlignment(TextAlignment.LEFT);
        centerGrid.add(errorPane, 0, 0, 3, 1);

        centerGrid.add(nameLabel, 1, 1);
        centerGrid.add(nameField, 2, 1);

        buttonLayout = new HBox();
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10.0);

        buttonLayout.getChildren().add(btPlay);
        centerGrid.add(buttonLayout, 1, 2, 2, 1);

        mainLayout.setCenter(centerGrid);
    }

    private void buildButtons() {
        btSettings = new Button("Instellingen");
        btSettings.setOnAction(e -> {
            model.switchScene("settings");
        });

        btPlay = new Button("Speel online");
        btPlay.setOnAction(e -> {
            play();
        });
    }

    private void buildNodes(){
        nameField = new TextField();
        nameLabel = new Label("Naam: ");
    }
}
