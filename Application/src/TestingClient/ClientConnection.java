package TestingClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements Runnable {

	private TestingClient.ClientApplication application;

	private int port = 7789;
	private OutputStreamWriter outputStream;

	public ClientConnection(TestingClient.ClientApplication application) {
		this.application = application;
	}

	@Override
	public void run() {
		try (Socket socket = new Socket("localhost", port)) {
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new OutputStreamWriter(socket.getOutputStream());
			while (true) {
				String message = inputStream.readLine();
				if (message != null) {
					application.writeMessage(message);
				}
			}
		} catch (UnknownHostException uhe) {
			application.writeMessage("Unable to find the server...");
		} catch (IOException ioe) {
			application.writeMessage("Unable to connect to the server...");
			ioe.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			outputStream.write(message + "\n");
			outputStream.flush();
			application.writeMessage(">>" + message);
		} catch (IOException e) {
			application.writeMessage("Unable to send message: \"" + message + "\"");
		}
	}
}
