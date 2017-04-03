package Workload;

import java.util.ArrayList;
import java.util.List;

import MQ.MySender;

public class Processor {

	private int numOfCores, capacity;
	private List<Core> coreList= new ArrayList<Core>();
	private MySender MQ;
	
	public Processor (int cores, int cap) {
		if (cap < 20) cap = 20;
		this.setNumOfCores(cores); 
		this.setCapacity(cap);
		for(int i = 0; i < this.numOfCores; i++) {
			Core temp = new Core(i, capacity);
			coreList.add(i,temp);
			new Thread(temp).start();
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
	
	public int getWorkload(){
		int totalLoad = 0;
		for(Core core : this.coreList){
			totalLoad += core.getWorkload();
		}
		//System.out.println("load = " + totalLoad + " div = " + this.numOfCores*this.capacity);
		return (100*totalLoad)/(this.numOfCores*this.capacity);
	}
	
	public void stop() {
		for(Core core : this.coreList){
			MQ.addTask(core.abortAndSend());
		}
		MQ.stop();
		
	}

}
