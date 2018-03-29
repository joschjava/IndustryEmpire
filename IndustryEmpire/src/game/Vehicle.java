package game;

import java.awt.Point;
import java.util.ArrayList;

import gui.GuiVehicle;
import mainpack.Functions;
import objects.ResourceList;

public class Vehicle extends Position implements  TickListener{
	//TODO: Add Random vehicle names

	/** City vehicle is driving to or located at */
	private City curCity = null;
	/** Destinations to drive to with in and output */
	Itinerary itinerary = null;
	private double distToLocation = 0; 
	private Point position;
	private int id = -1;
	private static int idCounter = 0;
	private VehicleSpecs specs;
	private ResourceList load = new ResourceList();
	private double difX;
	private double difY;
	private GuiVehicle guiObject;
	private int status = LOADING;
	
	
	private static final int DRIVING = 0;
	private static final int UNLOADING = 1;
	private static final int LOADING = 2;
	private static final int IDLE = 3;
	
	public Vehicle(VehicleSpecs specs, City city){
		super();
		setLocation(city);
		this.specs = specs;
		id = idCounter++;
		Game.getInstance().addListener(this);
	}
		
	public void setObserver(GuiVehicle guiObject) {
		this.guiObject = guiObject;
	}
	
	@Override
	public double getX() {
		return position.getX();
	}

	@Override
	public double getY() {
		return position.getY();
	}
	
	public Point getPosition() {
		return position;
	}
	
	private void changePositionBy(double x, double y, double angleRad) {
		position.setLocation(getX()+x, getY()+y);
		updateGuiPosition(angleRad);
	}
	
	public void setLocation(City city) {
		curCity = city;
		city.addVehicle(this);
		position = city.getPositionClone();
		updateGuiPosition(0);
	}

	private void updateGuiPosition(double angleDeg) {
		if(guiObject != null) {
			guiObject.updatePosition(position, angleDeg);
		}
	}
	
	public void driveToNextItineraryItem() {
		boolean nextDest = itinerary.setNextDestination();
		if(nextDest) {
			if(curCity != null) {
				curCity.removeVehicle(this);
				curCity = null;
			}
			curCity = itinerary.getDestination();	
			status = DRIVING;
			System.out.println("Vehicle "+id+" drives to "+curCity);
		}
	}
	
	private void drive() {
		distToLocation = Functions.getDistance(this,curCity);
		if(distToLocation > specs.getSpeed()) {
			difX = (curCity.getX() - this.getX())/distToLocation;
			difY = (curCity.getY() - this.getY() )/distToLocation;
//			System.out.println(difX+"/"+difY+" -> "+difY/difX);
			double angleRad = getAngleDeg(difX, difY);
			changePositionBy(difX*specs.getSpeed(), difY*specs.getSpeed(), angleRad);
//			System.out.println(this);
		} else {
			System.out.println("Vehicle "+id+" arrived in "+curCity);
			//Arrived
			setLocation(curCity);
			status = UNLOADING;
		}
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
			curCity.chgResource(resource, City.PLUS);
		});
		load.clear();
		status = LOADING;
	}
	
	private void load() {
		System.out.println("Vehicle "+id+" loads in "+curCity);
		boolean full = true;
		Resource[] input = itinerary.getLoad();
		if(input != null) { 
			for(Resource res: input) {
				Resource cityChgResource = curCity.chgResource(res, City.MINUS);
				load.chgResourceAmountBy(cityChgResource, ResourceList.PLUS);
				Resource vehicleRes = load.getElementByResSpec(res.getSpec());
				//Check if enough Resources are loaded to vehicle as specified in Itinerary
				boolean partFull = vehicleRes.isEqualAmount(res);
				if(!partFull) {
					full = false;
				}
			}
		}
		
		if(!itinerary.getWaitForFull()) { // If vehicle should wait for full
			status = IDLE;
		} else if(full) { 		// Vehicle waits for full, is it satisfied?
			status = IDLE;
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
		switch(status) {
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
