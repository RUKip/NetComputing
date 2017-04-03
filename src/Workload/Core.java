package Workload;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import MQ.MyReceiver;

public class Core implements Runnable {
	
	private int number, capacity;
	public int workload;
	private static boolean run;
	private List<Task> taskList = new ArrayList<Task>();
	private Random r = new Random();
	private MyReceiver receiver;

	public Core(int i, int capacity) {
		this.number = i;
		this.capacity = capacity;
		this.workload = 0;
	}
	
	@Override
	public void run() {
		run = true;

		//System.out.println("core running");
//		try {
//			this.receiver = new MyReceiver(this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		while(run) {
			updateWorkload();
//			if (this.workload != 0)
//				System.out.println("Workload: " + this.workload);
			updateTasks();
			try {
			    Thread.sleep(1000);                 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		if(receiver != null) receiver.stop();
	}

	private void updateTasks() {
		for (int i = 0; i < taskList.size(); i++) {
			Task t = taskList.get(i);
			if (t.getDuration() > 1)
				t.decrementDuration();
			else {
				taskList.remove(i);
				//System.out.println("completed task!");
			}
			
		}
	}

	private void updateWorkload() {
		int tempLoad = 0;
		for (int i = 0; i < taskList.size(); i++) {
			Task t = taskList.get(i);
			double rand = ((float) r.nextInt(200) / 100.0) - 1.0;
			int dev = (int) (t.getDeviation() * rand);
			dev = (dev > -t.getLoadperSec() ? dev : -t.getLoadperSec());
			tempLoad = tempLoad + t.getLoadperSec() + dev; 
		}
		workload = tempLoad;
	}
	
	public List<Task> abortAndSend() {
		Core.run = false;
		return this.taskList;
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
		return this.workload;
	}
	
	public int getMaxLoad() {
		int total = 0;
		for (int i = 0; i < taskList.size(); i++) {
			Task t = taskList.get(i);
			total += (t.getLoadperSec() + t.getDeviation());
		}
		return total;
	}

	public int space() {
		return capacity - workload;
	}

}
