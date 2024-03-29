package game.reversi;

import game.Node;

public class ReversiNode extends Node {

    int weight;

    public ReversiNode(int x, int y) {
        super(x, y);
    }

    public ReversiNode(int x, int y, int weight) {
        super(x, y);
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.valueOf("ABCDEFGH".charAt(x)) + (y + 1);
    }

    @Override
    public String toServerInput() {
        return String.valueOf(y*8 + x);
    }
}
