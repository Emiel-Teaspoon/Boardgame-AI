package game.reversi;

import game.AI;
import game.Move;

import java.util.List;

public class ReversiAI extends ReversiPlayer {

    public ReversiAI(Color color) {
        super(color);
    }

    public void play() {
        List<ReversiMove> moves = reversi.getPossibleMoves(this);

        if(moves.isEmpty()) {
            doMove(null);
            return;
        }

        int bestScore = -1;
        ReversiMove bestmove = null;
        for (ReversiMove move: moves) {
            if(bestScore < move.getScore()) {
                bestScore = move.getScore();
                bestmove = move;
            }
        }

        doMove(bestmove);
    }

    @Override
    public void doMove(Move move) {
        super.doMove(move);
    }
}
