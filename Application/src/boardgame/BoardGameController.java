package boardgame;

import game.Game;
import game.Player;
import game.reversi.Reversi;
import game.reversi.ReversiAI;
import game.reversi.ReversiPlayer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardGameController {

    private Stage primaryStage;

    private StartModel startModel;
    private SettingsModel settingsModel;
    private LobbyModel lobbyModel;
    private GameModel gameModel;

    private HashMap<String, Scene> scenes = new HashMap<>();
    private HashMap<String, Game> games = new HashMap<>();

    private ConnectionHandler connectionHandler;

    public BoardGameController() {
        connectionHandler = new ConnectionHandler(this);

        startModel = new StartModel();
        settingsModel = new SettingsModel();

        StartScene startScene = new StartScene(this, startModel);

        scenes.put("StartScene", startScene.getScene());
    }

    public void loadAvailableGames() {
        // TODO: change to server input
        if (games.get("Reversi") == null) {
            games.put("Reversi", new Reversi(new ReversiPlayer(Player.Color.WHITE), new ReversiAI(Player.Color.BLACK), this));
        } else {
            games.replace("Reversi", new Reversi(new ReversiPlayer(Player.Color.WHITE), new ReversiAI(Player.Color.BLACK), this));
        }
    }

    public void createLobbyScene() {
        if (lobbyModel == null) {
            lobbyModel = new LobbyModel(this);
            LobbyScene lobbyScene = new LobbyScene(this, lobbyModel);
            scenes.put("LobbyScene", lobbyScene.getScene());
        }
    }

    /**
     * Start the connection to the server
     */
    public void connectToServer() {
        // 145.33.225.170
        connectionHandler.connect("localhost", 7789);
        connectionHandler.login("Test");
    }

    public void handleMessage(HashMap<String, String> message) {
        switch (message.get("type")) {
            case "CHALLENGE":
                // Contains keys: challenger, challengenumber, gametype
                System.out.println("challenged");
            case "MATCH":
                // Contains keys
                // TODO: add better check
                if (lobbyModel != null) lobbyModel.message(message);
                break;
            case "MOVE":
                // Contains keys: player, move, details
            case "WIN":
                // The player won
                // Contains keys: playeronescore, playertwoscore, comment
            case "LOSE":
                // The player lost
                // Contains keys: playeronescore, playertwoscore, comment
            case "DRAW":
                // The player lost
                // Contains keys: playeronescore, playertwoscore, comment
                //TODO: Add better check;
                if (gameModel != null) gameModel.getCurrentGame().message(message);
                break;
            default:
                break;
        }
    }

    /**
     * Creates the game model and scene. Also fill the gameModel with the necessary information.
     */
    public void prepareGameScene() {
        gameModel = new GameModel();
        gameModel.setPlayerOne(startModel.getName());
        gameModel.setCurrentGame(games.get("Reversi"));
        gameModel.setPlayerTwo(lobbyModel.getChosenOpponent());
        GameScene gameScene = new GameScene(this, gameModel);
        scenes.put("GameScene", gameScene.getScene());
    }

    public boolean verifyGateway(String currentInput) {
        boolean isInteger = false;
        try {
            Integer.parseInt(currentInput);
            isInteger = true;
        } catch (NumberFormatException currentException) {
        }
        return isInteger;
    }

    public void switchScene(String sceneName, String sceneTitle) {
        primaryStage.setTitle(sceneTitle);
        primaryStage.setScene(scenes.get(sceneName));
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void updateIPAdress(String IP) {
        settingsModel.setIPAddress(IP);
    }

    public void updateGateway(String gateway) {
        settingsModel.setGateway(Integer.parseInt(gateway));
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
}
