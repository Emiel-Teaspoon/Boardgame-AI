package game.ai;

import game.utils.Board;
import game.utils.Node;
import game.utils.Move;
import game.utils.Player;

import java.util.List;

abstract class AbstractAI extends Player {

    Board board;

    public AbstractAI(Board board, Node.NodeState color) {
        super(board, color);
    }

    abstract List<Move> getPossibleMoves();
    abstract Move doMove();
    abstract Move getBestPossibleMove();

}
