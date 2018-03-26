package game;

import org.apache.commons.lang3.RandomUtils;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import mainpack.Const;
import mainpack.Functions;

public class Driver {

	SimpleStringProperty name;
	SimpleIntegerProperty salary;
	/** Time that driver is on the market */
	SimpleIntegerProperty time = new SimpleIntegerProperty(-1);
	
	/** Minimum time a driver is on the market */
	private final int MIN_DRIVER_TIME = Const.MIN_DRIVER_TIME;
	
	/** Maximum time a driver is on the market */
	private final int MAX_DRIVER_TIME = Const.MAX_DRIVER_TIME;
	
	/** Minimum basic salary of a driver (might be higher or lower due to driver qualifications) */
	private final int MIN_DRIVER_SALARY = Const.MIN_DRIVER_SALARY;
	
	/** Maximum basic salary of a driver (might be higher or lower due to driver qualifications) */
	private final int MAX_DRIVER_SALARY = Const.MAX_DRIVER_SALARY;
	
	
	
	
	/**
	 * Create new Driver
	 * @param minTime Minimum Time driver is on the market
	 * @param maxTime Maximum Time driver is on the market
	 */
	Driver(){
		name = new SimpleStringProperty(Functions.generateName());
		salary = new SimpleIntegerProperty(RandomUtils.nextInt(MIN_DRIVER_SALARY, MAX_DRIVER_SALARY));
		time = new SimpleIntegerProperty(RandomUtils.nextInt(MIN_DRIVER_TIME, MAX_DRIVER_TIME));
	}

    public static Callback<Driver, Observable[]> extractor() {
        return new Callback<Driver, Observable[]>() {
            @Override
            public Observable[] call(Driver driver) {
                return new Observable[]{driver.name, driver.salary, driver.time};
            }
        };
    }

	@Override
	public String toString() {
		return getName() + ", "+getSalary()+ " €, Hired in: "+getTime();
	}

	
	
	
	public String getName() {
		return name.get();
	}


	public void setName(String name) {
		this.name.set(name);
	}


	public int getSalary() {
		return salary.get();
	}


	public void setSalary(int salary) {
		this.salary.set(salary);
	}

	public int getTime() {
		return time.get();
	}
	
	/**
	 * Decreases time driver is on market, returns true if time is up
	 * @return true if time is up (=0), false otherwise
	 */
	public boolean decTime() {
		time.set(time.get()-1); 	// time--;
	
		if(time.get() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
