package Workload;

import java.util.ArrayList;
import java.util.List;

public class Processor {

	private int numOfCores, capacity;
	private List<Core> coreList= new ArrayList<Core>();
	
	public Processor (int cores, int cap) {
		this.setNumOfCores(cores); 
		this.setCapacity(cap);
		for(int i = 0; i < this.numOfCores; i++) {
			Core temp = new Core(i, capacity);
			coreList.add(i,temp);
			new Thread(new Core(i, capacity)).start();
		}
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

}
