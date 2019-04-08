package game.reversi;

import GameModuleReversi.Tile;
import game.Board;
import game.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ReversiBoard extends Board {

    List<ReversiNode> nodes;

    public ReversiBoard(int width, int height) {
        super(width, height);
    }

    @Override
    public List<ReversiNode> getNodes() {
        return nodes;
    }

    public ReversiNode getNode(int x, int y) {
        if(x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            return null;
        }
        return nodes.get(y * 8 + x);
    }

    public Pane drawBoard() {

        return null;
    }

    public List<ReversiNode> getNeighbourNodes(ReversiNode node) {
        List<ReversiNode> neighbours = new ArrayList<>();
        for (int y = node.getY() - 1; y <= node.getY() + 1; y++) {
            for (int x = node.getX() - 1; x <= node.getX() + 1; x++) {
                ReversiNode neighbour = getNode(x,y);
                if(neighbour != null) {
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

    public ReversiNode getNextNodeInDir(ReversiNode origin, Direction dir) {
        return getNode(origin.getX() + dir.getX(), origin.getY() + dir.getY());
    }
}
