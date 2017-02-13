import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * 
 * @author Robert WIndisch and Nick Martinez
 * 
 *creates a runnable connection for each client connected to the server
 *
 */
public class ClientsThread implements Runnable {

	Socket clientConnection;
	private static final int MESSAGE_LEN = 10;
	private static final int NUM_MESSAGES = 10;

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
	public void run(){
		// TODO Auto-generated method stub


		// DataInputStream for reading bytes in
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(clientConnection.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// DataOutputStream for writing ASCII bytes out
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(clientConnection.getOutputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	
		
		byte[] inMessage = new byte[MESSAGE_LEN];
	
		//Read message from client and send to reverse method
		for (int i = 0; i < NUM_MESSAGES; i++) {
    		try {
				dis.read(inMessage, 0, MESSAGE_LEN);
				System.out.println(new String(inMessage, "US-ASCII"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		try {
				dos.write(reverse(inMessage), 0, MESSAGE_LEN);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
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
	 * @param inMessage
	 *            to reverse
	 * @return reversed message
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] reverse(byte[] inMessage) throws UnsupportedEncodingException {

		byte[] newMsg = new byte[MESSAGE_LEN];
		
		System.out.println(new String(inMessage, "US-ASCII"));
		  for( int i = 0, j = MESSAGE_LEN - 1; i < j; ++i, --j ) 
		  {
		    newMsg[i] = inMessage[j];
		  } 
		  System.out.println(new String(newMsg, "US-ASCII"));
		return newMsg;

	}

}
