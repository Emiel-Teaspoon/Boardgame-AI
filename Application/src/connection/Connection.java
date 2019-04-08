package connection;

import java.io.*;
import java.net.Socket;

/**
 * A connection class that reads and writes to the server.
 *
 * @author Leon Smit
 */
public class Connection implements Runnable {

    private ConnectionHandler handler;
    private BufferedWriter writer;

    private String host;
    private int port;

    private boolean isConnected;
    private boolean running;
    private boolean okReceived;
    private int requestTimeOut;

    private String getString;

    /**
     * Contains connection  to write to and read from the game server
     *
     * @param handler Handler
     * @param host    Adress of the Game Server
     * @param port    The port used by the Game Server
     */
    public Connection(ConnectionHandler handler, String host, int port) {
        this.handler = handler;
        this.host = host;
        this.port = port;
        this.requestTimeOut = 1000;
        this.isConnected = false;
    }

    /**
     * Closes the connection to the server safely
     */
    public void close() {
        this.running = false;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            running = true;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            isConnected = true;
            String message;
            while (running) {
                message = reader.readLine();
                if (message != null) {
                    if (message.equals("OK") || message.startsWith("ERR")) {
                        okReceived = true;
                    } else if (message.startsWith("SVR GAMELIST") || message.startsWith("SVR PLAYERLIST")) {
                        getString = message;
                    } else {
                        // handler.addMessage(message);
                    }
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for isConnected
     *
     * @return whether the connection is established
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Writes a command to the connected server.
     *
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
     *
     * @param command the command to send to the server
     * @return Has the command been confirmed?
     */
    public synchronized boolean request(String command) {
        okReceived = false;
        write(command);
        long startTime = System.currentTimeMillis();


        while (!okReceived && System.currentTimeMillis() - startTime < requestTimeOut) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean confirmed = okReceived;
        okReceived = false;
        return confirmed;
    }

    /**
     * Returns the result of a get command.
     *
     * @param command "gamelist" returns the game list and "playerlist" returns player list
     * @return The server response or null if no response was received
     */
    public synchronized String get(String command) {
        getString = null;
        long firstStartTime = System.currentTimeMillis();
        if (request("get " + command)) {
            long startTime = System.currentTimeMillis();
            while (getString == null && System.currentTimeMillis() - startTime < requestTimeOut) {
                if (getString != null) break;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() - firstStartTime);
            return getString;
        } else {
            return null;
        }
    }
}
