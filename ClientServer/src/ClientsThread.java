import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * @author Robert WIndisch and Nick Martinez
 * 
 *creates a runnable connection for each client connected to the server
 *
 */
public class ClientsThread implements Runnable {

	Socket clientConnection;

	/**
	 * creates the client connection
	 * @param connect
	 * @throws IOException
	 */
	public ClientsThread(Socket connect) throws IOException {

		clientConnection = connect;
	}

	/**
	 * Runs the server reading and 
	 * handling of incoming messages
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		// To Read incoming Info.
		Scanner scan = null;
		try {
			scan = new Scanner(clientConnection.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// To Send messages out.
		PrintWriter out = null;
		try {
			out = new PrintWriter(clientConnection.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String msg;
	

		for (int i = 0; i < 10; i++) {
			try {
				msg = scan.nextLine();
			} catch (NoSuchElementException e) {
				break;
			}

			out.println(reverse(msg));

		}

		scan.close();
		
		try {
			clientConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Takes a string and reverses it
	 * 
	 * @param msg
	 *            to reverse
	 * @return reversed message
	 */
	private static String reverse(String msg) {

		String newMsg = "";

		for (int i = 0; i < msg.length(); i++) {
			newMsg = newMsg + msg.charAt(((msg.length() - 1) - i));

		}

		return newMsg;

	}

}
