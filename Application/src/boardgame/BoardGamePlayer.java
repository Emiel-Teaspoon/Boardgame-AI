package boardgame;

import javafx.application.Application;
import javafx.stage.Stage;

public class BoardGamePlayer extends Application {

    private BoardGameController controller = new BoardGameController();

    @Override
    public void start(Stage primaryStage) {
        controller.setStage(primaryStage);

        controller.switchScene("StartScene", "Startscherm");
        primaryStage.show();
    }
}
