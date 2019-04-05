package GameModuleReversi;

import java.util.List;

public class Move {

    private int score;
    private List<Tile> overtakenTiles;
    private Player player;
    private Tile targetTile;

    public Move(Player player, List<Tile> tiles, Tile targetTile) {
        this.overtakenTiles = tiles;
        this.score = overtakenTiles.size() + targetTile.getWeight();
        this.player = player;
        this.targetTile = targetTile;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Tile> getOvertakenTiles() {
        return overtakenTiles;
    }

    public void addOvertakenTiles(Tile tile) {
        overtakenTiles.add(tile);
    }

    public void setOvertakenTiles(List<Tile> overtakenTiles) {
        this.overtakenTiles = overtakenTiles;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile.TileState getColor() {
        return player.getColor();
    }

    public Tile getTargetTile() {
        return targetTile;
    }
}
