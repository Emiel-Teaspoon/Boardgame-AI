package client.scenes;

//import com.sun.org.apache.xpath.internal.operations.String;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import client.ClientModel;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class StartScene extends ClientScene {

    private Button btSettings;
    private Button btTestgame;

    @Override
    public void onEnter() {
        if (model.getConnection().isConnected()) {
            model.getConnection().close();
        }
        System.out.println("disconnected");
    }

    @Override
    public void onLeave() {

    }

    private Button btPlay;

    private Label lbName;
    private Label lbError;

    private TextField tfName;

    public StartScene(ClientModel model) {
        super(model);
        buildScene();
    }

    private void play() {
        if (!model.getSettings().get("name").equals(tfName.getText())) {
            HashMap<String, String> newSetting = new HashMap<>();
            newSetting.put("name", tfName.getText());
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
        buildLabels();
        buildTextFields();

        BorderPane pane = new BorderPane();
        pane.setRight(btPlay);
        pane.setCenter(btTestgame);
        pane.setLeft(btSettings);
        pane.setTop(tfName);

        scene = new Scene(pane, 1080, 720);
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

        btTestgame = new Button("Test AI spel");
        btTestgame.setOnAction(e -> {
            model.prepareGameScene("henk", "ingrid", "henk");
        });
    }

    private void buildLabels() {
        lbName = new Label("naam");
        lbError = new Label("Errortext");
        lbError.setTextFill(Color.CRIMSON);
    }

    private void buildTextFields() {
        tfName = new TextField();
        tfName.setText(model.getSettings().get("name"));
    }


}
