package game;

import java.util.ArrayList;

/**
 * Defines general properties of a specific car type
 * @author Jonas Scholten
 *
 */
public class VehicleSpecs {

	private int speed; //Distance per Tick
	private int load;
	private String name;
	private static ArrayList<VehicleSpecs> allSpecs = new ArrayList<VehicleSpecs>();
	
	/**
	 * @param speed
	 * @param load
	 * @deprecated Define a name
	 */
	public VehicleSpecs(int speed, int load) {
		this(speed,load, "Nameless "+allSpecs.size());
	}
	
	public VehicleSpecs(int speed, int load, String name) {
		this.name = name;
		this.speed = speed;
		this.load = load;
		allSpecs.add(this);
		System.out.println(this);
	}
	
	public int getSpeed() {
		return speed;
	}
	public int getLoad() {
		return load;
	}
	
	public static ArrayList<VehicleSpecs> getAllSpecs(){
		return allSpecs;
	}

	@Override
	public String toString() {
		return name+" [speed=" + speed + ", load=" + load + "]";
	}
	
	
	
//	VehicleSpecs(int speed, int load){
//		this.speed = speed;
//		this.load = load;
//	}
	
}
