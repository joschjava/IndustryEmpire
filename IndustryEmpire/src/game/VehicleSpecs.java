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
	private int tankSize;
	/** Consumption per 100 px */
	private double fuelConsumption;
	
	/**
	 * @param speed
	 * @param load
	 * @deprecated Define a name
	 */
	public VehicleSpecs(int speed, int load) {
		this(speed,load, "Nameless "+allSpecs.size());
	}
	
	/**
	 * 
	 * @param speed
	 * @param load
	 * @param name
	 * @deprecated Define tank size and fuel consumption
	 */
	public VehicleSpecs(int speed, int load, String name) {
		this(speed,load,name,40,20);
		System.err.println("Vehicle tankSize and fuel consumption not defined, used standard values!");
	}
	
	public VehicleSpecs(int speed, int load, String name, int tankSize, double fuelConsumption) {
		this.name = name;
		this.speed = speed;
		this.load = load;
		this.tankSize = tankSize;
		this.fuelConsumption = fuelConsumption;
		allSpecs.add(this);
	}
	
	/** Consumption per 100 px */
	public double getFuelConsumption() {
		return fuelConsumption;
	}
	
	public int getTankSize() {
		return tankSize;
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
