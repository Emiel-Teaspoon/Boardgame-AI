package GameModuleReversi;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private List<Tile> tiles;
    private Tile[][] gameBoardDS = new Tile[8][8];

    private int sizeX;
    private int sizeY;
    private int tileSize;
    Pane gameBoard;


    public GameBoard(int tileSize, int sizeX, int sizeY) {
        this.gameBoard = new Pane();
        this.tileSize = tileSize;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        buildBoard();
    }

    public void buildBoard() {
        tiles = new ArrayList<>();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Tile tile = new Tile(tileSize, x, y);
                tile.setTranslateX(x * tileSize);
                tile.setTranslateY(y * tileSize);

                gameBoard.getChildren().add(tile);
                tiles.add(tile);

                System.out.println("meme");
            }
        }
    }

    public Pane drawBoard() {

/*
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Tile tile = new Tile(tileSize, j, i);
                tile.setTranslateX(j * tileSize);
                tile.setTranslateY(i * tileSize);

                gameBoard.getChildren().add(tile);
                gameBoardDS[j][i] = tile;
            }
        }*/
/*
        gameBoardDS[3][3].drawBlackPiece();
        gameBoardDS[3][4].drawWhitePiece();
        gameBoardDS[4][3].drawWhitePiece();
        gameBoardDS[4][4].drawBlackPiece();

        System.out.println(gameBoardDS[3][3]);
        System.out.println(gameBoardDS[3][3].getState());
        System.out.println(gameBoardDS[3][3].getX());
        System.out.println(gameBoardDS[3][3].getY());
*/
        getTile(3,3).drawBlackPiece();
        getTile(3,4).drawWhitePiece();
        getTile(4,3).drawWhitePiece();
        getTile(4,4).drawBlackPiece();

        System.out.println(getTile(3,3).getState());
        System.out.println(getTile(3, 3).getX());
        System.out.println(getTile(3, 3).getY());
        System.out.println(getTile(3, 3));

        return gameBoard;
    }

    public Tile getTile(int x, int y) {
        return tiles.get(y * 8 + x);
    }



}
