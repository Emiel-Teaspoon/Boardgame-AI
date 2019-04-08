package game;

import java.util.Arrays;
import java.util.List;

public abstract class Game implements boardgame.Game {

    public List<Player> players;

    public Game(Player... players) {
        this.players = Arrays.asList(players);
    }

    public abstract List<? extends Move> getPossibleMoves(Player player);

    public List<Player> getPlayers() {
        return players;
    }
}
