package game.reversi;

import client.ClientModel;
import client.handlers.ConnectionHandler;
import game.Game;
import game.Player;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.*;

public class Reversi extends Game {

    private ClientModel controller;

    private ReversiBoard board;
    private ReversiPlayer currentPlayer;
    private List<ReversiMove> currentPossibleMoves;
    private HashMap<Player, Integer> scores;
    private ConnectionHandler connectionHandler;

    private boolean isOpponent;
    private boolean isFinished = false;
    private String myName;

    private int timer = 10;
    Thread timerThread;

    public Reversi(ReversiPlayer player1, ReversiPlayer player2, ClientModel controller) {
        super(player1, player2);
        this.controller = controller;
        scores = new HashMap<>();
        player1.setReversi(this);
        player2.setReversi(this);

        scores.put(player1, 2);
        scores.put(player2, 2);

        board = new ReversiBoard(8, 8, this, controller);

        board.setPlayer(player1);
    }

    public Reversi(ReversiPlayer player1, ReversiPlayer player2, ClientModel controller, ConnectionHandler handler, boolean isOpponent, String myName) {
        super(player1, player2);
        this.controller = controller;
        this.isOpponent = isOpponent;
        this.connectionHandler = handler;
        scores = new HashMap<>();
        player1.setReversi(this);
        player2.setReversi(this);

        scores.put(player1, 2);
        scores.put(player2, 2);
        this.myName = myName;

        board = new ReversiBoard(8, 8, this, controller);
        setCurrentPlayer(!isOpponent ? (ReversiAI) player1 : player2);
    }

    void startTimer() {
        stopTimer();
        timerThread = new Thread(() -> {
            boolean isAlive = true;
            while (timer > 0 && isAlive) {
                try {
                    Thread.sleep(1000);
                    timer--;
                    Platform.runLater(() -> {
                        if (currentPlayer.getColor() == Player.Color.WHITE) {
                            board.updateWhiteTimer("" + timer);
                        } else {
                            board.updateBlackTimer("" + timer);
                        }
                    });
                    if (timer <= 0) {
                        passMove(new ReversiMove(currentPlayer, null));
                        board.updateGameInfoDisplay("Tijd is op. Volgende speler is aan de beurt.");
                    }
                } catch (InterruptedException e) {
                    isAlive = false;
                }
            }
        });
        timerThread.start();
    }

