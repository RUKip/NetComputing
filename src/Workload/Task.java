package Workload;

public class Task {

	private int loadperSec, deviation, duration;

	public Task(int load, int dev, int dur) {
		this.loadperSec = load;
		this.deviation = dev;
		this.duration = dur;
	}
	
	public int getLoadperSec() {
		return loadperSec;
	}
	
	public int getDeviation() {
		return deviation;
	}
	public int getDuration() {
		return duration;
	}
	
	public void decrementDuration() {
		this.duration--;
	}
	
}
