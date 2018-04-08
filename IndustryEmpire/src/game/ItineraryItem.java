package game;

import java.util.Arrays;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Callback;

public class ItineraryItem {
	private City destination;
	private Resource[] load;
	private boolean waitForFull = false;	
	public BooleanProperty update = new SimpleBooleanProperty(false);
	private BooleanProperty nextDestination = new SimpleBooleanProperty(false);
	
	
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
	
	//TODO: Solve this!
	public static Callback<ItineraryItem, Observable[]> extractor() {
        return (ItineraryItem i) -> new Observable[] {
        		i.update
//                i.nameProperty(),
//                i.categoryProperty(),
        };
}
	
	public void setWaitForFull(boolean waitForFull) {
		this.waitForFull = waitForFull;
	}
	
	public City getDestination() {
		return destination;
	}

	public void setNextDestination(boolean nextDestination) {
		this.nextDestination.set(nextDestination);
	}
	
	public BooleanProperty nextDestinationProperty() {
		return nextDestination;
	}
	
	public boolean getWaitForFull() {
		return waitForFull;
	}

	public Resource[] getLoad() {
		return load;
	}
	
	public void setLoad(Resource[] load) {
//		update.set(true);
		this.load = load;
	}

	@Override
	public String toString() {
		return "ItineraryItem [destination=" + destination + ", input=" + Arrays.toString(load) + ", ]";
	}
	   
	
	
}
