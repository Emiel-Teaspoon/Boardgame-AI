import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {

    private ConnectionHandler handler;
    private BufferedWriter writer;
    private String host;
    private int port;

    public Connection(ConnectionHandler handler, String host, int port) {
        this.handler = handler;
        this.host = host;
        this.port = port;
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String command) {
        try {
            writer.write(command + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String request(String command) {
        write(command);
        return null;
    }
}
