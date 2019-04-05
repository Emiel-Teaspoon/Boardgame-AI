package boardgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LobbyModel {

    private ObservableList<String> playerList = FXCollections.observableArrayList("R2D2", "C3PO");
    private ObservableList<String> gameList = FXCollections.observableArrayList("Reversi", "TicTacToe");

    public void addPlayerToList(String player) {
        playerList.add(player);
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
}
