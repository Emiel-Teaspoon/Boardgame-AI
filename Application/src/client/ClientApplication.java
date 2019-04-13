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
       Platform.runLater(() -> {
           stage.setScene(scene);
       });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        new ClientModel(this);
//
//        new Thread(new ClientModel(this)).start();

        stage.show();
    }

    public static void main(String args) {
        launch(args);
        System.exit(0);
    }
}
