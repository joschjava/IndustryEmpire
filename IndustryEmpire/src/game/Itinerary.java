package game;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Itinerary {

	private ArrayList<ItineraryItem> itinerary = new ArrayList<ItineraryItem>();
	private ObservableList<ItineraryItem> observableList;
	private boolean loop = true;
	private IntegerProperty curPos = new SimpleIntegerProperty();
	
	/**
	 * @param itineraryView
	 */
	public Itinerary() {
	    this(new ArrayList<ItineraryItem>(),true);
	}
	
	/**
	 * @param itinerary
	 */
	public Itinerary(ArrayList<ItineraryItem> itinerary) {
		this(itinerary, true);
	}
	
	public void add(ItineraryItem item) {
		observableList.add(item);
	}
	
	public void remove(ItineraryItem item) {
		observableList.remove(item);
	}
	
	/**
	 * @param itinerary
	 */
	public Itinerary(ArrayList<ItineraryItem> itinerary, boolean loop) {
		this.itinerary = itinerary;
		this.loop = loop;
		observableList = FXCollections.observableList(itinerary);
		curPos.addListener(    		   
				(observable, oldvalue, newValue) ->{
    		    	itinerary.forEach((itinItem)->{
    		    		itinItem.setNextDestination(false);
    		    	});
    		    	itinerary.get(newValue.intValue()).setNextDestination(true);
    		    });
	}

	
	/**
	 * Sets next destination
	 * @return <b>true</b> if destination exists 
	 * <br><b>false</b> if vehicle reached the end of itinerary and loop is deactivated
	 */
	public boolean setNextDestination() {
		if(curPos.get()+1 >= itinerary.size()) {
			if(loop) {
				curPos.set(0);
				return true;
			} else {
				return false;
			}
		} else {
			curPos.set(curPos.get()+1);
			return true;
		}
	}
	
	public City getDestination() {
		return itinerary.get(curPos.get()).getDestination();
	}
	
	public Resource[] getLoad() {
		return itinerary.get(curPos.get()).getLoad();
	}
	
	public void setPos(int pos) {
		
//		if(0 <= pos && pos < itinerary.size()) {
			curPos.set(pos);
//		} else {
//			System.err.println("Invalid itineraryPosition: "+pos);
//		}
	}
	
	public IntegerProperty curPosProperty() {
		return curPos;
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public ArrayList<ItineraryItem> getItinerary() {
		return itinerary;
	}

	public ObservableList<ItineraryItem> getObservableItinerary(){
		return observableList;
	}
	
	public boolean getWaitForFull() {
		return itinerary.get(curPos.get()).getWaitForFull();
	}
	
	public boolean isLoop() {
		return loop;
	}

	public boolean isEmpty() {
		return itinerary.isEmpty();
	}
	
//    public static Callback<Driver, Observable[]> extractor() {
//        return new Callback<Driver, Observable[]>() {
//            @Override
//            public Observable[] call(ItineraryItem itinerary) {
//                return new Observable[]{driver.name, driver.salary, driver.time};
//            }
//        };
//    }
}
