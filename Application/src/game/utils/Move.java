package game.utils;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private int score;
    private List<Node> overtakenNodes;
    private Player player;
    private Node targetNode;

    public Move(Player player, List<Node> nodes, Node targetNode) {
        this.overtakenNodes = nodes;
        this.score = overtakenNodes.size() + targetNode.getWeight();
        this.player = player;
        this.targetNode = targetNode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Node> getOvertakenNodes() {
        return overtakenNodes;
    }

    public void addOvertakenNode(Node node) {
        overtakenNodes.add(node);
    }

    public Player getPlayer() {
        return player;
    }

    public Node.NodeState getColor() {
        return player.getColor();
    }

    public void setOvertakenNodes(List<Node> overtakenNodes) {
        this.overtakenNodes = overtakenNodes;
    }

    @Override
    public String toString() {
        return "Move: " + player.getColor().name() + " to " + targetNode.toString() + " for " + getScore() + " Points";
    }

    public Node getTargetNode() {
        return targetNode;
    }
}
