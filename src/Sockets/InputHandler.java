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
	}

	
	protected Message receiveMessage() {
		Message message = null;
		
		try {
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			message = (Message) inStream.readObject();
			System.out.println("Server is receiving the message: "+ message.getData());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	
	private void sendMessage(Message m){
		System.out.println("Server is sending a message back to client: " + m.getData());
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
			System.out.println("The server is busy; it cannot accept requests ");
		}else{
			server.setBusy(true);
			//TODO: return to client message that server is started and that it should stop searching
			sendMessage(new Message(Message.RESPONSE_TYPE, Message.AVAILABLE_MESSAGE));
			System.out.println("The server is available; it will accept a connection and start working ");
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
//					System.out.println("Socket was closed");
				} catch (IOException e) {
					System.out.println("Goes wrong here");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
