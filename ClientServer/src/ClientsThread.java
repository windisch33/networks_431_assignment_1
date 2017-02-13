import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
			System.out.println("Error creating dis");
		}

		// DataOutputStream for writing ASCII bytes out
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(clientConnection.getOutputStream());
		} catch (IOException e2) {
			System.out.println("Error creating dos");
		
		}
		
	
		
		byte[] inMessage = new byte[MESSAGE_LEN];
	
		//Read message from client and send to reverse method
		for (int i = 0; i < NUM_MESSAGES; i++) {
    		try {
				dis.read(inMessage, 0, MESSAGE_LEN);
			} catch (IOException e1) {
				System.out.println("Error reading message");
			}
    		try {
				dos.write(reverse(inMessage), 0, MESSAGE_LEN);
			} catch (IOException e) {
				System.out.println("Error writing message");
			}
		}

		
		
		try {
			clientConnection.close();
		} catch (IOException e) {
			System.out.println("Error closing server");
		}

	}

	/**
	 * Takes a message and reverses it
	 * 
	 * @param inMessage
	 *            to reverse
	 * @return reversed message
	 */
	private static byte[] reverse(byte[] inMessage){

		byte[] newMsg = new byte[MESSAGE_LEN];
		
		  for( int i = 0, j = MESSAGE_LEN - 1; i < MESSAGE_LEN; ++i, --j ) 
		  {
		    newMsg[i] = inMessage[j];
		  } 
		 
		return newMsg;

	}

}
