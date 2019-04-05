package GameModuleReversi;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private List<Tile> tiles;

    private int sizeX;
    private int sizeY;
    private int tileSize;

    public GameBoard(int tileSize, int sizeX, int sizeY) {
        this.tileSize = tileSize;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Pane drawBoard() {
        tiles = new ArrayList<>();
        Pane gameBoard = new Pane();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Tile tile = new Tile(tileSize, x, y, 0);
                tile.setTranslateX(x * tileSize);
                tile.setTranslateY(y * tileSize);

                gameBoard.getChildren().add(tile);
                tiles.add(tile);
            }
        }

        getTile(3,3).drawPiece(Tile.TileState.BLACK);
        getTile(3,4).drawPiece(Tile.TileState.WHITE);
        getTile(4,3).drawPiece(Tile.TileState.WHITE);
        getTile(4,4).drawPiece(Tile.TileState.BLACK);

        return gameBoard;
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x > sizeX - 1 || y > sizeY - 1) {
            return null;
        }
        return tiles.get(y * 8 + x);
    }

    public Tile getTile(int index) {
        return tiles.get(index);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTileState(Tile tile, Tile.TileState newState) {
        tiles.get(tiles.indexOf(tile)).drawPiece(newState);
    }

    public void displayMove(Move move) {
        for (Tile tile : move.getOvertakenTiles()) {
            setTileState(tile, move.getColor());
        }
    }



}
