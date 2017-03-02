import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


//TODO: change names of client server to sender receiver

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Client implements Runnable {
	
	private ArrayList<Connection> servers = new ArrayList<>();

	protected Socket socket;
	
	protected int lastPackageId;
	
	protected Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(String serverAddress, int serverPort) {
	}
	
	protected int connectToServer(Connection connection) {
		try {
			socket = new Socket(connection.getIp(), connection.getPort());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		logger.error("something went wrong while connecting to server"); 
		//change to logger thing - probably in catch blocks
		return -1;
	}
	
//	//TODO use this somewhere
//	protected void disconnectFromServer(int id) {
//		try {
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			ObjectOutputStream out = new ObjectOutputStream(os);
//			out.writeBoolean(false); //connect == true, disconnect == false
//			out.writeObject(connection);
//			out.writeInt(id);
//			out.writeObject();
//			byte[] data = os.toByteArray();
//			
//			DatagramPacket dp = new DatagramPacket(data, data.length, serverConnection.getIp(), 8851);
//			socket.send(dp);
//			
//			out.close();
//			os.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void run() {
		
	}
}
