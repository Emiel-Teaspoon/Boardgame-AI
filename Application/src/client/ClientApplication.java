package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    Stage stage;

    public void setTitle(String title) {
        Platform.runLater(() -> {
            stage.setTitle(title);
        });
    }

    public void setScene(Scene scene) {
        Platform.runLater(() -> stage.setScene(scene));
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        new ClientModel(this);
        stage.show();
    }
}
