import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class FieldConnector implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String address;
	private int port;
	
	public FieldConnector(int port) {
		this.address = "localhost";
		this.port = port;
	}
	
	public FieldConnector(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	/*Send the lemmings between the connected fields*/
	public void send(Lemming lemming) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);

		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		outStream.writeObject(lemming);
		
		socket.close();
	}
	
	public boolean equals(Object o){
		FieldConnector toCompare = (FieldConnector) o;
		return (address.equals(toCompare.address) && this.port == toCompare.port);
		

	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
}