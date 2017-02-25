import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


//TODO: change names of client server to sender receiver

import aoop.asteroids.Asteroids;
import aoop.asteroids.menu.MenuModel;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import aoop.asteroids.model.message;

public class Client implements Runnable {
	
	protected DatagramSocket socket;
	
	protected Connection connection, serverConnection;
	
	protected int lastPackageId;
	
	protected Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(String serverAddress, int serverPort) {
		serverConnection = new Connection(serverAddress,serverPort);
		try {
			socket = new DatagramSocket();
			connection = new Connection(InetAddress.getLocalHost().getHostAddress(), socket.getLocalPort());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	protected Message receivemessage() {
		Message message = null;
		
		try {
			byte[] inData = new byte[4096];
			DatagramPacket dp = new DatagramPacket(inData, inData.length);
			socket.receive(dp);
			
			int len = dp.getLength();
			byte[] data = new byte[len];
			data = dp.getData();
			
			data = Arrays.copyOfRange(data, 0, len);
			
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(is);
			
			int packageId = in.readInt();
			if (lastPackageId < packageId) {
				lastPackageId = packageId;
				message = (Message) in.readObject();
			}
			
			in.close();
			is.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	protected int connectToServer() {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);
			out.writeBoolean(true); //connect == true, disconnect == false
			out.writeObject(connection);
			out.writeInt(0); // 'id'
			out.writeObject(MenuModel.getNickname());
			byte[] data = os.toByteArray();
			
			DatagramPacket dp = new DatagramPacket(data, data.length, serverConnection.getIp(), 8851);
			socket.send(dp);
			
			out.close();
			os.close();
						
			byte[] inData = new byte[4096];
			dp = new DatagramPacket(inData, inData.length);
			socket.receive(dp);
			
			int len = dp.getLength();
			data = new byte[len];
			data = dp.getData();
			
			data = Arrays.copyOfRange(data, 0, len);
			
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(is);
			
			int clientId = in.readInt();
			
			in.close();
			is.close();
			
			return clientId;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		logger.error("something went wrong while connecting to server"); 
		//change to logger thing - probably in catch blocks
		return -1;
	}
	
	//TODO use this somewhere
	protected void disconnectFromServer(int id) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);
			out.writeBoolean(false); //connect == true, disconnect == false
			out.writeObject(connection);
			out.writeInt(id);
			out.writeObject(MenuModel.getNickname());
			byte[] data = os.toByteArray();
			
			DatagramPacket dp = new DatagramPacket(data, data.length, serverConnection.getIp(), 8851);
			socket.send(dp);
			
			out.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int id = connectToServer();
		asteroids = new Asteroids(socket, serverConnection, receiveGame());
		this.message = asteroids.getGame();
		
		Message game;
		while (true) {
			if (this.game.isStopped()) {
				disconnectFromServer(id);
				return;
			}
			message = receiveGame();
			if (message != null) {
				this.message.updateClient(message);
			}
		}
	}
}
