package game.reversi;

import game.Move;
import game.Node;
import game.Player;

import java.util.List;

public class ReversiMove extends Move {

    List<ReversiNode> checked;
    ReversiNode node;
    int score;

    public ReversiMove(Player player, Node node) {
        super(player, node);
    }

    public ReversiMove(Player player, List<ReversiNode> checked, ReversiNode targetNode) {
        super(player, targetNode);
        this.node = targetNode;
        this.player = player;
        this.checked = checked;
        this.score = targetNode.getWeight() + checked.size();
    }

    public List<ReversiNode> getChecked() {
        return checked;
    }

    @Override
    public ReversiNode getNode() {
        return node;
    }

    public int getScore() {
        return score;
    }
}
