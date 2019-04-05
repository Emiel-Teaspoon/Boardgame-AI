package game.ai;

import game.utils.Board;
import game.utils.Node;
import game.utils.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseAI extends AbstractAI {

    private List<Move> possibleMoves;

    public BaseAI(Board board, Node.NodeState color) {
        super(board, color);
        this.board = board;
        this.possibleMoves = new ArrayList<>();
    }

    @Override
    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Move doMove() {
        return null;
    }

    @Override
    public Move getBestPossibleMove() {
        List<Move> bestMoves = new ArrayList<>();
        int highestScore = 0;
        Random rand = new Random();
        for (Move move : possibleMoves) {
            if(move.getScore() > highestScore) {
                bestMoves.clear();
                highestScore = move.getScore();
                bestMoves.add(move);
            }
            else if (move.getScore() == highestScore) {
                bestMoves.add(move);
            }
        }
        if(!bestMoves.isEmpty()) {
            return bestMoves.get(rand.nextInt(bestMoves.size()));
        }
        else {
            return null;
        }
    }

    public void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}