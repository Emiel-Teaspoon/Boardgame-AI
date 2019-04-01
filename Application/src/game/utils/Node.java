package game.utils;

import game.ai.Direction;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public enum NodeState { EMPTY, WHITE, BLACK }

    private int x;
    private int y;
    private int weight;
    private NodeState state;
    private Board board;

    public Node(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.state = NodeState.EMPTY;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public NodeState getState() {
        return state;
    }

    public String getGraphic() {
        switch (state) {
            case EMPTY:
                return "-";
            case WHITE:
                return "O";
            case BLACK:
                return "X";
        }
        return " ";
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOccupied() {
        return getState() != NodeState.EMPTY;
    }

    public List<Node> getNeighbourNodes() {
        List<Node> neighbours = new ArrayList<>();
        for (int y = getY() - 1; y <= getY() + 1; y++) {
            for (int x = getX() - 1; x <= getX() + 1; x++) {
                Node node = getBoard().getNode(x,y);
                if(node != null) {
                    neighbours.add(node);
                }
            }
        }
        return neighbours;
    }

    public boolean isEnemy(NodeState state) {
        if(getState() == NodeState.EMPTY) {
            return false;
        }
        return getState() != state;
    }

    public static Direction getDirection(Node origin, Node dir) {
//        System.out.println("Checking node: " + origin.getX() + "   " + origin.getY());
//        System.out.println((dir.getX() - origin.getX())+ " / " + (dir.getY() - origin.getY()));
        return new Direction(dir.getX() - origin.getX(), dir.getY() - origin.getY());
    }

    public static Node getNextNodeInDir(Board board, Node origin, Direction dir) {
        return board.getNode(origin.getX() + dir.getX(), origin.getY() + dir.getY());
    }

    public int getWeight() {
        return weight;
    }
}
