import java.util.ArrayList;

public class ConnectionHandler {

    private ArrayList<String> gameList;

    private Connection connection;

    public ConnectionHandler(String host, int port) {
        connection = new Connection(this, host, port);
        new Thread(connection).start();
    }

    public void write(String message) {
        connection.write(message);
    }

    public void login(String name) {
        connection.write("login " + name);
    }

    public void disconnect() {
        connection.write("disconnect");
    }

    public ArrayList<String> getGameList() {
        if (gameList == null) {
            String answer = connection.request("get gamelist");
            gameList = parseList(answer);
        }
        return gameList;
    }

    public ArrayList<String> getPlayerList() {
        String answer = connection.request("get playerlist");
        return parseList(answer);
    }

    public void subscribe(String game) {
        connection.write("subscribe " + game);
    }

    public void move(String move) {
        connection.write("move " + move);
    }

    public void forfeit() {
        connection.write("forfeit");
    }

    public void challenge(String player, String game) {
        connection.write("challenge \"" + player + "\" \"" + game + "\"");
    }

    public void acceptChallenge(String number) {
        connection.write("challenge accept " + number);
    }

    public void help(String command) {
        connection.write("help " + command);
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

//    public static void main(String[] args) {
//        ConnectionHandler con = new ConnectionHandler("localhost", 7789);
//
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//        }
//
//        con.login("henk");
//        con.subscribe("Reversi");
//    }
}
