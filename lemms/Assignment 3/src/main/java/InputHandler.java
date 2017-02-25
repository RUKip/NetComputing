import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class InputHandler implements Runnable {
		private Socket socket;
		private Field field;
		
		public InputHandler(Socket socket, Field field) {
			this.socket = socket;
			this.field = field;
		}
		
		public void run() {
			try {
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
				Lemming in = (Lemming) inStream.readObject();
				Lemming lemming = new Lemming(in, field);
			
				field.addLemming(lemming);
					
				Thread thread = new Thread(lemming);				
				thread.start();				
				
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}