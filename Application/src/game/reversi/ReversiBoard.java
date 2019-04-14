package game.reversi;

import boardgame.BoardGameController;
import client.ClientModel;
import game.Board;
import game.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ReversiBoard extends Board implements Cloneable {

    private ClientModel controller;

    private List<ReversiNode> nodes;
    private List<Tile> tiles;
    private int tileSize = 80;
    private Pane boardGraphic;

    private TextArea gameInformation;
    private Label playerOneScore;
    private Label playerTwoScore;
    private Text whiteTimer;
    private Text blackTimer;

    private Reversi game;

    public ReversiBoard(int width, int height, Reversi game, ClientModel controller) {
        super(width, height);
        this.game = game;
        this.controller = controller;
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

    void setPlayer(ReversiPlayer player) {
        for (Tile tile : tiles) {
            tile.setMovePossible(false);
        }
        List<ReversiNode> nodes = new ArrayList<>();
        for (ReversiMove move : game.getPossibleMoves(player)) {
           if(!nodes.contains(move.getNode())) {
               nodes.add(move.getNode());
           }
        }

        for (ReversiNode node : nodes) {
            getTile(node.getX(), node.getY()).setMovePossible(true);
        }
    }

    public Pane buildBoard() {
        tiles = new ArrayList<>();
        nodes = new ArrayList<>();

        BorderPane mainLayout = new BorderPane();
        Pane gameBoard = new Pane();
        mainLayout.setCenter(gameBoard);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ReversiNode node = new ReversiNode(x, y, 0);
                Tile tile = new Tile(tileSize, x, y, game);
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

        gameInformation = new TextArea();
        gameInformation.setEditable(false);
        gameInformation.setMaxWidth(250);

        GridPane gameInfoGridLayout = new GridPane();
        gameInfoGridLayout.setAlignment(Pos.CENTER);
        gameInfoGridLayout.setTranslateX(585);
        gameInfoGridLayout.setTranslateY(0);
        gameInfoGridLayout.setVgap(10);

        playerOneScore = new Label("2");
        playerTwoScore = new Label("2");

        gameInfoGridLayout.add(gameInformation, 0, 0, 2, 1);
        gameInfoGridLayout.add(new Label("Wit"), 0, 3);
        gameInfoGridLayout.add(playerOneScore, 0, 4);
        gameInfoGridLayout.add(new Label("Zwart"), 1, 3);
        gameInfoGridLayout.add(playerTwoScore, 1, 4);

        FlowPane timerPaneWhite = new FlowPane();
        whiteTimer = new Text();
        whiteTimer.setFont(new Font(30));
        whiteTimer.setText("10");
        whiteTimer.setFill(Color.BLACK);
        timerPaneWhite.getChildren().add(whiteTimer);

        FlowPane timerPaneBlack = new FlowPane();
        blackTimer = new Text();
        blackTimer.setFont(new Font(30));
        blackTimer.setText("10");
        blackTimer.setFill(Color.BLACK);
        timerPaneBlack.getChildren().add(blackTimer);

        gameInfoGridLayout.add(timerPaneWhite, 0, 5);
        gameInfoGridLayout.add(timerPaneBlack, 1, 5);

        mainLayout.setRight(gameInfoGridLayout);

        Button backButton = new Button("Terug/Opgeven");
        backButton.setTranslateX(0);
        backButton.setTranslateY(375);
        backButton.setOnAction(e -> controller.switchScene("lobby"));

        mainLayout.setBottom(backButton);

        return mainLayout;
    }

    public void updateGameInfoDisplay(String text) {
        gameInformation.appendText(text + "\n");
    }

    public void updatePlayerscores(int playerOne, int playerTwo) {
        playerOneScore.setText("" + playerOne);
        playerTwoScore.setText("" + playerTwo);
    }

    public void updateWhiteTimer(String time) {
        blackTimer.setText("");
        whiteTimer.setText(time);
    }

    public void updateBlackTimer(String time) {
        whiteTimer.setText("");
        blackTimer.setText(time);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void updateBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).setMovePossible(false);
                if(getNode(x, y).isOccupied()) {
                    getTile(x, y).drawPiece(getNode(x, y).getColor() == Player.Color.WHITE ? Tile.TileState.WHITE : Tile.TileState.BLACK);
                }
                else {
                    getTile(x, y).drawPiece(Tile.TileState.EMPTY);
                }
            }
        }
    }

    public void disableMoves() {
        for (Tile tile : getTiles()) {
            tile.setMovePossible(false);
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

//    public ReversiBoard fakeMove(ReversiMove move) {
//        ReversiBoard clone = null;
//        try {
//            clone = (ReversiBoard) this.clone();
//            clone.setNodes(new ArrayList<>(this.nodes));
//            clone.setTiles(new ArrayList<>(this.tiles));
//            for (ReversiNode node : move.checked) {
//                clone.getNode(node.getX(), node.getY()).assignPlayer(move.getPlayer());
//            }
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return clone;
//    }

    public Pane getBoardGraphic() {
        return boardGraphic;
    }
}
