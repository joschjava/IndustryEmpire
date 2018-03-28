package game;

import java.awt.Point;

import org.apache.commons.lang3.reflect.MethodUtils;

import gui.GuiVehicle;
import mainpack.Functions;
import objects.ResourceList;

public class Vehicle extends Position implements  TickListener{
	//TODO: Add Random vehicle names

	/** City vehicle is driving to or located at */
	private City curCity = null;
	private boolean driving = false;
	/** Destinations to drive to with in and output */
	Itinerary itinerary = null;
	private double distToLocation = 0; 
	private Point position;
	private int id = -1;
	private static int idCounter = 0;
	private VehicleSpecs specs;
	private ResourceList load = new ResourceList();
	private boolean loadingDone = true;
	private double difX;
	private double difY;
	private GuiVehicle guiObject;
	
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
		boolean activeNextDestination = itinerary.setNextDestination();
		if(activeNextDestination) {
			if(curCity != null) {
				curCity.removeVehicle(this);
				curCity = null;
			}
			curCity = itinerary.getDestination();	
			driving = true;
			loadingDone = true;
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
			driving = false;
			loadingDone = false;
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
	
	private void load() {
		Resource[] input2 = itinerary.getInput();
		if(input2 != null) {
			for (int i = 0; i < input2.length; i++) {
				System.out.println(input2[i]);
			}
		}
		System.out.println("Vehicle "+id+" loads in "+curCity);
		boolean full = true;
		Resource[] input = itinerary.getInput();
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
		Resource[] output = itinerary.getOutput();
		if(output != null) { 
			for(Resource res: output) {
				Resource vehChgResource = load.chgResourceAmountBy(res, ResourceList.MINUS);
				curCity.chgResource(vehChgResource, City.PLUS);
			}
		}
		
		if(!itinerary.getWaitForFull()) { // If vehicle should wait for full
			loadingDone = true;
		} else if(full) { 		// Vehicle waits for full, is it satisfied?
			loadingDone = true;
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
	
	public boolean isDriving() {
		return driving;
	}
	
	public City getCity() {
		return curCity;
	}
	
	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
		itinerary.setPos(-1);
	}

	@Override
	public String toString() {
		return "Vehicle "+id+": "+getX()+":"+getY()+", "+curCity+", Dif:"+Math.round(difX*100)/100.0+","+Math.round(difY*100)/100.0+", Driving: "+driving;
	}
	
	public void printToMatlab() {
		System.out.println("car("+Game.getTick()+",:) = ["+getX()+" "+getY()+"];");
	}
	
	@Override
	public void onTick() {
		if(driving) {
			drive();
		} else {
			if(!loadingDone) {
				load();
			} else {
				driveToNextItineraryItem();	
			}
		}
	}
	
}
