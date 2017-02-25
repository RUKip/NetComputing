
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class Server implements Runnable {
	
	private ServerSocket socket;
	private int packageId;
	private int clientId = 1;
	
	
	protected Connection clientConnection;
	private Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	private Boolean tooMuchEnergy= false;
	
	
	public Server() {
		try {
				socket = new ServerSocket(8850);
		} catch (IOException e) {
//			logger.error("Unable to intialize socket");
			e.printStackTrace();
		}
	}
	
	private synchronized void send(Message message) { //responds to request(do we still need this? Client should do this right?)
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);

			out.writeInt(++packageId);
			out.writeObject(message);
			byte[] data = os.toByteArray();

//			socket.send(new DatagramPacket(data, data.length, client.getIp(), client.getPort()));
			
			out.close();
			os.close();
		} catch (IOException e) {
//			logger.debug("Unable to send datagram packet");
			e.printStackTrace();
		}
	}
	
	public void connect(Connection client, String name) {
		if (!clients.contains(client)) {
			clients.add(client);
			message.addName(name);
		}
	}
	
	public void disconnect(Connection client, int id) {
		clients.remove(client);
		message.removeSpaceship(id);
	}
	
	public void handle(int clientId, int command) {
		message.updateSpaceship(clientId, command);
	}
	
	public int getClientId() {
		return clientId++;
	}
	
	public void run() {
		try {
			ServerSocket handlerSocket = new ServerSocket(8851);
			ServerSocket receiverSocket = new ServerSocket(8852);
			
			InputHandler handler = new InputHandler(this, handlerSocket);
			Thread t_handler = new Thread(handler);
			t_handler.start();
			
			Receiver receiver = new Receiver(this, receiverSocket);
			Thread t_receiver = new Thread(receiver);
			t_receiver.start();
		} catch (SocketException e1) {
//			logger.error("Error in run");
			e1.printStackTrace();
		}
		

		
		long executionTime, sleepTime;
		while (true) {
			executionTime = System.currentTimeMillis();
						
			send(message);

			executionTime -= System.currentTimeMillis();
			sleepTime = 40 + executionTime;
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
//				logger.debug("Unable to send datagram packet");
				e.printStackTrace();
			}
		}
	}
}
