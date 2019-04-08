package connection;

import java.util.ArrayList;

/**
 * Handles the connection to the server by sending the right commands.
 *
 * @author Leon Smit
 */
public class ConnectionHandler {

    private Connection connection;
    private boolean isConnected;

    public ConnectionHandler() {
        isConnected = false;
    }

    /**
     * Establish a connection with the server
     *
     * @param host the address of the server
     * @param port the port number of the server
     */
    public void connect(String host, int port) {
        connection = new Connection(this, host, port);
        new Thread(connection).start();

        while (!connection.isConnected()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isConnected = true;
    }

    /**
     * Close the current connection to the server
     */
    public void close() {
        disconnect();
        connection.close();
        isConnected = false;
        connection = null;
    }

    /**
     * Returns whether the server currently has a connection to the server
     *
     * @return whether the handler has a connection to the server
     */
    public boolean isConnected() {
        return isConnected;
    }

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
        String answer = connection.get("gamelist");
        if (answer == null) return null;
        return parseList(answer);
    }

    public ArrayList<String> getPlayerList() {
        String answer = connection.get("playerlist");
        if (answer == null) {
            System.out.println("null in getPlayerList()");
            return null;
        }
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