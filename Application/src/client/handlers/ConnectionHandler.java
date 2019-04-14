package client.handlers;

import client.ClientModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles the connection to the server by sending the right commands.
 *
 * @author Leon Smit
 */
public class ConnectionHandler {

    private ClientModel model;
    private Connection connection;
    private boolean isConnected;

    public ConnectionHandler(ClientModel model) {
        this.model = model;
        isConnected = false;
    }

    /**
     * Establish a connection with the server
     *
     * @param host the address of the server
     * @param port the port number of the server
     */
    public boolean connect(String host, int port) {
        connection = new Connection(this, host, port);
        new Thread(connection).start();

        long starTime = System.currentTimeMillis();
        while (!connection.isConnected()) {
            // If it takes a second, connection should be closed
            if (System.currentTimeMillis() - starTime > 1000) {
                connection.close();
                connection = null;
                return false;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isConnected = true;
        return true;
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
        return connection.request("accept " + number);
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
            return null;
        }
        return parseList(answer);
    }

    /**
     * Parses a string into a HashMap of two Strings with a message type and sends it to the controller.
     *
     * @param message Servermessage starting with SVR GAME
     */
    public void handleGameMessage(String message) {
        message = message.substring(9);
        String command = message.substring(0, message.indexOf('{')-1);

        message = message.substring(message.indexOf('{') + 1, message.indexOf('}'));

        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put("type", command);

        boolean finished = false;
        while (!finished) {
            String key = message.substring(0, message.indexOf(':'));
            message = message.substring(message.indexOf("\"") + 1);

            String value = message.substring(0, message.indexOf('\"'));
            if (message.contains(",")) {
                message = message.substring(message.indexOf(",") + 2);
            } else {
                finished = true;
            }
            messageMap.put(key, value);
        }
        model.handleMessage(messageMap);
    }

    /**
     * Close the current connection
     */
    public void disconnect() {
        connection.write("disconnect");
        isConnected = false;
    }

    /**
     * Parses a list from the server into an ArrayList<String>
     * @param input server message
     * @return parsed ArrayList
     */
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