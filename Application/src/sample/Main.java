package sample;

import game.utils.GameManager;

public class Main/* extends Application*/ {

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 500, 400));
//        primaryStage.show();
//
//    }


    public static void main(String[] args) {
        GameManager manager = new GameManager(8,8,10);
//        launch(args);
    }
}
