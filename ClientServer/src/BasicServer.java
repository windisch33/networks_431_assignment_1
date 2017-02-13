import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * Listens for incoming connections
 *
 * @author Robert WIndisch and Nick Martinez
 *
 */
public class BasicServer extends Thread
{
	/**
	 * listens for connections
	 * if one is found sends it to thread to handle
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		System.out.println("JAVA Server is running");

		//Create sockets
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(4446);
		Socket connect;

		//Listen for multiple clients
		while(true)
		{
			connect = server.accept();
			ClientsThread newClient = new ClientsThread(connect);
			Thread thread = new Thread(newClient);
			thread.start();
		}

	}
}
