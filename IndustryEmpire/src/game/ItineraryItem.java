package game;

import java.util.Arrays;

public class ItineraryItem {
	private City destination;
	private Resource[] input;
	private Resource[] output;
	private boolean waitForFull = false;	
	
	/**
	 * Creates item for loading and unloading
	 * @param destination Destination where vehicle drives to
	 * @param input Resource that goes INTO the vehicle
	 * @param output Resource that goes OUT OF the vehicle
	 */
	public ItineraryItem(City destination, Resource[] input, Resource[] output) {
		this.destination = destination;
		this.input = input;
		this.output = output;
	}

	/**
	 * Creates item for loading and unloading
	 * @param destination Destination where vehicle drives to
	 * @param input Resource that goes INTO the vehicle
	 * @param output Resource that goes OUT OF the vehicle
	 */
	public ItineraryItem(City destination, Resource[] input, Resource[] output, boolean waitForFull) {
		this.destination = destination;
		this.input = input;
		this.output = output;
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

	public Resource[] getInput() {
		return input;
	}
	
	public void setInput(Resource[] input) {
		this.input = input;
	}

	public Resource[] getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return "ItineraryItem [destination=" + destination + ", input=" + Arrays.toString(input) + ", output="
				+ Arrays.toString(output) + "]";
	}
	   
	
	
}
