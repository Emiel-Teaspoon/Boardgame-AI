package game.reversi;

import game.Game;
import game.Move;
import game.Player;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Reversi extends Game implements boardgame.Game {

    private ReversiBoard board;

    public Reversi(ReversiPlayer player1, ReversiPlayer player2) {
        super(player1, player2);
        player1.setReversi(this);
        player2.setReversi(this);
        board = new ReversiBoard(8, 8, this);

        new Thread(() -> {
            for (int i = 0; i < 31; i++) {
                try {
                    Thread.sleep(200);
                    ((ReversiAI) player1).play();
                    Thread.sleep(200);
                    ((ReversiAI) player2).play();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
        return moves;
    }

    void passMove(ReversiMove move) {
        if(move == null) {
            board.updateBoard();
        }
        else {
            board.displayMove(move);
        }
    }

    @Override
    public Pane startGame() {
        return board.getBoardGraphic();
    }
}
