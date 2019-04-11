package boardgame;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Challenge {

    private VBox mainPane;
    private HBox challenge;

    private String challenger;
    private String challengeNumber;
    private String selectedGame;

    public Challenge(String challenger, String challengeNumber, String selectedGame, VBox pane){
        this.challenger = challenger;
        this.challengeNumber = challengeNumber;
        this.selectedGame = selectedGame;
        this.mainPane = pane;

        challenge = new HBox();
        challenge.setSpacing(20);
        Text challengeText = new Text(String.format("%s heeft je uitgedaagd voor %s", challenger, selectedGame));
        Button challengeDecline = new Button("Afwijzen");
        challengeDecline.setOnAction(e -> removeChallenge());
        Button challengeAccept = new Button("Accepteren");
        challengeAccept.setOnAction(e -> System.out.println("" + challenger + challengeNumber +selectedGame));
        challenge.getChildren().addAll(challengeText, challengeDecline, challengeAccept);
    }

    public HBox createChallengeGraphic() {
        return challenge;
    }

    public void removeChallenge() {
        mainPane.getChildren().remove(challenge);
    }
}
