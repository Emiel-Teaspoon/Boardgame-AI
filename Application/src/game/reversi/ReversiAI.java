package game.reversi;

import game.AI;
import game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReversiAI extends ReversiPlayer {

    public ReversiAI(Color color, boolean isPlayable) {
        super(color, isPlayable);
    }

    public void play() {

        List<ReversiMove> moves = reversi.getPossibleMoves(this);
        List<ReversiMove> bestMoves = new ArrayList<>();

        if(moves.isEmpty()) {
            doMove(null);
            return;
        }

        int bestScore = Integer.MIN_VALUE;
        ReversiMove bestMove = null;
        for (ReversiMove move: moves) {
            if(bestScore < move.getScore()) {
                bestScore = move.getScore();
                bestMove = move;
                bestMoves.clear();
                bestMoves.add(move);
            }
            if(bestScore == move.getScore()) {
                bestMoves.add(move);
            }
        }

//        for (ReversiMove move: moves) {
//            if (ReversiAI.this == reversi.player1) {
//                reversi.getPossibleMoves(reversi.getBoard().fakeMove(move), (ReversiPlayer) reversi.player2);
//            } else {
//                reversi.getPossibleMoves(reversi.getBoard().fakeMove(move), (ReversiPlayer) reversi.player1);
//            }
//        }
        Random rand = new Random();
        doMove(bestMoves.get(rand.nextInt(bestMoves.size())));
    }

    @Override
    public void doMove(Move move) {
        super.doMove(move);
    }
}
