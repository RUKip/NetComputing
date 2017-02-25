import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

public class Lemming implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int SLEEP = 0;
	private static final int GIVE_BIRTH = 1;
	private static final int MOVE = 2;
	
	private FieldMap fieldMap;
	private transient Field field;
	private String name;
	
	
	public Lemming(Field field, String name) {
		this.field = field;
		this.fieldMap = field.getFieldMap();
		this.name = name;
	}
	
	public Lemming(Lemming lemming, Field field) {
		this.field = field;
		
		//Like this because we need to recreate a lemming in a new field when it moves
		this.name = lemming.name;
		this.fieldMap = lemming.fieldMap;
		this.field.getFieldMap().update(fieldMap);
	}
	
	@Override
	public void run() {
		
		if (field.getNumberOfLemmings() > field.getCapacity())
			suicide();
		
		while(true){
			
			if (Thread.interrupted()) {
				System.out.println(name + " terminated its thread.");
				return;
			}
			
			//Make them sleep before choices - easier to understand the actions
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				continue;
			}
			
			/** Choices of the lemmings**/
			int option = new Random().nextInt(3);
			switch(option){
				case SLEEP : 
					sleep();
					break;
				case GIVE_BIRTH : 
					giveBirth();
					break;
				case MOVE : 
					move();
					break;
			}	
		}
	}
	
	/**The lemming moves between fields**/
	private void move() {
		try {
			FieldConnector connector = fieldMap.getRandomConn();
			
			//If lemming tries to move to its own field, abort move.
			if (connector.getAddress().equals(field.getAddress()) && 
					connector.getPort() == field.getPort()) { 
				System.out.println(name + " tried moving to its own field.");
				return;
			}
			fieldMap.update(field.getFieldMap());
			connector.send(this);
			System.out.println(name + " just moved!");

			suicide();

		} catch (IllegalArgumentException | IOException e) {
			System.out.println(name + ": move failed");
		}
	}

	private void giveBirth() {
		int tag = new Random().nextInt(Main.names.length);
		Lemming baby = new Lemming(field, Main.names[tag]);
		
		Thread babyThread = new Thread(baby);
		babyThread.start(); 
		
		field.addLemming(baby);
		System.out.println(baby.name + " was born!");
		
		if (field.getNumberOfLemmings() > field.getCapacity())
			suicide();
	}

	private void sleep() {
		int timer = new Random().nextInt(1000) + 2000; //for at least two seconds
		try {
			Thread.sleep(timer);
			System.out.println(name + " is sleeping");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void suicide() {
		field.removeLemming(this);
		Thread.currentThread().interrupt();	
	}

	public String getName() {
		return name;
	}

}