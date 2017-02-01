import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BasicServer
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Server is running");

		ServerSocket server = new ServerSocket(2333);

		Socket connect = server.accept();


		ClientsThread thread = new ClientsThread(connect);
		thread.start();

	}



}
