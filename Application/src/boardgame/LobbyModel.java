package boardgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class LobbyModel {

    private ObservableList<String> playerList = FXCollections.observableArrayList("R2D2", "C3PO");
    private ObservableList<String> gameList = FXCollections.observableArrayList("Reversi", "TicTacToe");

    private ArrayList<String> arrayPlayerList;
    private ArrayList<String> arrayGameList;

    private String selectedGame;
    private String chosenOpponent;

    public ArrayList<String> getArrayPlayerList(){return arrayPlayerList;}
    public void setArrayPlayerList(ArrayList<String> arrayList){this.arrayPlayerList = arrayList;}

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
}
