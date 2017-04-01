package Workload;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import MQ.MyReceiver;

public class Core implements Runnable, Comparable<Core> {
	
	private int number, capacity, workload;
	private boolean run;
	private List<Task> taskList = new ArrayList<Task>();
	private Random r = new Random();
	private MyReceiver receiver;

	public Core(int i, int capacity) {
		this.number = i;
		this.capacity = capacity;
		this.workload = 0;
		this.run();
	}
	
	@Override
	public void run() {
		run = true;

		System.out.println("core running");
		try {
			this.receiver = new MyReceiver(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(run) {
			updateTasks();
			updateWorkload();
			try {
			    Thread.sleep(1000);                 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		receiver.stop();
	}

	private void updateTasks() {
		for (int i = 0; i < taskList.size(); i++) {
			taskList.get(i).decrementDuration();
		}
	}

	private void updateWorkload() {
		int tempLoad = 0;
		for (int i = 0; i < taskList.size(); i++) {
			Task t = taskList.get(i);
			tempLoad = tempLoad + t.getLoadperSec() + t.getDeviation() * (r.nextInt(2) - 1); 
		}
		workload = tempLoad;
	}
	
	public List<Task> abortAndSend() {
		this.run = false;
		return this.taskList;
	}

	//TODO: switch to available resources
	@Override
	public int compareTo(Core c) {
		if(c != null && c instanceof Core) 
			return (this.capacity - this.workload) - (c.getCapacity() - c.getWorkload());
		return 0;
	}
	
	public void addTask(Task t) {
		this.taskList.add(t);
	}

	public int getNumber() {
		return number;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getWorkload() {
		return workload;
	}

}
