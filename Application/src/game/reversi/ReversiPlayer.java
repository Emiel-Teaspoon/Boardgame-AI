package game.reversi;

import game.Move;
import game.Player;

public class ReversiPlayer extends Player {

    Reversi reversi;

    public ReversiPlayer(Color color) {
        super(color);
    }

    public void setReversi(Reversi reversi) {
        this.reversi = reversi;
    }

    public void doMove(ReversiMove move) {
        super.doMove(move);
        reversi.passMove(move);
    }
}
