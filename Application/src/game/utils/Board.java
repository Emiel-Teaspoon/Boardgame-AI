package game.utils;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Node> nodes;

    private int sizeX;
    private int sizeY;

    public Board(int sizeX, int sizeY) {
        this.nodes = new ArrayList<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                nodes.add(new Node(x, y,  this));
            }
        }

        getNode(3,3).setState(Node.NodeState.WHITE);
        getNode(4,4).setState(Node.NodeState.WHITE);
        getNode(3,4).setState(Node.NodeState.BLACK);
        getNode(4,3).setState(Node.NodeState.BLACK);

        printBoard();

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodeState(Node node, Node.NodeState newState)
    {
        nodes.get(nodes.indexOf(node)).setState(newState);
    }

    public Node getNode(int x, int y) {
        if(x < 0 || y < 0 || x > sizeX - 1 || y > sizeY - 1) {
            return null;
        }
        return nodes.get(y * 8 + x);
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public void displayMove(Move move) {
        for (Node node : move.getOvertakenNodes()) {
            setNodeState(node, move.getColor());
        }
        printBoard();
    }

    public void printBoard() {
        int i = 0;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                System.out.print(nodes.get(i).getGraphic() + " ");
                i++;
            }
            System.out.print("\n");
        }

        System.out.print("\n");
        System.out.print("\n");
    }
}