    void stopTimer() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
        timer = 10;
    }

    @Override
    public List<ReversiMove> getPossibleMoves(Player player) {
        List<ReversiMove> moves = new ArrayList<>();

        List<ReversiNode> occupied = new ArrayList<>();
        List<ReversiNode> mine = new ArrayList<>();
        List<ReversiNode> checked = new ArrayList<>();
        List<ReversiNode> extraChecked = new ArrayList<>();

        Direction checkingDir;

        for (ReversiNode node : board.getNodes()) {
            if (node.isOccupied()) {
                occupied.add(node);
                if (node.isMine(player)) {
                    mine.add(node);
                }
            }
        }

        for (ReversiNode node : mine) {
            for (ReversiNode neighbour : board.getNeighbourNodes(node)) {
                if (neighbour.isEnemy(player)) {
                    checked = new ArrayList<>();
                    checked.add(neighbour);
                    checkingDir = ReversiNode.getDirection(node, neighbour);
                    boolean run = true;
                    ReversiNode nextToCheck = neighbour;
                    while (run) {
                        nextToCheck = board.getNextNodeInDir(nextToCheck, checkingDir);
                        if (nextToCheck == null) {
                            run = false;
                            continue;
                        }
                        if (nextToCheck.isMine(player) && nextToCheck.isOccupied()) {
                            run = false;
                        } else {
                            if (!checked.contains(nextToCheck)) {
                                checked.add(nextToCheck);
                            }
                            if (!nextToCheck.isOccupied()) {
                                //TODO backtracking
                                for (ReversiNode clearNodeNeighbour : board.getNeighbourNodes(nextToCheck)) {
                                    if (clearNodeNeighbour.isEnemy(player) && !checked.contains(clearNodeNeighbour)) {
                                        extraChecked.clear();
                                        checkingDir = ReversiNode.getDirection(nextToCheck, clearNodeNeighbour);
                                        if (!extraChecked.contains(clearNodeNeighbour)) {
                                            extraChecked.add(clearNodeNeighbour);
                                        }
                                        ReversiNode nextToCheckExtra = clearNodeNeighbour;
                                        boolean runNext = true;
                                        while (runNext) {
                                            nextToCheckExtra = board.getNextNodeInDir(nextToCheckExtra, checkingDir);
                                            if (nextToCheckExtra == null) {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                                continue;
                                            }
                                            if (nextToCheckExtra.isOccupied()) {
                                                if (nextToCheckExtra.isEnemy(player)) {
                                                    if (!extraChecked.contains(nextToCheckExtra)) {
                                                        extraChecked.add(nextToCheckExtra);
                                                    }
                                                } else {
                                                    runNext = false;
                                                    checked.addAll(extraChecked);
                                                }
                                            } else {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                            }
                                        }
                                    }
                                }

                                ReversiMove possibleMove = new ReversiMove(player, checked, nextToCheck);
                                boolean add = true;
                                for (ReversiMove move : moves) {
                                    if (move.getNode().equals(nextToCheck)) {
                                        add = false;
                                    }
                                }
                                if (add) {
                                    moves.add(possibleMove);
                                }
                                run = false;
                            }
                        }
                    }
                }
            }
        }
        return moves;

    }

    public List<ReversiMove> getPossibleMoves(ReversiBoard board, ReversiPlayer player) {

        List<ReversiMove> moves = new ArrayList<>();

        List<ReversiNode> occupied = new ArrayList<>();
        List<ReversiNode> mine = new ArrayList<>();
        List<ReversiNode> checked = new ArrayList<>();
        List<ReversiNode> extraChecked = new ArrayList<>();

        Direction checkingDir;

        for (ReversiNode node : board.getNodes()) {
            if (node.isOccupied()) {
                occupied.add(node);
                if (node.isMine(player)) {
                    mine.add(node);
                }
            }
        }

        for (ReversiNode node : mine) {
            for (ReversiNode neighbour : board.getNeighbourNodes(node)) {
                if (neighbour.isEnemy(player)) {
                    checked = new ArrayList<>();
                    checked.add(neighbour);
                    checkingDir = ReversiNode.getDirection(node, neighbour);
                    boolean run = true;
                    ReversiNode nextToCheck = neighbour;
                    while (run) {
                        nextToCheck = board.getNextNodeInDir(nextToCheck, checkingDir);
                        if (nextToCheck == null) {
                            run = false;
                            continue;
                        }
                        if (nextToCheck.isMine(player) && nextToCheck.isOccupied()) {
                            run = false;
                        } else {
                            if (!checked.contains(nextToCheck)) {
                                checked.add(nextToCheck);
                            }
                            if (!nextToCheck.isOccupied()) {
                                //TODO backtracking
                                for (ReversiNode clearNodeNeighbour : board.getNeighbourNodes(nextToCheck)) {
                                    if (clearNodeNeighbour.isEnemy(player) && !checked.contains(clearNodeNeighbour)) {
                                        extraChecked.clear();
                                        checkingDir = ReversiNode.getDirection(nextToCheck, clearNodeNeighbour);
                                        if (!extraChecked.contains(clearNodeNeighbour)) {
                                            extraChecked.add(clearNodeNeighbour);
                                        }
                                        ReversiNode nextToCheckExtra = clearNodeNeighbour;
                                        boolean runNext = true;
                                        while (runNext) {
                                            nextToCheckExtra = board.getNextNodeInDir(nextToCheckExtra, checkingDir);
                                            if (nextToCheckExtra == null) {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                                continue;
                                            }
                                            if (nextToCheckExtra.isOccupied()) {
                                                if (nextToCheckExtra.isEnemy(player)) {
                                                    if (!extraChecked.contains(nextToCheckExtra)) {
                                                        extraChecked.add(nextToCheckExtra);
                                                    }
                                                } else {
                                                    runNext = false;
                                                    checked.addAll(extraChecked);
                                                }
                                            } else {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                            }
                                        }
                                    }
                                }

                                ReversiMove possibleMove = new ReversiMove(player, checked, nextToCheck);
                                boolean add = true;
                                for (ReversiMove move : moves) {
                                    if (move.getNode().equals(nextToCheck)) {
                                        add = false;
                                    }
                                }
                                if (add) {
                                    moves.add(possibleMove);
                                }
                                run = false;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < moves.size(); i++) {
            System.out.println(moves.get(i).toString());
        }
        return moves;
    }

    void passMove(ReversiMove move) {
        if (move == null) {
            board.updateBoard();
        }
        else {
            if(move.getNode() == null) {
                board.updateBoard();
                return;
            }
            if(connectionHandler != null && move.getPlayer() == player1) {
                connectionHandler.move(move.getNode().toServerInput());
                stopTimer();
                setCurrentPlayer((ReversiPlayer) player2);
                System.out.println(move.getNode().toServerInput());
            }
            if (move.getPlayer() == player1) {
                int playerOneScore = scores.get(player1) + move.getActualScore();
                int playerTwoScore = scores.get(player2) - (move.getActualScore() - 1);

                scores.replace(player1, playerOneScore);
                scores.replace(player2, playerTwoScore);

                Platform.runLater(() -> board.updatePlayerscores(playerOneScore, playerTwoScore));
            } else {
                int playerOneScore = scores.get(player1) - (move.getActualScore() - 1);
                int playerTwoScore = scores.get(player2) + move.getActualScore();

                scores.replace(player2, playerTwoScore);
                scores.replace(player1, playerOneScore);

                Platform.runLater(() -> board.updatePlayerscores(playerOneScore, playerTwoScore));
            }
            board.displayMove(move);
        }
        if (!isGameFinished()) {
            if (move != null) {
                board.updateGameInfoDisplay(move.getPlayer().getColor() + "  heeft " + move.getActualScore() + " punten gekregen.");
            } else {
                board.updateGameInfoDisplay(currentPlayer.getColor() + " heeft deze beurt geen zet gedaan.");
            }
        } else {
            board.updateGameInfoDisplay("-----Spel geëindigd-----");

            int score1 = scores.get(player1);
            int score2 = scores.get(player2);

            if (score1 == score2) {
                board.updateGameInfoDisplay("Tied!");
            } else {
                board.updateGameInfoDisplay(score1 > score2 ?
                        String.format("%s heeft gewonnen met %s tegen %s", player1.getColor().name(), score1, score2) :
                        String.format("%s heeft gewonnen met %s tegen %s", player2.getColor().name(), score2, score1)
                );
            }
        }
    }

    // TODO: (optional) move to abstract class and make special abstract functions?
    // TODO: add what needs to happen with each case
    public void handleMessage(HashMap<String, String> message) {
        System.out.println("\n___________________________");
        System.out.println(Arrays.toString(message.entrySet().toArray()));
        switch (message.get("type")) {
            case "MOVE":
                // Contains keys: player, move, details
                String move = message.get("MOVE");
                System.out.println("MOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOVE " + move);
                if(move != null && !message.get("PLAYER").equals(myName)) {
                    passMove(move, player2);
                }
            case "WIN":
                System.out.println(message.get("PLAYERONESCORE"));
                System.out.println(message.get("PLAYERTWOSCORE"));
                System.out.println(message.get("COMMENT"));
//                setFinished(true);
                break;
            case "LOSE":
                System.out.println(message.get("PLAYERONESCORE"));
                System.out.println(message.get("PLAYERTWOSCORE"));
                System.out.println(message.get("COMMENT"));
//                setFinished(true);
                break;
            case "DRAW":
                System.out.println(message.get("PLAYERONESCORE"));
                System.out.println(message.get("PLAYERTWOSCORE"));
                System.out.println(message.get("COMMENT"));
//                setFinished(true);
                break;
            case "YOURTURN":
//                if(currentPlayer != player1) {
                    setCurrentPlayer((ReversiAI) player1);
//                }
            default:
                System.err.println("message not valid");
                break;
        }
        System.out.println("___________________________\n");
    }

    @Override
    public Pane startGame() {
        return board.getBoardGraphic();
    }

    @Override
    public HashMap<Player, Integer> scores() {
        return scores;
    }

    public ReversiBoard getBoard() {
        return board;
    }

    public ReversiPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void passMove(int x, int y) {
        if(isFinished) {
            return;
        }
        ReversiMove result = null;
        currentPossibleMoves = getPossibleMoves(currentPlayer);
        for (ReversiMove move : currentPossibleMoves) {
            int movex = move.getNode().getX();
            int movey = move.getNode().getY();

            if (movex == x && movey == y) {
                result = move;
            }
        }

        passMove(result);
    }

    public void passMove(String move, Player player) {
        if(isFinished) {
            return;
        }
        ReversiMove result = null;
        currentPossibleMoves = getPossibleMoves(player);
        for (ReversiMove possibleMove : currentPossibleMoves) {
            if(move.equals(possibleMove.getNode().toServerInput())) {
                result = possibleMove;
            }
        }

        if(result != null) {
            result.setPlayer(player2);
        }
        passMove(result);
    }

    public void setCurrentPlayer(ReversiPlayer currentPlayer) {
        if (this.currentPlayer != currentPlayer) {
            this.currentPlayer = currentPlayer;
            Platform.runLater(() -> {
                if (currentPlayer.getColor() == Player.Color.WHITE) {
                    board.updateWhiteTimer("" + timer);
                } else {
                    board.updateBlackTimer("" + timer);
                }
            });
            if(currentPlayer == player1) {
                startTimer();
                if (currentPlayer instanceof ReversiAI) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            ((ReversiAI) currentPlayer).play();
                            stopTimer();
                        } catch (InterruptedException ignored) {

                        }
                    }).start();
                } else {
                    board.setPlayer(currentPlayer);
                }
            }
        }
    }

    public boolean isGameFinished() {
        if(!isFinished) {
            List<ReversiMove> possibleMoves1 = getPossibleMoves(player1);
            List<ReversiMove> possibleMoves2 = getPossibleMoves(player2);

            if (possibleMoves1.isEmpty() && possibleMoves2.isEmpty()) {
                isFinished = true;
                stopTimer();
            }
        }
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isOpponent() {
        return isOpponent;
    }
}
