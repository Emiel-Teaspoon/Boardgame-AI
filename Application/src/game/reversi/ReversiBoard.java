package game.reversi;

import GameModuleReversi.Tile;
import game.Board;
import game.Node;
import game.Player;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ReversiBoard extends Board {

    List<ReversiNode> nodes;
    private List<Tile> tiles;
    private int tileSize = 80;
    Pane boardGraphic;

    Reversi game;

    public ReversiBoard(int width, int height, Reversi game) {
        super(width, height);
        this.game = game;
        boardGraphic = buildBoard();
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

    public Pane buildBoard() {
        tiles = new ArrayList<>();
        nodes = new ArrayList<>();
        Pane gameBoard = new Pane();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ReversiNode node = new ReversiNode(x, y, 0);
                Tile tile = new Tile(tileSize, x, y, 0);
                tile.setTranslateX(x * tileSize);
                tile.setTranslateY(y * tileSize);

                gameBoard.getChildren().add(tile);
                tiles.add(tile);
                nodes.add(node);
            }
        }

        getNode(3,3).assignPlayer(game.player2);
        getNode(3,4).assignPlayer(game.player1);
        getNode(4,3).assignPlayer(game.player1);
        getNode(4,4).assignPlayer(game.player2);

        updateBoard();

        return gameBoard;
    }

    public void updateBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(getNode(x,y).isOccupied()) {
                    getTile(x, y).drawPiece(getNode(x, y).getColor() == Player.Color.WHITE ? Tile.TileState.WHITE : Tile.TileState.BLACK);
                }
                else {
                    getTile(x, y).drawPiece(Tile.TileState.EMPTY);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            return null;
        }
        return tiles.get(y * 8 + x);
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

    public void displayMove(ReversiMove move) {
        for (ReversiNode node : move.checked) {
            getNode(node.getX(), node.getY()).assignPlayer(move.getPlayer());
        }
        updateBoard();
    }

    public Pane getBoardGraphic() {
        return boardGraphic;
    }
}
