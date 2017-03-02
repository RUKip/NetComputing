
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Arrays;

public class InputHandler implements Runnable {
	private Server server;
	private Socket socket;
	
	public InputHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	
	protected Message receiveMessage() {
		Message message = null;
		
		try {
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			message = (Message) inStream.readObject();
			
			socket.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	@Override
	public void run() {
		while (true) {
				Message message = receiveMessage();
				
				System.out.println(message.getData());	//TODO: now just for showing it works.
//				
//				boolean connect = in.readBoolean();
//				Connection clientConnection = (Connection) in.readObject();
//				int id = in.readInt();
//				String name = (String) in.readObject();
//				if (connect)
//					server.connect(clientConnection, name);
//				else
//					server.disconnect(clientConnection, id);
//				
//				in.close();
//				is.close();
//				
//				ByteArrayOutputStream os = new ByteArrayOutputStream();
//				ObjectOutputStream out = new ObjectOutputStream(os);
//				
//				int clientId = server.getClientId();
//				out.writeInt(clientId);
//				out.flush();
//				
//				data = os.toByteArray();				
//				dp = new DatagramPacket(data, data.length, clientConnection.getIp(), clientConnection.getPort());
//				socket.send(dp);
//				
//				out.close();
//				os.close();
				
		}
	}
}
