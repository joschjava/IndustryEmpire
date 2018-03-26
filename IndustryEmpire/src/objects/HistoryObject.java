package objects;

public class HistoryObject {

	private double value;
	private long time;
	
	public HistoryObject(double value, long time) {
		this.value = value;
		this.time = time;
	}

	public double getValue() {
		return value;
	}

	public long getTime() {
		return time;
	}
	
	
	
}
