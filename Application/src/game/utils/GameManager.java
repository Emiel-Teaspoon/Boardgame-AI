package game.utils;

import game.ai.ReversiAI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private List<Player> players;
    private int maxTurnTime;
    private Board board;
    private int whiteScore = 0;

    public GameManager(int boardSizeX, int boardSizeY, int maxTurnTime) {
        while(whiteScore < 63) {
            this.board = new Board(boardSizeX, boardSizeY);
            this.maxTurnTime = maxTurnTime;
            this.players = new ArrayList<>();

            ReversiAI white = new ReversiAI(board, Node.NodeState.WHITE, ReversiAI.Difficulty.UNBEATABLE);
            ReversiAI black = new ReversiAI(board, Node.NodeState.BLACK, ReversiAI.Difficulty.SLAVE);

            this.players.add(white);
            this.players.add(black);

//            try {
                for (int i = 0; i < 30; i++) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("White turn");

//                    boolean didMove = false;
//                    while(!didMove) {
//                        System.out.println("Type move: ");
//                        String s = br.readLine();
//                        didMove = white.doMove(s);
//                    }

//                    Thread.sleep(2500);
                    white.doMove();
                    setScores(white, black);
                    System.out.println("Black turn");
                    black.doMove();
                    setScores(white, black);
                }
//            }
//            catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
            whiteScore = white.getScore();
        }
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
        System.out.println("___________________________________________________\n");
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
