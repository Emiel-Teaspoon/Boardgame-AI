package boardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class SettingsWindow {

    private Stage settingsStage;

    private BoardGameController controller;

    private BorderPane mainLayout;
    private HBox buttonLayout;

    private Text errorText;
    private Label IPLabel;
    private Label gatewayLabel;
    private Label AICheckLabel;
    private TextField IPTextField;
    private TextField gatewayTextField;
    private RadioButton AICheckButton;

    private Button backButton;
    private Button settingsResetButton;
    private Button saveButton;

    /**
     * Code weggehaald van https://farenda.com/java/java-regex-matching-ip-address/
     */
    private final static String IPSegment = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private final static String IPRegex = IPSegment + "\\." + IPSegment + "\\." + IPSegment + "\\." + IPSegment;
    private final static Pattern IPPattern = Pattern.compile(IPRegex);

    public SettingsWindow(BoardGameController controller) {
        this.controller = controller;
    }

    public void display() {
        settingsStage = new Stage();
        settingsStage.setTitle("Instellingen aanpassen");
        settingsStage.initModality(Modality.APPLICATION_MODAL);

        createNodes();
        configureLayoutManagers();
        createActionListeners();

        settingsStage.setScene(new Scene(mainLayout, 400, 300));
        settingsStage.showAndWait();
    }

    private void createNodes() {
        errorText = new Text();
        errorText.setFont(new Font(12));
        errorText.setFill(Color.CRIMSON);

        IPLabel = new Label("IP adres: ");
        IPTextField = new TextField();

        gatewayLabel = new Label("Poort: ");
        gatewayTextField = new TextField();

        AICheckLabel = new Label("AI aanzetten: ");
        AICheckButton = new RadioButton();

        backButton = new Button("Terug");
        settingsResetButton = new Button("Standaard instellingen");
        saveButton = new Button("Wijzigingen opslaan");
    }

    private void configureLayoutManagers() {
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

        buttonLayout.getChildren().addAll(backButton, settingsResetButton, saveButton);
        settingsLayout.add(buttonLayout, 0, 4, 2, 1);

        TextFlow errorArea = new TextFlow();
        errorArea.setTextAlignment(TextAlignment.CENTER);
        errorArea.getChildren().add(errorText);

        mainLayout.setTop(errorArea);
        mainLayout.setCenter(settingsLayout);
    }

    private void createActionListeners() {
        backButton.setOnAction(e -> settingsStage.close());
        // TODO implement settings reset button
        saveButton.setOnAction(e -> {
            if (IPPattern.matcher(IPTextField.getText()).matches()) {
                controller.updateIPAdress(IPTextField.getText());
            } else {
                errorText.setText("Een ongeldig IP adres is ingevoerd.\n");
            }
            String gateway = gatewayTextField.getText();
            if(controller.verifyGateway(gateway)) {
                controller.updateGateway(gateway);
            } else {
                errorText.setText(errorText.getText() + "Een ongeldige poort is ingevoerd.");
            }
            if (controller.verifyGateway(gateway) && IPPattern.matcher(IPTextField.getText()).matches()) {
                errorText.setText("");
            }
            controller.updateSettings();
        });
    }
}
