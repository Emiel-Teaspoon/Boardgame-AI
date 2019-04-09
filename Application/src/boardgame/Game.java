package boardgame;

import game.Player;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public interface Game {
    Pane startGame();

    HashMap<Player, Integer> scores();
}
