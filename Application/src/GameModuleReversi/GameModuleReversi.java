package GameModuleReversi;

import boardgame.Game;
import javafx.scene.layout.Pane;


// TODO zet gameboardDS om in key value pairs genummerd 0 tot 63

public class GameModuleReversi implements Game{

    private final GameBoard gameBoard;

    public GameModuleReversi() {
        gameBoard = new GameBoard(80, 8, 8);
    }

    @Override
    public Pane startGame() {
        return gameBoard.drawBoard();
    }
}
