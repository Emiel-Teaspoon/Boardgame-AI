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
        ReversiMove bestMove = null;
        for (ReversiMove move: moves) {
            if(bestScore < move.getScore()) {
                bestScore = move.getScore();
                bestMove = move;
            }
        }

//        for (ReversiMove move: moves) {
//            if (ReversiAI.this == reversi.player1) {
//                reversi.getPossibleMoves(reversi.getBoard().fakeMove(move), (ReversiPlayer) reversi.player2);
//            } else {
//                reversi.getPossibleMoves(reversi.getBoard().fakeMove(move), (ReversiPlayer) reversi.player1);
//            }
//        }

        doMove(bestMove);
    }

    @Override
    public void doMove(Move move) {
        super.doMove(move);
    }
}
