package game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    protected int width;
    protected int height;
    private List<Node> nodes;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.nodes = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodes.add(new Node(x, y));
            }
        }
    }

    public List<? extends Node> getNeighbourNodes(Node node) {
        List<Node> neighbours = new ArrayList<>();
        for (int y = node.getY() - 1; y <= node.getY() + 1; y++) {
            for (int x = node.getX() - 1; x <= node.getX() + 1; x++) {
                Node neighbour = getNode(x,y);
                if(neighbour != null) {
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<? extends Node> getNodes() {
        return nodes;
    }

    public Node getNode(int x, int y) {
        if(x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            return null;
        }
        return nodes.get(y * 8 + x);
    }
}
