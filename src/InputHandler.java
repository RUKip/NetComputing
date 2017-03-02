import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InputHandler implements Runnable {
	private Server server;
	private Socket socket;
	
	
	//handles the input for one connection between client and server, server uses inputHandlers for one to many connection
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
	
	
	private void sendMessage(Message m){
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
		}else{
			server.setBusy(true);
			//TODO: return to client message that server is started and that it should stop searching
			sendMessage(new Message(Message.RESPONSE_TYPE, Message.AVAILABLE_MESSAGE));
		}
	}
	
	@Override
	public void run() {
		while (true) {
				Message message = receiveMessage();
				
				System.out.println(message.getData());	//TODO: now just for showing it works.
				
				respond();
				
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
