package game;

public class Player {

    public enum Color { WHITE, BLACK }

    private int score;
    private Color color;

    public Player(Color color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Color getColor() {
        return color;
    }

    public void doMove(Move move) {
        return;
    }
}
