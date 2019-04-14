package game;

public class Player {

    public enum Color { WHITE, BLACK }

    private int score;
    private Color color;
    private boolean isPlayable;

    public Player(Color color, boolean isPlayable) {
        this.color = color;
        this.isPlayable = isPlayable;
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

    public boolean isPlayable() {
        return isPlayable;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }
}
