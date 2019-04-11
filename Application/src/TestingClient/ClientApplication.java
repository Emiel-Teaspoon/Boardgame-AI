package TestingClient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientApplication extends Application {

	TextArea textArea;
	TestingClient.ClientConnection connection;

	@Override
	public void start(Stage primaryStage) {
		BorderPane mainPane = new BorderPane();
		mainPane.prefWidthProperty().bind(primaryStage.widthProperty());
		mainPane.prefHeightProperty().bind(primaryStage.heightProperty());
		mainPane.setPadding(new Insets(5, 5, 5, 5));

		GridPane commandPane = new GridPane();
		commandPane.prefWidthProperty().bind(mainPane.widthProperty());

		commandPane.add(new Label("Enter command:  "), 0, 0);

		TextField textField = new TextField();
		textField.prefWidthProperty().bind(commandPane.widthProperty().subtract(115));
		commandPane.add(textField, 1, 0);

		mainPane.setTop(commandPane);

		textArea = new TextArea();
		ScrollPane scrollPane = new ScrollPane(textArea);
		scrollPane.prefWidthProperty().bind(primaryStage.widthProperty());
		scrollPane.prefHeightProperty().bind(primaryStage.heightProperty().subtract(textField.heightProperty()));

		textArea.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20.0));
		textArea.prefHeightProperty().bind(scrollPane.heightProperty());

		mainPane.setCenter(scrollPane);
		textArea.setEditable(false);

		// Connection aanmaken
		connection = new TestingClient.ClientConnection(this);
		new Thread(connection).start();

		textField.setOnAction(e -> {
			String message = textField.getText();
			connection.sendMessage(message);
			textField.clear();
		});

		Scene scene = new Scene(mainPane, 480, 235);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void writeMessage(String message) {
		Platform.runLater(() -> {
			textArea.appendText(message + "\n");
			textArea.selectPositionCaret(textArea.getLength());
		});
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}
