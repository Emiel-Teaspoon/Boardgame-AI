package game;

import java.util.Arrays;
import java.util.List;

public abstract class Game implements boardgame.Game {

    public Player player1;
    public Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public abstract List<? extends Move> getPossibleMoves(Player player);

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
