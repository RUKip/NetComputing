
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Sender {
	
	private DatagramSocket socket;
	private Connection serverConnection;
		
	public Sender(DatagramSocket socket, Connection connection){
		this.socket = socket;
		this.serverConnection = connection;
	}
	
	public synchronized void send(int command, int id){
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);
			
			out.writeInt(id);
			out.writeInt(command);
			out.flush();
			
			byte[] data = os.toByteArray();
			DatagramPacket dp = new DatagramPacket(data, data.length, serverConnection.getIp(), 8852);
//			DatagramPacket dp = new DatagramPacket(data, data.length, serverConnection.getIp(), serverConnection.getPort());

			socket.send(dp);
			out.close();
			os.close();
		} catch (IOException ioe) {
//			logger.debug("Unable to send datagram packet");
			ioe.printStackTrace();
		}

	}
}
