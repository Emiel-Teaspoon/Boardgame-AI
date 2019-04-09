package game.reversi;

import game.Player;
import game.reversi.Reversi;
import game.reversi.ReversiPlayer;
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
    private boolean isMovePossible;
    private Rectangle tileGraphic;
    private Reversi game;

    public Tile(int tileSize, int x, int y, Reversi game) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.state = TileState.EMPTY;

        circle = new Circle(tileSize * 0.45);
        circle.fillProperty().setValue(circleColor);

        tileGraphic = new Rectangle(tileSize, tileSize);
        tileGraphic.setFill(Color.GREEN);
        tileGraphic.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);

        getChildren().addAll(tileGraphic, circle);


        // TODO possible moves als mouse hover andere kleur geven
    }

    public void drawPiece(TileState color) {
        if (color == TileState.BLACK) {
            state = TileState.BLACK;
            Color black = Color.BLACK;
            circle.fillProperty().setValue(black);
            circleColor = black;
            circle.setStroke(Color.DARKGRAY);
            circle.setStrokeWidth(3);
//            circle.setStrokeDashOffset(-1.5);
        } else if (color == TileState.WHITE) {
            state = TileState.WHITE;
            Color white = Color.WHITE;
            circle.fillProperty().setValue(white);
            circleColor = white;
            circle.setStroke(Color.LIGHTGRAY);
            circle.setStrokeWidth(3);
//            circle.setStrokeDashOffset(-1.5);
        }
    }

    public void setMovePossible(boolean movePossible) {
        isMovePossible = movePossible;
        if(isMovePossible) {
            setOnMouseEntered(e -> circle.setFill(Color.GREENYELLOW));
            setOnMouseExited(e -> circle.setFill(Color.GREEN));

            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    game.getBoard().disableMoves();
                    setOnMouseEntered(null);
                    setOnMouseExited(null);
                    circle.setFill(Color.GREEN);
                    game.passMove(x, y);
                }
            });
        }
        else {
            setOnMouseEntered(null);
            setOnMouseExited(null);
            setOnMouseClicked(null);
        }
        if(state == TileState.EMPTY) {
            circle.setFill(Color.GREEN);
        }
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public boolean isMovePossible() {
        return isMovePossible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
