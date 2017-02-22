package aoop.asteroids.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import aoop.asteroids.Asteroids;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import aoop.asteroids.model.Game;


public class Server implements Runnable {
	
	private volatile ArrayList<Connection> clients = new ArrayList<>();
	private volatile DatagramSocket socket;
	private int packageId;
	private int clientId = 1;
		
	private Game game;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Server() {
		try {
			socket = new DatagramSocket(8850);
		} catch (SocketException se) {
//			logger.error("Unable to intialize socket");
			se.printStackTrace();
		}
	}
	
	private synchronized void send(Game game) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);

			out.writeInt(++packageId);
			out.writeObject(game);
			byte[] data = os.toByteArray();

			for (Connection client : clients)
				socket.send(new DatagramPacket(data, data.length, client.getIp(), client.getPort()));
			
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
			game.addName(name);
		}
	}
	
	public void disconnect(Connection client, int id) {
		clients.remove(client);
		game.removeSpaceship(id);
	}
	
	public void handle(int clientId, int command) {
		game.updateSpaceship(clientId, command);
	}
	
	public int getClientId() {
		return clientId++;
	}
	
	public void run() {
		try {
			DatagramSocket handlerSocket = new DatagramSocket(8851);
			DatagramSocket receiverSocket = new DatagramSocket(8852);
			
			InputHandler handler = new InputHandler(this, handlerSocket);
			Thread t_handler = new Thread(handler);
			t_handler.start();
			
			KeyReceiver receiver = new KeyReceiver(this, receiverSocket);
			Thread t_receiver = new Thread(receiver);
			t_receiver.start();
		} catch (SocketException e1) {
//			logger.error("Error in run");
			e1.printStackTrace();
		}

		Asteroids asteroids = new Asteroids();
		game = asteroids.getGame();
		

		
		long executionTime, sleepTime;
		while (true) {
			executionTime = System.currentTimeMillis();
						
			send(game);

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
