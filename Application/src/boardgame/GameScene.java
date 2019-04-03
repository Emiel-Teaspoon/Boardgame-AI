package boardgame;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class GameScene {

    private BoardGameController controller;
    private GameModel model;

    public GameScene(BoardGameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
    }

    public Scene getScene() {
        HBox layout = new HBox();

        return new Scene(layout, 500, 500);
    }
}
