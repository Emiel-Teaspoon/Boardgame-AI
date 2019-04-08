package GameModuleReversi;

public class Player {

    private Tile.TileState color;
    private int score;

    public Player(Tile.TileState color) {
        this.color = color;
    }

    public Tile.TileState getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
