package boardgame;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class GameModuleReversi implements Game{

    private Tile[][] gameBoardDS = new Tile[8][8];
    private final int TILE_SIZE = 80;

    public Pane drawGameBoard() {
        Pane gameBoard = new Pane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * TILE_SIZE);
                tile.setTranslateY(i * TILE_SIZE);

                gameBoard.getChildren().add(tile);
                gameBoardDS[j][i] = tile;
            }
        }

        gameBoardDS[3][3].drawBlackPiece();
        gameBoardDS[3][4].drawWhitePiece();
        gameBoardDS[4][3].drawWhitePiece();
        gameBoardDS[4][4].drawBlackPiece();

        return gameBoard;
    }

    @Override
    public Pane startGame() {
        return null;
    }

    public class Tile extends StackPane {
        private final Circle circle = new Circle(TILE_SIZE*0.45);
        private Color circleColor;

        public Tile() {
            circleColor = Color.GREEN;
            circle.fillProperty().setValue(circleColor);

            Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
            tile.setFill(Color.GREEN);
            tile.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);

            getChildren().addAll(tile, circle);

            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    drawBlackPiece();
                    System.out.println(e);
                }

                if (e.getButton() == MouseButton.SECONDARY) {
                    drawWhitePiece();
                }

                if (e.getButton() == MouseButton.MIDDLE) {
                    circle.fillProperty().setValue(Color.GREEN);
                }
            });
        }

        public void drawBlackPiece() {
            Color black = Color.BLACK;
            circle.fillProperty().setValue(black);
            circleColor = black;
        }

        public void drawWhitePiece() {
            Color white = Color.WHITE;
            circle.fillProperty().setValue(white);
            circleColor = white;
        }

        public Color getCircleColor() {return circleColor;}

    }

}
