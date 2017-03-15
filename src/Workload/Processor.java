package Workload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Processor implements Runnable {

	private int numOfCores, capacity;
	private boolean run;
	private List<Core> coreList= new ArrayList<Core>();
	private Queue<Task> taskQueue = new LinkedList<Task>();
	
	public Processor (int cores, int cap) {
		this.setNumOfCores(cores); 
		this.setCapacity(cap);
		for(int i = 0; i < this.numOfCores; i++) {
			coreList.add(i, new Core(i, capacity));
		}
	}

	@Override
	public void run() {
		run = true;
		while(run) {
			if(taskQueue.isEmpty()) {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			} else {
				Core min = coreList.get(coreList.indexOf(Collections.min(coreList)));
				if (min.getCapacity()-min.getWorkload() > taskQueue.peek().getLoadperSec()) {
					min.addTask(taskQueue.poll());
				}
			}
		}
	}
	
	public void addTask(Task t) {
		this.taskQueue.add(t);
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
