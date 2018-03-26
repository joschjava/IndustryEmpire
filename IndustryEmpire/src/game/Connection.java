package game;

import mainpack.Functions;

public class Connection {

	public static int PATH = 0;
	public static int STREET = 1;
	public static int RAIL = 2;
	public static int TUBE = 3;

	private boolean path = false;
	private boolean street = false;
	private boolean rail = false;
	private boolean tube = false;

	private double distance = -1;

	private City city1 = null;
	private City city2 = null;

	/** Create a connection between two cities */
	public Connection(City city1, City city2) {
		this.city1 = city1;
		this.city2 = city2;
		city1.addConnection(this);
		city2.addConnection(this);
		calculateDistance();
		System.out.println(this);
	}

	private void calculateDistance() {
		distance = Functions.getDistance(city1, city2);
	}

	public boolean hasPath() {
		return path;
	}

	public double getDistance() {
		return distance;
	}
	
	public void setPath(boolean path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Connection [" + city1 + "<->" + city2 + ", Distance: " + getDistance() + "]";
	}

	
	
}
