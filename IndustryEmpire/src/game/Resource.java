package game;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import mainpack.Const;
import mainpack.Functions;
import objects.HistoryObject;

public class Resource {

	private ResourceSpec spec;
	private DoubleProperty amount = new SimpleDoubleProperty();
	
	private HistoryManager history = new HistoryManager();
	
	public static final int INC = 1;
	public static final int DEC = -1;
	
	public Resource(ResourceSpec spec){
		this(spec, 0.0);
	}
	
	public Resource(ResourceSpec spec, double amount){
		this.spec = spec;
		setAmount(amount);
	}

	public double getAmount() {
		return amount.get();
	}
	
	public DoubleProperty AmountProperty() {
		return amount;
	}
	
	public void setAmount(double amount) {
		history.addNumber(amount);
		this.amount.set(amount);
	}
	
	public ResourceSpec getSpec() {
		return spec;
	}
	
	public int getResourceId() {
		return spec.getResId();
	}
	

	/**
	 * Increase or decrease value 
	 * @param change
	 */
	public void chgResource(double change) {
		setAmount(amount.get()+change);
	}
	

	@Override
	public String toString() {
		return spec + " = " + amount;
	}
	
	/**
	 * Checks if a given resource is 0 with a tolerance (Balance.TOLERANCE)
	 * @param res Resource to be checked
	 * @return true if zero, false if not
	 */
	public static boolean isZero(Resource res) {
		double amount = res.getAmount();
		return Functions.isZero(amount);
	}
	
	/**
	 * Checks if two resources have the same amount with a tolerance (Balance.TOLERANCE)
	 * @param res Resource to be checked
	 * @return true if zero, false if not
	 */
	public boolean isEqualAmount(Resource res) {
		double tolerance = Const.TOLERANCE;
		if(Math.abs(res.getAmount()-getAmount()) <= tolerance ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if Resource has the same
	 * @param res
	 * @return
	 */
	public boolean isEqualResource(Resource res) {
		if(isEqualAmount(res) && this.getSpec() == res.getSpec()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Resource getCopy() {
		return new Resource(getSpec(), getAmount());
	}
	
	public ArrayList<HistoryObject> getHistory(){
		return history.getHistory();
	}
	
}
