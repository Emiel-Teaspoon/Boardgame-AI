package client.scenes;

import client.handlers.ConnectionHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import client.ClientModel;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class LobbyScene extends ClientScene {

    private LobbyUpdater lobbyUpdater;

    private ObservableList<String> playerList;
    private ObservableList<String> gameList;

    private String selectedPlayer;
    private String selectedGame;

    private ArrayList<Challenge> challenges;

    // GUI Items
    private Button btBack;
    private Button btSubscribe;

    private BorderPane mainLayout;
    private HBox buttonLayoutRight;
    private HBox buttonLayoutLeft;
    private ScrollPane challengeLayout;
    private VBox scrollPaneContainer;

    private ListView<String> playerOverview;
    private ComboBox<String> gameListing;

    public LobbyScene (ClientModel model) {
        super(model);
        playerList = FXCollections.observableArrayList();
        gameList = FXCollections.observableArrayList();
        challenges = new ArrayList<>();
        buildScene();
    }

    /**
     * Set the values of the PlayerList
     *
     * @param players ArrayList containing Strings of players
     */
    public void updatePlayerList(ArrayList<String> players) {
        if (players == null) return;
        playerList.clear();
        playerList.addAll(players);
    }

    public void createChallenge(String challenger, String challengeNumber, String selectedGame) {
        System.out.println("At create challenge");
        Challenge challenge = new Challenge(challenger, challengeNumber, selectedGame, scrollPaneContainer);
        challenges.add(challenge);
        scrollPaneContainer.getChildren().add(challenge.createChallengeGraphic());
    }

    public void removeChallenge(String challengeNumber) {
        for (Challenge challenge : challenges) {
            if (challenge.getId().equals(challengeNumber)) {
                challenge.removeChallenge();
            }
        }
    }

    /**
     * Get the values of the GameList
     */
    public void updateGameList() {
        ArrayList<String> games = model.getConnection().getGameList();
        gameList.clear();
        gameList.addAll(games);
    }

    @Override
    public String getName() {
        return "Game Client - Lobby";
    }

    @Override
    public void buildScene() {
        buildButtons();
        buildNodes();
        buildLayoutManagers();

        scene = new Scene(mainLayout, 1080, 720);
    }

    @Override
    public void onEnter() {
        if (!model.getConnection().isConnected()) {
            model.connect();
            model.login();
        }
        //TODO: check if logged in

        updateGameList();
        lobbyUpdater = new LobbyUpdater(this, model.getConnection());
        new Thread(lobbyUpdater).start();
    }

    @Override
    public void onLeave() {
        if (lobbyUpdater != null) {
            lobbyUpdater.close();
        }
        // TODO: Stop lobby updater
    }

    private void buildButtons() {
        btBack = new Button("Terug");
        btBack.setOnAction(e -> {
            model.switchScene("start");
        });

        btSubscribe = new Button("Zoek Tegenstander");
        btSubscribe.setOnAction(e -> {
            model.getConnection().subscribe("Reversi");
        });
    }

    private void buildLayoutManagers() {
        mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        HBox centerGrid = new HBox();
        centerGrid.setAlignment(Pos.TOP_CENTER);
        centerGrid.setSpacing(10);
        centerGrid.getChildren().addAll(playerOverview, gameListing);
        mainLayout.setCenter(centerGrid);

        buttonLayoutRight = new HBox();
        buttonLayoutRight.setAlignment(Pos.CENTER_RIGHT);
        buttonLayoutRight.getChildren().addAll(btSubscribe);
        mainLayout.setRight(buttonLayoutRight);

        buttonLayoutLeft = new HBox();
        buttonLayoutLeft.setAlignment(Pos.CENTER_LEFT);

        //Button testButton = new Button("Testing");
        //testButton.setOnAction(e -> createChallenge("Bob", "Reversi"));
        //buttonLayoutLeft.getChildren().addAll(btBack, testButton);
        buttonLayoutLeft.getChildren().add(btBack);

        mainLayout.setLeft(buttonLayoutLeft);

        scrollPaneContainer = new VBox();
        challengeLayout = new ScrollPane(scrollPaneContainer);
        challengeLayout.setMaxHeight(150);
        challengeLayout.setMinHeight(150);

        mainLayout.setBottom(challengeLayout);
    }

    private void buildNodes() {
        playerOverview = new ListView<>(playerList);
        playerOverview.setMaxWidth(150);
        playerOverview.setMaxHeight(250);

        gameListing = new ComboBox<>();
        gameListing.setItems(gameList);
        gameListing.getSelectionModel().selectFirst();
    }

    private class Challenge {
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
            challengeAccept.setOnAction(e -> {
                System.out.println("" + challenger + challengeNumber +selectedGame);
                model.getConnection().acceptChallenge(challengeNumber);
            });
            challenge.getChildren().addAll(challengeText, challengeDecline, challengeAccept);
        }

        public HBox createChallengeGraphic() {
            return challenge;
        }

        public void removeChallenge() {
            mainPane.getChildren().remove(challenge);
        }

        public String getId() {
            return challengeNumber;
        }
    }

    /**
     * Runnable that gets the playerlist and updates it in the lobby
     */
    private class LobbyUpdater implements Runnable {
        private LobbyScene lobby;
        private ConnectionHandler handler;
        private boolean running;

        public LobbyUpdater(LobbyScene lobby, ConnectionHandler handler) {
            this.lobby = lobby;
            this.handler = handler;
            this.running = true;
        }

        @Override
        public void run() {
            while (running) {
                ArrayList<String> players = handler.getPlayerList();
                if (players != null) Platform.runLater(() -> lobby.updatePlayerList(players));

                try {
                    Thread.sleep(5000); // Request playerlist every **** ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close() {
            running = false;
        }
    }
}