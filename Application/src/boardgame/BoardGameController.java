package boardgame;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class BoardGameController {

    private StartModel startModel;
    private SettingsModel settingsModel;
    private LobbyModel lobbyModel;
    private GameModel gameModel;

    private HashMap<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public BoardGameController() {
        startModel = new StartModel();
        settingsModel = new SettingsModel();
        lobbyModel = new LobbyModel();
        gameModel = new GameModel();

        StartScene startScene = new StartScene(this, startModel);
        LobbyScene lobbyScene = new LobbyScene(this, lobbyModel);
        GameScene gameScene = new GameScene(this, gameModel);

        scenes.put("StartScene", startScene.getScene());
        scenes.put("LobbyScene", lobbyScene.getScene());
        scenes.put("GameScene", gameScene.getScene());
    }

    public boolean verifyGateway(String currentInput) {
        boolean isInteger = false;
        try {
            Integer.parseInt(currentInput);
            isInteger = true;
        } catch (NumberFormatException currentException){}
        return  isInteger;
    }

    public void switchScene(String sceneName, String sceneTitle) {
        primaryStage.setTitle(sceneTitle);
        primaryStage.setScene(scenes.get(sceneName));
    }

    public void setStage(Stage stage) {this.primaryStage = stage;}

    public void updateName(String name) {startModel.setName(name);}

    public void updateIPAdress(String IP) {settingsModel.setIPAddress(IP);}

    public void updateGateway(String gateway) {settingsModel.setGateway(Integer.parseInt(gateway));}

}
