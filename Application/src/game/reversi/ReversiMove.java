package game.reversi;

import game.Move;
import game.Node;
import game.Player;

import java.util.List;

public class ReversiMove extends Move {

    List<ReversiNode> checked;
    ReversiNode node;

    public ReversiMove(Player player, Node node) {
        super(player, node);
    }

    public ReversiMove(Player player, List<ReversiNode> checked, ReversiNode targetNode) {
        super(player, targetNode);
        this.node = targetNode;
        this.player = player;
        this.checked = checked;
    }

    public List<ReversiNode> getChecked() {
        return checked;
    }

    @Override
    public ReversiNode getNode() {
        return node;
    }

    public int getScore() {
        return node.getWeight() + checked.size();
    }

    @Override
    public String toString() {
        return player.getColor().name() + " can move to " + node.toString() + " for " + getScore() + " Points";
    }

    public int getActualScore() {
        return checked.size();
    }
}
