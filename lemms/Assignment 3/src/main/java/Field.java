import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Field extends Observable implements Runnable {

	private String address;
	private int port;
	private ServerSocket serverSocket;

	private volatile FieldMap fieldMap = new FieldMap();
	private Observer fieldView;

	private int capacity;
	private volatile ArrayList<Lemming> lemmings = new ArrayList<>();
	
	public Field(int port, int neighbourPort) {
		capacity = new Random().nextInt(5)+8;
		this.address = "localhost";
		this.port = port;
		fieldMap.add(port);
		
		if (neighbourPort != 0)
			fieldMap.add(neighbourPort);
		
		try {
			serverSocket = new ServerSocket(port);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while (true) {
				setChanged();
				notifyObservers();
				Socket inConn = serverSocket.accept();
				Thread handler = new Thread(new InputHandler(inConn, this));
				handler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public synchronized void addLemming(Lemming lemming) {
		lemmings.add(lemming);
		fieldView.update(this, null);
	}

	public synchronized void removeLemming(Lemming lemming) {
		lemmings.remove(lemming);
		fieldView.update(this, null);
	}
	
	public void addObserver(Observer fieldView) {
		this.fieldView = fieldView;
	}

	
	// Getters
	public synchronized String getLemmingsListing() {
		String listing = new String();
		for (Lemming lemming : lemmings)
			listing += lemming.getName() + "\n";
		return listing;
	}

	public synchronized String getFieldsListing() {
		return fieldMap.toString();
	}
	
	public int getCapacity() {
		return capacity;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public FieldMap getFieldMap() {
		return fieldMap;
	}

	public synchronized int getNumberOfLemmings() {
		return lemmings.size();
	}

	
}