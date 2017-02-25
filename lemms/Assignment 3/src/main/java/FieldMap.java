import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class FieldMap implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private volatile ArrayList<FieldConnector> connections = new ArrayList<FieldConnector>();
	
	public synchronized void update(FieldMap fieldMap) {
		for (FieldConnector fc : fieldMap.connections)
			if (!connections.contains(fc))
				connections.add(fc);
	}

	public synchronized FieldConnector getRandomConn() {
		return connections.get(new Random().nextInt(connections.size()));
	}
	
	public synchronized void add(int port) {
		connections.add(new FieldConnector(port));
	}

	public synchronized void add(String address, int port) {
		connections.add(new FieldConnector(address, port));		
	}
	
	public synchronized String toString() {
		String result = new String();
		for (FieldConnector fc : connections)
			result += fc.getAddress() + ":" + fc.getPort() + "\n";
		return result;
	}
}
