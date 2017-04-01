package Workload;

import java.util.Arrays;
import java.util.List;

public class Task {

	private int loadperSec, deviation, duration;

	public Task(int load, int dev, int dur) {
		this.loadperSec = load;
		this.deviation = dev;
		this.duration = dur;
	}
	
	public Task(String message) {
		List<String> values = Arrays.asList(message.split(","));
		this.loadperSec = Integer.parseInt(values.get(0));
		this.deviation = Integer.parseInt(values.get(1));
		this.duration = Integer.parseInt(values.get(2));
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

	@Override
	public String toString() {
		return loadperSec + "," + deviation + "," + duration;
	}
	
}
