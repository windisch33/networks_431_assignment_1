import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
/**
 * Client to create a connection with server
 * Sends messages to be reversed
 *
 * @author Robert Windisch and Nick Martinez
 *
 */
public class BasicClient
{
	private static final int NUM_MESSAGES = 10;
	private static final int MESSAGE_LEN = 10;

	public static void main(String[] args) throws IOException
	{
		System.out.println("JAVA Client is running");

		// Create connection with server
		Socket connect = new Socket("127.0.0.1", 4446);

		// DataInputStream for reading bytes in
		DataInputStream dis = new DataInputStream(connect.getInputStream());

		// DataOutputStream for writing ASCII bytes out
		DataOutputStream dos = new DataOutputStream(connect.getOutputStream());

		
		byte[] outMessage = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
		byte[] inMessage = new byte[MESSAGE_LEN];
		// read messages coming back from server and print them
        for(int i = 0; i < NUM_MESSAGES; i++)
        {
        	try
        	{
        		//System.out.println(outMessage.getBytes("US-ASCII"));
				dos.write(outMessage, 0, MESSAGE_LEN);
        		dis.read(inMessage, 0, MESSAGE_LEN);
        	}catch (NoSuchElementException e)
        	{
        		connect.close();
        		break;
        	}
        	System.out.println(new String(inMessage, "US-ASCII"));
        }
        
        System.out.println("JAVA client done");
        connect.close();
	}
}
