import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * 
 * @author Robert WIndisch and Nick Martinez
 * 
 * Client to create a connection with server
 * Sends messages to be reversed
 *
 */
public class BasicClient
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		System.out.println("Client is running");

		//Create connection with server
		Socket connect;
		connect = new Socket("127.0.0.1",4446);

		// Read incoming Info.
		Scanner scan = new Scanner(connect.getInputStream());

		// Send messages out.
		PrintWriter out = new PrintWriter(connect.getOutputStream(), true);


		//send messages to server
		for (int i = 0; i < 2; i++)
		{
			out.println("HI SERVERS");
			out.println("0123456789");
			out.println("ABCDEFGHIJ");
			out.println("KLMNOPQRST");
			out.println("UVWXYZ");
		}
		
		String msg = scan.nextLine();
		System.out.println(msg);

		//Read messages coming back form server and print them
        while(msg != "")
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
        
        System.out.println("CLIENT DONE");
        scan.close();
        connect.close();
      
		
	}

}
