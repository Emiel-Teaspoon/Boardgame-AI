package game;

public class Move {

    protected Player player;
    protected Node node;

    public Move(Player player, Node node) {
        this.player = player;
        this.node = node;
    }

    public <T extends Node> Node getNode() {
        return node;
    }

    public Player getPlayer() {
        return player;
    }
}