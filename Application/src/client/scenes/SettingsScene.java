package client.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import client.ClientModel;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SettingsScene extends ClientScene {

    private BorderPane mainLayout;
    private HBox buttonLayout;

    private Text errorText;
    private Label IPLabel;
    private Label gatewayLabel;
    private Label AICheckLabel;
    private TextField IPTextField;
    private TextField gatewayTextField;
    private RadioButton AICheckButton;

    private Button btBack;
    private Button settingsResetButton;
    private Button saveButton;

    /**
     * Code weggehaald van https://farenda.com/java/java-regex-matching-ip-address/
     */
    private final static String IPSegment = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private final static String IPRegex = IPSegment + "\\." + IPSegment + "\\." + IPSegment + "\\." + IPSegment;
    private final static Pattern IPPattern = Pattern.compile(IPRegex);

    public SettingsScene(ClientModel model) {
        super(model);
        buildScene();
    }

    /**
     * Set settings to default
     */
    private void defaultSettings() {
        model.setDefaultSettings();
        IPTextField.setText(model.getSettings().get("host"));
        gatewayTextField.setText(model.getSettings().get("port"));
    }

    /**
     * Save the settings
     */
    private void save() {
        HashMap<String, String> newSettings = new HashMap<>();
        errorText.setText("");

        if (IPPattern.matcher(IPTextField.getText()).matches() || IPTextField.getText().equals("localhost")) {
            newSettings.put("host", IPTextField.getText());
            System.out.println("new host = " + IPTextField.getText());
        } else {
            errorText.setText("Een ongeldig IP adres is ingevoerd.\n");
        }

        String gateway = gatewayTextField.getText();
        try {
            int port = Integer.parseInt(gateway);
            if (port > 0) {
                newSettings.put("port", gateway);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            errorText.setText(errorText.getText() + "Een ongeldige poort is ingevoerd.");
        }

        if (!newSettings.isEmpty()) {
            model.updateSettings(newSettings);
        }
    }

    @Override
    public String getName() {
        return "Game Client - Settings";
    }

    @Override
    public void buildScene() {
        buildNodes();
        buildButtons();
        buildLayoutManagers();

        scene = new Scene(mainLayout, 640, 480);
    }

    @Override
    public void onEnter() {
        System.out.println(model.getSettings().toString());
    }

    @Override
    public void onLeave() {
        System.out.println(model.getSettings().toString());
    }

    private void buildButtons() {
        btBack = new Button("Terug");
        btBack.setOnAction(e -> {
            model.switchScene("start");
        });

        settingsResetButton = new Button("Standaard Instellingen");
        settingsResetButton.setOnAction(e -> {
            defaultSettings();
        });

        saveButton = new Button("Wijzigingen opslaan");
        saveButton.setOnAction(e -> {
            save();
        });
    }

    private void buildLayoutManagers() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20, 0, 20, 20));
        mainLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane settingsLayout = new GridPane();
        settingsLayout.setAlignment(Pos.CENTER);
        settingsLayout.setHgap(10.0);
        settingsLayout.setVgap(12.0);

        settingsLayout.add(IPLabel, 0, 1);
        settingsLayout.add(IPTextField, 1, 1);
        settingsLayout.add(gatewayLabel, 0, 2);
        settingsLayout.add(gatewayTextField, 1, 2);
        settingsLayout.add(AICheckLabel, 0, 3);
        settingsLayout.add(AICheckButton, 1, 3);

        buttonLayout = new HBox();
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(5);

        buttonLayout.getChildren().addAll(btBack, settingsResetButton, saveButton);
        settingsLayout.add(buttonLayout, 0, 4, 2, 1);

        TextFlow errorArea = new TextFlow();
        errorArea.setTextAlignment(TextAlignment.CENTER);
        errorArea.getChildren().add(errorText);

        mainLayout.setTop(errorArea);
        mainLayout.setCenter(settingsLayout);
    }

    private void buildNodes() {
        errorText = new Text();
        errorText.setFont(new Font(12));
        errorText.setFill(Color.CRIMSON);

        IPLabel = new Label("IP adres: ");
        IPTextField = new TextField(model.getSettings().get("host"));

        gatewayLabel = new Label("Poort: ");
        gatewayTextField = new TextField(model.getSettings().get("port"));

        AICheckLabel = new Label("AI aanzetten: ");
        AICheckButton = new RadioButton();
    }
}
