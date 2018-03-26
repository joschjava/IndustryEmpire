package game;

import java.util.ArrayList;

import Exceptions.DoubleResourceException;

/** Defines how much in- and output is needed for the building to produce something */
public class BuildingSpec {

	public static final int INPUT = 0;
	public static final int OUTPUT = 1;
	
	private ArrayList<Resource> inputRes = new ArrayList<Resource>();
	private ArrayList<Resource> outputRes = new ArrayList<Resource>();
	
	private String name;
	
	/** Defines how quickly one output is produced */
	private int speed;
	
	/**
	 * Defines a building prototype
	 * @param name Name of the building
	 * @param name Defines how quickly one output is produced
	 */
	public BuildingSpec(String name, int speed) {
		this.name = name;
		this.speed = speed;
	}

	/**
	 * Don't add same Resource in input and output!
	 * @param resSpec
	 * @param amount
	 * @param direction Use BuildingSpec.INPUT and OUTPUT
	 */
	public void addResource(ResourceSpec resSpec, int amount, int direction) {
		Resource res = new Resource(resSpec);
		res.setAmount(amount);
		if(direction == INPUT) {
			inputRes.add(res);
		} else if (direction == OUTPUT) {
			outputRes.add(res);
		} else {
			System.err.println("Invalid direction for "+resSpec+": "+direction);
		}
	} 
	
	/**
	 * Returns the ticks that are needed to produce one output
	 * @return 
	 */
	public int getSpeed() {
		return speed;
	}
	
	public ArrayList<Resource> getInputRes() {
//		return copyResourceArray(inputRes);
		return inputRes;
	}

	public ArrayList<Resource> getOutputRes() {
		return outputRes;
//		return copyResourceArray(outputRes);
	}
	
	private ArrayList<Resource> copyResourceArray(ArrayList<Resource> array) {
		ArrayList<Resource> inputCopy = new ArrayList<Resource>();
		for(Resource r:array) {
			inputCopy.add(r.getCopy());
		}
		return inputCopy;
	}



	public String getName() {
		return name;
	}
}
