package aoop.asteroids.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class InputHandler implements Runnable {
	private Server server;
	private DatagramSocket socket;
	
	public InputHandler(Server server, DatagramSocket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			byte[] inData = new byte[1024];
			DatagramPacket dp = new DatagramPacket(inData, inData.length);
			
			try {
				socket.receive(dp);
				int len = dp.getLength();
				
				byte[] data = new byte[len];
				data = dp.getData();
				data = Arrays.copyOfRange(data, 0, len);
				
				ByteArrayInputStream is = new ByteArrayInputStream(data);
				ObjectInputStream in = new ObjectInputStream(is);
				
				boolean connect = in.readBoolean();
				Connection clientConnection = (Connection) in.readObject();
				int id = in.readInt();
				String name = (String) in.readObject();
				if (connect)
					server.connect(clientConnection, name);
				else
					server.disconnect(clientConnection, id);
				
				in.close();
				is.close();
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(os);
				
				int clientId = server.getClientId();
				out.writeInt(clientId);
				out.flush();
				
				data = os.toByteArray();				
				dp = new DatagramPacket(data, data.length, clientConnection.getIp(), clientConnection.getPort());
				socket.send(dp);
				
				out.close();
				os.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
