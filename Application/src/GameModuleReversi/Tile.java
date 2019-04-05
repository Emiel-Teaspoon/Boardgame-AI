package GameModuleReversi;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {

    public enum TileState { EMPTY, WHITE, BLACK}
    private int x;
    private int y;
    private int weight;

    private TileState state;

    private final Circle circle;

    private Color circleColor = Color.GREEN;

    public Tile(int tileSize, int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;

        circle = new Circle(tileSize * 0.45);
        circle.fillProperty().setValue(circleColor);

        Rectangle tile = new Rectangle(tileSize, tileSize);
        tile.setFill(Color.GREEN);
        tile.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);

        getChildren().addAll(tile, circle);

        // TODO possible moves als mouse hover andere kleur geven
        setOnMouseEntered(e -> tile.setFill(Color.GREENYELLOW));
        setOnMouseExited(e -> tile.setFill(Color.GREEN));

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                drawPiece(TileState.BLACK);
            }

            if (e.getButton() == MouseButton.SECONDARY) {
                drawPiece(TileState.WHITE);
            }

            if (e.getButton() == MouseButton.MIDDLE) {
                state = TileState.EMPTY;
                circle.fillProperty().setValue(Color.GREEN);
            }
        });
    }

    public void drawPiece(TileState color) {
        if (color == TileState.BLACK) {
            state = TileState.BLACK;
            Color black = Color.BLACK;
            circle.fillProperty().setValue(black);
            circleColor = black;
        } else if (color == TileState.WHITE) {
            state = TileState.WHITE;
            Color white = Color.WHITE;
            circle.fillProperty().setValue(white);
            circleColor = white;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public boolean isOccupied() {
        return getState() != TileState.EMPTY;
    }

    public boolean isEnemy(TileState state) {
        if(getState() == TileState.EMPTY) {
            return false;
        }
        return getState() != state;
    }

    public int getWeight() {
        return weight;
    }
}
