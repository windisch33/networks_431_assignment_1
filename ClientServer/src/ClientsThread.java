import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientsThread extends Thread{

	public ClientsThread(Socket connect) throws IOException {


		// Read incoming Info.
		Scanner scan = new Scanner(connect.getInputStream());
		// Send messages out.
		PrintWriter out = new PrintWriter(connect.getOutputStream(), true);

		String msg = scan.nextLine();
		out.println(reverse(msg));

        while(msg != null)
        {
        	try
        	{
        		msg = scan.nextLine();
        	}catch (NoSuchElementException e)
        	{
        		break;
        	}

             out.println(reverse(msg));

        }

        out.flush();
        connect.close();

	}

	private static String reverse(String msg) {

		String newMsg = "";

		for(int i = 0; i < msg.length(); i++)
		{
			newMsg = newMsg + msg.charAt(((msg.length()-1)- i));

		}

		return newMsg;


	}

}
