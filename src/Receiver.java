

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Receiver implements Runnable {
	
	private DatagramSocket socket;
	private Server server;
	private int lastPackageId;
	
	public Receiver(Server server, DatagramSocket socket){
		this.socket = socket;
		this.server = server;
	}
	
	public synchronized void receive(){
		try {
			byte[] inData = new byte[1024];
			DatagramPacket dp = new DatagramPacket(inData, inData.length);
			socket.receive(dp);
			
			int len = dp.getLength();
			byte[] data = new byte[len];
			
			data = dp.getData();
			data = Arrays.copyOfRange(data, 0, len);
			
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(is);
			
			int clientId = in.readInt();
			int command = in.readInt();
	
			server.handle(clientId, command);
//			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true)
			receive();
	}
}