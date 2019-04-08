package boardgame;

import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {

    private ConnectionHandler handler;
    private BufferedWriter writer;

    private String host;
    private int port;

    private boolean okReceived;
    private boolean getReceived;
    private int requestTimeOut;

    private String getString;

    /**
     * Contains connection  to write to and read from the game server
     * @param handler Handler
     * @param host Adress of the Game Server
     * @param port The port used by the Game Server
     */
    public Connection(ConnectionHandler handler, String host, int port) {
        this.handler = handler;
        this.host = host;
        this.port = port;
        this.requestTimeOut = 1000;
    }

    @Override
    public void run() {
        boolean running = true;
        try (Socket socket = new Socket(host, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String message;
            while (running) {
                message = reader.readLine();
                if (message != null) {
                    System.out.println(message);
                    if (message.equals("OK") || message.startsWith("ERR")) {
                        okReceived = true;
                    } else if (message.startsWith("SVR GAMELIST") || message.startsWith("SVR PLAYERLIST")) {
                        getString = message;
                    } else {
                        // handler.addMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a command to the connected server.
     * @param command the command to send to the server
     */
    public void write(String command) {
        try {
            writer.write(command + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a command to the server and confirms whether the server received it.
     * @param command the command to send to the server
     * @return Has the command been confirmed?
     */
    public synchronized boolean request(String command) {
        write(command);
        long startTime = System.currentTimeMillis();
        okReceived = false;

        while (!okReceived && System.currentTimeMillis() - startTime < requestTimeOut) {
        }

        boolean confirmed = okReceived;
        okReceived = false;
        return confirmed;
    }

    /**
     * Returns the result of a get command.
     * @param command "gamelist" returns the game list and "playerlist" returns player list
     * @return The server response or null if no response was received
     */
    public synchronized String get(String command) {
        getReceived = false;
        if (request("get " + command)) {
            System.out.println("bladiebla");
            long startTime = System.currentTimeMillis();
            while (getString == null || System.currentTimeMillis() - startTime < requestTimeOut) {
            }
            System.out.println("> " + getString);
            String result = getString;
            getString = null;
            return result;
        } else {
            System.out.println("else of get");
            return null;
        }
    }
}

