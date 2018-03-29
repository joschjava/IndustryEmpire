package game;

import java.util.Arrays;

public class ItineraryItem {
	private City destination;
	private Resource[] load;
	private boolean waitForFull = false;	
	
	
	/**
	 * Creates item for loading in city
	 * @param destination Destination where vehicle drives to
	 * @param input Resource that goes INTO the vehicle at destination
	 */
	public ItineraryItem(City destination, Resource[] input) {
		this.destination = destination;
		this.load = input;
	}
	
	/**
	 * Creates item for loading and unloading
	 * @param destination Destination where vehicle drives to
	 * @param load Resource that goes INTO the vehicle at destination
	 */
	public ItineraryItem(City destination, Resource[] load, boolean waitForFull) {
		this.destination = destination;
		this.load = load;
		this.waitForFull = waitForFull;
	}
	
	public void setWaitForFull(boolean waitForFull) {
		this.waitForFull = waitForFull;
	}
	
	public City getDestination() {
		return destination;
	}

	
	
	public boolean getWaitForFull() {
		return waitForFull;
	}

	public Resource[] getLoad() {
		return load;
	}
	
	public void setLoad(Resource[] load) {
		this.load = load;
	}

	@Override
	public String toString() {
		return "ItineraryItem [destination=" + destination + ", input=" + Arrays.toString(load) + ", ]";
	}
	   
	
	
}
