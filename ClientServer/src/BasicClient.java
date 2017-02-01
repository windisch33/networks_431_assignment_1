import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BasicClient
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		System.out.println("Client is running");

		Socket connect;
		connect = new Socket("127.0.0.1",2333);
		//connect = new Socket("localhost",2333);

		// Read incoming Info.
		Scanner scan = new Scanner(connect.getInputStream());

		// Send messages out.
		PrintWriter out = new PrintWriter(connect.getOutputStream(), true);


		out.println("Hello server this is the client");
		out.println("1 2 3 4 5 6 7 8 9");
		out.flush();

		String msg = scan.nextLine();
		System.out.println(msg);

        while(msg != null)
        {

        	try
        	{
        		msg = scan.nextLine();
        	}catch (NoSuchElementException e)
        	{
        		connect.close();
        		break;
        	}

        	System.out.println(msg);

        }

		connect.close();
	}

}
