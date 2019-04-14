package client;

import client.scenes.*;
import client.handlers.ConnectionHandler;
import client.handlers.SettingsHandler;
import game.Game;
import game.Player;
import game.reversi.Reversi;
import game.reversi.ReversiAI;
import game.reversi.ReversiPlayer;
import javafx.application.Platform;

import java.util.HashMap;

public class ClientModel {

    private ClientApplication application;
    private ConnectionHandler connectionHandler;
    private SettingsHandler settingsHandler;

    private GameScene gameScene;
    private LobbyScene lobbyScene;
    private SettingsScene settingsScene;
    private StartScene startScene;

    private Reversi reversiGame;

    private HashMap<String, ClientScene> scenes;
    private HashMap<String, Game> games = new HashMap<>();
    private GameModel gameModel;
    private String activeScene;

    public ClientModel(ClientApplication application) {
        this.application = application;
        initHandlers();

        scenes = new HashMap<>();
        initScenes();

        switchScene("start");
    }

    public void switchScene(String scene) {
        if (!scenes.containsKey(scene)) {
            throw new IllegalStateException("Scene \"" + scene + "\" does not exist");
        }

        if (activeScene != null) {
            scenes.get(activeScene).onLeave();
        }

        activeScene = scene;
        System.out.println(activeScene);
        scenes.get(activeScene).onEnter();

        ClientScene nextScene = scenes.get(activeScene);
        application.setTitle(nextScene.getName());
        application.setScene(nextScene.getScene());
    }

    public boolean connect() {
        String host = settingsHandler.getSettings().get("host");
        int port = Integer.parseInt(settingsHandler.getSettings().get("port"));
        return connectionHandler.connect(host, port);
    }

    public void login() {
        connectionHandler.login(settingsHandler.getSettings().get("name"));
    }

    public void handleMessage(HashMap<String, String> message) {
        if(reversiGame != null) {
            reversiGame.handleMessage(message);
        }
        switch (message.get("type")) {
            case "CHALLENGE":
                // Contains keys: challenger, challengenumber, gametype
                Platform.runLater(() -> lobbyScene.createChallenge(message.get("CHALLENGER"), message.get("CHALLENGENUMBER"), message.get("GAMETYPE")));
                System.out.println(message.get("gametype"));
                System.out.println(message.keySet().toString());
                break;
            case "CHALLENGE CANCELLED":
                Platform.runLater(() -> lobbyScene.removeChallenge(message.get("CHALLENGENUMBER")));
                break;
            case "MATCH":
                // Contains keys
                System.out.println("Match starting");
                prepareGameScene(settingsHandler.getSettings().get("name"), message.get("OPPONENT"), message.get("PLAYERTOMOVE"));
                // TODO: add better check
                //if (lobbyModel != null) lobbyModel.message(message);
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
            case "YOURTURN":
                //TODO: Add better check;
                //if (gameModel != null) gameModel.getCurrentGame().message(message);
                break;
            default:
                break;
        }
    }

    public void prepareGameScene(String me, String opponent, String playerToMove) {
        boolean isOpponent = false;
        if (playerToMove.equals(opponent)) {
            isOpponent = true;
        }
        //TODO Voeg spelers toe aan Reversispel
        //  Onderstaande code zou moeten werken als BoardGameController in de game map wordt vervangen door ClientModel
        //  Hoeft volgens mij alleen bij Reversi en Reversiboard.
        //  in plaats van control.switchScene("Lobby...   kan de switchScene("lobby") worden aangeroepen op de model.

        //Game newGame = new Reversi(new ReversiPlayer(Player.Color.WHITE), new ReversiAI(Player.Color.BLACK), this));

        reversiGame = new Reversi(new ReversiPlayer(Player.Color.WHITE), new ReversiPlayer(Player.Color.BLACK), this, connectionHandler, isOpponent);

        gameModel = new GameModel();
        gameModel.setPlayerOne(me);
        gameModel.setPlayerTwo(opponent);
        gameModel.setCurrentGame(reversiGame);

        switchScene("game");
        gameScene.startGame(gameModel);
    }

    // Inits
    public void initHandlers() {
        connectionHandler = new ConnectionHandler(this);
        settingsHandler = new SettingsHandler(this);
    }

    public void initScenes() {
        gameScene = new GameScene(this);
        scenes.put("game", gameScene);
        lobbyScene = new LobbyScene(this);
        scenes.put("lobby", lobbyScene);
        settingsScene = new SettingsScene(this);
        scenes.put("settings", settingsScene);
        startScene = new StartScene(this);
        scenes.put("start", startScene);
    }
    //

    // Settings
    public void setDefaultSettings() {
       settingsHandler.defaultSettings();
    }

    /**
     * Update settings and write to Settingshandler
     * @param settings the settings that need to be updated
     */
    public void updateSettings(HashMap<String, String> settings) {
        settingsHandler.updateSettings(settings);
    }

    /**
     * Getter for settings
     * @return HashMap with settings
     */
    public HashMap<String, String> getSettings() {
        return settingsHandler.getSettings();
    }
    //

    // Handler getters
    public ConnectionHandler getConnection() {
        return connectionHandler;
    }

    public SettingsHandler settingsHandler() {
        return settingsHandler;
    }
    //

    // Scene getters
    public GameScene getGameScene() {
        return gameScene;
    }

    public LobbyScene getLobbyScene() {
        return lobbyScene;
    }

    public SettingsScene getSettingsScene() {
        return settingsScene;
    }

    public StartScene getStartScene() {
        return startScene;
    }
    //
}
