package game.ai;

import game.utils.Board;
import game.utils.Node;
import game.utils.Move;

import java.util.ArrayList;
import java.util.List;

public class ReversiAI extends BaseAI {

    List<Node> occupied;
    List<Node> mine;
    List<Node> checked;
    List<Node> extraChecked;

    public enum Dir { N, NE, E, SE, S, SW, W, NW }

    public ReversiAI(Board board, Node.NodeState color) {
        super(board, color);
    }

    @Override
    public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();

        occupied = new ArrayList<>();
        mine = new ArrayList<>();
        checked = new ArrayList<>();
        extraChecked = new ArrayList<>();

        Direction checkingDir;

        for (Node node : board.getNodes()) {
            if(node.isOccupied()) {
                occupied.add(node);
                if(node.getState() == getColor()) {
                    mine.add(node);
                }
            }
        }

        for (Node node : mine) {
            for (Node neighbour : node.getNeighbourNodes()) {
                if(neighbour.isEnemy(getColor())) {
                    checked = new ArrayList<>();
                    checked.add(neighbour);
                    checkingDir = Node.getDirection(node, neighbour);
                    boolean run = true;
                    Node nextToCheck = neighbour;
                    while(run) {
                        nextToCheck = Node.getNextNodeInDir(board, nextToCheck, checkingDir);
                        if(nextToCheck == null) {
                            run = false;
                            continue;
                        }
                        if (!nextToCheck.isEnemy(getColor()) && nextToCheck.isOccupied()) {
                            run = false;
                        }
                        else {
                            checked.add(nextToCheck);
                            if(!nextToCheck.isOccupied()) {
                                Move possibleMove = new Move(this, checked, nextToCheck);
                                moves.add(possibleMove);
                                //TODO backtracking



                                run = false;
                            }
                        }
                    }
                }
            }
        }

//        setPossibleMoves(moves);

        return moves;
    }

    @Override
    public Move doMove() {
        setPossibleMoves(getPossibleMoves());
        if(getBestPossibleMove() != null) {
            board.displayMove(getBestPossibleMove());
        }
        else {
            System.out.println(getColor().name() + " cant move");
        }
        return super.doMove();
    }
}
