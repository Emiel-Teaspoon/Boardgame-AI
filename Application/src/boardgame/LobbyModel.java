package boardgame;

import boardgame.Connection;
import boardgame.ConnectionHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyModel {

    private ObservableList<String> playerList;
    private ObservableList<String> gameList;

    private String selectedGame;
    private String chosenOpponent;

    private ConnectionHandler connectionHandler;
    private LobbyUpdater lobbyUpdater;

    public LobbyModel(BoardGameController controller) {
        playerList = FXCollections.observableArrayList();
        gameList = FXCollections.observableArrayList();

        // Connect to server if not connected yet
        if (!controller.getConnectionHandler().isConnected()) {
            controller.connectToServer();
        }

        connectionHandler = controller.getConnectionHandler();
        updateGameList(connectionHandler.getGameList());
        updatePlayerList(connectionHandler.getPlayerList());
        startUpdating();
    }

    /**
     * Handles incoming messages from the server.
     *
     * @param message HashMap containing type and information from message
     */
    public void message(HashMap<String, String> message) {
        switch (message.get("type")) {
            case "CHALLENGE":
                // TODO: Challenge should be handled here
                // Contains keys: challenger, challengenumber, gametype
                break;
            case "MATCH":
                // TODO: Match starts, make new Game
                break;
        }
    }

    /**
     * Creates new LobbyUpdater and runs it to update playerlist. Run when entering lobby.
     */
    public void startUpdating() {
        lobbyUpdater = new LobbyUpdater(this, connectionHandler);
        new Thread(lobbyUpdater).start();
    }

    /**
     * Stops the LobbyUpdater from updating playerlist. Use when leaving lobby.
     */
    public void stopUpdating() {
        lobbyUpdater.close();
    }

    /**
     * Set the values of the GamesList
     *
     * @param games ArrayList containing Strings of games
     */
    public void updateGameList(ArrayList<String> games) {
        if (games == null) return;
        gameList.clear();
        gameList.addAll(games);
    }

    /**
     * Set the values of the PlayerList
     *
     * @param players ArrayList containing Strings of players
     */
    public void updatePlayerList(ArrayList<String> players) {
        if (players == null) return;
        playerList.clear();
        playerList.addAll(players);
    }

    public ObservableList<String> getPlayerList() {
        return playerList;
    }

    public void addGameToList(String game) {
        gameList.add(game);
    }

    public ObservableList<String> getGameList() {
        return gameList;
    }

    public String getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(String selectedGame) {
        this.selectedGame = selectedGame;
    }

    public String getChosenOpponent() {
        return chosenOpponent;
    }

    public void setChosenOpponent(String chosenOpponent) {
        this.chosenOpponent = chosenOpponent;
    }

    /**
     * Runnable that gets the playerlist and updates it in the lobby
     */
    private class LobbyUpdater implements Runnable {
        private LobbyModel lobbyModel;
        private ConnectionHandler handler;
        private boolean running;

        public LobbyUpdater(LobbyModel lobbyModel, ConnectionHandler handler) {
            this.lobbyModel = lobbyModel;
            this.handler = handler;
            this.running = true;
        }

        @Override
        public void run() {
            while (running) {
                ArrayList<String> players = handler.getPlayerList();
                if (players != null) Platform.runLater(() -> lobbyModel.updatePlayerList(players));

                try {
                    Thread.sleep(2000); // Request playerlist every **** ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close() {
            running = false;
        }
    }
}