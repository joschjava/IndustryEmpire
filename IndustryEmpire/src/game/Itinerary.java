package game;

import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class Itinerary {

	private ArrayList<ItineraryItem> itinerary = new ArrayList<ItineraryItem>();
	private ObservableList<ItineraryItem> observableList;
	private boolean loop = true;
	private int curPos = 0;
	
	/**
	 * @param itineraryView
	 */
	public Itinerary() {
	    observableList = FXCollections.observableList(itinerary);
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
	}

	public boolean setNextDestination() {
		if(curPos+1 >= itinerary.size()) {
			if(loop) {
				curPos = 0;
				return true;
			} else {
				return false;
			}
		} else {
			curPos++;
			return true;
		}
	}
	
	public City getDestination() {
		return itinerary.get(curPos).getDestination();
	}
	
	public Resource[] getLoad() {
		return itinerary.get(curPos).getLoad();
	}
	
	public void setPos(int pos) {
		
//		if(0 <= pos && pos < itinerary.size()) {
			curPos = pos;
//		} else {
//			System.err.println("Invalid itineraryPosition: "+pos);
//		}
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
		return itinerary.get(curPos).getWaitForFull();
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
