package Workload;

import java.util.ArrayList;
import java.util.List;

import MQ.MySender;

public class Processor {

	private int numOfCores, capacity;
	private List<Core> coreList= new ArrayList<Core>();
	private MySender MQ;
	
	public Processor (int cores, int cap) {
		this.setNumOfCores(cores); 
		this.setCapacity(cap);
		for(int i = 0; i < this.numOfCores; i++) {
			Core temp = new Core(i, capacity);
			coreList.add(i,temp);
			new Thread(new Core(i, capacity)).start();
		}
		MQ = new MySender();
		new Thread(MQ).start();
	}

	public int getNumOfCores() {
		return numOfCores;
	}

	public void setNumOfCores(int numOfCores) {
		this.numOfCores = numOfCores;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void enqueueTask(Task t) {
		MQ.addTask(t);
	}
	
	public void stop() {
		for(int i = 0; i < this.numOfCores; i++) {
			Core c = new Core(i, capacity);
			MQ.addTask(c.abortAndSend());
		}
		MQ.stop();
		
	}

}
