package boardgame;

import GameModuleReversi.GameModuleReversi;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class BoardGameController {

    private Stage primaryStage;

    private StartModel startModel;
    private SettingsModel settingsModel;
    private LobbyModel lobbyModel;
    private GameModel gameModel;

    private HashMap<String, Scene> scenes = new HashMap<>();
    private HashMap<String, Game> games = new HashMap<>();

    //private ConnectionHandler connectionHandler;

    public BoardGameController() {
        startModel = new StartModel();
        settingsModel = new SettingsModel();

        StartScene startScene = new StartScene(this, startModel);

        scenes.put("StartScene", startScene.getScene());
    }

    public void loadAvailableGames() {
        games.put("Reversi", new GameModuleReversi());
    }

    public void createLobbyScene() {
        if (lobbyModel == null) {
            lobbyModel = new LobbyModel();
            LobbyScene lobbyScene = new LobbyScene(this, lobbyModel);
            scenes.put("LobbyScene", lobbyScene.getScene());
        }
    }

    /**
     * Creates the game model and scene. Also fill the gameModel with the necessary information.
     */
    public void prepareGameScene() {
        if (gameModel == null) {
            gameModel = new GameModel();
            gameModel.setPlayerOne(startModel.getName());
            gameModel.setCurrentGame(games.get(lobbyModel.getSelectedGame()));
            gameModel.setPlayerTwo(lobbyModel.getChosenOpponent());
            GameScene gameScene = new GameScene(this, gameModel);
            scenes.put("GameScene", gameScene.getScene());
        }
        gameModel.setPlayerOne(startModel.getName());
        gameModel.setCurrentGame(games.get(lobbyModel.getSelectedGame()));
        gameModel.setPlayerTwo(lobbyModel.getChosenOpponent());
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

    public void updateIPAdress(String IP) {settingsModel.setIPAddress(IP);}

    public void updateGateway(String gateway) {settingsModel.setGateway(Integer.parseInt(gateway));}
/*
    public void establishConnection() {
        connectionHandler = new ConnectionHandler(settingsModel.getIPAddress(), settingsModel.getGateway());
        // TODO remove test code if no longer needed.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        if (connectionHandler.login("henk")) {
            System.out.println("woohoo");
        } else {
            System.out.println("failed");
        }

        if (connectionHandler.login("henk")) {
            System.out.println("woohoo");
        } else {
            System.out.println("failed");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        connectionHandler.subscribe("Reversi");
    }

    public ConnectionHandler getConnectionHandler() {return connectionHandler;}

    public void updatePlayerList() {
        lobbyModel.setArrayPlayerList(connectionHandler.getPlayerList());
    }
*/
}
