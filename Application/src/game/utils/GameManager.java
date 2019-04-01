package game.utils;

import game.ai.ReversiAI;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private List<Player> players;
    private int maxTurnTime;
    private Board board;

    public GameManager(int boardSizeX, int boardSizeY, int maxTurnTime) {
        this.board = new Board(boardSizeX, boardSizeY);
        this.maxTurnTime = maxTurnTime;
        this.players = new ArrayList<>();

        ReversiAI white = new ReversiAI(board, Node.NodeState.WHITE);
        ReversiAI black = new ReversiAI(board, Node.NodeState.BLACK);

        this.players.add(white);
        this.players.add(black);

//        white.doMove();

//        try {
            for(int i = 0; i < 30; i++) {
//                Thread.sleep(1000);
                System.out.println("White turn");
                white.doMove();

                setScores(white, black);
//                Thread.sleep(1000);
                System.out.println("Black turn");
                black.doMove();
                setScores(white, black);
            }
//        }
//        catch (InterruptedException e) {
//
//        }


    }

    void setScores(Player white, Player black) {
        int whitePoints = 0;
        int blackPoints = 0;

        for (Node node : board.getNodes()) {
            if(node.getState() == Node.NodeState.WHITE) {
                whitePoints++;
            }
            if(node.getState() == Node.NodeState.BLACK) {
                blackPoints++;
            }
        }

        white.setScore(whitePoints);
        black.setScore(blackPoints);

        System.out.println("White points: " + white.getScore());
        System.out.println("Black points: " + black.getScore());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }

    public Board getBoard() {
        return board;
    }

    public int getMaxTurnTime() {
        return maxTurnTime;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
