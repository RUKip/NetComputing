package Sockets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InputHandler implements Runnable {
	private SocketServer server;
	private Socket socket;
	
	
	//handles the input for one connection between client and server, server uses inputHandlers for one to many connection
	public InputHandler(SocketServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		System.out.println("completed making inputhandler");
	}

	
	protected Message receiveMessage() {
		Message message = null;
		System.out.println("receiving message");
		try {
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			message = (Message) inStream.readObject();
	
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	
	private void sendMessage(Message m){
		System.out.println("sending a message back to client");
	    try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		    out.flush();
			out.writeObject(m);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void respond(){ //respond to client request
		if(server.isBusy()){
			//return to client message that server is already busy and it should keep searching
			sendMessage(new Message(Message.RESPONSE_TYPE, Message.BUSY_MESSAGE));
			System.out.println("I am Busy so don't accept requests");
		}else{
			server.setBusy(true);
			//TODO: return to client message that server is started and that it should stop searching
			sendMessage(new Message(Message.RESPONSE_TYPE, Message.AVAILABLE_MESSAGE));
			System.out.println("I am available so starting now");
		}
	}
	
	@Override
	public void run() {
				Message message = receiveMessage();
				
				System.out.println("Client: " + message.getData());	//TODO: now just for showing it works.
//				System.out.println("trying to respond");
				respond();
//				System.out.println("respod happened");
					
				try {
					socket.close();
					System.out.println("Socket was closed");
				} catch (IOException e) {
					System.out.println("Goes wrong here");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
