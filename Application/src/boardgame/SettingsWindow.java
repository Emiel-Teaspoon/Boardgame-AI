package boardgame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow {

    private BoardGameController controller;

    private Text errorText;

    public SettingsWindow(BoardGameController controller) {
        this.controller = controller;
    }

    public void display() {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Instellingen aanpassen");
        settingsStage.initModality(Modality.APPLICATION_MODAL);

        errorText = new Text();
        errorText.setFont(new Font(12));
        errorText.setFill(Color.CRIMSON);

        Label IPLabel = new Label("IP adres: ");
        TextField IpTextField = new TextField();

        Label gatewayLabel = new Label("Poort: ");
        TextField gatewayTextField = new TextField();

        Label AICheckLabel = new Label("AI aanzetten: ");
        RadioButton AICheckButton = new RadioButton();

        Button saveButton = new Button("Wijzigingen opslaan");
        saveButton.setOnAction(e -> {
            controller.updateIPAdress(IpTextField.getText());
            String gateway = gatewayTextField.getText();
            if(controller.verifyGateway(gateway)) {
                controller.updateGateway(gateway);
            } else {
                errorText.setText("Een ongeldige poort is ingevoerd.\n");
            }
        });

        GridPane settingsLayout = new GridPane();
        settingsLayout.add(IPLabel, 0, 1);
        settingsLayout.add(IpTextField, 1, 1);
        settingsLayout.add(gatewayLabel, 0, 2);
        settingsLayout.add(gatewayTextField, 1, 2);
        settingsLayout.add(AICheckLabel, 0, 3);
        settingsLayout.add(AICheckButton, 1, 3);
        settingsLayout.add(saveButton, 2, 5);

        TextFlow errorArea = new TextFlow();
        errorArea.getChildren().add(errorText);

        VBox layouts = new VBox();
        layouts.getChildren().addAll(errorArea, settingsLayout);

        settingsStage.setScene(new Scene(layouts, 350, 200));
        settingsStage.showAndWait();
    }
}
