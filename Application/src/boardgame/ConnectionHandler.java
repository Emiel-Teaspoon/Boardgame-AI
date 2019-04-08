package boardgame;

import java.util.ArrayList;

public class ConnectionHandler {

    private Connection connection;

    public ConnectionHandler(String host, int port) {
        connection = new Connection(this, host, port);
        new Thread(connection).start();
    }

//    nodig?
//    public void write(String message) {
//        connection.write(message);
//    }

    public boolean login(String name) {
        return connection.request("login " + name);
    }

    public boolean subscribe(String game) {
        return connection.request("subscribe " + game);
    }

    public boolean move(String move) {
        return connection.request("move " + move);
    }

    public boolean forfeit() {
        return connection.request("forfeit");
    }

    public boolean challenge(String player, String game) {
        return connection.request("challenge \"" + player + "\" \"" + game + "\"");
    }

    public boolean acceptChallenge(String number) {
        return connection.request("challenge accept " + number);
    }

    public void help(String command) {
        connection.write("help " + command);
    }

    public ArrayList<String> getGameList() {
        String answer = connection.get("playerlist");
        return parseList(answer);
    }

    public ArrayList<String> getPlayerList() {
        String answer = connection.get("playerlist");
        return parseList(answer);
    }

    public void disconnect() {
        connection.write("disconnect");
    }

    private ArrayList<String> parseList(String input) {
        ArrayList<String> list = new ArrayList<>();

        while (input.indexOf("\"") > -1) {
            input = input.substring(input.indexOf("\"") + 1);
            list.add(input.substring(0, input.indexOf("\"")));
            input = input.substring(input.indexOf("\"") + 1);
        }

        return list;
    }
}