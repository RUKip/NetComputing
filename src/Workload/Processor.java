package Workload;

import java.util.ArrayList;
import java.util.List;

import MQ.MyReceiver;
import MQ.MySender;

public class Processor {

	private int numOfCores, capacity;
	private List<Core> coreList= new ArrayList<Core>();
	private MySender MQ;
	
	public Processor (int cores, int cap) {
		if (cores < 1) cores = 1;
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
		try {
			new MyReceiver(this);
		} catch (Exception e) {
			e.printStackTrace();
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

	public Core getLeastActiveCore() {
		Core leastActive = coreList.get(0);
		for (Core core : coreList) {
			if (core.space() > leastActive.space()) leastActive = core;
		}
		return leastActive;
	}

}
