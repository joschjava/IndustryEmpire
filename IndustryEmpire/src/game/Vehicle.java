package game;

import java.util.ArrayList;

import gui.GuiVehicle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import mainpack.Functions;
import objects.ResourceList;

public class Vehicle extends Position implements  TickListener{
	//TODO: Add Random vehicle names

	/** City vehicle is driving to or located at */
	private City curCity = null;
	/** Destinations to drive to with in and output */
	Itinerary itinerary = null;
	private double distToLocation = 0; 
	private int id = -1;
	private static int idCounter = 0;
	private VehicleSpecs specs;
	private ResourceList load = new ResourceList();
	private double difX;
	private double difY;
	private IntegerProperty status = new SimpleIntegerProperty(LOADING);
	private DoubleProperty x = new SimpleDoubleProperty();
	private DoubleProperty y = new SimpleDoubleProperty();
	private DoubleProperty angle = new SimpleDoubleProperty();
	public Timeline driveTimeline = new Timeline();
	
	public static final int DRIVING = 0;
	public static final int UNLOADING = 1;
	public static final int LOADING = 2;
	public static final int IDLE = 3;
	
	
	
	public Vehicle(VehicleSpecs specs, City city){
		super();
		setLocation(city);
		this.specs = specs;
		id = idCounter++;
		Game.getInstance().addListener(this);
	}
		
	@Override
	public double getX() {
		return x.get();
	}

	@Override
	public double getY() {
		return y.get();
	}
	
	public void setX(double x) {
		this.x.set(x);
	}
	
	public void setY(double y) {
		this.y.set(y);
	}
	
	public DoubleProperty xProperty() {
		return x;
	}
	
	public DoubleProperty yProperty() {
		return y;
	}
	
	public DoubleProperty angleProperty() {
		return angle;
	}
	
	public void setAngle(double value) {
		angle.set(value);
	}
	
	public double getAngle() {
		return angle.get();
	}
	
	public void setLocation(City city) {
		curCity = city;
		city.addVehicle(this);
		x.set(city.getX());
		y.set(city.getY());
	}

	public void driveToNextItineraryItem() {
		boolean nextDest = itinerary.setNextDestination();
		if(nextDest) {
			if(curCity != null) {
				curCity.removeVehicle(this);
				curCity = null;
			}
			curCity = itinerary.getDestination();	
			status.set(DRIVING);
			System.out.println("Vehicle "+id+" drives to "+curCity);
		}
	}
	
	public IntegerProperty statusProperty() {
		return status;
	}
	
	private void drive() {
		if(driveTimeline.getStatus() == Animation.Status.STOPPED) {
			distToLocation = Functions.getDistance(this,curCity);
			int timeNeeded = (int) Math.ceil(distToLocation / (double) specs.getSpeed() * Game.tickInterval);
			difX = curCity.getX() - this.getX();
			difY = curCity.getY() - this.getY();
			angle.set(getAngleDeg(difX, difY));
	        KeyValue keyValueX = new KeyValue(xProperty(), curCity.getX());
	        KeyValue keyValueY = new KeyValue(yProperty(), curCity.getY());
	        System.out.println(xProperty().get() + ":" + yProperty());
	        
			driveTimeline =   new Timeline(
					new KeyFrame(Duration.millis(0)),
					new KeyFrame(Duration.millis(timeNeeded), keyValueX, keyValueY)
					);
			driveTimeline.setOnFinished(e -> arriveInCity());
			driveTimeline.play();
		}
	}

	private void arriveInCity() {
		System.out.println("Vehicle "+id+" arrived in "+curCity);
		//Arrived
		setLocation(curCity);
		status.set(UNLOADING);
		Game.getInstance().addListener(this);
	}
	
	/**
	 * Calculates angle from x and y, <b>Adds 360 to indicate that image needs to flipped</b>
	 * @param x
	 * @param y
	 * @return
	 */
	private double getAngleDeg(double x, double y) {
		double angleDeg = 0;
		if(x == 0) {
			if(y>0) {
				angleDeg = 0;
			} else if(y<0) {
				angleDeg = 90;
			} else {
				angleDeg = 0;
			}
		} else {
			double angleRad = Math.atan(y/x);
			angleDeg = angleRad*180/Math.PI;
			if(x<0) {
				angleDeg += 360;
			}
		}
		return angleDeg;
	}
	
	private void unload() {
		System.out.println("Vehicle "+id+" unloads in "+curCity);	
		ArrayList<Resource> allRes = load.getAllResources();
		allRes.forEach((resource)-> {
			curCity.chgResource(resource, ResourceList.PLUS);
		});
		load.clearList();
		status.set(LOADING);
	}
	
	private void load() {
		//TODO: initialise variables once, then only iterate for loop
		System.out.println("Vehicle "+id+" loads in "+curCity);
		boolean full = true;
		Resource[] input = itinerary.getLoad();
		if(input != null) { 
			for(Resource res: input) {
				//TODO: This takes a lot of resources, dude!
				//Create static resource?
				Resource leftRes = res.getCopy();
				Resource vehicleRes = load.getElementByResSpec(leftRes.getSpec());
				double leftAmount = res.getAmount()-vehicleRes.getAmount();
				leftRes.setAmount(leftAmount, false);
				
				Resource cityChgResource = curCity.chgResource(leftRes, ResourceList.MINUS);
				load.chgResourceAmountBy(cityChgResource, ResourceList.PLUS);
				
				//Check if enough Resources are loaded to vehicle as specified in Itinerary
				boolean partFull = vehicleRes.isEqualAmount(res);
				if(!partFull) {
					full = false;
				}
			}
		}
		
		if(!itinerary.getWaitForFull()) { // If vehicle should wait for full
			status.set(IDLE);
		} else if(full) { 		// Vehicle waits for full, is it satisfied?
			status.set(IDLE);
		}
	}
	
	public void printLoad() {
		System.out.println(this);
		if(load.isEmpty()) {
			System.out.println("\t(empty) ");
		} else {
			load.stream().forEach((res) -> System.out.println("\t"+res));
		}
		System.out.println();
	}
	
	public City getCity() {
		return curCity;
	}
	
	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	@Override
	public String toString() {
		return "Vehicle "+id+": "+getX()+":"+getY()+", "+curCity+", Dif:"+Math.round(difX*100)/100.0+","+Math.round(difY*100)/100.0+", status: "+status;
	}
	
	@Override
	public void onTick() {
		switch(status.intValue()) {
			case DRIVING:
				drive();
				break;
				
			case UNLOADING:
				unload();
				break;
				
			case LOADING:
				load();
				break;
				
			case IDLE:
				driveToNextItineraryItem();	
				break;
		}
	}


	
}
