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
}
