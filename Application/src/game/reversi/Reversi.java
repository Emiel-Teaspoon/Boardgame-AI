package game.reversi;

import game.Game;
import game.Player;
import javafx.scene.layout.Pane;

import java.util.*;

public class Reversi extends Game implements boardgame.Game {

    private ReversiBoard board;
    private ReversiPlayer currentPlayer;
    private List<ReversiMove> currentPossibleMoves;
    private HashMap<Player, Integer> scores;

    public Reversi(ReversiPlayer player1, ReversiPlayer player2) {
        super(player1, player2);
        scores = new HashMap<>();
        player1.setReversi(this);
        player2.setReversi(this);

        scores.put(player1, 2);
        scores.put(player2, 2);

        board = new ReversiBoard(8, 8, this);

        board.setPlayer(player1);
        setCurrentPlayer(player1);
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
            if(node.isOccupied()) {
                occupied.add(node);
                if(node.isMine(player)) {
                    mine.add(node);
                }
            }
        }

        for (ReversiNode node : mine) {
            for (ReversiNode neighbour : board.getNeighbourNodes(node)) {
                if(neighbour.isEnemy(player)) {
                    checked = new ArrayList<>();
                    checked.add(neighbour);
                    checkingDir = ReversiNode.getDirection(node, neighbour);
                    boolean run = true;
                    ReversiNode nextToCheck = neighbour;
                    while(run) {
                        nextToCheck = board.getNextNodeInDir(nextToCheck, checkingDir);
                        if(nextToCheck == null) {
                            run = false;
                            continue;
                        }
                        if (nextToCheck.isMine(player) && nextToCheck.isOccupied()) {
                            run = false;
                        }
                        else {
                            if(!checked.contains(nextToCheck)) {
                                checked.add(nextToCheck);
                            }
                            if(!nextToCheck.isOccupied()) {
                                //TODO backtracking
                                for (ReversiNode clearNodeNeighbour: board.getNeighbourNodes(nextToCheck)) {
                                    if (clearNodeNeighbour.isEnemy(player) && !checked.contains(clearNodeNeighbour)) {
                                        extraChecked.clear();
                                        checkingDir = ReversiNode.getDirection(nextToCheck, clearNodeNeighbour);
                                        if(!extraChecked.contains(clearNodeNeighbour)) {
                                            extraChecked.add(clearNodeNeighbour);
                                        }
                                        ReversiNode nextToCheckExtra = clearNodeNeighbour;
                                        boolean runNext = true;
                                        while (runNext) {
                                            nextToCheckExtra = board.getNextNodeInDir(nextToCheckExtra, checkingDir);
                                            if(nextToCheckExtra == null) {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                                continue;
                                            }
                                            if(nextToCheckExtra.isOccupied()){
                                                if(nextToCheckExtra.isEnemy(player)) {
                                                    if(!extraChecked.contains(nextToCheckExtra)) {
                                                        extraChecked.add(nextToCheckExtra);
                                                    }
                                                }
                                                else {
                                                    runNext = false;
                                                    checked.addAll(extraChecked);
                                                }
                                            }
                                            else {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                            }
                                        }
                                    }
                                }

                                ReversiMove possibleMove = new ReversiMove(player, checked, nextToCheck);
                                boolean add = true;
                                for (ReversiMove move: moves) {
                                    if(move.getNode().equals(nextToCheck)) {
                                        add = false;
                                    }
                                }
                                if(add) {
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
            if(node.isOccupied()) {
                occupied.add(node);
                if(node.isMine(player)) {
                    mine.add(node);
                }
            }
        }

        for (ReversiNode node : mine) {
            for (ReversiNode neighbour : board.getNeighbourNodes(node)) {
                if(neighbour.isEnemy(player)) {
                    checked = new ArrayList<>();
                    checked.add(neighbour);
                    checkingDir = ReversiNode.getDirection(node, neighbour);
                    boolean run = true;
                    ReversiNode nextToCheck = neighbour;
                    while(run) {
                        nextToCheck = board.getNextNodeInDir(nextToCheck, checkingDir);
                        if(nextToCheck == null) {
                            run = false;
                            continue;
                        }
                        if (nextToCheck.isMine(player) && nextToCheck.isOccupied()) {
                            run = false;
                        }
                        else {
                            if(!checked.contains(nextToCheck)) {
                                checked.add(nextToCheck);
                            }
                            if(!nextToCheck.isOccupied()) {
                                //TODO backtracking
                                for (ReversiNode clearNodeNeighbour: board.getNeighbourNodes(nextToCheck)) {
                                    if (clearNodeNeighbour.isEnemy(player) && !checked.contains(clearNodeNeighbour)) {
                                        extraChecked.clear();
                                        checkingDir = ReversiNode.getDirection(nextToCheck, clearNodeNeighbour);
                                        if(!extraChecked.contains(clearNodeNeighbour)) {
                                            extraChecked.add(clearNodeNeighbour);
                                        }
                                        ReversiNode nextToCheckExtra = clearNodeNeighbour;
                                        boolean runNext = true;
                                        while (runNext) {
                                            nextToCheckExtra = board.getNextNodeInDir(nextToCheckExtra, checkingDir);
                                            if(nextToCheckExtra == null) {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                                continue;
                                            }
                                            if(nextToCheckExtra.isOccupied()){
                                                if(nextToCheckExtra.isEnemy(player)) {
                                                    if(!extraChecked.contains(nextToCheckExtra)) {
                                                        extraChecked.add(nextToCheckExtra);
                                                    }
                                                }
                                                else {
                                                    runNext = false;
                                                    checked.addAll(extraChecked);
                                                }
                                            }
                                            else {
                                                runNext = false;
                                                extraChecked = new ArrayList<>();
                                            }
                                        }
                                    }
                                }

                                ReversiMove possibleMove = new ReversiMove(player, checked, nextToCheck);
                                boolean add = true;
                                for (ReversiMove move: moves) {
                                    if(move.getNode().equals(nextToCheck)) {
                                        add = false;
                                    }
                                }
                                if(add) {
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
        if(move == null) {
            board.updateBoard();
        }
        else {
            if(move.getPlayer() == player1) {
                scores.replace(player1, scores.get(player1) + move.getActualScore());
                scores.replace(player2, scores.get(player2) - (move.getActualScore() - 1));
            }
            else {
                scores.replace(player2, scores.get(player2) + move.getActualScore());
                scores.replace(player1, scores.get(player1) - (move.getActualScore() - 1));
            }
            board.displayMove(move);

        }
        if(!isGameFinished()) {
            System.out.println(Arrays.toString(scores.entrySet().toArray()));
            setCurrentPlayer(getCurrentPlayer() == player1 ? (ReversiPlayer) player2 : (ReversiPlayer) player1);
        }
        else {
            System.out.println("GAME FINISHED");

            int score1 = scores.get(player1);
            int score2 = scores.get(player2);

            if(score1 == score2) {
                System.out.println("Tied!");
            }
            else {
                System.out.println(score1 > score2 ? String.format("%s wins with %s to %s", player1.getColor().name(), score1, score2) : String.format("%s wins with %s to %s", player2.getColor().name(), score2, score1));
            }
        }
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
        ReversiMove result = null;
        currentPossibleMoves = getPossibleMoves(currentPlayer);
        for (ReversiMove move : currentPossibleMoves) {
            int movex = move.getNode().getX();
            int movey = move.getNode().getY();

            if(movex == x && movey == y) {
                result = move;
            }
        }

        passMove(result);
    }

    public void setCurrentPlayer(ReversiPlayer currentPlayer) {
        if(this.currentPlayer != currentPlayer) {
            this.currentPlayer = currentPlayer;
            if(currentPlayer instanceof ReversiAI) {
                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                        ((ReversiAI) currentPlayer).play();
                    }
                    catch (InterruptedException ignored) {

                    }
                }).start();
            }
            else {
                board.setPlayer(currentPlayer);
            }
        }
    }

    public boolean isGameFinished() {
        boolean isFinished = false;
        List<ReversiMove> possibleMoves1 = getPossibleMoves(player1);
        List<ReversiMove> possibleMoves2 = getPossibleMoves(player2);

        if(possibleMoves1.isEmpty() && possibleMoves2.isEmpty()) {
            isFinished = true;
        }

        return isFinished;
    }
}
