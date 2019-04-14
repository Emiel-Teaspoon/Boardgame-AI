package game;

import game.reversi.Direction;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public enum State { FREE, OCCUPIED }

    protected int x;
    protected int y;

    protected Player.Color color;
    protected State state;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine(Player player) {
        return state == State.OCCUPIED && player.getColor() == color;
    }

    public boolean isOccupied() {
        return state == State.OCCUPIED;
    }

    public void assignPlayer(Player player) {
        state = State.OCCUPIED;
        color = player.getColor();
    }

    public Player.Color getColor() {
        return color;
    }

    public boolean isEnemy(Player currentPlayer) {
        if (isOccupied()) {
            return currentPlayer.getColor() != color;
        }
        else {
            return false;
        }
    }

    public static Direction getDirection(Node origin, Node dir) {
        return new Direction(dir.getX() - origin.getX(), dir.getY() - origin.getY());
    }

    public String toServerInput() {
        return String.valueOf("0");
    }
}
