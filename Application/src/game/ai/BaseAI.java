package game.ai;

import game.utils.Board;
import game.utils.Node;
import game.utils.Move;

import java.util.ArrayList;
import java.util.List;

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
        Move bestMove = null;
        int highestScore = 0;
        for (Move move : possibleMoves) {
            if(move.getScore() > highestScore) {
                highestScore = move.getScore();
                bestMove = move;
            }
        }
        return bestMove;
    }

    public void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}