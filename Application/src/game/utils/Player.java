package game.utils;

public class Player {

    private int score;
    private Node.NodeState color;
    private Board board;

    public Player(Board board, Node.NodeState color) {
        this.color = color;
        this.board = board;
    }

    public Node.NodeState getColor() {
        return color;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Board getBoard() {
        return board;
    }
}
