package game;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.commons.lang3.RandomUtils;

import javafx.beans.Observable;
import javafx.util.Callback;
import mainpack.Balance;
import objects.HistoryObject;
import objects.ResourceList;

public class City extends Position{

	/** Name of the city */
	private String name;
	
	/** Position of city */
	private Point position;
	
	/** List of buildings in this city */
	ArrayList<Building> buildings = new ArrayList<Building>();
	
	/** List of connections to other cities */
	ArrayList<Connection> connections = new ArrayList<Connection>();
	
	/** List of vehicles to other cities */
	ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	/** List of all resources contained in this city */
	private ResourceList resources = new ResourceList();
	
	public static final int PLUS = ResourceList.PLUS;
	public static final int MINUS = ResourceList.MINUS;
	
	public static ArrayList<City> cityList = new ArrayList<City>();
	
	/**
	 * Creates city with random coordinates
	 * @param name
	 */
	public City(String name){
		super();
		int x = RandomUtils.nextInt(0, Balance.MAPSIZE_X);
		int y = RandomUtils.nextInt(0, Balance.MAPSIZE_Y);
		init(name, x, y);
		cityList.add(this);
	}

	
	/**
	 * Creates city with given coordinates
	 * @param name
	 */
	public City(String name, int x, int y){
		super();
		if(0<=x && x<=Balance.MAPSIZE_X && 
		   0<=y && y<=Balance.MAPSIZE_Y) {
			init(name, x, y);
		} else {
			System.err.println("City "+name+" is outside of map");
		}
	}
	
	private void init(String name, int x, int y) {
		position = new Point(x,y);
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "City [" + name + ", "+getX()+":"+getY()+"]";
	}

	public void addConnection(Connection connection) {
		if(!connections.contains(connection)) {
			connections.add(connection);
		} else {
			System.err.println("Cities already connected, might be program error");
		}
	}
	
	public Point getPositionClone() {
		return new Point(position);
	}
	
	public void addBuilding(Building building, ResourceSpec[] resSpecs) {
		buildings.add(building);
	}
	
	/**
	 * Adds a ResourceSpecification to the city 
	 * @param resSpec ResourceSpec of Resource to be added
	 * @return Created resource, which you can change the amount of
	 */
//	public Resource addToResourceList(ResourceSpec resSpec) {
//		Resource res = new Resource(resSpec);
//		resources.put(resSpec.getResId(), res);
//		return res;
//	}
	
//	public void chgResource(Resource resSpec, double change) {
//		Resource res = resources.get(resSpec.getResId());
//		if(res == null) {
//			res = addToResourceList(resSpec);
//		}
//		res.chgResource(change);
//	}
	
	
	/**
	 *  Changes resource amount provided in the given resource
	 * @param res
	 * @param operation Use PLUS or MINUS to add or Subtract resource
	 * @return Resources that have been changed
	 */
	public Resource chgResource(Resource res, int operation) {
		return resources.chgResourceAmountBy(res, operation);
	}
	
	/**
	 *  Changes resource amount provided in the given resource
	 * @param res
	 * @param operation Use PLUS or MINUS to add or Subtract resource
	 */
	public void chgResource(ResourceSpec resSpec, double amount) {
		int operation = 0;
		if(amount >= 0) {
			operation = PLUS;
		} else {
			operation = MINUS;
		}
		Resource res = new Resource(resSpec, Math.abs(amount));
		chgResource(res, operation);
	}
	
	/**
	 * Only use for debugging
	 * @deprecated
	*/
	public void setResourceAmount(ResourceSpec resSpec, double value) {
		Resource res = new Resource(resSpec, value);
		resources.setResourceAmount(res);
	}
	
	public ArrayList<HistoryObject> getHistory(ResourceSpec resSpec) {
		Resource res = resources.getElementByResSpec(resSpec);
		return res.getHistory();
	}
	
	public double getResourceAmount(ResourceSpec resSpec) {
		return resources.getResAmountByResSpec(resSpec);
	}
	
	public double getX() {
		return position.getX();
	}
	
	public double getY() {
		return position.getY();
	}
	
	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}
	
	public void removeVehicle(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}
	
	public void printResources() {
		System.out.println(getName());
		if(!resources.isEmpty()) {
			resources.stream()
			.forEach((res) -> System.out.println("\t"+res));

		} else {
			System.out.println("\t(empty)");  
		}
	}

	public String getName() {
		return name;
	}
	
	public static ArrayList<City> getAllCities(){
		return cityList;
	}
}
