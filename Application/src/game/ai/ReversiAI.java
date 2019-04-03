package game.ai;

import game.utils.Board;
import game.utils.Node;
import game.utils.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReversiAI extends BaseAI {

    List<Node> occupied;
    List<Node> mine;
    List<Node> checked;
    List<Node> extraChecked;

    public enum Difficulty { SLAVE, MEDIUM, HARD, EXPERT, MASTER, UNBEATABLE }
    Difficulty difficulty;

    public ReversiAI(Board board, Node.NodeState color, Difficulty difficulty) {
        super(board, color);
        this.difficulty = difficulty;
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
                            if(!checked.contains(nextToCheck)) {
                                checked.add(nextToCheck);
                            }
                            if(!nextToCheck.isOccupied()) {
                                //TODO backtracking
                                for (Node clearNodeNeighbour: nextToCheck.getNeighbourNodes()) {
                                    if (clearNodeNeighbour.isEnemy(getColor()) && !checked.contains(clearNodeNeighbour)) {
                                        extraChecked.clear();
                                        checkingDir = Node.getDirection(nextToCheck, clearNodeNeighbour);
                                        if(!extraChecked.contains(clearNodeNeighbour)) {
                                            extraChecked.add(clearNodeNeighbour);
                                        }
                                        Node nextToCheckExtra = clearNodeNeighbour;
                                        boolean runNext = true;
                                        while (runNext) {
                                            nextToCheckExtra = Node.getNextNodeInDir(board, nextToCheckExtra, checkingDir);
                                            if(nextToCheckExtra == null) {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                                continue;
                                            }
                                            if(nextToCheckExtra.isOccupied()){
                                                if(nextToCheckExtra.isEnemy(getColor())) {
                                                    if(!extraChecked.contains(nextToCheckExtra)) {
                                                        extraChecked.add(nextToCheckExtra);
                                                    }
                                                }
                                                else {
                                                    runNext = false;
                                                    checked.addAll(extraChecked);
                                                }
                                            }
                                            else {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                            }
                                        }
                                    }
                                }

                                Move possibleMove = new Move(this, checked, nextToCheck);
                                boolean add = true;
                                for (Move move: moves) {
                                    if(move.getTargetNode().equals(nextToCheck)) {
                                        add = false;
                                    }
                                }
                                if(add) {
                                    moves.add(possibleMove);
                                }
                                run = false;
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public Move doMove() {
        List<Move> moves = getPossibleMoves();
        for (Move move : moves) {
            System.out.println("Possible " + move.toString());
        }

        setPossibleMoves(moves);
        if(getBestPossibleMove() != null) {
            System.out.println("\n" + getBestPossibleMove());
            board.displayMove(getBestPossibleMove());
        }
        else {
            System.out.println(getColor().name() + " cant move");
        }
        return super.doMove();
    }

    @Override
    public Move getBestPossibleMove() {
        switch (difficulty) {
            case SLAVE:
                if(getPossibleMoves().size() > 0) {
                    return getPossibleMoves().get(new Random().nextInt(getPossibleMoves().size()));
                }
                return null;
            case MEDIUM:

                break;
            case HARD:

                break;
            case EXPERT:

                break;
            case MASTER:

                break;
            case UNBEATABLE:
                return super.getBestPossibleMove();
        }
        return super.getBestPossibleMove();
    }
//
//    private Move getDifficultyLevelMove(Difficulty difficulty) {
//
//    }
}
